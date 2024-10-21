package test.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 队列
 */
public class LeetCodeCase {


	/**
	 * 933. 最近的请求次数
	 * 写一个 RecentCounter 类来计算特定时间范围内最近的请求。
	 * 请你实现 RecentCounter 类：
	 * RecentCounter() 初始化计数器，请求数为 0 。
	 * int ping(int t) 在时间 t 添加一个新请求，其中 t 表示以毫秒为单位的某个时间，并返回过去 3000 毫秒内发生的所有请求数（包括新请求）。确切地说，返回在 [t-3000, t] 内发生的请求数。
	 * 保证 每次对 ping 的调用都使用比之前更大的 t 值。
	 * 示例 1：
	 * 输入：
	 * ["RecentCounter", "ping", "ping", "ping", "ping"]
	 * [[], [1], [100], [3001], [3002]]
	 * 输出：
	 * [null, 1, 2, 3, 3]
	 * 解释：
	 * RecentCounter recentCounter = new RecentCounter();
	 * recentCounter.ping(1);     // requests = [1]，范围是 [-2999,1]，返回 1
	 * recentCounter.ping(100);   // requests = [1, 100]，范围是 [-2900,100]，返回 2
	 * recentCounter.ping(3001);  // requests = [1, 100, 3001]，范围是 [1,3001]，返回 3
	 * recentCounter.ping(3002);  // requests = [1, 100, 3001, 3002]，范围是 [2,3002]，返回 3
	 * 提示：
	 * 1 <= t <= 109
	 * 保证每次对 ping 调用所使用的 t 值都 严格递增
	 * 至多调用 ping 方法 104 次
	 */
	static class RecentCounter {

		/**
		 * 思路:
		 * 用队列来模拟请求的出队和入队,当然队列中的请求只能保存时间范围3000ms以内的数据个数
		 * 当新进入的请求时间减最开始进入的请求时间:t-t1>3000 则t1需要出队,直到满足t-t1<=3000,则返回队列的长度
		 */
		private final LinkedList<Integer> queue;

		public RecentCounter() {
			queue = new LinkedList<>();
		}

		public int ping(int t) {
			queue.addLast(t);
			while (!queue.isEmpty() && t - queue.getFirst() > 3000) {
				queue.removeFirst();
			}
			return queue.size();
		}

		public static void main(String[] args) {
			RecentCounter recentCounter = new RecentCounter();
			System.out.println(recentCounter.ping(1));
			System.out.println(recentCounter.ping(100));
			System.out.println(recentCounter.ping(3001));
			System.out.println(recentCounter.ping(3002));
		}
	}

	/**
	 * 649. Dota2 参议院
	 * Dota2 的世界里有两个阵营：Radiant（天辉）和 Dire（夜魇）
	 * Dota2 参议院由来自两派的参议员组成。现在参议院希望对一个 Dota2 游戏里的改变作出决定。他们以一个基于轮为过程的投票进行。在每一轮中，每一位参议员都可以行使两项权利中的 一 项：
	 * 禁止一名参议员的权利：参议员可以让另一位参议员在这一轮和随后的几轮中丧失 所有的权利 。
	 * 宣布胜利：如果参议员发现有权利投票的参议员都是 同一个阵营的 ，他可以宣布胜利并决定在游戏中的有关变化。
	 * 给你一个字符串 senate 代表每个参议员的阵营。字母 'R' 和 'D'分别代表了 Radiant（天辉）和 Dire（夜魇）。然后，如果有 n 个参议员，给定字符串的大小将是 n。
	 * 以轮为基础的过程从给定顺序的第一个参议员开始到最后一个参议员结束。这一过程将持续到投票结束。所有失去权利的参议员将在过程中被跳过。
	 * 假设每一位参议员都足够聪明，会为自己的政党做出最好的策略，你需要预测哪一方最终会宣布胜利并在 Dota2 游戏中决定改变。输出应该是 "Radiant" 或 "Dire" 。
	 * 示例 1：
	 * 输入：senate = "RD"
	 * 输出："Radiant"
	 * 解释：
	 * 第 1 轮时，第一个参议员来自 Radiant 阵营，他可以使用第一项权利让第二个参议员失去所有权利。
	 * 这一轮中，第二个参议员将会被跳过，因为他的权利被禁止了。
	 * 第 2 轮时，第一个参议员可以宣布胜利，因为他是唯一一个有投票权的人。
	 * 示例 2：
	 * 输入：senate = "RDD"
	 * 输出："Dire"
	 * 解释：
	 * 第 1 轮时，第一个来自 Radiant 阵营的参议员可以使用第一项权利禁止第二个参议员的权利。
	 * 这一轮中，第二个来自 Dire 阵营的参议员会将被跳过，因为他的权利被禁止了。
	 * 这一轮中，第三个来自 Dire 阵营的参议员可以使用他的第一项权利禁止第一个参议员的权利。
	 * 因此在第二轮只剩下第三个参议员拥有投票的权利,于是他可以宣布胜利
	 * 提示：
	 * n == senate.length
	 * 1 <= n <= 104
	 * senate[i] 为 'R' 或 'D'
	 */
	static class PredictPartyVictory {

		/**
		 * 思路:
		 * 使用两个队列,分别存放R和D的序列,当出队一个R时,就应该出队一个idx较大的D,反之同理
		 * 反复多次操作后,其中某个队列为空时,说明另外一个队列获胜
		 *
		 * @param senate
		 * @return
		 */
		public static String predictPartyVictory(String senate) {
			Queue<Integer> rQueue = new LinkedList<>();
			Queue<Integer> dQueue = new LinkedList<>();
			for (int i = 0; i < senate.length(); ++i) {
				if (senate.charAt(i) == 'R') {
					rQueue.offer(i);
				} else {
					dQueue.offer(i);
				}
			}
			while (!rQueue.isEmpty() && !dQueue.isEmpty()) {  // 都不为空时,idx小的R/D可以淘汰idx大的R/D
				int rIdx = rQueue.peek();
				int dIdx = dQueue.peek();
				rQueue.poll();
				dQueue.poll();
				if (rIdx < dIdx) {  // D弹出,R保留到队尾;那么他的idx应该是多少呢?因为到队尾其序列应该比整个字符长度更大,所以idx=idx+len
					rQueue.offer(rIdx + senate.length());
				} else {
					dQueue.offer(dIdx + senate.length());
				}
			}
			return rQueue.isEmpty() ? "Dire" : "Radiant";
		}

		public static String predictPartyVictoryII(String senate) {
			Queue<Integer> rQueue = new LinkedList<>();
			Queue<Integer> dQueue = new LinkedList<>();
			for (int i = 0; i < senate.length(); ++i) {
				if (senate.charAt(i) == 'R') {
					rQueue.offer(i);
				} else {
					dQueue.offer(i);
				}
			}
			while (!rQueue.isEmpty() && !dQueue.isEmpty()) {  // 都不为空时,idx小的R/D可以淘汰idx大的R/D
				int rIdx = rQueue.poll();
				int dIdx = dQueue.poll();
				if (rIdx < dIdx) {  // D弹出,R保留到队尾;那么他的idx应该是多少呢?因为到队尾其序列应该比整个字符长度更大,所以idx=idx+len
					rQueue.offer(rIdx + senate.length());
				} else {
					dQueue.offer(dIdx + senate.length());
				}
			}
			return rQueue.isEmpty() ? "Dire" : "Radiant";
		}

		public static void main(String[] args) {
			System.out.println(predictPartyVictory("RDD"));
		}
	}
}
