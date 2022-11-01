package org.llbqhh.study.algorithm.leetcode.数组;

// 动态规划
public class L64_最小路径和 {
    //给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
//
// 说明：每次只能向下或者向右移动一步。
//
// 示例:
//
// 输入:
//[
//  [1,3,1],
//  [1,5,1],
//  [4,2,1]
//]
//输出: 7
//解释: 因为路径 1→3→1→1→1 的总和最小。
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int rows = grid.length;
        int columns = grid[0].length;

        // 使用动态规划，dp存的是从左上角到每个点的最小路径和
        int[][] dp = new int[rows][columns];
        // 原点的最小路径和等于它本身的值
        dp[0][0] = grid[0][0];
        // 第一行的点都只能从左上角一步步向右走来，所以第一行的最小路径和为对应的路径上的数字和
        for (int j = 1; j < columns; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        // 第一列的点都只能从左上角一步步向下走来，所以第一列的最小路径和为对应的路径上的数字和
        for (int i = 1; i < rows; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        // 从第二行的第二个元素开始，每个点的最小路径值为它上方点最小路径和和左侧点最小路径和的最小值加这个点本身的值
        for (int k = 1; k < rows; k++) {
            for (int n = 1; n < columns; n++) {
                dp[k][n] = Math.min(dp[k-1][n], dp[k][n-1]) + grid[k][n];
            }
        }
        return dp[rows-1][columns-1];
    }

    public static void main(String[] args) {
        int[][] grid = {{1,3,1},{1,5,1},{4,2,1}};
        System.out.println(new L64_最小路径和().minPathSum(grid));
    }
}
