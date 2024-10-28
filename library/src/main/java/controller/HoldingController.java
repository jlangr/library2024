package controller;

import api.library.HoldingService;
import domain.core.Holding;
import domain.core.HoldingAlreadyCheckedOutException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/holdings")
public class HoldingController {
    private HoldingService service = new HoldingService();

    @PostMapping
    public String addBookHolding(@RequestBody AddHoldingRequest request) {
        return service.add(request.sourceId(), request.branchScanCode());
    }

    @PostMapping(value = "/checkout")
    public void checkout(@RequestBody CheckoutRequest request, HttpServletResponse response) {
        try {
            service.checkOut(request.getPatronId(), request.getHoldingBarcode(), request.getCheckoutDate());
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
            @RequestParam(required = true, value = "branchScanCode") String scanCode) {
        List<Holding> holdings = service.findByBranch(scanCode);
        return holdings.stream()
                .map(holding -> new HoldingResponse(holding))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{holdingBarcode}")
    public HoldingResponse retrieve(@PathVariable("holdingBarcode") String holdingBarcode) {
        return new HoldingResponse(service.find(holdingBarcode));
    }
}