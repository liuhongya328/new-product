package springboot.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewioServer {

	public static void main(String[] args) throws Exception {

		// 创建一个极少数量的线程池,大概为cpu的核数
		int threads = 4;
		// 创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(threads);

		// 创建一个selector
		Selector selector = Selector.open();

		ServerSocketChannel ssc = ServerSocketChannel.open();
		int port = 9001;
		ssc.bind(new InetSocketAddress(port));

		// 将channel注册是到selector,可以
		// 1.用非阻塞方式
		ssc.configureBlocking(false);
		// 2.注册
		ssc.register(selector, SelectionKey.OP_ACCEPT);

		// 一个线程负责监听就绪的channel
		while (true) {

			// 查询就绪的channel数量,select()方法可以被中断
			int readyChannelCount = selector.select();

			if (readyChannelCount == 0) {
				continue;
			}

			// 得到就绪的
			Set<SelectionKey> selectedKeys = selector.selectedKeys();

			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

			while (keyIterator.hasNext()) {
				SelectionKey key = keyIterator.next();
				if (key.isAcceptable()) {
					// 连接完成
					// 接受连接
					SocketChannel channel = ssc.accept();

					// 注册到selector上,然后将检测channel的下一个数据到达的状态
					// 1.用非阻塞方式
					channel.configureBlocking(false);
					// 2.注册
					channel.register(selector, SelectionKey.OP_READ);

				} else if (key.isReadable()) {
					// 可读状态,数据通过线程进行处理
					pool.submit(new SocketProcess(key));

					// 取消selector中的key注册,因异步处理，防止异步线程处理延误,造成重复处理
					key.cancel();

				} else if (key.isWritable()) {
					// 可发送

				} else if (key.isConnectable()) {
					// 客户端用的
				}
				keyIterator.remove();// 处理完成要移除，否则重复处理
			}
		}
	}

	private static Charset charset = Charset.forName("UTF-8");
	private static CharsetDecoder decoder = charset.newDecoder();

	static class SocketProcess implements Runnable {

		private SelectionKey key;

		public SocketProcess(SelectionKey key) {
			super();
			this.key = key;
		}

		@Override
		public void run() {
			// Nio buffer,连续的存储空间 缓冲区:数据临时存放区
			SocketChannel channel = (SocketChannel) key.channel();

			// 创建buffer
			int size = 1024;
			ByteBuffer rbf = ByteBuffer.allocate(size);

			try {
				channel.read(rbf);
				// 转成读模式
				rbf.flip();
				System.out.println(decoder.decode(rbf).toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 通道结束时关闭
			try {
				channel.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
