package singleton;

/**
 * 枚举模式实现单例
 */
public class EnumSingleton {

	private EnumSingleton() {

	}

	public static EnumSingleton getInstance() {
		return Holder.HOLDER.instance;
	}

	private enum Holder {
		HOLDER;
		private final EnumSingleton instance;

		Holder() {
			instance = new EnumSingleton();
		}
	}

}
