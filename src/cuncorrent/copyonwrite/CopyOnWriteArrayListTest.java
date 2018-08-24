package cuncorrent.copyonwrite;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程安全的list，运用读写分离的思想，每次需要修改容器时，会先复制一份，在容器中修改，完成后将原容器的应用指向新容器，读的话还在原容器上读; add()的时候运用Reentrantlock加锁，get()不需要加锁; 运用场景：
 * 解决的开发工作中的多线程的并发问题。
 * 缺点:
 *  1.内存占有问题:很明显，两个数组同时驻扎在内存中，如果实际应用中，数据比较多，而且比较大的情况下，占用内存会比较大，针对这个其实可以用ConcurrentHashMap来代替。
 *	2.数据一致性:CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。所以如果你希望写入的的数据，马上能读到，请不要使用CopyOnWrite容器
 * @author wangjianlou 2018年8月23日
 * @version V1.0
 */
public class CopyOnWriteArrayListTest {
	
	private static final int THREAD_POOL_MAX_NUM = 10;
	private List<String> mList = new CopyOnWriteArrayList<String>();
	
	public static void main(String args[]) {
		new CopyOnWriteArrayListTest().start();
	}
	
	private void initData() {
		for (int i = 0; i <= THREAD_POOL_MAX_NUM; i++) {
			this.mList.add("...... Line " + (i + 1) + " ......");
		}
	}
	
	private void start() {
		initData();
		ExecutorService service = Executors.newFixedThreadPool(THREAD_POOL_MAX_NUM);
		for (int i = 0; i < THREAD_POOL_MAX_NUM; i++) {
			service.execute(new ListReader(this.mList));
			service.execute(new ListWriter(this.mList, i));
		}
		service.shutdown();
	}
	
	private class ListReader implements Runnable {
		
		private List<String> mList;
		
		public ListReader(List<String> list) {
			this.mList = list;
		}
		
		@Override
		public void run() {
			if (this.mList != null) {
				for (String str : this.mList) {
					System.out.println(Thread.currentThread().getName() + " : " + str);
				}
			}
		}
	}
	
	private class ListWriter implements Runnable {
		
		private List<String> mList;
		private int mIndex;
		
		public ListWriter(List<String> list, int index) {
			this.mList = list;
			this.mIndex = index;
		}
		
		@Override
		public void run() {
			if (this.mList != null) {
				// this.mList.remove(this.mIndex);
				this.mList.add("...... add " + mIndex + " ......");
			}
		}
	}
	
	/*
	 * private static final int THREAD_POOL_MAX_NUM = 10; private List<String> mList = new ArrayList<String>(); public static void main(String args[]){ new
	 * CopyOnWriteArrayListTest().start(); } private void initData() { for(int i = 0 ; i <= THREAD_POOL_MAX_NUM ; i ++){
	 * this.mList.add("...... Line "+(i+1)+" ......"); } } private void start(){ initData(); ExecutorService service =
	 * Executors.newFixedThreadPool(THREAD_POOL_MAX_NUM); for(int i = 0 ; i < THREAD_POOL_MAX_NUM ; i ++){ service.execute(new ListReader(this.mList));
	 * service.execute(new ListWriter(this.mList,i)); } service.shutdown(); } private class ListReader implements Runnable{ private List<String> mList ; public
	 * ListReader(List<String> list) { this.mList = list; }
	 * @Override public void run() { if(this.mList!=null){ for(String str : this.mList){ System.out.println(Thread.currentThread().getName()+" : "+ str); } } }
	 * } private class ListWriter implements Runnable{ private List<String> mList ; private int mIndex; public ListWriter(List<String> list,int index) {
	 * this.mList = list; this.mIndex = index; }
	 * @Override public void run() { if(this.mList!=null){ //this.mList.remove(this.mIndex); this.mList.add("...... add "+mIndex +" ......"); } } }
	 */
	
}
