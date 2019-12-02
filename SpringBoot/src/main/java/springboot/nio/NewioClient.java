package springboot.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class NewioClient {

	private static Charset charset = Charset.forName("UTF-8");

	public static void main(String arg[]) {
		System.out.println("客户端已经启动.............");
		try {
			// 1、创建socket通道
			SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9001));

			// 2、切换异步非阻塞
			socketChannel.configureBlocking(false);
			// 3、指定缓冲区大小
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

			Scanner scanner = new Scanner(System.in);
			System.out.println("请输入:");
			String word = scanner.nextLine();
			byteBuffer.put(word.getBytes(charset));
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			byteBuffer.clear();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
