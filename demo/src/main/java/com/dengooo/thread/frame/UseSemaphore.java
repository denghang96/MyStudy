package com.dengooo.thread.frame;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * 类说明：有10把勺子，提供给多个人使用来打饭
 * @author denghang
 *
 */
public class UseSemaphore {

    private static final Integer POOL_SIZE = 10;//连接池大小

    private static LinkedList<Spoon> pool = new LinkedList<>();//连接池

    private static Semaphore useful,useless;//可用连接，和已用连接

    static {
        for(int i= 0; i < POOL_SIZE; i++) {
            pool.addLast(new Spoon());
        }
    }
    /**
     * 勺子实体类
     * @author denghang
     *
     */
    public static class Spoon {}

    public UseSemaphore() {
        this.useful = new Semaphore(10);
        this.useless = new Semaphore(0);
    }

    /**
     * 拿到勺子
     * @return
     * @throws InterruptedException
     */
    public Spoon getSpoon() throws InterruptedException {
        useful.acquire();
        Spoon spoon;
        synchronized (pool) {
            spoon = pool.getFirst();
            pool.removeFirst();
        }
        useless.release();
        return spoon;
    }
    /**
     * 归还勺子
     * @param spoon
     * @throws InterruptedException
     */
    public void returnSpoon(Spoon spoon) throws InterruptedException {
        if(spoon!=null) {
            useless.acquire();
            synchronized (pool) {
                pool.addLast(spoon);
            }
            useful.release();
            System.out.println("当前有" + useful.getQueueLength() +"个人等待使用勺子！！！"
                    + "可用勺子数"+ useful.availablePermits());
        }

    }
}

