package file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * lambda表达式实现
 */
public class FileCopyForLambda {

	public static void main(String[] args) {
		int threadCount = 4;
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		File source = new File("D:\\Users\\Desktop\\large.txt");
		File dest = new File("D:\\Users\\Desktop\\copy.txt");
		long fileSize = source.length();
		long blockSize = fileSize / threadCount;
		for (int i = 0; i < threadCount; i++) {
			long start = i * blockSize;
			long end = (i == threadCount - 1) ? fileSize : start + blockSize;

			executor.submit(() -> {
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
			});

			// 等待子线程返回
			executor.shutdown();
		}
	}

}
