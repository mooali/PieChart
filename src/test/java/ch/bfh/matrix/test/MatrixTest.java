package ch.bfh.matrix.test;

import ch.bfh.matrix.Matrix;
import ch.bfh.matrix.ex.InvalidDimensionsException;
import ch.bfh.matrix.ex.MatrixException;
import ch.bfh.matrix.ex.MatrixIndexOutOfBoundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    @Test
    public void testDimensions() throws Exception{

        // valid dimensions
        Matrix a = new Matrix(2, 4);
        assertEquals(a.nbOfLines(), 2);
        assertEquals(a.nbOfColumns(), 4);

        a = new Matrix(4, 2);
        assertEquals(a.nbOfLines(), 4);
        assertEquals(a.nbOfColumns(), 2);

        // invalid dimenstions
        assertThrows(InvalidDimensionsException.class, () -> new Matrix(0, 4));
        assertThrows(InvalidDimensionsException.class, () -> new Matrix(1, 0));
        assertThrows(InvalidDimensionsException.class, () -> new Matrix(0, 0));

        // valid dimensions in value array
        a = new Matrix(new double[][] {{ 1, 2, 3 }});
        assertEquals(a.nbOfLines(), 1);
        assertEquals(a.nbOfColumns(), 3);

        // invalid dimensions in value array
        assertThrows(InvalidDimensionsException.class, () -> new Matrix(new double[][]{
                { 1.0, 2.0, 3.0},
                { 4.0, 5.0 }
        }));
        assertThrows(InvalidDimensionsException.class, () -> new Matrix(new double[][]{ {}, {} }));
    }

    @Test
    public void testGetValues() throws Exception {

        Matrix a = new Matrix(new double[][]{{ 1, 2 }, { 3, 4 }});
        assertEquals(a.get(0,0), 1, Matrix.EPSILON);
        assertEquals(a.get(0,1), 2, Matrix.EPSILON);
        assertEquals(a.get(1,0), 3, Matrix.EPSILON);
        assertEquals(a.get(1,1), 4, Matrix.EPSILON);
        assertThrows(MatrixIndexOutOfBoundsException.class, () -> a.get(2, 1));
        assertThrows(MatrixIndexOutOfBoundsException.class, () -> a.get(1, 2));

        Matrix b = new Matrix(new double[][] {
                { 0.0, 0.1, 0.2 },
                { 1.1, 1.2, 1.3 },
                { 2.2, 2.3, 2.4 },
        });
        for (int i=0; i<b.nbOfLines(); i++)
            for (int j=0; j<b.nbOfColumns(); j++)
                assertEquals(b.get(i, j), (double)i + (double)(i + j) / 10.0, Matrix.EPSILON);
        assertThrows(MatrixIndexOutOfBoundsException.class, () -> b.get(0, -1));
        assertThrows(MatrixIndexOutOfBoundsException.class, () -> b.get(-1, 0));

        Matrix c = new Matrix(5, 5);
        for (int i=0; i<c.nbOfLines(); i++)
            for (int j=0; j<c.nbOfColumns(); j++)
                assertEquals(c.get(i, j), 0.0, Matrix.EPSILON);
        assertThrows(MatrixIndexOutOfBoundsException.class, () -> b.get(c.nbOfLines(), 0));
        assertThrows(MatrixIndexOutOfBoundsException.class, () -> b.get(0, c.nbOfColumns()));
    }

    @Test
    public void testTranspose() throws Exception {
        // non-quadratic matrix
        Matrix a = new Matrix(new double[][]{
                { 1, 2},
                { 3, 4},
                { 5, 6}
        });
        Matrix b = a.transpose();

        // compare dimensions
        assertEquals(a.nbOfLines(), b.nbOfColumns());
        assertEquals(b.nbOfLines(), a.nbOfColumns());
        // compare values
        for (int i=0; i<a.nbOfLines(); i++)
            for (int j=0; j<a.nbOfColumns(); j++)
                assertEquals(a.get(i, j), b.get(j, i));
        // fails for a non-quadratic matrix
        assertThrows(Exception.class, () -> b.get(a.nbOfLines()-1, a.nbOfColumns()-1));
        // transposing twice results in the same matrix
        assertNotEquals(a, b);
        assertEquals(a, b.transpose());

        // quadratic matrix
        Matrix c = new Matrix(new double[][]{
                { 1.5, 0.6, 3.4},
                {4.1, 5.3, -6.0},
                {0.1, 7.3, -6.0}
        });
        Matrix d = c.transpose();

        // compare dimensions
        assertEquals(d.nbOfLines(), d.nbOfColumns());
        // compare values on the diagonale
        for (int i=0; i<c.nbOfLines(); i++)
            assertEquals(c.get(i, i), d.get(i, i));
        // transposing twice results in the same matrix
        assertNotEquals(c, d);
        assertEquals(c, d.transpose());
    }

    @Test
    public void testDet() throws Exception {
        // test with some examples from the internet
        Matrix a = new Matrix(new double[][]{
                { 2, 3, 4 },
                { 0, 3, 4 },
                { 0, 0, 4 },
        });
        assertEquals(a.determinant(), 24, Matrix.EPSILON);
        a = new Matrix(new double[][]{
                { 2, -3 },
                { 5, 1 },
        });
        assertEquals(a.determinant(), 17, Matrix.EPSILON);
        a = new Matrix(new double[][]{
                {  1, 3,  5 },
                { -1, 2,  0 },
                {  4, 2, -3 },
        });
        assertEquals(a.determinant(), -65, Matrix.EPSILON);
        a = new Matrix(new double[][]{
                {  3.0, 7.0,  3.0,  0.0 },
                {  0.0, 2.0, -1.0,  1.0 },
                {  5.0, 4.0,  3.0,  2.0 },
                {  6.0, 6.0,  4.0, -1.0 },
        });
        assertEquals(a.determinant(), 105.0, Matrix.EPSILON);

        // with a column (or row) being all 0.0, the determinant must be 0.0
        a = new Matrix(new double[][]{
                {  3.0, 7.0, 0.0,  0.0 },
                {  0.0, 2.0, 0.0,  1.0 },
                {  5.0, 4.0, 0.0,  2.0 },
                {  6.0, 6.0, 0.0, -1.0 },
        });
        assertEquals(a.determinant(), 0.0, Matrix.EPSILON);

        // a non-quadratic matrix has no determinant
        Matrix b = new Matrix(new double[][] {
                {  5.0, 4.0,  3.0,  2.0 },
                {  6.0, 6.0,  4.0, -1.0 },
        });
        assertThrows(MatrixException.class, () -> b.determinant());
    }

    @Test
    public void testInverse() throws Exception {
        // example from https://www.analyzemath.com/linear-algebra/matrices/inverse-matrix-questions-with-solutions.html
        Matrix a = new Matrix(new double[][] {
                { 2, 3 },
                { 3, 4 }
        });
        Matrix b = a.inverse();
        assertNotEquals(a, b);
        assertEquals(b, new Matrix(new double[][] {
                { -4, 3 },
                { 3, -2 }
        }));
        // multiplying a matrix and its inverse results in identity matrix
        assertEquals(a.multiply(b), new Matrix(new double[][] {
                { 1, 0 },
                { 0, 1 }
        }));
        // inverting twice results in the original matrix
        assertEquals(a, b.inverse());

        // example from https://www.analyzemath.com/linear-algebra/matrices/inverse-matrix-questions-with-solutions.html
        Matrix c = new Matrix(new double[][] {
                { -2, 2, 0 },
                { 2, 1, 3 },
                { -2, 4, -2 }
        });
        Matrix d = c.inverse();
        assertNotEquals(c, d);
        assertEquals(d, new Matrix(new double[][] {
                { -7.0/12.0, 1.0/6.0,  1.0/4.0 },
                { -1.0/12.0, 1.0/6.0,  1.0/4.0 },
                {  5.0/12.0, 1.0/6.0, -1.0/4.0 }
        }));
        // multiplying a matrix and its inverse results in identity matrix
        assertEquals(c.multiply(d), new Matrix(new double[][] {
                { 1, 0, 0 },
                { 0, 1, 0 },
                { 0, 0, 1 }
        }));
        // inverting twice results in the original matrix
        assertEquals(c, d.inverse());

        // non-quadratic matrices may not be inverted
        Matrix e = new Matrix(new double[][] {
                { -2, 2, 0 },
                { 2,  1, 3 }
        });
        assertThrows(InvalidDimensionsException.class, () -> e.inverse());

        // with a column (or row) being all 0.0, the determinant is 0.0 and the matrix cannot be inverted
        Matrix f = new Matrix(new double[][]{
                {  3.0, 7.0, 0.0,  0.0 },
                {  0.0, 2.0, 0.0,  1.0 },
                {  5.0, 4.0, 0.0,  2.0 },
                {  6.0, 6.0, 0.0, -1.0 },
        });
        assertThrows(MatrixException.class, () -> f.inverse());
    }

    @Test
    public void testAdd() throws Exception {
        Matrix a = new Matrix(new double[][] {
                {  5.0, 4.0, 3.0, 2.0 },
                {  6.0, 5.0, 4.0, 3.0 },
        });
        Matrix b = new Matrix(new double[][] {
                {  1.0, 2.0, 3.0, 4.0 },
                {  5.0, 6.0, 7.0, 8.0 },
        });
        assertEquals(a.add(b), new Matrix(new double[][] {
                {  6.0, 6.0, 6.0, 6.0 },
                {  11.0, 11.0, 11.0, 11.0 },
        }));
        // add Null-Matrix
        assertEquals(a, a.add(new Matrix(2, 4)));
        // dimension must be equal
        assertThrows(MatrixException.class, () -> a.add(new Matrix(2, 3)));

        double[][] c = new double[a.nbOfLines()][];
        for (int i=0; i< a.nbOfLines(); i++) {
            c[i] = new double[a.nbOfColumns()];
            for (int j=0; j<a.nbOfColumns(); j++)
                c[i][j] = -a.get(i, j);
        }
        assertEquals(a.add(new Matrix(c)), new Matrix(2, 4));
    }

    @Test
    public void testMultiplyScalar() throws Exception {
        Matrix a = new Matrix(new double[][] {
                {1, 2, 3, 4 },
                { 5, 6, 7, 8 }
        });
        Matrix b = a.multiply(100.0);
        assertEquals(b, new Matrix(new double[][] {
                {100, 200, 300, 400 },
                { 500, 600, 700, 800 }
        }));
        Matrix c = b.multiply(0.01);
        assertEquals(a, c);
    }

    @Test
    public void testMultiplyMatrix() throws Exception {
        Matrix a = new Matrix(new double[][] {
                {  5.0, 4.0, 3.0 },
                {  6.0, 5.0, 4.0 },
        });
        Matrix b = new Matrix(new double[][] {
                {  1.0, 4.0 },
                {  2.0, 5.0 },
                {  3.0, 6.0 },
        });
        Matrix c = new Matrix(new double[][] {
                {  22.0, 58.0 },
                {  28.0, 73.0 },
        });
        assertEquals(a.multiply(b), c);

        Matrix d = b.multiply(c);
        assertEquals(b.nbOfLines(), d.nbOfLines());
        assertEquals(c.nbOfColumns(), d.nbOfColumns());

        // multiply with null-matrix
        assertEquals(a.multiply(new Matrix(3, 2)), new Matrix(2, 2));

        // invalid dimensions
        assertThrows(MatrixException.class, () -> a.multiply(c));
        assertThrows(MatrixException.class, () -> c.multiply(b));
    }


}
