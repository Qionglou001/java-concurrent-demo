package cuncorrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock：读写锁，分为readLock和writeLock，
 * readLock可多线程并发执行，writeLock只能单线程执行（类似于synchronized）,线程获取writeLock锁之后可以继续获取readLock，
 * 但线程获取readLock后不能再获取writeLock锁
 * 问题：
 * 你需要实现一个高效的缓存，它允许多个用户读，但只允许一个用户写，以此来保持它的完整性，你会怎样去实现它？：可用reentrantReadWritelock实现
 * @author wangjianlou 2018年11月8日
 * @version V1.0
 */
public class ReentrantReadWriteLockTest {
	
	private final Map<String, String> map = new HashMap<String, String>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock  readLock = lock.readLock();
	private final Lock  writeLock = lock.writeLock();
	
    public Map<String, String> getMap() {
    	try{
    		readLock.lock();
    		return map;
    	}finally{
    		readLock.unlock();
    	}
    }
    
    public String putMap(String key, String value){
    	try{
    		writeLock.lock();
    		return map.put(key, value);
    	}finally{
    		writeLock.unlock();
    	}
    }
	
}
