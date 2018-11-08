package cuncorrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * java实现同步机制的另外一种方式，使用ReentrantLock类，
 * 其与synchronized区别：
 * 1.synchronized是基于jvm底层实现的，lcok是基于jdk实现的；
 * 2.lock可以实现公平锁
 * 3.lock可以响应中断
 * 4.lock可以进行设置获取锁要等待的时间
 * 5.使用lock()方法后必须使用unLock()方法进行释放锁
 * 6.synchronized又可分为偏向锁、轻量级锁、重量级锁
 * @author wangjianlou 2018年8月22日
 * @version V1.0
 */
public class ReentrantlockTest implements Runnable{

    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;
    
    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            lock.lock();  // 看这里就可以
            System.out.println(Thread.currentThread().getName() + "正在执行");
            try {
                i++;
            } finally {
                lock.unlock(); // 看这里就可以
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
    	ReentrantlockTest test = new ReentrantlockTest();
        Thread t1 = new Thread(test,"t1");
        Thread t2 = new Thread(test,"t2");
        Thread t3 = new Thread(test,"t3");
        Thread t4 = new Thread(test,"t4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        System.out.println(i);
    }
	
}
