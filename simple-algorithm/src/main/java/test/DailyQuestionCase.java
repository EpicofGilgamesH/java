package test;

public class DailyQuestionCase {

	/**
	 * 860. 柠檬水找零
	 * 在柠檬水摊上，每一杯柠檬水的售价为5美元。顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
	 * <p>
	 * 每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
	 * <p>
	 * 注意，一开始你手头没有任何零钱。
	 * <p>
	 * 给你一个整数数组 bills ，其中 bills[i] 是第 i 位顾客付的账。如果你能给每位顾客正确找零，返回true，否则返回 false。
	 * <p>
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：bills = [5,5,5,10,20]
	 * 输出：true
	 * 解释：
	 * 前 3 位顾客那里，我们按顺序收取 3 张 5 美元的钞票。
	 * 第 4 位顾客那里，我们收取一张 10 美元的钞票，并返还 5 美元。
	 * 第 5 位顾客那里，我们找还一张 10 美元的钞票和一张 5 美元的钞票。
	 * 由于所有客户都得到了正确的找零，所以我们输出 true。
	 * 示例 2：
	 * <p>
	 * 输入：bills = [5,5,10,10,20]
	 * 输出：false
	 * 解释：
	 * 前 2 位顾客那里，我们按顺序收取 2 张 5 美元的钞票。
	 * 对于接下来的 2 位顾客，我们收取一张 10 美元的钞票，然后返还 5 美元。
	 * 对于最后一位顾客，我们无法退回 15 美元，因为我们现在只有两张 10 美元的钞票。
	 * 由于不是每位顾客都得到了正确的找零，所以答案是 false。
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= bills.length <= 105
	 * bills[i]不是5就是10或是20
	 * <p>
	 */
	static class LemonadeChange {

		/**
		 * 个人思路:
		 * 常规来说,找零思路就是,遇见5即手中5的个数+1;遇见10在手中10的个数+1,同时5的个数-1;遇见20则手中10,5各-1,20+1
		 * 实际上20的个数不用计数,因为不能作为零钱来找
		 *
		 * @param bills
		 * @return
		 */
		public static boolean lemonadeChange(int[] bills) {

			int five = 0, ten = 0;
			for (int i = 0; i < bills.length; i++) {
				if (bills[i] == 5) {
					five++;
				} else if (bills[i] == 10) {
					five--;
					ten++;
				} else {  // 20块有两种找法 1.5+10 2.5+5+5
					if (ten == 0) {
						five -= 3;
					} else {
						five--;
						ten--;
					}
				}
				if (five < 0 || ten < 0)
					return false;
			}
			return true;
		}

		public static void main(String[] args) {

			int[] bills = {5, 5, 10, 20, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 20, 5, 20, 5};
			boolean b = lemonadeChange(bills);
			System.out.println(b);
		}
	}
}
