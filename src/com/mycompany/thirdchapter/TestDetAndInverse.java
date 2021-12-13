package com.mycompany.thirdchapter;

import org.junit.Test;

/**
 * 对数器，判断算法是否正确
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class TestDetAndInverse {
    // 测试求行列式
    @Test
    public void testDet() {
        for (int k = 0; k < 10; k++) {
            int row = (int)(Math.random() * 10) + 1;
            int col = row;
            double[][] matrix = new double[row][col];
            // 生成一个随机矩阵
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    matrix[i][j] = (int)(Math.random() * 100);
                }
            }
            System.out.println("矩阵" + (k + 1) + " :");
            MatrixUtils.show(matrix);
            double res = MatrixUtils.det(matrix);
            System.out.println("res = " + res);
        }
    }

    // 测试求逆矩阵
    @Test
    public void testInverse() {
        for (int k = 0; k < 10; k++) {
            int row = (int)(Math.random() * 10) + 1;
            int col = row;
            double[][] matrix = new double[row][col];
            // 生成一个随机矩阵
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    matrix[i][j] = (int)(Math.random() * 100);
                }
            }
            System.out.println("矩阵" + (k + 1) + " :");
            MatrixUtils.show(matrix);
            double[][] inverse = MatrixUtils.inverse(matrix);
            System.out.println("逆矩阵：");
            MatrixUtils.show(inverse);
        }
    }

    // 测试矩阵转置
    @Test
    public void testTrans() {
        double[][] m = new double[][]{
                {1, 2},
                {2, 5},
                {5, 7}
        };
        double[][] transpose = MatrixUtils.transpose(m);
        MatrixUtils.show(transpose);
    }

    // 测试利用若尔当全主元消元法求逆矩阵
    @Test
    public void testInverseByJardan() throws Exception {
        double[][] matrix = new double[][]{
                {-3, 8, 5},
                {2, -7, 4},
                {1, 9, -6}
        };
        MatrixUtils.show(matrix);
        double[][] inverse = MatrixUtils.inverseByJardanEliminate(matrix);
        MatrixUtils.show(inverse);
        double[][] res = MatrixUtils.multiply(matrix, inverse);
        MatrixUtils.show(res);
        System.out.println((-0.39574468085106385 * 3 + 8 * 0.05531914893617023 + 5 * 0.14893617021276598));
    }


    @Test
    public void testInverseByJardan2() {
        double[][] matrix = new double[][]{
                {1, 0},
                {0, 1}
        };
        double[][] inverse = MatrixUtils.inverseByJardanEliminate(matrix);
        MatrixUtils.show(inverse);
    }
}
