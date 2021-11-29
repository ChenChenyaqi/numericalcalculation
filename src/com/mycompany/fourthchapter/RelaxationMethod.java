package com.mycompany.fourthchapter;

import java.util.Arrays;

/**
 * 松弛法解线性方程组
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class RelaxationMethod {

    public static void main(String[] args) {
        double[][] A = new double[][]{
                {4, -1, 0},
                {-1, 4, -1},
                {0, -1, 4}
        };
        double[] b = new double[]{1, 4, -3};
        int n = 2;
        int maxTime = 10000;
        double esp = 0.000005;
        double[] omega = new double[]{1.03, 1, 1.1};

        for (int i = 0; i < omega.length; i++) {
            // 带入不同的omega，求出不同的X
            double[][] result = relaxationMethod(A, b, n, maxTime, esp, omega[i]);
            assert result != null;
            double[] X = result[0];
            System.out.println("omega为：" + omega[i] + " 时，求出X：" + Arrays.toString(X));
            // 检验
            double[] res = new double[X.length];
            for (int j = 0; j < X.length; j++) {
                double sum = 0;
                for (int k = 0; k < X.length; k++) {
                    sum += A[j][k] * X[k];
                }
                res[j] = sum;
            }
            System.out.println("X带入方程组，求出结果：res: " + Arrays.toString(res));
            System.out.println("对比b：" + Arrays.toString(b));
            System.out.println("迭代次数为：" + Arrays.toString(result[1]));
            System.out.println("=======================");
        }
    }

    /**
     * 松弛法求线性方程组的解
     * 方程格式：AX=b
     *
     * @param A       方程系数矩阵
     * @param b       方程右边的y值
     * @param n       求第n范数
     * @param maxTime 指定最大迭代次数
     * @param omega   ω
     * @return 返回方程的解X和当前迭代次构成的二维数组
     */
    public static double[][] relaxationMethod(double[][] A, double[] b, int n,
                                              int maxTime, double esp, double omega) {
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
        // ΔX
        double[] derX = new double[len];

        for (int i = 0; i < maxTime; i++) {
            for (int j = 0; j < len; j++) {
                // 先求出公式里的两个求和
                double sum1 = getSum1(j, A, X);
                double sum2 = getSum2(j, len, A, preX);
                // 计算derX
                derX[j] = (b[j] - sum1 - sum2) * omega / A[j][j];
                // X = preX + derX
                X[j] = preX[j] + derX[j];
            }
            if (i != 0 && Math.abs(GaussSeidelMethod.getNorm(X, n) - GaussSeidelMethod.getNorm(preX, n)) < esp) {
                return new double[][]{X, {i + 1}};
            }
            // 复制X到preX
            for (int j = 0; j < len; j++) {
                preX[j] = X[j];
            }
        }
        return null;
    }

    public static double getSum2(int i, int n, double[][] A, double[] preX) {
        double sum = 0;
        for (int j = i; j < n; j++) {
            sum += A[i][j] * preX[j];
        }
        return sum;
    }

    private static double getSum1(int i, double[][] A, double[] X) {
        double sum = 0;
        for (int j = 0; j <= i - 1; j++) {
            sum += A[i][j] * X[j];
        }
        return sum;
    }
}
