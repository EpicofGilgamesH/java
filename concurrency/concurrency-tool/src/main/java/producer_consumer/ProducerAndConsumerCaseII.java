package producer_consumer;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * 生产者-消费者模型 场景二
 * 支持分段提交:
 * 写文件如果同步刷盘性能会很低,对于不是很重要的数据,往往采用异步刷盘的方式.例如日志组件的实现,采用异步刷盘,刷盘时机如下:
 * 1.ERROR级别的日志需要立即刷盘
 * 2.数据累计到500条需要立即刷盘
 * 3.存在未刷盘数据,且5秒内未曾刷盘,需要立即刷盘
 */
public class ProducerAndConsumerCaseII {

	private final LogMsg poisonPill = new LogMsg("", Level.ERROR);
	// 阻塞队列
	private final BlockingQueue<LogMsg> bq = new LinkedBlockingQueue<>();
	// flush数量
	private final int batchSize = 500;
	// 写日志线程池,只需要一个线程来完成
	ExecutorService es = Executors.newFixedThreadPool(1);

	public void start() throws IOException {
		File file = File.createTempFile("foo", ".log");
		try (FileWriter writer = new FileWriter(file)) {
			this.es.execute(() -> {
				// 未刷盘日志数
				int curIndex = 0;
				long preFT = System.currentTimeMillis();
				try {
					while (true) {
						// 阻塞5秒从阻塞队列中拿日志数据
						LogMsg log = bq.poll(5, TimeUnit.SECONDS);
						if (log != null) {
							// 是“毒丸”,终止执行
							if (log.equals(poisonPill)) {
								break;
							}
							writer.write(log.toString());
							curIndex++;
						}
						// 写入后进行刷盘操作,如果没有写入日志则无需刷盘
						if (curIndex <= 0) {
							continue;
						}
						// 触发刷盘
						if ((log != null && log.level == Level.ERROR)
								|| curIndex >= batchSize
								|| System.currentTimeMillis() - preFT > 5000) {
							writer.flush();
							curIndex = 0;
							preFT = System.currentTimeMillis();
						}
					}
				} catch (InterruptedException | IOException e) {
					throw new RuntimeException(e);
				} finally {
					try {
						writer.flush();
						writer.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
			});
		}
	}

	/**
	 * 终止写日志线程
	 */
	public void stop() {
		// 将“毒丸”对象加入阻塞队列
		bq.add(poisonPill);
	}

	/**
	 * 写入INFO级别日志
	 *
	 * @param msg
	 */
	public void info(String msg) throws InterruptedException {
		bq.put(new LogMsg(msg, Level.INFO));
	}

	/**
	 * 写入ERROR级别日志
	 *
	 * @param msg
	 * @throws InterruptedException
	 */
	public void error(String msg) throws InterruptedException {
		bq.put(new LogMsg(msg, Level.ERROR));
	}

	/**
	 * 日志消息类
	 */
	@Data
	@AllArgsConstructor
	static class LogMsg {
		private String info;
		private Level level;
	}

	/**
	 * 日志级别
	 */
	enum Level {
		INFO,
		ERROR,
		WARMING,
	}
}
