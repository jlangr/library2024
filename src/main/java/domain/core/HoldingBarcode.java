package domain.core;

public class HoldingBarcode {
    public static final String BARCODE_SEPARATOR = ":";
    private String classification;
    private int copyNumber;

    public HoldingBarcode(String barcode) {
        extractClassificationAndCopy(barcode);
    }

    private void extractClassificationAndCopy(String barcode) {
        copyNumber = extractCopyNumber(barcode);
        classification = extractClassification(barcode);
    }

    public static int getCopyNumber(String barcode) {
        return new HoldingBarcode(barcode).getCopyNumber();
    }

    public static String getClassification(String barcode) {
        return new HoldingBarcode(barcode).getClassification();
    }

    public String getClassification() {
        return classification;
    }

    // TODO test
    public int getCopyNumber() {
        return copyNumber;
    }

    private int extractCopyNumber(String code) {
        var copy = splitOnColon(code)[1];
        return parsePositiveInt(copy);
    }

    private String extractClassification(String barcode) {
        return splitOnColon(barcode)[0];
    }

    private int parsePositiveInt(String text) {
        var number = parseInt(text);
        if (number < 1)
            throw new IllegalArgumentException();
        return number;
    }

    private int parseInt(String text) {
        try {
            return Integer.parseInt(text, 10);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private String[] splitOnColon(String barcode) {
        var barcodeParts = barcode.split(":");
        if (barcodeParts.length != 2)
            throw new IllegalArgumentException();
        return barcodeParts;
    }

    @Override
    public String toString() {
        return createCode(classification, copyNumber);
    }

    private String getBarcode() {
        return classification + BARCODE_SEPARATOR + copyNumber;
    }

    public static String createCode(String classification, int copyNumber) {
        return classification + BARCODE_SEPARATOR + copyNumber;
    }

    @Override
    public int hashCode() {
        return getBarcode().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass())
            return false;
        var that = (HoldingBarcode) object;
        return getBarcode().equals(that.getBarcode());
    }
}
