package cuncorrent.pruducer_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁ReentrantLock实现生产者消费者await()/signal()
 * @author wangjianlou 2018年8月27日
 * @version V1.0
 */
public class Test2 {
    private static Integer count = 0;
    private static final Integer FULL = 10;
    //创建一个锁对象
    private Lock lock = new ReentrantLock();
    //创建两个条件变量，一个为缓冲区非满，一个为缓冲区非空
    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();
    
    public static void main(String[] args) {
        Test2 test2 = new Test2();
        new Thread(test2.new Producer()).start();
        new Thread(test2.new Consumer()).start();
        new Thread(test2.new Producer()).start();
        new Thread(test2.new Consumer()).start();
        new Thread(test2.new Producer()).start();
        new Thread(test2.new Consumer()).start();
        new Thread(test2.new Producer()).start();
        new Thread(test2.new Consumer()).start();
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
                //获取锁
                lock.lock();
                try {
                    while (count == FULL) {
                        try {
                        	full.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println(Thread.currentThread().getName()
                            + "生产者生产，目前总共有" + count);
                    //唤醒消费者
                    empty.signal();
                } finally {
                    //释放锁
                    lock.unlock();
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
                lock.lock();
                try {
                    while (count == 0) {
                        try {
                            empty.await();
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println(Thread.currentThread().getName()
                            + "消费者消费，目前总共有" + count);
                    full.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
