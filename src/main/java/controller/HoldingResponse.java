package controller;

import domain.core.Holding;

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
    private static final long serialVersionUID = 1L;
    public HoldingResponse(Holding holding) {
        this(holding.getMaterial().getAuthor(),
           holding.getMaterial().getTitle(),
           holding.getMaterial().getYear(),
           holding.getMaterial().getFormat().toString(),
           holding.dateDue(),
           holding.getBarcode(),
           holding.getCopyNumber(),
           holding.dateCheckedOut(),
           holding.dateLastCheckedIn(),
           holding.isAvailable());
    }
}
