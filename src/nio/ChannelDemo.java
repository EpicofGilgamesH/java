package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description ChannelDemo
 * @Date 2021-08-13 14:31
 * @Created by wangjie
 */
public class ChannelDemo {

	public static void main(String[] args) throws IOException {

		RandomAccessFile accessFile = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\新建文本文档.txt", "rw");
		FileChannel fileChannel = accessFile.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(48);
		int read = fileChannel.read(buffer);
		while (read != -1) {
			System.out.println("Read:" + read);
			buffer.flip();
			while (buffer.hasRemaining()) {
				System.out.print((char) buffer.get());
			}
			buffer.clear();
			read = fileChannel.read(buffer);
		}
		accessFile.close();
	}
}
