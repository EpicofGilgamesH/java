package file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 *普通类实现
 */
public class FileCopyForThread extends Thread {
	private String sourcePath, destPath;
	private long startPos, endPos;

	public FileCopyForThread(String sourcePath, String destPath, long startPos, long endPos) {
		this.sourcePath = sourcePath;
		this.destPath = destPath;
		this.startPos = startPos;
		this.endPos = endPos;
	}

	@Override
	public void run() {
		try (RandomAccessFile srcFile = new RandomAccessFile(sourcePath, "r");
		     RandomAccessFile destFile = new RandomAccessFile(destPath, "rw")) {
			srcFile.seek(startPos);
			destFile.seek(startPos);
			byte[] buffer = new byte[8192];
			long remaining = endPos - startPos;
			while (remaining > 0) {
				int read = srcFile.read(buffer, 0, (int) Math.min(buffer.length, remaining));
				if (read == -1) break;
				destFile.write(buffer, 0, read);
				remaining -= read;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 调用示例
	public static void main(String[] args) {
		File source = new File("D:\\Users\\Desktop\\large.txt");
		long fileSize = source.length();
		int threadCount = 4;
		for (int i = 0; i < threadCount; i++) {
			long start = i * (fileSize / threadCount);
			long end = (i == threadCount - 1) ? fileSize : start + (fileSize / threadCount);
			new FileCopyForThread("D:\\Users\\Desktop\\large.txt", "D:\\Users\\Desktop\\copy.txt", start, end).start();
		}
		// 等待子线程返回
	}
}
