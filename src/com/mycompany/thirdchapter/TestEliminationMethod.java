package com.mycompany.thirdchapter;

import org.junit.Test;

import java.util.Arrays;

/**
 * 测试消元法求解线性方程组算法
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class TestEliminationMethod {
    // 测试高斯全主元消元法求解线性方程组
    @Test
    public void testTotalEliminate() {
        // 系数矩阵
        double[][] matrix = new double[][]{
                {30.5, 6.5, 7, -55},
                {65, 2.36, 10, 615},
                {5, 56, 5.5, 102.9, 165},
                {-12, 0, 0, 15, 61}
        };
        double[] b = new double[]{1, 4, 18.5, -10};
        double[] roots = EliminationMethod.totalElementEliminate(matrix, b);
        System.out.println("线性方程组 AX=b 求解测试结果如下：");
        System.out.println("线性方程组系数矩阵A为：");
        MatrixUtils.show(matrix);
        System.out.println("解为：");
        System.out.println(Arrays.toString(roots));
        System.out.println("带入线性方程组算y：");
        double[] y = new double[roots.length];
        for (int i = 0; i < roots.length; i++) {
            for (int j = 0; j < roots.length; j++) {
                y[i] += roots[j] * matrix[i][j];
            }
        }
        System.out.println(Arrays.toString(y));
        System.out.println("b为：");
        System.out.println(Arrays.toString(b));
    }

    @Test
    public void testJardan() {
        double[][] matrix = new double[][]{
                {-3, 8, 5},
                {2, -7, 4},
                {1, 9, -6}
        };
        double[] b = new double[]{
                6, 9, 1
        };
        double[] roots = EliminationMethod.jardanTotalElementEliminate(matrix, b);
        System.out.println("高斯-若尔当全主元消元法求线性方程组 AX=b 的根：");
        System.out.println("系数矩阵A为：");
        MatrixUtils.show(matrix);
        System.out.println("求出根为：");
        System.out.println(Arrays.toString(roots));
        System.out.println("根带入方程组求出值：");
        double[] y = new double[roots.length];
        for (int i = 0; i < roots.length; i++) {
            for (int j = 0; j < roots.length; j++) {
                y[i] += roots[j] * matrix[i][j];
            }
        }
        System.out.println(Arrays.toString(y));
        System.out.println("b为：");
        System.out.println(Arrays.toString(b));
    }
}
