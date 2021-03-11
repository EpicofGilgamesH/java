package twentyseven;

/**
 * @Description 递归树, 分析时间复杂度
 * @Date 2021-02-23 9:50
 * @Created by wangjie
 */
public class practice {

	//int[] a={1,2,3,4} printPermutations(a,4,4)
	//n表示
	//k表示需要处理的子数组个数
	//递推公式  f(1,2,3...n)={最后一位是 1+f(n-1),最后一位是 2+f(n-1)....最后一位是 n+f(n-1)
	public static void printPermutations(int[] datas, int n, int k) {
		if (k == 1) {
			for (int i = 0; i < n; ++i) {
				System.out.print(datas[i] + " ");
			}
			System.out.println("");
		}
		for (int i = 0; i < k; ++i) {
			int temp = datas[i];
			datas[i] = datas[k - 1];
			datas[k - 1] = temp;

			printPermutations(datas, n, k - 1);

			temp = datas[i];
			datas[i] = datas[k - 1];
			datas[k - 1] = temp;

		}
	}

	public static void main(String[] args) {
		/*int[] data = {1, 2, 3, 4};
		printPermutations(data, 4, 4);*/
		System.out.println(Integer.toBinaryString(58));
		String trueIndex = getTrueIndex(58);
		System.out.println(trueIndex);

		getStringIndex("111010");

		int i = ~12 ;
		System.out.println(i);
	}


	/**
	 * 用2^n次方做开关组合,一个组合相加得到的值只能反向解析成这个组合的方式
	 * 由于不超过20个开关的自由组合设置(数据大达到Integer最大值,则运算效率变低)
	 * eg: 1 2 4 8 16 32 64 128 512
	 * 2+4+8+32+128 的二进制位为1010 1110 解析 n值的二进制位上为1的位置,即能得到此唯一组合
	 *
	 * @param n
	 * @return
	 */
	private static String getTrueIndex(Integer n) {
		String str = "";
		int index = 1;
		while (n != 0) {
			//判断n最后一位是否为1
			if ((n & 1) == 1) {
				str += index + " ";
			}
			n = n >> 1; //将n向右移动一位,继续判断其第二位是否为1
			index++;
		}
		return str;
	}

	private static int[] getTrueIndexFixSize(int n, int size) {
		int[] result = new int[size];
		return result;
	}

	/**
	 * 将开关组合设置到String中,1表示开 0表示关,按照String的index进行排序
	 *
	 * @param str
	 * @return
	 */
	private static String getStringIndex(String str) {
		byte[] bytes = str.getBytes();
		for (int i = 0; i < bytes.length; i++) {

		}
		return "";
	}


}
