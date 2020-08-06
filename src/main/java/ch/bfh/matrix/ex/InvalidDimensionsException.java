package ch.bfh.matrix.ex;

// To be thrown when an operation cannot be done due to dimensional restrictions
public class InvalidDimensionsException extends MatrixException {

    private static final long serialVersionUID = 1L;

    public InvalidDimensionsException(String msg) {
        super(msg);
    }

    public InvalidDimensionsException(Exception cause) {
        super(cause);
    }
}
