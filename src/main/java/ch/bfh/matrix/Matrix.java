package ch.bfh.matrix;

import ch.bfh.matrix.ex.*;

import java.util.Arrays;

public class Matrix {

    private final int lines;
    private final int columns;
    private final double[][]values;


    // expected precision in floating point calucations
    public static final double EPSILON = 0.000000001;

    // Creates a null-matrix with given dimensions
    public Matrix(int lines, int columns) throws InvalidDimensionsException{
        // TODO
        if((columns <= 0 || lines <=0) || (columns<=0 && lines<=0)){
            throw new InvalidDimensionsException("InvalidDimensionsException");
        }
        this.columns = columns;
        this.lines = lines;
        this.values = new double[lines][columns];
    }

    // Creates a matrix with values given in a 2-dimensional array.
    public Matrix(final double[][] _values) {
        this.columns = _values[0].length;
        this.lines = _values.length;
        this.values = new double[lines][columns];
        if(columns <=0){
            throw new InvalidDimensionsException("test");
        }
        for(int i =0; i<lines;i++){
            for(int j =0; j<columns; j++){
                if(_values[i].length!=columns){
                    throw new InvalidDimensionsException("test");
                }
                this.values[i][j] = _values[i][j];
            }
        }
    }


    // Returns the number of lines in this matrix.
    public int nbOfLines() {
        return this.lines;
    }

    // Returns the number of columns in this matrix.
    public int nbOfColumns() {
        return this.columns;
    }

    // Returns the value at the given position in the matrix.
    public double get(final int line, final int col) {
        try {
            return this.values[line][col];
        }catch (ArrayIndexOutOfBoundsException e){
            throw new MatrixIndexOutOfBoundsException("MatrixIndexOutOfBoundsException");
        }
    }

    // Sets the value at the given position in the matrix.
    protected void set(final int line, final int col, final double value) {
        try {
            this.values[line][col] = value;
        }catch (ArrayIndexOutOfBoundsException e){
            throw new MatrixIndexOutOfBoundsException("MatrixIndexOutOfBoundsException");
        }
    }

    // Returns the transpose of the matrix.
    public Matrix transpose() {
        Matrix matrix = new Matrix(this.columns,this.lines);
        for (int i = 0; i < this.lines; i++) {
            for (int j = 0; j < this.columns; j++) {
                matrix.values[j][i] = this.values[i][j];
            }
        }
        return matrix;
    }

    // Returns the determinant of the matrix
    public double determinant() {
        Matrix matrix = this;
        double temp[][];
        double result = 0;
        if(this.lines != this.columns){
            throw new InvalidDimensionsException("invalid dimension, make sure you have a quadratic matrix");
        }
        /*
        else if(this.values.length==2){
            result = matrix.get(0,0)*matrix.get(1,1) - matrix.get(0,1)*matrix.get(1,0);
            return result;
        }
        else if(this.values.length==3){
            result = matrix.get(0,0)*matrix.get(1,1)*matrix.get(2,2) + matrix.get(0,1)*matrix.get(1,2)*matrix.get(2,0)
                    +matrix.get(0,2)*matrix.get(1,0)*matrix.get(2,1)
                    -matrix.get(0,2)*matrix.get(1,1)*matrix.get(2,0)-matrix.get(2,1)*matrix.get(1,2)*matrix.get(0,0)
            -matrix.get(2,2)*matrix.get(1,0)*matrix.get(0,1)
            ;
            return result;
        }else
*/
        return det(this.values);
    }


