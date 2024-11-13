package persistence;

import domain.core.Holding;
import util.MultiMap;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class HoldingStore implements Iterable<Holding> {
    private static final MultiMap<String, Holding> holdings = new MultiMap<>();

    public static void deleteAll() {
        holdings.clear();
    }

    public static boolean isEmpty() {
        return holdings.isEmpty();
    }

    public void save(Holding holding) {
        holdings.put(holding.getMaterial().classification(), copy(holding));
    }

    private Holding copy(Holding holding) {
        return new Holding(holding.getMaterial(), holding.getBranch(),
                holding.getCopyNumber());
    }

    public List<Holding> findByClassification(String classification) {
        var results = holdings.get(classification);
        if (results == null)
            return new ArrayList<>();
        return results;
    }

    public Holding findByBarcode(String barCode) {
        var results = holdings.get(classificationFrom(barCode));
        if (results == null)
            return null;
        return results.stream()
           .filter(holding -> holding.getBarcode().equals(barCode))
           .findFirst()
           .orElse(null);
    }

    private String classificationFrom(String barCode) {
        var index = barCode.indexOf(Holding.BARCODE_SEPARATOR);
        return barCode.substring(0, index);
    }

    public int size() {
        return holdings.size();
    }

    @Override
    @Nonnull
    public Iterator<Holding> iterator() {
        return holdings.values().iterator();
    }

    public List<Holding> findByBranch(String branchScanCode) {
        return StreamSupport.stream(this.spliterator(), false)
           .filter(holding -> holding.getBranch().getScanCode().equals(branchScanCode))
           .toList();
    }
}
