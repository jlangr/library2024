package controller;

import domain.core.Holding;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public record HoldingResponse(
   String author,
   String title,
   String year,
   String format,
   Date dateDue,
   String barcode,
   Integer copyNumber,
   Date dateCheckedOut,
   Date dateLastCheckedIn,
   boolean isAvailable
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public HoldingResponse(Holding holding) {
        this(holding.getMaterial().author(),
           holding.getMaterial().title(),
           holding.getMaterial().year(),
           holding.getMaterial().materialType().toString(),
           holding.dateDue(),
           holding.getBarcode(),
           holding.getCopyNumber(),
           holding.dateCheckedOut(),
           holding.dateLastCheckedIn(),
           holding.isAvailable());
    }
}
