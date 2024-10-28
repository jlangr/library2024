package api.library;

import com.loc.material.api.Material;
import domain.core.*;
import persistence.PatronStore;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HoldingService {
    private Catalog catalog = new Catalog();

    public String add(String sourceId, String branchId) {
        Branch branch = findBranch(branchId);
        Holding holding = new Holding(retrieveMaterialDetails(sourceId), branch);
        catalog.add(holding);
        return holding.getBarcode();
    }

    private Material retrieveMaterialDetails(String sourceId) {
        Material material =
                ClassificationApiFactory.getService().retrieveMaterial(sourceId);
        if (material == null)
            throw new InvalidSourceIdException("cannot retrieve material with source ID " + sourceId);
        return material;
    }

    private Branch findBranch(String branchId) {
        Branch branch = new BranchService().find(branchId);
        if (branch == null)
            throw new BranchNotFoundException("Branch not found: " + branchId);
        return branch;
    }

    public boolean isAvailable(String barCode) {
        Holding holding = find(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        return holding.isAvailable();
    }

    public HoldingMap allHoldings() {
        HoldingMap stack = new HoldingMap();
        for (Holding holding : catalog)
            stack.add(holding);
        return stack;
    }

    public Holding find(String barCode) {
        return catalog.find(barCode);
    }

    public List<Holding> findByBranch(String branchScanCode) {
        return catalog.findByBranch(branchScanCode);
    }

    public void transfer(String barcode, String branchScanCode) {
        Holding holding = find(barcode);
        if (holding == null)
            throw new HoldingNotFoundException();
        Branch branch = new BranchService().find(branchScanCode);
        holding.transfer(branch);
    }

    public Date dateDue(String barCode) {
        Holding holding = find(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        return holding.dateDue();
    }

    public void checkOut(String patronId, String barCode, Date date) {
        Holding holding = find(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        if (!holding.isAvailable())
            throw new HoldingAlreadyCheckedOutException();
        holding.checkOut(date);

        PatronStore patronAccess = new PatronStore();
        Patron patron = patronAccess.find(patronId);
        patronAccess.addHoldingToPatron(patron, holding);
    }

    @SuppressWarnings("incomplete-switch")
    public int checkIn(String barCode, Date date, String branchScanCode) {
        Branch branch = new BranchService().find(branchScanCode);
        Holding hld = find(barCode);
        if (hld == null)
            throw new HoldingNotFoundException();

        // set the holding to returned status
        HoldingMap holdings = null;
        hld.checkIn(date, branch);

        // locate the patron with the checked out book
        // could introduce a patron reference ID in the holding...
        Patron f = null;
        for (Patron p : new PatronService().allPatrons()) {
            holdings = p.holdingMap();
            for (Holding patHld : holdings) {
                if (hld.getBarcode().equals(patHld.getBarcode()))
                    f = p;
            }
        }

        // remove the book from the patron
        f.remove(hld);

        // check for late returns
        boolean isLate = false;
        Calendar c = Calendar.getInstance();
        c.setTime(hld.dateDue());
        int d = Calendar.DAY_OF_YEAR;

        // check for last day in year
        if (c.get(d) > c.getActualMaximum(d)) {
            c.set(d, 1);
            c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
        }

        if (hld.dateLastCheckedIn().after(c.getTime())) // is it late?
            isLate = true;

        if (isLate) {
            int daysLate = hld.daysLate(); // calculate # of days past due
            int fineBasis = hld.getMaterial().getFormat().getDailyFine();
            switch (hld.getMaterial().getFormat()) {
                case Book:
                    f.addFine(fineBasis * daysLate);
                    break;
                case DVD:
                    int fine = Math.min(1000, 100 + fineBasis * daysLate);
                    f.addFine(fine);
                    break;
                case NewReleaseDVD:
                    f.addFine(fineBasis * daysLate);
                    break;
            }
            return daysLate;
        }
        return 0;
    }
}