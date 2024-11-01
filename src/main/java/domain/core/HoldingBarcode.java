package domain.core;

public record HoldingBarcode(String barcode) {
   public static final String BARCODE_SEPARATOR = ":";

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

   // TODO
   public static String createCode(String classification, int copyNumber) {
      return classification + BARCODE_SEPARATOR + copyNumber;
   }
}
