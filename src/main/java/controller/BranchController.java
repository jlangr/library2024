package controller;

import api.library.BranchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {
   private final BranchService service = new BranchService();

   @PostMapping
   public String add(@RequestBody BranchRequest branchRequest) {
      return service.add(branchRequest.getName());
   }

   @GetMapping
   public List<BranchRequest> retrieveAll() {
      return service.allBranches().stream()
         .map(BranchRequest::new)
         .toList();
   }
}
