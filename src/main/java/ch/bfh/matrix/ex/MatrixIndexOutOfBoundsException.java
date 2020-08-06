package ch.bfh.matrix.ex;

// To be thrown when non-existing values are accessed
public class MatrixIndexOutOfBoundsException extends MatrixException {

    private static final long serialVersionUID = 1L;

    public MatrixIndexOutOfBoundsException(String msg) {
        super(msg);
    }

    public MatrixIndexOutOfBoundsException(Exception cause) {
        super(cause);
    }
}
