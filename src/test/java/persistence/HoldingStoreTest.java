package persistence;

import domain.core.Holding;
import domain.core.HoldingBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static testutil.CollectionsUtil.soleElement;

public class HoldingStoreTest {
    private HoldingStore store;
    private Holding savedHolding;

    @Before
    public void setUp() {
        HoldingStore.deleteAll();
        store = new HoldingStore();
        savedHolding = new HoldingBuilder().create();
        store.save(savedHolding);
    }

    @Test
    public void returnsAddedHoldings() {
        List<Holding> retrieved = store.findByClassification(classification(savedHolding));

        assertThat(soleElement(retrieved).getMaterial(), equalTo(savedHolding.getMaterial()));
    }

    private String classification(Holding holding) {
        return holding.getMaterial().getClassification();
    }

    @Test
    public void returnsNewInstanceOnRetrieval() {
        store = new HoldingStore();

        List<Holding> retrieved = store.findByClassification(classification(savedHolding));

        assertThat(soleElement(retrieved), not(sameInstance(savedHolding)));
    }

    @Test
    public void findByBarCodeReturnsMatchingHolding() {
        Holding holding = store.findByBarcode(savedHolding.getBarcode());

        assertThat(holding.getBarcode(), equalTo(savedHolding.getBarcode()));
    }

    @Test
    public void findBarCodeNotFound() {
        assertThat(store.findByBarcode("nonexistent barcode:1"), nullValue());
    }
}