package completableFuture;

/**
 * @Description TODO
 * @Date 2020/9/2 11:13
 * @Created by wangjie
 */
public class Xiaolong1 {

	public static void main(String[] args) {
		boolean isflag = true;
		String detail = "收支\t账户金额\t收支金额\t说明";
		int balance = 10000;
		do {
			System.out.println("======收支记账软件=======");
			System.out.println();
			System.out.println("      1收支明细");
			System.out.println("      2登记收入");
			System.out.println("      3登记支出");
			System.out.println("      4退出");
			System.out.println("      请选择(1-4):");
			char menuselect = Utility.readMenuSelection();
			switch (menuselect) {
				case '1':
					System.out.println("=====当前收支明细记录=====");
					System.out.println(detail);
					System.out.println("========================");
					break;
				case '2':
					System.out.println("========================");
					System.out.print("本次收入金额");
					int income = Utility.readNumber();
					balance += income;
					System.out.print("本次收入说明");
					String incomedes = Utility.readString();
					detail += "\n收入" + "\t" + balance + "\t" + income + "\t" + incomedes;
					System.out.println("=========登记完成========");
					break;
				case '3':
					System.out.println("3");
					break;
				case '4':
					System.out.println("确认是否退出(Y/N):");
					char confiremenu = Utility.readConfirmSelection();
					if (confiremenu == 'Y') {
						isflag = false;
					}
					break;
			}
		} while (isflag);
	}
}
