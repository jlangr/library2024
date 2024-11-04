package controller;

import api.library.HoldingService;
import domain.core.HoldingAlreadyCheckedOutException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holdings")
public class HoldingController {
   private final HoldingService service = new HoldingService();

   @PostMapping
   public String addBookHolding(@RequestBody AddHoldingRequest request) {
      return service.add(request.sourceId(), request.branchScanCode());
   }

   @PostMapping(value = "/checkout")
   public void checkout(@RequestBody CheckoutRequest request, HttpServletResponse response) {
      try {
         service.checkOut(request.patronId(), request.holdingBarcode(), request.checkoutDate());
      } catch (HoldingAlreadyCheckedOutException exception) {
         response.setStatus(409);
      }
   }

   @PostMapping(value = "/checkin")
   public void checkin(@RequestBody CheckinRequest request) {
      service.checkIn(request.holdingBarcode(), request.checkinDate(), request.branchScanCode());
   }

   @GetMapping
   public List<HoldingResponse> retrieveHoldingsByQuery(
      @RequestParam(value = "branchScanCode") String scanCode) {
      return service.findByBranch(scanCode).stream()
         .map(HoldingResponse::new)
         .toList();
   }

   @GetMapping(value = "/{holdingBarcode}")
   public HoldingResponse retrieve(@PathVariable("holdingBarcode") String holdingBarcode) {
      return new HoldingResponse(service.find(holdingBarcode));
   }
}