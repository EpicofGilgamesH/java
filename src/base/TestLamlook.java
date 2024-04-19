package base;

import java.io.Serializable;

/**
 * @Description TODO
 * @Date 2021-12-31 14:49
 * @Created by wangjie
 */
public class TestLamlook {


	static class TestVo implements Serializable {
		private static final long serialVersionUID = 1573758848744025102L;
		private float piece;
		private String scanDate;

	}

	public static void main(String[] args) {
		String phone = "13628560155";
		String substring = phone.substring(phone.length() - 4);
		System.out.println(substring);
	}
}
