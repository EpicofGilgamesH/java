package backtracking;

/**
 * @Description 回溯算法 - 八皇后
 * @Date 2022-05-09 9:47
 * @Created by wangjie
 */
public class EightQueen {

	//八皇后
	//8x8的方格中,放入8个"皇后",要求每个"皇后"的行、列、对角线不能有其他"皇后",找到所有的存放情况
	//初步分析:方格中,每行、每列都只能放一个"皇后"
	private static int[] result = new int[8]; //index表示行,value表示 'queen' 所在列

	/**
	 * 每行怎么放置的方法
	 *
	 * @param row
	 */
	public static void calc(int row) {
		if (row == 8) { //所有行都放置完成,打印
			printQ(result);
			return;
		}
		for (int i = 0; i < 8; i++) { //指定的行,从第一列开始尝试,该元素是否能放置
			if (isOk(row, i)) { //可以放在这一列,则赋值
				result[row] = i;
				calc(row + 1); //考察下一行
			}
		}
	}

	/**
	 * 判断是否能放在这一行一列,判断本行、本列、左上对角线、右上对角线是否存在 'queen'
	 *
	 * @param row
	 * @param column
	 * @return
	 */
	public static boolean isOk(int row, int column) {
		int leftUp = column - 1, rightUp = column + 1;
		for (int i = row - 1; i >= 0; i--, leftUp--, rightUp++) {  //逐行往上进行遍历,查找上面的行是否存在相同列
			if (result[i] == column) return false; //判断是否存在同一列的 'queen'
			if (leftUp >= 0) { //判断左上对角线
				if (result[i] == leftUp)
					return false;
			}
			if (rightUp < 8) { //判断右上对角线
				if (result[i] == rightUp)
					return false;
			}
		}
		return true;
	}

	/**
	 * 打印二维矩阵
	 *
	 * @param result
	 */
	public static void printQ(int[] result) {
		for (int i = 0; i < 8; i++) { //行
			for (int j = 0; j < 8; j++) {
				if (result[i] == j)
					System.out.print("o ");
				else
					System.out.print("* ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	public static void main(String[] args) {
		calc(0);
		System.out.println("");
	}
}
