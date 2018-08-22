package cuncorrent.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier是一个同步工具类，允许一组线程相互之间等待，当所有线程都达到一个屏障点后，所有线程再继续执行。
 * 例子：假设一组线程都要进行读操作，等所有线程读操作完成后才能进行接下来的操作
 * CountDownLatch与CyclicBarrier区别：
 * 1.CountDownLatch是一个或多个线程等待其他线程都完成某些事情后才开始执行；CyclicBarrier是一组线程之间相互等待，都到达一个屏障点后，所有线程再继续执行；
 * 2.CountDownLatch不可以复用，而CyclicBarrier可以复用。
 * 3.CountDownLatch构造方法只有一个，CyclicBarrier多了一个：设置所有线程到达屏障后优先执行的Runnable对象
 * @author wangjianlou 2018年8月22日
 * @version V1.0
 */
public class CyclicBarrierTest {
	private static CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new Runnable() {
		
		@Override
		public void run() {
			System.out.println("宿舍四人一块去操场打球");
		}
	});
	
	private static Runnable createRunnable(String name){
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				System.out.println(name + "开始从宿舍出发");
				try {
		            Thread.sleep(100);
	            } catch (InterruptedException e) {
		            e.printStackTrace();
	            }
				try {
		            cyclicBarrier.await();
	            } catch (InterruptedException | BrokenBarrierException e) {
		            e.printStackTrace();
	            }
				System.out.println(name + "到达操场");
			}
		};
		return runnable;
	}
	
	public static void main(String[] args) {
	    ExecutorService executor = Executors.newFixedThreadPool(4);
	    String[] names = {"wangjianlou","wangshifeng","tangbo","wangyifan"};
        for (String name : names) {
        	executor.execute(createRunnable(name));
        }
	    executor.shutdown();
	    
		try{
			Thread.sleep(2000);
			System.out.println("四个人一起到达球场，现在开始打球");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
    }
	
}
