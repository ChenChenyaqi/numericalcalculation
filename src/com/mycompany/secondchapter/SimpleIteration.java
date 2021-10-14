package com.mycompany.secondchapter;

import java.util.Arrays;

/**
 * P22，第4、5题（埃特金法，见 AitkenIteration方法）
 * 简单迭代法与埃特金加速迭代法
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class SimpleIteration {
    public static void main(String[] args) {
        // 最大迭代次数
        int N = 10000;
        // 精度，x_n-1 与 x_n 的距离 小于 eps1
        double eps1 = 0.00001;
        // f(x) = x - g(x) 小于 eps2
        double eps2 = 0.00000001;

        // 埃特金迭代法
        System.out.println("埃特金迭代法：");
        double[] res = aitkenIteration(4.5, N, eps1, eps2);
        // 根x
        System.out.println("求出的根：x = " + res[0]);
        // 此时的f(x)
        System.out.println("此时的f(x): f(x) = " + (res[0] - gFunction(res[0])));
        // 此时的N
        System.out.println("迭代次数：" + res[1]);
        // 是否找到根
        System.out.println("是否找到根：" + ((int)res[2] == 1 ? "是" : "否"));

        // 简单迭代法
        System.out.println("简单迭代法：");
        double[] res2 = simpleIteration(4.5, N, eps1, eps2);
        System.out.println("res2 = " + Arrays.toString(res2));
    }


    /**
     * 埃特金加速迭代法求解方程在x附近的根
     *
     * @param x    初值x0
     * @param N    最大迭代次数
     * @param eps1 前后x值的距离小于eps1
     * @param eps2 f(x)的值小于eps2
     * @return 返回埃特金加速迭代法 [求出的根、当前迭代次数、是否成功找到根(1:true; 0:false) ]
     */
    public static double[] aitkenIteration(double x, int N, double eps1, double eps2) {
        // i：当前迭代次数
        int i = 0;
        // 表示 是否 在达到最大迭代次数 N 之前 找到根， 0:没有找到；1：找到
        int success = 0;
        for (; i < N; i++) {
            // 埃特金迭代算法核心
            double x1 = gFunction(x);
            double x2 = gFunction(x1);
            double x3 = x - Math.pow((x1 - x), 2) / (x2 - 2 * x1 + x);
            // 判断是否满足条件：前后 x 的距离 ，f(x)的值与0的接近程度
            if (Math.abs(x3 - x) < eps2 && Math.abs(x3 - gFunction(x3)) < eps1) {
                x = x3;
                // 标记找到根
                success = 1;
                break;
            }
            x = x3;
        }
        return new double[]{x, i, success};
    }

    /**
     * f(x) = x - g(x), x = g(x) 的那个 g(x)
     *
     * @param x 自变量x
     * @return g(x)值
     */
    private static double gFunction(double x) {
        return Math.tan(x);
    }


    /*
     * 简单迭代法求解方程在x附近的根
     *
     * @param x    初值x0
     * @param N    最大迭代次数
     * @param eps1 前后x值的距离小于eps1
     * @param eps2 f(x)的值小于eps2
     * @return 返回简单迭代法求出的根、当前迭代次数、是否成功找到根(1:true; 0:false)
     */
    public static double[] simpleIteration(double x, int N, double eps1, double eps2) {
        int i = 0;
        int success = 0;
        for (; i < N; i++) {
            // 上一个值
            double x1 = x;
            // 这次求出的值
            double x2 = gFunction(x);
            if (Math.abs(x2 - x1) < eps1 && Math.abs(x2 - gFunction(x2)) < eps2) {
                x = x2;
                success = 1;
                break;
            }
            x = x2;
        }
        return new double[]{x, i, success};
    }
}
