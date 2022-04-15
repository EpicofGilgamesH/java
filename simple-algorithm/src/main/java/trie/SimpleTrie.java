package trie;

/**
 * @Description 简单Trie数思想
 * @Date 2021-12-01 14:24
 * @Created by wangjie
 */
public class SimpleTrie {

	public TrieNode root = new TrieNode('/'); //根节点
	private static final byte A_ASCII = 'a';

	/**
	 * 一个trie树节点 包含当前节点存放的字母和下一个节点所在的文字
	 */
	public static class TrieNode {
		public char data; //当前节点数据
		public TrieNode[] childes = new TrieNode[26];  //26个字母
		public boolean isEnding = false; //是否为根节点

		public TrieNode(char data) {
			this.data = data;
		}
	}

	/**
	 * 将字符串存储到trie树中
	 *
	 * @param text
	 */
	public void insert(char[] text) {
		TrieNode p = root;
		for (byte i = 0; i < text.length; i++) {
			//找到子节点的序列 index,如果子节点还未生成,则创建
			byte index = (byte) (text[i] - A_ASCII);
			if (p.childes[index] == null) {
				TrieNode node = new TrieNode(text[i]);
				p.childes[index] = node;
			}
			p = p.childes[index];
		}
		p.isEnding = true; //一个字符串插入完成后,最后一个节点为尾节点
	}

	/**
	 * 查找字符串
	 *
	 * @param pattern
	 * @return
	 */
	public boolean find(char[] pattern) {
		TrieNode p = root;
		for (byte i = 0; i < pattern.length; i++) {
			byte index = (byte) (pattern[i] - A_ASCII);
			if (p.childes[index] == null) {
				//不存在
				return false;
			}
			p = p.childes[index];
		}
		return p.isEnding; //如果完全匹配且最后一个节点为尾节点,则匹配成功
	}


	public static void main(String[] args) {
		char[] text = "abcedfg".toCharArray();

		for (byte i = 0; i < text.length; i++) {
			byte index = (byte) (text[i] - A_ASCII);
			System.out.println(index);
		}

		SimpleTrie trie = new SimpleTrie();
		trie.insert("hello".toCharArray());
		trie.insert("hi".toCharArray());
		trie.insert("his".toCharArray());
		trie.insert("hit".toCharArray());

		boolean b = trie.find("hi".toCharArray());
		System.out.println(b);

		String a = " ";
		String[] split = a.split(":");
		System.out.println("");

		Long max = 0x7fffffffffffffffL;

		boolean flag;
		int i = 0;
		long l = 1;
		long r;
		do {
			r = l;
			l *= 26;
			i++;
			flag = l >= 0;
		}
		while (flag);
		System.out.println(r);
		System.out.println(i - 2);

		double pow = Math.pow(26, 13);
		long l1 = new Double(pow).longValue();
		System.out.println("26的16次方:" + l1);
		System.out.println("long的最大值:" + max);

		long l2 = 9169742482168496128L;
		long l3 = l2 * 26;
		System.out.println("");

		//9223372036854775807 max
		//9169742482168496128 26^14
		//2481152873203736576 26^13
		//Math.pow 在次方运算时,超出long类型最大值时,以其最大值为准
		//26^n long类型最多只支持n=13,当26个字母用26进制时,最多只支持13个字母的串表示
	}
}
