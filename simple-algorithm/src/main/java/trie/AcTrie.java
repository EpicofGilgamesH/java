package trie;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description AcTrie
 * @Date 2022-04-24 18:14
 * @Created by wangjie
 */
public class AcTrie {

	public AcNode root = new AcNode('/'); //根节点
	private static final byte A_ASCII = 'a';

	/**
	 * 一个trie树节点 包含当前节点存放的字母和下一个节点所在的文字
	 */
	public static class AcNode {
		public char data; //当前节点数据
		public AcNode[] childes = new AcNode[26];  //26个字母
		public boolean isEnding = false; //是否为根节点
		public int length = -1; //isEnding=true时,记录模式串长度
		public AcNode fail; //失败指针

		public AcNode(char data) {
			this.data = data;
		}
	}

	/**
	 * 将字符串存储到trie树中
	 *
	 * @param text
	 */
	public void insert(char[] text) {
		AcNode p = root;
		for (byte i = 0; i < text.length; i++) {
			//找到子节点的序列 index,如果子节点还未生成,则创建
			byte index = (byte) (text[i] - A_ASCII);
			if (p.childes[index] == null) {
				AcNode node = new AcNode(text[i]);
				p.childes[index] = node;
			}
			p = p.childes[index];
		}
		p.isEnding = true; //一个字符串插入完成后,最后一个节点为尾节点
		p.length = text.length;
	}

	/**
	 * 查找字符串
	 *
	 * @param pattern
	 * @return
	 */
	public boolean find(char[] pattern) {
		AcNode p = root;
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

	/**
	 * 构建失败指针,同理kpm的next数组
	 */
	public void buildFailurePoint() {

		Queue<AcNode> queue = new LinkedList<>();
		root.fail = null;
		queue.add(root);
		while (!queue.isEmpty()) {
			//取出队头元素
			AcNode p = queue.remove();
			for (int i = 0; i < 26; i++) {
				AcNode pc = p.childes[i];
				if (pc == null) continue;
				if (p == root)
					pc.fail = root;
				else {
					AcNode q = p.fail;
					while (q != null) {
						AcNode qc = q.childes[pc.data - A_ASCII];
						if (qc != null) { //存在
							pc.fail = qc;
							break;
						}
						q = q.fail;
					}
					if (q == null) { //root
						pc.fail = root;
					}
				}
				//每次处理的是p下面的子节点pc,需要将pc加入queue中
				queue.add(pc);
			}
		}
	}

	/**
	 * AC匹配
	 *
	 * @param text
	 */
	public void match(char[] text) { // text是主串
		int n = text.length;
		AcNode p = root;
		for (int i = 0; i < n; ++i) {
			int idx = text[i] - 'a';
			while (p.childes[idx] == null && p != root) {
				p = p.fail; // 失败指针发挥作用的地方
			}
			p = p.childes[idx];
			if (p == null) p = root; // 如果没有匹配的，从root开始重新匹配
			AcNode tmp = p;
			while (tmp != root) { // 打印出可以匹配的模式串
				if (tmp.isEnding) {
					int pos = i - tmp.length + 1;
					System.out.println("匹配起始下标" + pos + "; 长度" + tmp.length);
				}
				tmp = tmp.fail;
			}
		}
	}

	public static void main(String[] args) {

		AcTrie acTrie = new AcTrie();
		acTrie.insert("abce".toCharArray());
		acTrie.insert("bcd".toCharArray());
		acTrie.insert("ce".toCharArray());

		acTrie.buildFailurePoint();

		acTrie.match("fabcdegfabcabce".toCharArray());

		System.out.println("");
	}
}
