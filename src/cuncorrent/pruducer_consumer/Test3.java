package cuncorrent.pruducer_consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 使用阻塞队列实现生产者消费者
 * @author wangjianlou 2018年8月27日
 * @version V1.0
 */
public class Test3 {
	private static volatile Integer count = 0;
    //创建一个阻塞队列
    final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
    private String lock = "lock"; 
    public static void main(String[] args) {
        Test3 test3 = new Test3();
        new Thread(test3.new Producer()).start();
        new Thread(test3.new Consumer()).start();
        new Thread(test3.new Producer()).start();
        new Thread(test3.new Consumer()).start();
        new Thread(test3.new Producer()).start();
        new Thread(test3.new Consumer()).start();
        new Thread(test3.new Producer()).start();
        new Thread(test3.new Consumer()).start();
    }
    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    blockingQueue.put(1);
                    synchronized(lock){
                    	count++;
                    	System.out.println(Thread.currentThread().getName()
                    			+ "生产者生产，目前总共有" + count);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    blockingQueue.take();
                    synchronized(lock){
                    	count--;
                    	System.out.println(Thread.currentThread().getName()
                    			+ "消费者消费，目前总共有" + count);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
