package ch.bfh.matrix.ex;

// Base class for more specific exceptions and thrown when the specific exceptions
// are not suitable for a given situation
public class MatrixException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MatrixException(String msg) {
        super(msg);
    }

    public MatrixException(Exception ex) {
        super(ex);
    }
}
