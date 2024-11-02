package domain.core;

import static java.lang.String.format;

public record HoldingBarcode(String barcode) {
   public HoldingBarcode(String classification, int copyNumber) {
      this(format("%s:%s", classification, copyNumber));
   }

   public String getClassification() {
      return splitOnColon(barcode)[0];
   }

   public int getCopyNumber() {
      var copy = splitOnColon(barcode)[1];
      return IntUtils.parsePositiveInt(copy);
   }

   private String[] splitOnColon(String barcode) {
      var barcodeParts = barcode.split(":");
      if (barcodeParts.length != 2)
         throw new IllegalArgumentException();
      return barcodeParts;
   }
}
