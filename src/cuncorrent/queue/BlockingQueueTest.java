package cuncorrent.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 阻塞队列: 
 * ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。 
 * LinkedBlockingQueue ：一个由链表结构组成的有界阻塞队列。 
 * PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。 
 * DelayQueue：一个使用优先级队列实现的无界阻塞队列。
 * SynchronousQueue：一个不存储元素的阻塞队列。 
 * LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。 
 * LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。 
 * 主要方法：
 * 1.一直阻塞：put()/take();
 * 2.返回特殊值：offer()/poll()/peek();
 * @author wangjianlou 2018年8月27日
 * @version V1.0
 */
public class BlockingQueueTest {
	
	
	public static void main(String[] args) {
		 ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(4);
		
		 new Thread(new Runnable() {
		
		 @Override
		 public void run() {
		 for(int i = 1; i <= 10; i++){
		 try {
		 queue.put(i + "");
		 System.out.println(Thread.currentThread().getName() + "添加" + i +"成功");
		 } catch (InterruptedException e) {
		 e.printStackTrace();
		 }
		 }
		
		 }
		 },"thread1").start();
		
		 new Thread(new Runnable() {
		
		 @Override
		 public void run() {
		 for(int i = 1; i <= 10; i++){
		 try {
		 String s = queue.take();
		 System.out.println(Thread.currentThread().getName() + "获取" + s +"成功");
		 } catch (InterruptedException e) {
		 e.printStackTrace();
		 }
		 }
		
		 }
		 },"thread2").start();
	}
}
