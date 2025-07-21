package file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 匿名类实现
 */
public class FileCopyForAnonymous {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		File source = new File("D:\\Users\\Desktop\\large.txt");
		File dest = new File("D:\\Users\\Desktop\\copy.txt");
		long fileSize = source.length();
		long blockSize = fileSize / 4;

		for (int i = 0; i < 4; i++) {
			final long start = i * blockSize;
			final long end = (i == 3) ? fileSize : start + blockSize;

			executor.submit(new Runnable() {
				@Override
				public void run() {
					try (RandomAccessFile srcFile = new RandomAccessFile(source, "r");
					     RandomAccessFile destFile = new RandomAccessFile(dest, "rw")) {
						srcFile.seek(start);
						destFile.seek(start);
						byte[] buffer = new byte[8192];
						long bytesCopied = 0;
						while (bytesCopied < end - start) {
							int len = srcFile.read(buffer);
							if (len == -1) break;
							destFile.write(buffer, 0, len);
							bytesCopied += len;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		//等待子线程返回,此处就不写了
		executor.shutdown();
	}

}
