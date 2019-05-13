package com.dengooo.thread;

import java.sql.Connection;
import java.util.Random;

import com.denooo.thread.frame.UseSemaphore;
import com.denooo.thread.frame.UseSemaphore.Spoon;
import com.denooo.thread.tools.SleepTools;


/**
 * 测试使用勺子类（UseSemaphore）
 * @author denghang
 *
 */
public class TestUseSemaphore {
	
	private static UseSemaphore spoonPool = new UseSemaphore();
	
	public static class useSpoonThread extends Thread {
		@Override
		public void run() {
			Random r = new Random();//让每个线程持有勺子的时间不一样
			long start = System.currentTimeMillis();
			try {
				Spoon spoon = spoonPool.getSpoon();
				System.out.println("Thread_"+Thread.currentThread().getId()
						+"_获取勺子共耗时【"+(System.currentTimeMillis()-start)+"】ms.");
				SleepTools.ms(100+r.nextInt(100));//模拟业务操作，线程打饭所花费的时间
				System.out.println(Thread.currentThread().getName() + "打饭完成，正在归还勺子..........");
				spoonPool.returnSpoon(spoon);
				
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 30; i++) {
            Thread thread = new useSpoonThread();
            thread.start();
        }
	}
}
