package com.mycompany.thirdchapter;

import java.util.Arrays;

/**
 * 追赶法求解三对角线方程组
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class ChasingMethod {

    public static void main(String[] args) {
        double[][] A = new double[][]{
                {4, -1, 0, 0, 0},
                {-1, 4, -1, 0, 0},
                {0, -1, 4, -1, 0},
                {0, 0, -1, 4, -1},
                {0, 0, 0, -1, 4}
        };
        double[] f = new double[]{100, 200, 200, 200, 100};
        double[] X = chasingMethod(A, f);
        System.out.println("求出X为：");
        System.out.println(Arrays.toString(X));
        double[] res = new double[f.length];
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < A.length; j++) {
                res[i] += A[i][j]*X[j];
            }
        }
        System.out.println("代回方程组，AX = ");
        System.out.println(Arrays.toString(res));
    }

    /**
     * 返回 AX = f 三对角线方程组的解 X
     *
     * @param A 系数矩阵，是一个三对角线稀疏矩阵
     * @param f f
     * @return 方程组的解 X
     */
    public static double[] chasingMethod(double[][] A, double[] f) {
        if (A == null || A.length == 0 || A[0].length == 0 ||
                A.length != A[0].length || f == null || A.length != f.length) {
            System.out.println("无法求解，请检查A，f的输入是否正确！");
            return null;
        }
        // 先进行LU分解
        int n = A.length;
        double[] alpha = new double[n];
        double[] gamma = new double[n];
        double[] beta = new double[n - 1];
        // 对alpha、gamma、beta进行赋值
        alpha[0] = A[0][0];
        beta[0] = A[0][1] / alpha[0];
        int i = 1;
        while (i < n - 1) {
            alpha[i] = A[i][i] - A[i][i - 1] * beta[i - 1];
            beta[i] = A[i][i + 1] / alpha[i];
            gamma[i] = A[i][i-1];
            i++;
        }
        alpha[i] = A[i][i] - A[i][i - 1] * beta[i - 1];
        gamma[i] = A[i][i-1];
        // 先求解LY=f中的 Y
        double[] Y = new double[n];
        Y[0] = f[0] / alpha[0];
        for (int j = 1; j < n; j++) {
            Y[j] = (f[j] - gamma[j] * Y[j - 1]) / alpha[j];
        }
        // 再求解UX=Y中的X
        double[] X = new double[n];
        X[n - 1] = Y[n - 1];
        for (int j = n - 2; j >= 0; j--) {
            X[j] = (Y[j] - beta[j] * X[j + 1]);
        }
        return X;
    }
}
