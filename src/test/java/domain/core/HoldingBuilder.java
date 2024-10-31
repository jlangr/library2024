package domain.core;

import com.loc.material.api.Material;
import com.loc.material.api.MaterialType;

public class HoldingBuilder {
    private String classification = "";
    private int copyNumber = 1;
    private Branch branch = Branch.CHECKED_OUT;

    public HoldingBuilder withClassification(String classification) {
        this.classification = classification;
        return this;
    }

    public HoldingBuilder withBarcode(String barcode) {
        this.copyNumber = HoldingBarcode.getCopyNumber(barcode);
        this.classification = HoldingBarcode.getClassification(barcode);
        return this;
    }

    public Holding create() {
        var material = new Material("1", "", "1", classification, "");
        return new Holding(material, branch, copyNumber);
    }
}