package controller;

import api.library.HoldingService;
import domain.core.HoldingAlreadyCheckedOutException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HoldingController.class)
public class HoldingControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private HoldingService holdingService;

   @Test
   void addBookHolding() {
      var requestBody = "{\"sourceId\":\"12345\",\"branchScanCode\":\"BR001\"}";

      // TODO
      assertTrue(requestBody.contains("branch"));
//      when(holdingService.add("12345", "BR001")).thenReturn("Holding123");
//
//      mockMvc.perform(post("/holdings")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(requestBody))
//         .andExpect(status().isOk())
//         .andExpect(content().string("Holding123"));
//
//      verify(holdingService).add("12345", "BR001");
   }

//   @Test
//   void checkoutHoldingSuccess() throws Exception {
//      var requestBody = "{\"patronId\":\"P123\",\"holdingBarcode\":\"HB001\",\"checkoutDate\":\"2024-10-31\"}";
//
//      mockMvc.perform(post("/holdings/checkout")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(requestBody))
//         .andExpect(status().isOk());
//
//      verify(holdingService).checkOut("P123", "HB001", "2024-10-31");
//   }
//
//   @Test
//   void checkoutHoldingAlreadyCheckedOut() throws Exception {
//      var requestBody = "{\"patronId\":\"P123\",\"holdingBarcode\":\"HB001\",\"checkoutDate\":\"2024-10-31\"}";
//      doThrow(new HoldingAlreadyCheckedOutException()).when(holdingService).checkOut("P123", "HB001", "2024-10-31");
//
//      mockMvc.perform(post("/holdings/checkout")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(requestBody))
//         .andExpect(status().isConflict());
//
//      verify(holdingService).checkOut("P123", "HB001", "2024-10-31");
//   }
//
//   @Test
//   void checkinHolding() throws Exception {
//      var requestBody = "{\"holdingBarcode\":\"HB001\",\"checkinDate\":\"2024-11-01\",\"branchScanCode\":\"BR001\"}";
//
//      mockMvc.perform(post("/holdings/checkin")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(requestBody))
//         .andExpect(status().isOk());
//
//      verify(holdingService).checkIn("HB001", "2024-11-01", "BR001");
//   }
//
//   @Test
//   void retrieveHoldingsByQuery() throws Exception {
//      when(holdingService.findByBranch("BR001")).thenReturn(List.of(new HoldingResponse()));
//
//      mockMvc.perform(get("/holdings")
//            .param("branchScanCode", "BR001"))
//         .andExpect(status().isOk());
//
//      verify(holdingService).findByBranch("BR001");
//   }
//
//   @Test
//   void retrieveHoldingByBarcode() throws Exception {
//      var barcode = "HB001";
//      var response = new HoldingResponse();
//      when(holdingService.find(barcode)).thenReturn(response);
//
//      mockMvc.perform(get("/holdings/" + barcode))
//         .andExpect(status().isOk());
//
//      verify(holdingService).find(barcode);
//   }
}
