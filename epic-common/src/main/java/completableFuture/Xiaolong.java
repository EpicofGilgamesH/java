package completableFuture;

/**
 * @Description TODO
 * @Date 2020/9/1 19:12
 * @Created by wangjie
 */
public class Xiaolong {

	public static void main(String[] args) {

		//System.out.println("This is my first javacode");
		int i = 1;
		int sum = 0;
		int count = 0;
		do {
			if (i % 2 == 0)
			{
				count++;
				sum += i;
				System.out.println(i);
			}
			i++;
		} while (i <= 100);
		System.out.println("偶数的个数:" + count + " ," + "sum值为:" + sum);
	}
}


