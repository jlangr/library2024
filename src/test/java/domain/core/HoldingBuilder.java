package domain.core;

import com.loc.material.api.Material;

public class HoldingBuilder {
   private String classification = "";
   private int copyNumber = 1;
   private Branch branch = Branch.CHECKED_OUT;

   public HoldingBuilder withClassification(String classification) {
      this.classification = classification;
      return this;
   }

   public HoldingBuilder withBarcode(String barcodeText) {
      var barcode= new HoldingBarcode(barcodeText);
      this.copyNumber = barcode.getCopyNumber();
      this.classification = barcode.getClassification();
      return this;
   }

   public Holding create() {
      var material = new Material("1", "", "1", classification, "");
      return new Holding(material, branch, copyNumber);
   }
}