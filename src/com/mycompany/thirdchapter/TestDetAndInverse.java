package com.mycompany.thirdchapter;

import org.junit.Test;

/**
 * 对数器，判断算法是否正确
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class TestDetAndInverse {
    public static void main(String[] args) {
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

    @Test
    public void testInverse(){
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
}