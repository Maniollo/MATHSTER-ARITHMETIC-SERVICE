package marmas.arithmetic.exception;

public class TaskSheetException extends RuntimeException {
    public TaskSheetException(String msg, Exception e) {
        super(msg, e    );
    }
}
