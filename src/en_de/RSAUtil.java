package en_de;

/**
 * @Description 非对称加密RSA
 * @Date 2021-09-29 11:16
 * @Created by wangjie
 */
public class RSAUtil {

	/**
	 * 发送方
	 */
	public static class Sender extends RSABase {

		private Sender() {
			super(Mode.SENDER);
		}

		private static class SenderSingletonInstance {
			private static final Sender sender = new Sender();
		}

		public static Sender getInstance() {
			return SenderSingletonInstance.sender;
		}
	}

	/**
	 * 接收方
	 */
	public static class Receiver extends RSABase {

		protected Receiver() {
			super(Mode.RECEIVER);
		}
	}

}
