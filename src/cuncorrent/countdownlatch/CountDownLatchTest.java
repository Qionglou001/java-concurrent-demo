package cuncorrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程执行完后再执行。
 * 例子：解析一个Excel里多个sheet的数据时，可以考虑使用多线程，每个线程解析一个sheet里的数据，等到所有的sheet都解析完之后，
 * 程序需要提示解析完成。在这个需求中，要实现主线程等待所有线程完成sheet的解析操作
 * @author wangjianlou 2018年8月22日
 * @version V1.0
 */
public class CountDownLatchTest {
	public static void main(String[] args) {
	    CountDownLatch latch = new CountDownLatch(2);
		
		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "执行");
				try {
	                Thread.sleep(100);
                } catch (InterruptedException e) {
	                e.printStackTrace();
                }
				latch.countDown();
			}
		},"thread1");
		
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "执行");
				try {
	                Thread.sleep(100);
                } catch (InterruptedException e) {
	                e.printStackTrace();
                }
				latch.countDown();
			}
		},"thread2");
		thread1.start();
		thread2.start();
		
		try {
	        latch.await();
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
		System.out.println("线程全部执行完毕");
    }
}
