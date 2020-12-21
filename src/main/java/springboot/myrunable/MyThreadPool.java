package springboot.myrunable;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {

	private BlockingQueue<Runnable> taskQueue;

	// 放线程的集合
	private List<Worker> workers;

	// 方便线程池关闭
	private volatile boolean working = true;

	public MyThreadPool(int poolSize, int taskQueueSize) throws Exception {
		if (poolSize <= 0 || taskQueueSize <= 0) {
			throw new IllegalAccessException("参数错误！");
		}

		// 初始化任务队列,用来存放待执行的任务
		this.taskQueue = new LinkedBlockingQueue<Runnable>(taskQueueSize);

		// 创建放工作线程的集合
		this.workers = new ArrayList<>();

		// 初始化工作线程，即指定线程池的线程数
		for (int i = 0; i < poolSize; i++) {
			Worker w = new Worker(this);
			this.workers.add(w);
			w.start();
		}
	}

	// 将任务提交给线程池
	public boolean submit(Runnable task) {
		return this.taskQueue.offer(task);
	}

	public void shutdown() {
		if (this.working) {
			this.working = false;
			// 如果线程处于阻塞状态，则唤醒
			for (Thread t : this.workers) {
				if (t.getState().equals(State.BLOCKED) || t.getState().equals(State.WAITING)) {
					// 中断阻塞状态
					t.interrupt();
				}
			}
		}
	}

	// 工作线程
	private class Worker extends Thread {

		private MyThreadPool myPool;

		public Worker(MyThreadPool myPool) {
			super();
			this.myPool = myPool;
		}

		@Override
		public void run() {

			// 从仓库取任务执行-- 设计重点！！！如何进行线程池的关闭
			while (this.myPool.working || this.myPool.taskQueue.size() > 0) {
				Runnable task = null;
				try {
					if (this.myPool.working) {
						// take()阻塞的
						task = this.myPool.taskQueue.take();
					} else {
						// 非阻塞的取出待执行任务
						task = this.myPool.taskQueue.poll();
					}
				} catch (InterruptedException e) {
					//中断状态
					e.printStackTrace();
				}
				if(task != null) {
					try {
						// 执行任务
						task.run();
						System.out.println(System.currentTimeMillis() +":"+ Thread.currentThread().getName()+"正在执行!");
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			super.run();
		}
	}
	
	public static void main(String[] args) throws Exception {
		MyThreadPool pool = new MyThreadPool(3, 5);
		//模拟任务执行
		for (int i =0; i < 5; i++) {
			pool.submit(()->{
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		
		pool.shutdown();
	}
}
