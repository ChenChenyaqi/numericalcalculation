package com.mycompany.thirdchapter;

/**
 * 消元法解线性方程组（求唯一解，即要求系数矩阵的行列式不为0）
 * 高斯全主元消元法 和 高斯列主元消元法
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class EliminationMethod {
    // 记录线性方程组 n 个未知数的位置，针对 进行行交换或列交换时，使其乱序问题
    private static int[] rootIndex;

    /**
     * 用高斯全主元消元法求线性方程组的唯一解
     *
     * @param matrix 线性方程组的系数矩阵
     * @param b      线性方程组等式右端的值, AX = b
     * @return 唯一解
     */
    public static double[] totalElementEliminate(double[][] matrix, double[] b) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0 || matrix.length != matrix[0].length) {
            System.out.println("输入的系数矩阵不合法");
            return null;
        }
        if (b == null || b.length != matrix.length) {
            System.out.println("输入的b不合法");
            return null;
        }
        if (MatrixUtils.det(matrix) == 0) {
            System.out.println("行列式值为0，无法求解");
            return null;
        }
        // 系数矩阵的阶
        int n = matrix.length;
        // 增广矩阵
        double[][] augmentedMatrix = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (j == n) {
                    augmentedMatrix[i][j] = b[i];
                    continue;
                }
                augmentedMatrix[i][j] = matrix[i][j];
            }
        }
        // 初始化rootIndex
        rootIndex = new int[n];
        for (int i = 0; i < n; i++) {
            // x1 = 1, x2 = 2, x3 = 4 ...
            rootIndex[i] = i + 1;
        }
        // 开始消元
        // 用第 i 行, 消 i + 1 ~ n - 1 行
        for (int i = 0; i < n - 1; i++) {
            // 先进行行交换或列交换，使[i,i]位置上的元素绝对值最大
            change(augmentedMatrix, i);
            for (int j = i + 1; j < n; j++) {
                MatrixUtils.eliminate(augmentedMatrix, i, j);
            }
        }
        // 求解算法，倒着往回求
        double[] noOrderRoots = new double[n];
        noOrderRoots[n - 1] = augmentedMatrix[n - 1][n] / augmentedMatrix[n - 1][n - 1];
        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                augmentedMatrix[i][n] -= noOrderRoots[j] * augmentedMatrix[i][j];
            }
            noOrderRoots[i] = augmentedMatrix[i][n] / augmentedMatrix[i][i];
        }
        // 根据rootIndex中此时各个未知量的相对次序，对解排序，返回排好序的解
        double[] orderRoots = new double[n];
        for (int i = 0; i < n; i++) {
            orderRoots[rootIndex[i] - 1] = noOrderRoots[i];
        }
        return orderRoots;
    }

    /**
     * 对矩阵进行行列交换，使得每次消元前，主对角元的值都是相对较大的（绝对值大）
     *
     * @param matrix 矩阵matrix
     * @param i      从i行i列到末尾的这个子阵
     */
    private static void change(double[][] matrix, int i) {
        // 得到[i,i]之后最大数的下标
        int[] maxNumIndex = MatrixUtils.getMaxNumIndex(matrix, i);
        // 如果最大数就是matrix[i][i]本身，则不交换
        if (maxNumIndex[0] == i && maxNumIndex[1] == i) {
            return;
        }
        // 如果列不相等
        if (maxNumIndex[1] != i) {
            // 交换列
            for (int j = 0; j < matrix.length; j++) {
                double temp = matrix[j][i];
                matrix[j][i] = matrix[j][maxNumIndex[1]];
                matrix[j][maxNumIndex[1]] = temp;
            }
            // 交换rootIndex里xn 和 xm 的位置
            int temp = rootIndex[i];
            rootIndex[i] = rootIndex[maxNumIndex[1]];
            rootIndex[maxNumIndex[1]] = temp;
        }
        // 如果行不相等
        if (maxNumIndex[0] != i) {
            // 交换行
            for (int j = 0; j < matrix[0].length; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[maxNumIndex[0]][j];
                matrix[maxNumIndex[0]][j] = temp;
            }
        }
    }

    /**
     * 用高斯-若尔当全主元消元法求线性方程组的唯一解
     *
     * @param matrix 线性方程组的系数矩阵
     * @param b      线性方程组中AX=b的b列向量
     * @return 唯一解
     */
    public static double[] jardanTotalElementEliminate(double[][] matrix, double[] b) {
        // 拦截非法输入
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0 || matrix.length != matrix[0].length) {
            System.out.println("输入的系数矩阵不合法");
            return null;
        }
        if (b == null || b.length != matrix.length) {
            System.out.println("输入的b不合法");
            return null;
        }
        if (MatrixUtils.det(matrix) == 0) {
            System.out.println("行列式值为0，无法求解");
            return null;
        }
        // 系数矩阵的阶
        int n = matrix.length;
        // 增广矩阵
        double[][] augmentedMatrix = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (j == n) {
                    augmentedMatrix[i][j] = b[i];
                    continue;
                }
                augmentedMatrix[i][j] = matrix[i][j];
            }
        }
        // 开始消元
        // 寻找系数矩阵中绝对值最大(下称最大元)的那个，然后把它变成 1，用它来消其他行
        // 注意：在找到某一个最大元素后，下次寻找最大元时，应避开这一列，而在其他列里找
        // used[i]:是否已经在第i列寻找过最大元
        boolean[] used = new boolean[n];
        // 寻找 n 次
        for (int i = 0; i < n; i++) {
            // 最大元的下标[i,j]
            int[] index = MatrixUtils.findMaxNumIndex(augmentedMatrix, used);
            // 对最大元所在的这一行进行消
            double maxNum = augmentedMatrix[index[0]][index[1]];
            for (int j = 0; j < n + 1; j++) {
                augmentedMatrix[index[0]][j] /= maxNum;
            }
            // 最大元变成1
            augmentedMatrix[index[0]][index[1]] = 1;
            // 对除 最大元所在行外的 其余行进行消元
            for (int j = 0; j < n; j++) {
                // 不要消最大元所在这一行，已经消过了
                if (j == index[0]){
                    continue;
                }
                // 依次保存最大元所在列的元素
                double temp = augmentedMatrix[j][index[1]];
                for (int k = 0; k < n + 1; k++) {
                    augmentedMatrix[j][k] += augmentedMatrix[index[0]][k] * (-temp);
                }
                augmentedMatrix[j][index[1]] = 0;
            }
        }
        // 求根
        double[] roots = new double[n];
        for (double[] arr : augmentedMatrix){
            for (int j = 0; j < n + 1; j++) {
                if (arr[j] != 0){
                    // xj的值
                    roots[j] = arr[n];
                    break;
                }
            }
        }
        return roots;
    }




}
