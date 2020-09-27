package completableFuture;

import cn.hutool.crypto.digest.MD5;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Date 2020/8/25 16:26
 * @Created by wangjie
 */
public class test {

	public static void main(String[] args) throws InterruptedException {

		/*{
			List<String> list = new ArrayList<>();
			CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("执行1");
				list.add("abc" + "123");
			});
			CompletableFuture<Void> completableFuture2 = CompletableFuture.runAsync(() -> {
				System.out.println("执行2");
				list.add("abc" + "456");
			});

//		completableFuture1.thenAccept(x -> list.add(x));
//		completableFuture2.thenAccept(y -> list.add(y));

			CompletableFuture<Void> completableFuture3 = CompletableFuture.allOf(completableFuture1, completableFuture2);
			completableFuture3.thenAccept(x -> System.out.println(list));

		}*/

		/*{
			get();
		}*/

		/*{
			MD5 md5 = MD5.create();
			String jiajia = md5.digestHex16("jiajia");
			String s = md5.digestHex16("jiajia!!");
			System.out.println(jiajia);
			System.out.println(s);
		}*/
		/*{
			test();
		}*/
		Test test = new Test();
		List<String> list = new ArrayList<>();
		list.add("1");
		test.setaList(list);
		test.setbList(new ArrayList<>());

		List<String> list1 = test.getbList();
		List<String> aaa = new ArrayList<>();
		aaa.addAll(list1);
		List<String> list2 = test.getaList();
		aaa.addAll(list2);

		System.out.println("");
	}

	static class Test {
		List<String> aList;
		List<String> bList;

		public List<String> getaList() {
			return aList;
		}

		public void setaList(List<String> aList) {
			this.aList = aList;
		}

		public List<String> getbList() {
			return bList;
		}

		public void setbList(List<String> bList) {
			this.bList = bList;
		}
	}


	private static void test() {
		CompletableFuture.runAsync(() -> System.out.println("123"));
		String a = null;
		CompletableFuture.runAsync(() -> System.out.println("result:" + a.equals("1")))
				.exceptionally((e) -> {
					System.out.println("执行异常:" + e.getMessage());
					return null;
				});
	}

	private static void get() throws InterruptedException {
		List<String> list = new ArrayList<>();
		CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("执行1");
			return "abc" + "123";
		});
		CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
			System.out.println("执行2");
			return "def" + "456";
		});

		CompletableFuture<Void> voidCompletableFuture1 = completableFuture1.thenAccept(x -> list.add(x));
		CompletableFuture<Void> voidCompletableFuture2 = completableFuture2.thenAccept(y -> list.add(y));

		CompletableFuture<Void> completableFuture3 = CompletableFuture.allOf(voidCompletableFuture1, voidCompletableFuture2);
		completableFuture3.thenAccept(x -> System.out.println(list));

		TimeUnit.SECONDS.sleep(100);
	}
}
