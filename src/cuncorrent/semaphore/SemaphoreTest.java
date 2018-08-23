package cuncorrent.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore信号量，可作为资源控制器，限制同时运行的线程数量 acquire()、release();
 * 分为公平锁和非公平锁，默认非公平锁，即在限制解除后其他线程是否有机会运行是随机的
 * @author wangjianlou 2018年8月23日
 * @version V1.0
 */
public class SemaphoreTest implements Runnable{
	private Semaphore semaphore;
	
    public SemaphoreTest(Semaphore semaphore) {
	    this.semaphore = semaphore;
    }

	@Override
    public void run() {
		try {
	        semaphore.acquire();
	        System.out.println("有人在跑道跑步");
	        Thread.sleep(50);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
		System.out.println("跑完了");
		semaphore.release();
    }
	
	
    
    public static void main(String[] args) {
	    ExecutorService executors = Executors.newFixedThreadPool(100);
	    SemaphoreTest semaphoreTest = new SemaphoreTest(new Semaphore(5));
	    for(int i = 0; i < 10; i++){
	    	executors.execute(semaphoreTest);
	    }
	    executors.shutdown();
    }
	
}
