package test.trie;

/**
 * trie树类型题
 * <a href="https://blog.csdn.net/m0_46202073/article/details/107253959">csdn</a>
 */
public class LeetCodeCase {

	/**
	 * 208. 实现 Trie (前缀树)
	 * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补全和拼写检查。
	 * 请你实现 Trie 类：
	 * Trie() 初始化前缀树对象。
	 * void insert(String word) 向前缀树中插入字符串 word 。
	 * boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
	 * boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
	 * 示例：
	 * 输入
	 * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
	 * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
	 * 输出
	 * [null, null, true, false, true, null, true]
	 * 解释
	 * Trie trie = new Trie();
	 * trie.insert("apple");
	 * trie.search("apple");   // 返回 True
	 * trie.search("app");     // 返回 False
	 * trie.startsWith("app"); // 返回 True
	 * trie.insert("app");
	 * trie.search("app");     // 返回 True
	 * 提示：
	 * 1 <= word.length, prefix.length <= 2000
	 * word 和 prefix 仅由小写英文字母组成
	 * insert、search 和 startsWith 调用次数 总计 不超过 3 * 104 次
	 */
	static class Trie {

		private TrieNode root; // 根节点

		static class TrieNode {
			private char data; // 这个场景data可以不需要,通过idx就能判断字符
			private TrieNode[] children = new TrieNode[26];
			private boolean isEnding = false; // 是否为尾节点,即children中为空

			public TrieNode(char data) {
				this.data = data;
			}
		}

		/**
		 * trie树,题目要求仅由小写字母组成,那么可以看成一个26叉数,每个节点都有26个子节点
		 */
		public Trie() {
			root = new TrieNode('/');
		}

		public void insert(String word) {
			TrieNode n = root;
			for (int i = 0; i < word.length(); i++) {
				int idx = word.charAt(i) - 'a';
				if (n.children[idx] == null) {  // 子节点中不存在该字符
					TrieNode r = new TrieNode(word.charAt(i));
					n.children[idx] = r;
				}
				n = n.children[idx];
			}
			n.isEnding = true; // 标记该单词的结尾节点
		}

		public boolean search(String word) {
			TrieNode p = root;
			for (int i = 0; i < word.length(); i++) {
				int idx = word.charAt(i) - 'a';
				TrieNode r = p.children[idx];
				if (r == null) {
					return false;
				}
				p = r;
			}
			return p.isEnding;
		}

		public boolean startsWith(String prefix) {
			TrieNode p = root;
			for (int i = 0; i < prefix.length(); i++) {
				int idx = prefix.charAt(i) - 'a';
				TrieNode r = p.children[idx];
				if (r == null) {
					return false;
				}
				p = r;
			}
			return true;
		}

		public static void main(String[] args) {
			Trie trie = new Trie();
			trie.insert("apple");
			System.out.println(trie.search("apple"));
			System.out.println(trie.search("app"));
			System.out.println(trie.startsWith("app"));
			trie.insert("app");
			System.out.println(trie.search("app"));
		}
	}
}
