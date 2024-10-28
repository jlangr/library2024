package controller;

import java.util.Date;

public record CheckinRequest(String holdingBarcode, Date checkinDate, String branchScanCode) {
}