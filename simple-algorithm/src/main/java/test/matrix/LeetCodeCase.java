package test.matrix;

public class LeetCodeCase {

	/**
	 * 73. 矩阵置零
	 * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
	 * 示例 1：
	 * 输入：matrix = [[1,1,1],[1,0,1],[1,1,1]]
	 * 输出：[[1,0,1],[0,0,0],[1,0,1]]
	 * 示例 2：
	 * 输入：matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
	 * 输出：[[0,0,0,0],[0,4,5,0],[0,3,1,0]]
	 * 提示：
	 * m == matrix.length
	 * n == matrix[0].length
	 * 1 <= m, n <= 200
	 * -231 <= matrix[i][j] <= 231 - 1
	 * 进阶：
	 * 一个直观的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
	 * 一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
	 * 你能想出一个仅使用常量空间的解决方案吗？
	 */
	public static class SetZeroes {

		/**
		 * 个人思路：
		 * 题意中额外空间 O(mn)则是新建一个二维数字来存放为0的坐标
		 * O(m+n)则是将二维数组变成一位数组
		 * 如何能达到额外空间复杂度O(1)呢?
		 * 通过一次遍历需要知道二维数组的哪一行、哪一列 需要全部置为0
		 * 我们要在遍历中去修改矩阵中的值,同时记录已修改的行和列
		 * 先使用简单的思路来实现,记录哪一行那一列需要被置为0
		 *
		 * @param matrix
		 */
		public static void setZeroes(int[][] matrix) {
			int[] row = new int[matrix.length];
			int[] col = new int[matrix[0].length];
			for (int i = 0; i < row.length; i++) {
				for (int j = 0; j < col.length; j++) {
					if (matrix[i][j] == 0) {
						row[i] = 1;
						col[j] = 1;
					}
				}
			}
			/*for (int i = 0; i < row.length; i++) { // 行
				for (int j = 0; j < col.length; j++) {
					if (row[i] == 1) matrix[i][j] = 0;
				}
			}
			for (int i = 0; i < col.length; i++) { // 列
				for (int j = 0; j < row.length; j++) {
					if (col[i] == 1) matrix[j][i] = 0;
				}
			}*/
			// 行和列的更新可以同时进行
			for (int i = 0; i < row.length; i++) {
				for (int j = 0; j < col.length; j++) {
					if (row[i] == 1 || col[j] == 1) {
						matrix[i][j] = 0;
					}
				}
			}
		}

		public static void main(String[] args) {
			/*int[][] ints = {{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};*/
			int[][] ints = {{0}, {1}};
			setZeroes(ints);
			System.out.println(ints);
		}
	}
}
