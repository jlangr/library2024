package controller;

import domain.core.Holding;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public record HoldingResponse(
   String author,
   String title,
   String year,
   int format,
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
        this(holding.getMaterial().getAuthor(),
           holding.getMaterial().getTitle(),
           holding.getMaterial().getYear(),
           holding.getMaterial().getFormat(),
           holding.dateDue(),
           holding.getBarcode(),
           holding.getCopyNumber(),
           holding.dateCheckedOut(),
           holding.dateLastCheckedIn(),
           holding.isAvailable());
    }
}
