package lang.String;

/**
 * @Description java字符串使用
 * @Date 2021-07-23 9:38
 * @Created by wangjie
 */
public class CharacterString {

	public static void main(String[] args) {
		StringBuilder stringBuilder = new StringBuilder();

		final char[] value = {'a', 'b', 'c', ' ', ' ', ' '};
		int len = 3;
		value[++len] = 'd';
		value[++len] = 'e';
		value[++len] = 'f';

		System.out.println("");
	}
}
