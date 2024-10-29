package domain.core;

import persistence.HoldingStore;

import java.util.Iterator;
import java.util.List;

public class Catalog implements Iterable<Holding> {
    private final HoldingStore access = new HoldingStore();

    public int size() {
        return access.size();
    }

    public List<Holding> findAll(String classification) {
        return access.findByClassification(classification);
    }

    public String add(Holding holding) {
        List<Holding> existing = access.findByClassification(holding.getMaterial()
                .getClassification());
        if (!existing.isEmpty())
            holding.setCopyNumber(existing.size() + 1);
        access.save(holding);
        return holding.getBarcode();
    }

    @Override
    public Iterator<Holding> iterator() {
        return access.iterator();
    }

    public Holding find(String barCode) {
        return access.findByBarcode(barCode);
    }

    public List<Holding> findByBranch(String branchScanCode) {
        return access.findByBranch(branchScanCode);
    }
}