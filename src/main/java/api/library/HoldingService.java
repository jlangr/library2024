package api.library;

import com.loc.material.api.Material;
import domain.core.*;
import persistence.PatronNotFoundException;
import persistence.PatronStore;

import java.util.Date;
import java.util.List;

public class HoldingService {
   private final Catalog catalog = new Catalog();

   public String add(String sourceId, String branchId) {
      var branch = findBranch(branchId);
      var holding = new Holding(retrieveMaterialDetails(sourceId), branch);
      catalog.add(holding);
      return holding.getBarcode();
   }

   private Branch findBranch(String branchId) {
      var branch = new BranchService().find(branchId);
      if (branch == null)
         throw new BranchNotFoundException("Branch not found: " + branchId);
      return branch;
   }

   private Material retrieveMaterialDetails(String sourceId) {
      var material =
         ClassificationApiFactory.getService().retrieveMaterial(sourceId);
      if (material == null)
         throw new InvalidSourceIdException("cannot retrieve material with source ID " + sourceId);
      return material;
   }

   public boolean isAvailable(String barCode) {
      var holding = find(barCode);
      if (holding == null)
         throw new HoldingNotFoundException();
      return holding.isAvailable();
   }

   public Holding find(String barCode) {
      return catalog.find(barCode);
   }

   public List<Holding> findByBranch(String branchScanCode) {
      return catalog.findByBranch(branchScanCode);
   }

   public void transfer(String barcode, String branchScanCode) {
      var holding = find(barcode);
      if (holding == null)
         throw new HoldingNotFoundException();
      var branch = new BranchService().find(branchScanCode);
      holding.transfer(branch);
   }

   public Date dateDue(String barCode) {
      var holding = find(barCode);
      if (holding == null)
         throw new HoldingNotFoundException();
      return holding.dateDue();
   }

   public void checkOut(String patronId, String barCode, Date date) {
      var holding = find(barCode);
      if (holding == null)
         throw new HoldingNotFoundException();
      if (!holding.isAvailable())
         throw new HoldingAlreadyCheckedOutException();
      holding.checkOut(date);

      var patronAccess = new PatronStore();
      var patron = patronAccess.find(patronId);
      patronAccess.addHoldingToPatron(patron, holding);
   }

   public int checkIn(String barCode, Date date, String branchScanCode) {
      var branch = new BranchService().find(branchScanCode);
      var holding = find(barCode);
      if (holding == null)
         throw new HoldingNotFoundException();

      holding.checkIn(date, branch);

      var foundPatron = findPatronWith(holding);
      removeBookFromPatron(foundPatron, holding);
      // ...

      if (holding.isLate()) {
         foundPatron.addFine(calculateLateFine(holding));
         return holding.daysLate();
      }
      return 0;
   }

   private int calculateLateFine(Holding holding) {
      var daysLate = holding.daysLate();
      var fineBasis = holding.getMaterial().getFormat().getDailyFine();

      var fine = 0;
      switch (holding.getMaterial().getFormat()) {
         case BOOK, NEW_RELEASE_DVD:
            fine = fineBasis * daysLate;
            break;
         case AUDIO_CASSETTE, VINYL_RECORDING, MICRO_FICHE, AUDIO_CD, SOFTWARE_CD, DVD, BLU_RAY, VIDEO_CASSETTE:
            fine = Math.min(1000, 100 + fineBasis * daysLate);
            break;
         default:
            break;
      }
      return fine;
   }

   private Patron findPatronWith(Holding holding) {
      return new PatronService().allPatrons().stream()
         .filter(patron -> hasCheckedOut(patron, holding))
         .findFirst()
         .orElse(null);
   }

   private boolean hasCheckedOut(Patron patron, Holding holding) {
      return patron.holdingMap().holdings().stream()
         .anyMatch(patronHolding -> patronHolding.getBarcode().equals(holding.getBarcode()));
   }

   private void removeBookFromPatron(Patron foundPatron, Holding holding) {
      if (foundPatron == null) throw new PatronNotFoundException();
      foundPatron.remove(holding);
   }
}