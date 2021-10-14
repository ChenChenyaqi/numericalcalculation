package com.mycompany.secondchapter;

import java.util.ArrayList;
import java.util.List;

/**
 * P17，第5题（求多根，见 findAllRootsByDichotomy方法）
 * 二分法求单根及多根
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class Dichotomy {
    public static void main(String[] args) {
        Dichotomy dichotomy = new Dichotomy();
        // 定义精度判断f(x)是否接近于0
        double esp1 = 0.000001;
        // 定义精度判断区间长度是否接近于0
        double esp2 = 0.000001;
        // 迭代次数
        int N = 10000;
        // 步长
        double dx = 0.1;

        // 求单根
        // 传入根大概所在的区间，(0,2), 最大迭代次数10000
        System.out.println("二分法求单根：");
        double[] res = dichotomy.findOneRootByDichotomy(0, 2, esp1, esp2, N);
        if (res == null) {
            System.out.println("求根失败!");
        } else {
            System.out.println("求出根为：" + res[0] + ", 函数值为：" + res[1] + ", 迭代次数为：" + res[2]);
        }

        // 求所有根
        System.out.println("二分法求所有根：");
        List<Double> res2 = dichotomy.findAllRootsByDichotomy(-5, 5, esp1, esp2, dx, N);
        if (res2.size() == 0) {
            System.out.println("求根失败!");
        } else {
            int k = 1;
            for (int i = 0; i < res2.size() - 1; i += 2) {
                System.out.print("第" + k + "个根为：" + res2.get(i));
                System.out.println(" , 迭代次数为：" + res2.get(i + 1));
                k++;
            }
        }
    }


    /**
     * 二分法求所有根(注意：此方法调用了二分法求单根方法)
     *
     * @param left  左边界
     * @param right 右边界
     * @param eps1  判断f(x)是否接近于0
     * @param eps2  判断区间长度是否接近于0
     * @param dx    步长
     * @param N     最大迭代次数
     * @return [root1, N1, root2, N2, ... , rootm, Nm]
     */
    public List<Double> findAllRootsByDichotomy(double left, double right,
                                                double eps1, double eps2, double dx, int N) {
        // 存储根的集合
        List<Double> roots = new ArrayList<>();
        // index表示每次二分的右端点
        double index = left + dx;
        // 当index小于right，一直进行二分
        while (index < right) {
            // 每次二分右端点的函数值
            double y = function(index);
            // 如果恰好就是根，加入index到roots中
            if (Math.abs(y) == 0) {
                roots.add(index);
            }
            // 如果小区间左右端点函数值异号，则进行二分，否则跳过这段小区间
            if (isDifferent(left, index)) {
                double[] res = findOneRootByDichotomy(left, index, eps1, eps2, N);
                // 求根成功时才加入到roots里
                if (res != null) {
                    roots.add(res[0]);
                    roots.add(res[2]);
                }
            }
            // left --- index这一小区间向右移动
            left = index;
            index = left + dx;
        }
        return roots;
    }


    /***
     * 二分法求单根
     * @param left  左边界
     * @param right 右边界
     * @param eps1 判断f(x)是否接近于0
     * @param eps2 判断区间长度是否接近于0
     * @param N     最大迭代次数
     * @return 若不为空，则第一个值为求出来的根，第二个为此时的函数值，
     * 第三个为此时迭代的次数；若为空，则无法在所给区间内求出根
     */
    public double[] findOneRootByDichotomy(double left, double right, double eps1, double eps2, int N) {
        for (int i = 0; i < N; i++) {
            // 取中点
            double mid = (left + right) / 2;
            // mid代入函数求出y
            double y = function(mid);
            // 如果y的绝对值小于精度precision，即，与0接近；且这个区间长度小于precision
            if (Math.abs(y) < eps1 && (right - left) < eps2) {
                // 返回结果
                return new double[]{mid, y, i + 1};
            }
            // 若这两端点的函数值异号
            if (isDifferent(left, mid)) {
                // 取左区间
                right = mid;
            } else {
                // 取右区间
                left = mid;
            }
        }
        return null;
    }


    /**
     * 传入两个自变量取值，求出函数值，并判断它们是否异号
     *
     * @param a 取值1
     * @param b 取值2
     * @return 同号返回false，异号返回true
     */
    private boolean isDifferent(double a, double b) {
        if ((function(a) > 0 && function(b) > 0) || (function(a) < 0 && function(b) < 0)) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 定义函数 f(x) = (x - 1)^11;
     *
     * @param x 自变量取值
     * @return 带入函数求出因变量值并返回
     */
    private double function(double x) {
        double y = 6 * Math.pow(x, 4) - 40 * Math.pow(x, 2) + 9;
        return y;
    }

}
