package com.mycompany.fourthchapter;

import java.util.Arrays;

/**
 * 高斯赛德尔迭代法求解线性方程组
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class GaussSeidelMethod {

    public static void main(String[] args) {
        double[][] A = new double[][]{
                {8, -3, 2},
                {4, 11, -1},
                {6, 3, 12}
        };
        double[] b = new double[]{20,33,36};
        int n = 2;
        int maxTime = 10000;
        double esp = 0.00001;
        double[] X = gaussSeidelMethod(A, b, n, maxTime, esp);
        System.out.println("X = " + Arrays.toString(X));
        // 检验
        double[] res = new double[X.length];
        for (int i = 0; i < X.length; i++) {
            double sum = 0;
            for (int j = 0; j < X.length; j++) {
                sum += A[i][j]*X[j];
            }
            res[i] = sum;
        }
        // 输出AX的结果
        System.out.println("求出X，带入方程组算出AX的结果：" + Arrays.toString(res));
        System.out.println("b的值：" + Arrays.toString(b));
    }

    /**
     * 高斯赛德尔求线性方程组的解
     * 方程格式：AX=b
     *
     * @param A       方程系数矩阵
     * @param b       方程右边的y值
     * @param n       求第n范数
     * @param maxTime 指定最大迭代次数
     * @return 返回方程的解X
     */
    public static double[] gaussSeidelMethod(double[][] A, double[] b, int n, int maxTime, double esp) {
        if (A == null || A.length == 0 || A[0].length == 0 || A.length != A[0].length || n < 0
                || b == null || b.length == 0 || A.length != b.length) {
            System.out.println("传参不合法！");
            return null;
        }
        // A的型号
        int len = A.length;
        // 解X
        double[] X = new double[len];
        // 存储上一次的X
        double[] preX = new double[len];

        for (int i = 0; i < maxTime; i++) {
            for (int j = 0; j < len; j++) {
                // 先求出公式里的两个求和
                double sum1 = getSum1(j, A, X);
                double sum2 = getSum2(j, len, A, preX);
                X[j] = (b[j] - sum1 - sum2) / A[j][j];
            }
            if(Math.abs(getNorm(X, n) - getNorm(preX, n)) < esp){
                return X;
            }
            // 复制X到preX
            for (int j = 0; j < len; j++) {
                preX[j] = X[j];
            }
        }
        return null;
    }

    private static double getSum1(int i, double[][] A, double[] X) {
        double sum = 0;
        for (int j = 0; j <= i - 1; j++) {
            sum += A[i][j] * X[j];
        }
        return sum;
    }

    private static double getSum2(int i, int n, double[][] A, double[] preX) {
        double sum = 0;
        for (int j = i + 1; j < n; j++) {
            sum += A[i][j] * preX[j];
        }
        return sum;
    }


    /**
     * 返回向量X的第n范数
     *
     * @param n 第n范数，n如果为0，表示无穷大范数
     * @return 向量X的第n范数, 返回-1表示传参有问题
     */
    public static double getNorm(double[] X, int n) {
        if (n < 0) {
            System.out.println("n不能为负！");
            return -1;
        }
        if (X == null || X.length == 0) {
            System.out.println("X向量不合法");
            return -1;
        }
        // 无穷大范数
        if (n == 0) {
            double max = Math.abs(X[0]);
            for (int i = 1; i < X.length; i++) {
                if (Math.abs(X[i]) > max) {
                    max = Math.abs(X[i]);
                }
            }
            return max;
        }
        // 1范数
        double norm = 0;
        if (n == 1) {
            for (int i = 0; i < X.length; i++) {
                norm += Math.abs(X[i]);
            }
            return norm;
        }
        // 其他范数
        for (int i = 0; i < X.length; i++) {
            norm += Math.pow(X[i], n);
        }
        return Math.pow(norm, 1.0 / n);
    }
}
