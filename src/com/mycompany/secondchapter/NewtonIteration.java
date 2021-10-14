package com.mycompany.secondchapter;

/**
 * 牛顿迭代法(弦截法取代每次求导数)
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class NewtonIteration {
    public static void main(String[] args) {
        // 设置最大迭代次数
        int N = 10000;
        // 设置前后两个 x 之前的距离 小于 精度eps1
        double eps1 = 0.000001;
        // 设置 f(x) 的值 小于 精度eps2
        double eps2 = 0.000001;
        // 初值 x0 和 x1
        double[] x = new double[]{1, 1.5};

        // 调用牛顿迭代法求单根
        System.out.println("牛顿迭代法：");
        double[] res = newtonIterationBySecantMethod(x, eps1, eps2, N);
        if (res[2] == -1){
            System.out.println("求根失败！");
        } else {
            System.out.println("求出的根：" + res[0]);
            System.out.println("此时的f(x): " + res[1]);
            System.out.println("迭代次数：" + res[2]);
        }
    }

    /**
     * 牛顿迭代法求根
     * 其中由于计算机计算导数较为繁琐，因此用差商替代微商，此方法实则为 弦截法（正割法）
     *
     * @param x    初值 x0 和 x1, [x0, x1]
     * @param eps1 控制前后x的精度
     * @param eps2 控制f(x)的精度
     * @param N    最大迭代次数
     * @return [根, 此时的f(x), 当前迭代次数]
     */
    public static double[] newtonIterationBySecantMethod(double[] x, double eps1, double eps2, int N) {
        // 当前迭代次数 i
        int i = 0;
        // 初值 x0和 x1
        double x0 = x[0];
        double x1 = x[1];
        for (; i < N; i++) {
            double x3 = x1 - (fFun(x1) * (x1 - x0) / (fFun(x1) - fFun(x0)));
            if (Math.abs(x1 - x3) < eps1 && fFun(x3) < eps2) {
                return new double[]{x3, fFun(x3), i};
            }
            x0 = x1;
            x1 = x3;
        }
        // 没有找到根，返回0，0，-1
        // 用当前迭代次数为 -1 表示求根失败
        return new double[]{0, 0, -1};
    }

    /**
     * f(x) = 0
     *
     * @param x 自变量x
     * @return f(x)值
     */
    private static double fFun(double x) {
        return Math.pow(x, 3) - x - 1;
    }

}
