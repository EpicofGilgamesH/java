package test.trie;

import java.util.*;

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

	/**
	 * 1268. 搜索推荐系统
	 * 给你一个产品数组 products 和一个字符串 searchWord ，products  数组中每个产品都是一个字符串。
	 * 请你设计一个推荐系统，在依次输入单词 searchWord 的每一个字母后，推荐 products 数组中前缀与 searchWord 相同的最多三个产品。如果前缀相同的可推荐产品超过三个，请按字典序返回最小的三个。
	 * 请你以二维列表的形式，返回在输入 searchWord 每个字母后相应的推荐产品的列表。
	 * 示例 1：
	 * 输入：products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
	 * 输出：[
	 * ["mobile","moneypot","monitor"],
	 * ["mobile","moneypot","monitor"],
	 * ["mouse","mousepad"],
	 * ["mouse","mousepad"],
	 * ["mouse","mousepad"]
	 * ]
	 * 解释：按字典序排序后的产品列表是 ["mobile","moneypot","monitor","mouse","mousepad"]
	 * 输入 m 和 mo，由于所有产品的前缀都相同，所以系统返回字典序最小的三个产品 ["mobile","moneypot","monitor"]
	 * 输入 mou， mous 和 mouse 后系统都返回 ["mouse","mousepad"]
	 * 示例 2：
	 * 输入：products = ["havana"], searchWord = "havana"
	 * 输出：[["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
	 * 示例 3：
	 * 输入：products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
	 * 输出：[["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
	 * 示例 4：
	 * 输入：products = ["havana"], searchWord = "tatiana"
	 * 输出：[[],[],[],[],[],[],[]]
	 * 提示：
	 * 1 <= products.length <= 1000
	 * 1 <= Σ products[i].length <= 2 * 10^4
	 * products[i] 中所有的字符都是小写英文字母。
	 * 1 <= searchWord.length <= 1000
	 * searchWord 中所有字符都是小写英文字母。
	 */
	static class SuggestedProducts {

		/**
		 * 思路：
		 * 1.构建tire树
		 * 2.找到tire树指定的节点,先序遍历找前三
		 *
		 * @param products
		 * @param searchWord
		 * @return
		 */
		public static List<List<String>> suggestedProducts(String[] products, String searchWord) {
			// 排序后构建tire树
			Arrays.sort(products);
			MyTire tire = new MyTire();
			for (String product : products) {
				tire.insert(product);
			}
			StringBuilder sb = new StringBuilder();
			List<List<String>> res = new ArrayList<>();
			for (char c : searchWord.toCharArray()) {
				sb.append(c);
				List<String> list = tire.startWithAndLenovo(sb.toString());
				if (list.size() > 3)
					res.add(list.subList(0, 3));
				else
					res.add(list);
			}
			return res;
		}

		public static void main(String[] args) {
			List<List<String>> lists = suggestedProducts(new String[]{"mobile", "mouse", "moneypot", "monitor", "mousepad"}, "mouse");
			System.out.println(lists);
		}

		static class Node {
			public Node(char c) {
				this.c = c;
			}

			private char c;
			private Node[] children = new Node[26];
			private boolean isEnding = false;
		}

		static class MyTire {
			public MyTire() {
				root = new Node('/');
			}

			private Node root; // 根节点

			/**
			 * 添加单词
			 *
			 * @param word
			 */
			private void insert(String word) {
				Node n = root;
				for (char c : word.toCharArray()) {
					int idx = c - 'a';
					if (n.children[idx] == null) {  // 当前子节点树不存在该字符时
						n.children[idx] = new Node(c);
					}
					n = n.children[idx];
				}
				n.isEnding = true;  // 标记该单词结尾了,如此才能区分出这个单词
			}

			/**
			 * 查找单词,依次遍历tire树,遍历完成时,如果最后的节点isEnding=true,说明存在该单词
			 *
			 * @param word
			 * @return
			 */
			private boolean search(String word) {
				Node n = root;
				for (char c : word.toCharArray()) {
					int idx = c - 'a';
					if (n.children[idx] == null) {
						return false;
					}
					n = n.children[idx];
				}
				return n.isEnding;
			}

			/**
			 * 以输入的字符串开头,联想出三个单词
			 *
			 * @param word
			 * @return
			 */
			private List<String> startWithAndLenovo(String word) {
				List<String> list = new ArrayList<>();
				StringBuilder sb = new StringBuilder();
				Node n = root;
				for (char c : word.toCharArray()) {
					int idx = c - 'a';
					if (n.children[idx] == null) {
						return list;
					}
					sb.append(c);
					n = n.children[idx];
				}
				// 以n节点为开始,做先序遍历,查找3个字符串后结束
				dfs(n, 3, sb, list);
				 return list;
			}

			/**
			 * 使用dfs,一定会遍历完整个26叉数
			 *
			 * @param node
			 * @param n
			 * @param sb
			 * @param list
			 */
			private void dfs(Node node, int n, StringBuilder sb, List<String> list) {
				if (node == null) return;
				if (node.isEnding) {
					list.add(sb.toString());
					if (list.size() >= n) return;
				}
				for (Node child : node.children) {
					if (child != null) {
						sb.append(child.c);
						dfs(child, n, sb, list);
						sb.deleteCharAt(sb.length() - 1);
					}
				}
			}
		}
	}
}
