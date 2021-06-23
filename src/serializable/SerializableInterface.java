package serializable;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description Serializable 序列化标识的使用
 * @Date 2021-06-02 14:24
 * @Created by wangjie
 */
public class SerializableInterface {

	public static File file = new File("C:\\Users\\Administrator\\Desktop", "serializable.txt");

	public static void main(String[] args) {
		try {
			writeUser();
			System.out.println("------------------");
			TimeUnit.SECONDS.sleep(3);
			System.out.println(JSON.toJSONString(readUser()));
		} catch (IOException | InterruptedException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void writeUser() throws IOException {
		User user = new User("yl", "123", 12);
		/*user.setName("yl");
		user.setTelephone("123");
		user.setOld(12);*/

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
		objectOutputStream.writeObject(user);
		System.out.println("序列化成功");
		objectOutputStream.close();
	}

	private static User readUser() throws IOException, ClassNotFoundException {
		User user = null;
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
		Object o = objectInputStream.readObject();
		if (o instanceof User) {
			user = (User) o;
		}
		return user;
	}

	static class User implements Serializable {
		private static final long serialVersionUID = 3674478122746531770L;

		public User(String name, String tel, int old) {
			this.name = name;
			this.telephone = tel;
			this.old = old;
		}

		private String name;
		private String telephone;
		private int old;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public int getOld() {
			return old;
		}

		public void setOld(int old) {
			this.old = old;
		}
	}
}
