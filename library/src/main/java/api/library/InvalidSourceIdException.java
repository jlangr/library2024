package api.library;

import java.io.Serial;

public class InvalidSourceIdException extends RuntimeException {
    public InvalidSourceIdException(String message) {
        super(message);
    }

    @Serial
    private static final long serialVersionUID = 1L;

}
