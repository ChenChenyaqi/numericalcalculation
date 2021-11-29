package com.mycompany.sixthchapter;

/**
 * 拉格朗日差值法
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class LagrangeInterpolation {
    public static void main(String[] args) {
        double[] x = new double[]{0.5, 0.7, 0.9, 1.1, 1.3, 1.5, 1.7, 1.9};
        double[] fx = new double[]{0.4794, 0.6442, 0.7833, 0.8912, 0.9636, 0.9975, 0.9917, 0.9463};
        double[] x0 = new double[]{0.6, 0.8, 1.0};
        // 依次求解 f(x0)
        for (int i = 0; i < x0.length; i++) {
            try {
                double res = lagrangeInterpolate(x0[i], x, fx);
                System.out.println("x0 = " + x0[i] + " 时，fx值为：" + res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拉格朗日插值多项式计算给定数据下，f(x0)的值
     *
     * @param x0 要求的f(x0)中的x0
     * @param x  数据点x
     * @param fx 数据点fx
     * @return 返回给定数据点下，f(x0)的值
     * @throws Exception x,fx参数不合法时则报错
     */
    public static double lagrangeInterpolate(double x0, double[] x, double[] fx) throws Exception {
        if (x == null || fx == null || x.length != fx.length) {
            throw new Exception("输入参数x, fx不合法");
        }

        int n = x.length;
        double res = 0;
        for (int i = 0; i < n; i++) {
            res += fx[i] * ln(x0, x, n, i);
        }
        return res;
    }

    /**
     * 根据拉格朗日基函数，ln 和 给定的fxn数据，计算 ln*fxn
     *
     * @param x0 要计算的f(x0)中的x0
     * @param x  数据点x
     * @param n  数据的长度
     * @param k  第几个点
     * @return ln*fxn
     */
    private static double ln(double x0, double[] x, int n, int k) {
        double res = 1.0;
        for (int i = 0; i < n; i++) {
            if (k == i) {
                continue;
            }
            res *= (x0 - x[i]);
        }
        for (int i = 0; i < n; i++) {
            if (k == i) {
                continue;
            }
            res /= (x[k] - x[i]);
        }
        return res;
    }
}
