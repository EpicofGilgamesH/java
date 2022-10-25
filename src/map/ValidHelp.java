package map;

/**
 * @Description ValidHelp
 * @Date 2022-10-21 17:06
 * @Created by wangjie
 */
public class ValidHelp {

	public static void main(String[] args) {
		Entry[] es = new Entry[10];
		Entry a = new Entry("a", "1");
		es[0] = a;
		es[1] = new Entry("b", "2", a);
		Entry e;
		if ((e = es[1]) != null) {
			es[1] = null;
			if(e.next!=null){
				System.out.println("");
			}
		}
	}

	static class Entry {
		private Object key;
		private Object value;

		private Entry next;

		public Entry(Object k, Object v) {
			this.key = k;
			this.value = v;
		}

		public Entry(Object k, Object v, Entry n) {
			this(k, v);
			this.next = n;
		}

		public Object getKey() {
			return key;
		}

		public void setKey(Object key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}
}
