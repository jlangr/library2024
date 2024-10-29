package controller;

import java.util.Date;

public record CheckoutRequest(String patronId, String holdingBarcode, Date checkoutDate) {
}