    private static double det(double[][]matrix){
        double temporary[][];
        double result = 0;
        if (matrix.length == 1) {
            result = matrix[0][0];
            return (result);
        }
        for (int i = 0; i < matrix.length; i++) {
            temporary = new double[matrix.length - 1][matrix.length - 1];

            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    if (k < i) {
                        temporary[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temporary[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }
            result += matrix[0][i] * Math.pow (-1, (double) i) * det (temporary);
        }
        return (result);
    }

    //this methode was written with some help from https://gist.github.com/hallazzang/4e6abbb05ff2d3e168a87cf10691c4fb
    // Returns the inverse of the matrix
    public Matrix inverse() {
        if(nbOfLines()!= this.nbOfColumns()) throw new InvalidDimensionsException("");
        double det = determinant();
        if (det == 0.0) {
            throw new MatrixException("Det is 0");
        }
        if (lines != columns || det == 0.0) {
            return null;
        } else {
            Matrix result = new Matrix(lines, columns);
            for (int row = 0; row < lines; ++row) {
                for (int col = 0; col < columns; ++col) {
                    Matrix sub = subMatrix(this, row + 1, col + 1);

                    result.values[col][row] = (1.0 / det *
                            Math.pow(-1, row + col) *
                            sub.determinant());
                }
            }
            return result;
        }
    }
    //this methode was written with some help from https://gist.github.com/hallazzang/4e6abbb05ff2d3e168a87cf10691c4fb
    public static Matrix subMatrix(Matrix matrix, int exclude_row, int exclude_col) {
        Matrix result = new Matrix(matrix.lines - 1, matrix.columns - 1);

        for (int row = 0, p = 0; row < matrix.lines; ++row) {
            if (row != exclude_row - 1) {
                for (int col = 0, q = 0; col < matrix.columns; ++col) {
                    if (col != exclude_col - 1) {
                        result.values[p][q] = matrix.values[row][col];

                        ++q;
                    }
                }
                ++p;
            }
        }
        return result;
    }


/*
    public double[][] cofactor(double[][] matrix) {
        double[][] cofactor = new double[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cofactor[row][col] = matrix[(row + 1) % 3][(col + 1) % 3] * matrix[(row + 2) % 3][(col + 2) % 3] - matrix[(row + 1) % 3][(col + 2) % 3] * matrix[(row + 2) % 3][(col + 1) % 3];
            }
        }
        return cofactor;
    }

 */
    // Returns the product of the matrix with the given scalar.
    public Matrix multiply(final double scalar) {
        Matrix matrix = new Matrix(this.values);
            for (int i = 0; i < this.lines; i++) {
                for (int j = 0; j < this.columns; j++) {
                    matrix.values[i][j] = matrix.values[i][j] * scalar;
                }
            }
            return matrix;
    }

    // Returns the product of the matrix with another matrix.
    public Matrix multiply(Matrix other) {
        Matrix A = new Matrix(this.values);
        if(other.lines != A.columns) throw new MatrixException("invalid dim");
        Matrix result = new Matrix(A.lines,other.columns);
        for(int i=0; i<result.lines;i++){
            for(int j=0; j<result.columns;j++){
                for(int k=0; k<A.columns;k++){
                    result.values[i][j] +=(A.values[i][k] * other.values[k][j]);
                }
            }
        }
        return result; // TODO0
    }

    // returns the sum of the two matrices
    public Matrix add(Matrix other) {
        Matrix A = this;
        if(other.columns != A.columns || other.lines != A.lines) {
            throw new InvalidDimensionsException("Invalid dimension");
        }
        Matrix result = new Matrix(lines,columns);
        for(int i=0; i<result.lines; i++){
            for(int j=0; j<result.columns; j++){
                result.values[i][j]= A.values[i][j] + other.values[i][j];
            }
        }
        return result;// TODO
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass()) {
            return false;
        }
        Matrix other = (Matrix) obj;
        if (this.nbOfLines() != other.nbOfLines() || this.nbOfColumns() != other.nbOfColumns()) {
            return false;
        }
        for (int line = 0; line < this.nbOfLines(); line++) {
            for(int i = 0; i < this.nbOfColumns(); i++) {
                if (Math.abs(this.get(line, i) - other.get(line, i)) > EPSILON) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public String toString() {
        return "Matrix{" +
                "lines=" + lines +
                ", columns=" + columns +
                ", values=" + Arrays.deepToString(values) +
                '}';
    }
}
