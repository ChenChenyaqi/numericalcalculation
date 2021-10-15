package com.mycompany.thirdchapter;

import java.util.Arrays;

/**
 * 关于矩阵运算的工具箱
 *
 * @author Chen Yaqi
 * @version 1.0
 */
public class MatrixUtils {
    // 如果交换了行列式的某两行或某两列，则变号
    private static boolean symbol = true;

    /**
     * 按照MATLAB中矩阵的格式输出matrix
     *
     * @param matrix 矩阵matrix
     */
    public static void show(double[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            System.out.println("矩阵不合法，无法输出！");
            return;
        }
        System.out.println("============");
        System.out.print("[");
        // matrix的行
        int n = matrix.length;
        int j = 0;
        for (double[] temp : matrix) {
            for (int i = 0; i < temp.length; i++) {
                if (i == temp.length - 1) {
                    System.out.print(temp[i] + ";");
                    break;
                }
                System.out.print(temp[i] + ", ");
            }
            if (j == n - 1) {
                System.out.println("]");
                break;
            }
            System.out.println();
            j++;
        }
        System.out.println("============");
    }


    /**
     * 高斯全主元消元法，返回矩阵matrix求行列式的值
     *
     * @param matrix 方阵
     * @return 该矩阵的行列式
     */
    public static double det(double[][] matrix) {
        // 初始化符号标记
        symbol = true;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return Double.NaN;
        }
        // 行
        int row = matrix.length;
        // 列
        int col = matrix[0].length;
        // 不是方阵，无法求行列式
        if (row != col) {
            return Double.NaN;
        }
        // matrix复制品，防止行列变换时 污染 原矩阵, 导致求逆矩阵时出现bug
        double[][] copyMatrix = new double[row][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                copyMatrix[i][j] = matrix[i][j];
            }
        }
        // 处理行列式一定为0的情况(某行或某列全为0)
        if (handlingSpecialCases(copyMatrix, row) == 0) {
            return 0;
        }
        // 用第 i 行, 消 i + 1 ~ row - 1 行
        for (int i = 0; i < row - 1; i++) {
            // 全主元消元法的体现之处：
            // 把copyMatrix[i][i]元素通过行交换、列交换换成最大的那个，以防止大数吃小数
            change(copyMatrix, i);
            for (int j = i + 1; j < row; j++) {
                // 用i行消j行
                eliminate(copyMatrix, i, j);
            }
        }
        // 行列式结果
        double res = 1;
        for (int i = 0; i < row; i++) {
            // 如果上三角矩阵对角元中，有一个为0，则行列式值为0
            if (copyMatrix[i][i] == 0) {
                return 0;
            }
            res *= copyMatrix[i][i];
        }
        // 行列式符号判断
        if (symbol) {
            return res;
        } else {
            return -res;
        }
    }

    /**
     * 处理行列式一定为 0 的情况
     *
     * @param matrix 矩阵matrix
     * @param n      matrix的阶
     * @return 一定为0，返回0；否则返回 1
     */
    private static int handlingSpecialCases(double[][] matrix, int n) {
        // 检查行列式是否有一行全为0
        for (int i = 0; i < n; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                // 如果这一行有一个元素不为0，则flag为false;
                if (matrix[i][j] != 0) {
                    flag = false;
                }
            }
            if (flag) {
                return 0;
            }
        }
        // 检查行列式是否有一列全为0
        for (int i = 0; i < n; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (matrix[j][i] != 0) {
                    flag = false;
                }
            }
            if (flag) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * 用第 i 行消第 j 行
     *
     * @param matrix 矩阵matrix
     * @param i      i行
     * @param j      j行
     */
    public static void eliminate(double[][] matrix, int i, int j) {
        // j行每个元素所乘的消元因子
        double temp = -matrix[j][i] / matrix[i][i];
        // j行i列一定可以消成0
        matrix[j][i] = 0;
        // 对j行每个元素开始消元
        for (int k = i + 1; k < matrix[0].length; k++) {
            matrix[j][k] += matrix[i][k] * temp;
        }
    }

    /**
     * 对矩阵进行行列交换，使得每次消元前，主对角元的值都是相对较大的（绝对值大）
     *
     * @param matrix 矩阵matrix
     * @param i      从i行i列到末尾的这个子阵
     */
    private static void change(double[][] matrix, int i) {
        // 得到下面最大数的下标
        int[] maxNumIndex = getMaxNumIndex(matrix, i);
        // 如果最大数就是matrix[i][i]本身，则不交换
        if (maxNumIndex[0] == i && maxNumIndex[1] == i) {
            return;
        }
        // 如果列不相等
        if (maxNumIndex[1] != i) {
            symbol = !symbol;
            // 交换列
            for (int j = 0; j < matrix.length; j++) {
                double temp = matrix[j][i];
                matrix[j][i] = matrix[j][maxNumIndex[1]];
                matrix[j][maxNumIndex[1]] = temp;
            }
        }
        // 如果行不相等
        if (maxNumIndex[0] != i) {
            symbol = !symbol;
            // 交换行
            for (int j = 0; j < matrix[0].length; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[maxNumIndex[0]][j];
                matrix[maxNumIndex[0]][j] = temp;
            }
        }
    }

    /**
     * 得到从 i 行 i 列 到 末尾 这个子方阵中最大的那个数的下标
     *
     * @param matrix 矩阵matrix
     * @param i      从 i 行 i 列开始
     * @return 下标[i,j]
     */
    public static int[] getMaxNumIndex(double[][] matrix, int i) {
        double maxNum = Double.MIN_VALUE;
        int[] index = new int[2];
        Arrays.fill(index, i);
        for (int j = i; j < matrix.length; j++) {
            for (int k = i; k < matrix.length; k++) {
                if (Math.abs(matrix[j][k]) > maxNum) {
                    maxNum = matrix[j][k];
                    index[0] = j;
                    index[1] = k;
                }
            }
        }
        return index;
    }


    /**
     * 返回矩阵matrix的逆矩阵
     *
     * @param matrix 矩阵matrix
     * @return matrix的逆矩阵
     */
    public static double[][] inverse(double[][] matrix) {
        double det = det(matrix);
        if (Double.isNaN(det) || det == 0) {
            System.out.println("该矩阵不存在逆矩阵");
            return null;
        }
        // 得到matrix的伴随矩阵
        double[][] adjointMatrix = getAdjointMatrix(matrix);
        // 计算逆矩阵
        for (int i = 0; i < adjointMatrix.length; i++) {
            for (int j = 0; j < adjointMatrix.length; j++) {
                adjointMatrix[i][j] *= 1 / det;
            }
        }
        // 返回逆矩阵
        return adjointMatrix;
    }

    /**
     * 得到matrix的伴随矩阵
     *
     * @param matrix 矩阵matrix
     * @return matrix的伴随矩阵
     */
    private static double[][] getAdjointMatrix(double[][] matrix) {
        // matrix的阶
        int n = matrix.length;
        // 伴随矩阵
        double[][] adjointMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 得到matrix[i][j]的余子式
                double complement = getComplement(matrix, i, j);
                // 计算代数余子式
                double algebraicComplement = (int)Math.pow(-1, i + j + 2) * complement;
                // 赋值进伴随矩阵中
                adjointMatrix[j][i] = algebraicComplement;
            }
        }
        return adjointMatrix;
    }

    /**
     * 得到matrix[i][j]的余子式
     *
     * @param matrix 矩阵matrix
     * @param i      i行
     * @param j      j列
     * @return matrix[i][j]的余子式
     */
    private static double getComplement(double[][] matrix, int i, int j) {
        // 余子式的阶
        int n = matrix.length - 1;
        // 余子式
        double[][] complement = new double[n][n];
        // matrix的行指针
        int preI = 0;
        for (int k = 0; k < n; k++) {
            // matrix的列指针
            int preJ = 0;
            for (int l = 0; l < n; l++) {
                // 当前元素恰好是[i,j]
                if (preI == i && preJ == j) {
                    preI++;
                    preJ++;
                }
                // 行 = i
                if (preI == i) {
                    preI++;
                }
                // 列 = j
                if (preJ == j) {
                    preJ++;
                }
                complement[k][l] = matrix[preI][preJ];
                preJ++;
            }
            preI++;
        }
        return det(complement);
    }

    /**
     * 返回矩阵matrix的转置
     * @param matrix 矩阵matrix
     * @return matrix的转置
     */
    public static double[][] transpose(double[][] matrix){
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            System.out.println("矩阵不合法");
            return null;
        }
        // 行
        int row = matrix.length;
        // 列
        int col = matrix[0].length;
        // 转置后的矩阵
        double[][] tranMatrix = new double[col][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                tranMatrix[j][i] = matrix[i][j];
            }
        }
        return tranMatrix;
    }
}
