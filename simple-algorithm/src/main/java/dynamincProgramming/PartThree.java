package dynamincProgramming;

/**
 * @Description 动态规划 第3部分
 * @Date 2022-05-23 10:56
 * @Created by wangjie
 */
public class PartThree {

	//拼写纠错
	//莱文斯坦距离（Levenshtein distance）和最长公共子串长度（Longest common substring

	//多阶段决策最优解模型
	//两个字符串进行逐一字符比较,才有一定的比较策略,最后找出匹配到的最多字符串个数
	//匹配策略:
	//1)如果a[i]==b[j] 则继续比较 a[i+1] 和 b[j+1]
	//2)如果a[i]!=b[j] 1.比较a[i+1]和b[j] 2.比较a[i]和b[j+1] 3,比较a[i+1]和b[i+1] 其他所有情况,都会被以上3点操作所覆盖


	public static char[] a = "mitcmu".toCharArray();
	public static char[] b = "mtacnu".toCharArray();
	public static final int m = 6;
	public static final int n = 6;

	public static int mV = Integer.MAX_VALUE;

	/**
	 * 运用回溯进行a,b字符串的逐一字符比较,计算莱文斯坦距离
	 *
	 * @param i a比较字符位置
	 * @param j b比较字符位置
	 * @param v 两个字符串的差异编辑数量量
	 */
	public static void compare(int i, int j, int v) {
		//判断终止条件,即有一个字符串已经比较到结尾
		if (i == n || j == m) {
			if (i < n) { //说明b到了结尾,但a还未到结尾,多出的字符都是编辑量
				v += n - i;
			}
			if (j < m) { //同上
				v += m - j;
			}
			if (v < mV) {
				mV = v;
			}
			return;
		}
		if (a[i] == b[j]) { //匹配到了,则无编辑量,进行下一步匹配
			compare(i + 1, j + 1, v);
		} else { //不匹配,则有很多种情况
			compare(i, j + 1, v + 1); //1.b数组删掉j元素后进行匹配;2.或者a[i]前添加一个元素,同样是a[i]和b[j+1]比较
			compare(i + 1, j, v + 1); //同上,编辑量+1
			compare(i + 1, j + 1, v + 1); //替换a[i],b[j]其中一个,直接匹配下一个
		}
	}

	/**
	 * 运用动态规划来计算莱文斯坦距离
	 * 1.状态转移方程:f(i,j,minV)=
	 * if(a[i]==b[j]{
	 * min(f(i-1,j)+1,f(i,j-1)+1,f(i-1,j-1));
	 * }else{
	 * min(f(i-1,j)+1,f(i,j-1)+1,f(i-1,j-1)+1);
	 * }
	 * 2.思路分析 i,j分别为两个串比较字符的位置,minV表示处理到当前位置,已执行的最小编辑次数,包含当前位置的编辑处理
	 * 3.状态转移二维数组:f(0,0)=0 首先推出 f(1...n,0) 和f(0,1...m)的值,然后基于推出其他值
	 */
	public static int compare_dp() {
		int[][] f = new int[m][n];
		f[0][0] = (a[0] == b[0]) ? 0 : 1;
		for (int i = 1; i < m; i++) {
			f[i][0] = (a[i] == b[0]) ? i : f[i - 1][0] + 1;
		}
		for (int j = 1; j < n; j++) {
			f[0][j] = (a[0] == b[j]) ? j : f[0][j - 1] + 1;
		}
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				int min = Integer.MAX_VALUE;
				if (f[i - 1][j] + 1 < min)
					min = f[i - 1][j] + 1;
				if (f[i][j - 1] + 1 < min)
					min = f[i][j - 1] + 1;
				if (a[i] == b[j]) {
					if (f[i - 1][j - 1] < min)
						min = f[i - 1][j - 1];
				} else {
					if (f[i - 1][j - 1] + 1 < min) {
						min = f[i - 1][j - 1] + 1;
					}
				}
				f[i][j] = min; //拿到最小值,进行复制操作
			}

		}
		return f[m - 1][n - 1];
	}

	//计算最长公共子串长度,即比较时只能删除和新增元素时

	public static void main(String[] args) {
		//compare(0, 0, 0);
		//System.out.println("莱文斯坦距离:" + mV);

		int i = compare_dp();
		System.out.println("莱文斯坦距离:" + i);
	}
}
