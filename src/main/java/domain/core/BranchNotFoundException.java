package domain.core;

import java.io.Serial;

public class BranchNotFoundException extends RuntimeException {
   public BranchNotFoundException(String message) {
      super(message);
   }

   @Serial
   private static final long serialVersionUID = 1L;
}
