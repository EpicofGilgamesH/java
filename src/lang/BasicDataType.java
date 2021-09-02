package lang;

/**
 * @Description 基础数据类型知识
 * @Date 2021-07-03 11:33
 * @Created by wangjie
 */
public class BasicDataType {

	/**
	 * 单纯使用i++ 和++i 时没有区别,因为没有作为右值进行赋值操作
	 * 但是i++按某些文档来说,会生成临时变量,比++i成本高一些
	 * 见：https://www.zhihu.com/question/19811087
	 * @param args
	 */
	public static void main(String[] args) {
		int i = 0;
		i = i++;
	}

}

