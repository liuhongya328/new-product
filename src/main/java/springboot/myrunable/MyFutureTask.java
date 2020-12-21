package springboot.myrunable;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * 手写自己的FutureTask,只能实现其核心原理,真实jdk中有很多异常及特殊状态的判断。
 * 
 */
public class MyFutureTask<T> implements Runnable {

	// 包装了代码逻辑,让线程去执行
	MyCallable<T> myCallable;

	T result;

	// 任务执行状态,用来判断run方法的结果已经执行完毕
	volatile String state = "NEW";

	// 当前正在等待任务执行完毕的线程队列,实际上这个队列里面只会有一个thread
	LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

	public MyFutureTask(MyCallable<T> myCallable) {
		this.myCallable = myCallable;
	}

	// futuretask的核心就是run方法执行callable中的call方法
	@Override
	public void run() {
		try {
			result = myCallable.call();
		} catch (Exception e) {
			// state = "EXCEPTION"; //异常状态,因状态太多就不一一例举
			e.printStackTrace();
		} finally {
			state = "END";
		}

		// 任务执行完毕,则通知停车场可以放行。告诉外部线程可以获取返回值。
		Thread waiter = waiters.poll();
		while (waiter != null) {
			LockSupport.unpark(waiter);
			waiter = waiters.poll();
		}
	}

	// 获取结果
	// 线程是异步执行的,外部线程调用get的时候,上面的run方法还没结束,则等待结果
	public T get() {
		if ("END".equals(state)) {
			return result;
		} // else()....

		// 没有执行完毕,则等待, park/unpark ,控制线程的停和走。
		// 将外部线程加入到等待线程队列
		waiters.add(Thread.currentThread());
		while (!"END".equals(state)) {
			// 进入停车场
			// park 方法还可以在其他任何时间"毫无理由"地返回,因此通常必须在重新检查返回条件的循环里调用此方法。从这个意义上说,park
			// 是“忙碌等待”的一种优化,它不会浪费这么多的时间进行自旋,但是必须将它与 unpark 配对使用才更高效
			LockSupport.park();
		}

		return result;
	}

}
