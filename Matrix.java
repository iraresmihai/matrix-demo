import java.util.Scanner;

public class Matrix {
    private double[][] m;
    private int nrLines; //nrLines
    private int nrCol; //nrColumns

    /// Part 1: Building a matrix from a double[][]
    public Matrix(double[][] data) {
        int i, i2;

        nrLines = data.length;
        nrCol = data[0].length;
        m = new double[nrLines][nrCol];

        // copy the values, so changing data later does not change our matrix
        for ( i = 0; i < nrLines; i++ ) {
            for ( i2 = 0; i2 < nrCol; i2++ ) {
                m[i][i2] = data[i][i2];
            }
        }
    }

    /// Part 2: Reading a matrix from a Scanner
    public Matrix(Scanner s, int nrLines, int nrCol) {
        // this(...) has to be the first statement, so we read the values in a helper
        this(readData(s, nrLines, nrCol));
    }

    private static double[][] readData(Scanner s, int nrLines, int nrCol) {
        double[][] aux = new double[nrLines][nrCol];
        int i, i2;

        for ( i = 0; i < nrLines; i++ ) {
            for ( i2 = 0; i2 < nrCol; i2++ ) {
                aux[i][i2] = s.nextDouble();
            }
        }

        return aux;
    }

    /// Part 3: Printing a matrix
    public String toString() {
        int i, i2;
        String x = "";

        for ( i = 0; i < nrLines; i++ ) {
            for ( i2 = 0; i2 < nrCol; i2++ ) {
                x += m[i][i2] + " ";
            }
            x += "\n";
        }

        return x;
    }

    /// Part 4: Getters
    public int getNrLines() {
        return nrLines;
    }

    public int getNrCol() {
        return nrCol;
    }

    public int getSize() {
        return nrLines * nrCol;
    }

    /// Part 5: Adding and subtracting matrices
    public static Matrix addSubtractMatrix(Matrix a, Matrix b, int sign) {
        int i, i2;
        double[][] res = new double[a.nrLines][a.nrCol];

        for ( i = 0; i < a.nrLines; i++ ) {
            for ( i2 = 0; i2 < a.nrCol; i2++ ) {
                res[i][i2] = a.m[i][i2] + sign * b.m[i][i2];
            }
        }

        return new Matrix(res);
    }

    public static Matrix addMatrix(Matrix a, Matrix b) {
        return addSubtractMatrix(a, b, 1);
    }

    public static Matrix subMatrix(Matrix a, Matrix b) {
        return addSubtractMatrix(a, b, -1);
    }

    public void addMatrix(Matrix b) {
        Matrix aux = addMatrix(this, b);
        this.copyMatrix(aux);
    }

    public void subMatrix(Matrix b) {
        Matrix aux = subMatrix(this, b);
        this.copyMatrix(aux);
    }

    public void copyMatrix(Matrix x) {
        this.m = x.m;
        this.nrCol = x.nrCol;
        this.nrLines = x.nrLines;
    }

    /// Part 6: Element-wise multiplication
    public static Matrix multipMatrix(Matrix a, Matrix b) {
        int i, i2;
        double[][] res = new double[a.nrLines][a.nrCol];

        for ( i = 0; i < a.nrLines; i++ ) {
            for ( i2 = 0; i2 < a.nrCol; i2++ ) {
                res[i][i2] = a.m[i][i2] * b.m[i][i2];
            }
        }

        return new Matrix(res);
    }

    /// Part 7: Cropping a matrix
    public static Matrix crop(Matrix x, int nrLines, int nrCol, int startLine, int startCol) {
        double[][] res = new double[nrLines][nrCol];

        int i, i2;

        for ( i = startLine; i < startLine + nrLines; i++ ) {
            for ( i2 = startCol; i2 < startCol + nrCol; i2++ ) {
                res[i - startLine][i2 - startCol] = x.m[i][i2];
            }
        }

        return new Matrix(res);
    }

    public Matrix crop(int nrLines, int nrCol, int startLine, int startCol) {
        return Matrix.crop(this, nrLines, nrCol, startLine, startCol);
    }

    /// Part 8: Pasting a smaller matrix into this one
    public void copyData(Matrix x, int startLine, int startCol) {
        int i, i2;

        for ( i = startLine; i < startLine + x.nrLines; i++ ) {
            for ( i2 = startCol; i2 < startCol + x.nrCol; i2++ ) {
                this.m[i][i2] = x.m[i - startLine][i2 - startCol];
            }
        }
    }

    /// Part 9: Sum
    public double sum() {
        int i, i2;
        double sum = 0;

        for ( i = 0; i < nrLines; i++ ) {
            for ( i2 = 0; i2 < nrCol; i2++ ) {
                sum += m[i][i2];
            }
        }

        return sum;
    }

    /// Part 10: Average
    public double avg() {
        return sum() / getSize();
    }

    /// Part 11: Matrix product
    public static Matrix realProduct(Matrix a, Matrix b) {
        double[][] ans = new double[a.nrLines][b.nrCol];
        int i, i2, i3;

        for ( i = 0; i < a.nrLines; i++ ) {
            for ( i2 = 0; i2 < b.nrCol; i2++ ) {
                for ( i3 = 0; i3 < a.nrCol; i3++ ) {
                    ans[i][i2] += a.m[i][i3] * b.m[i3][i2];
                }
            }
        }

        return new Matrix(ans);
    }

    /// Part 12: Convolution
    public Matrix convolute(Matrix x) {
        int i, i2;
        int resLines = this.nrLines - x.nrLines + 1;
        int resCol = this.nrCol - x.nrCol + 1;
        double[][] res = new double[resLines][resCol];

        for ( i = 0; i < resLines; i++ ) {
            for ( i2 = 0; i2 < resCol; i2++ ) {
                res[i][i2] = multipMatrix(crop(this, x.nrLines, x.nrCol, i, i2), x).sum();
            }
        }

        return new Matrix(res);
    }
}
