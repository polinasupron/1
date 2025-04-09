//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Random;
import java.util.concurrent.locks.Lock;

class Philosopher implements Runnable {
    private final int id;
    private final Lock leftFork;
    private final Lock rightFork;
    private final Random random;
    private static final int MAX_EATING = 2000;
    private int eatingCount = 0;
    private static final Object eatingMonitor = new Object();




    public Philosopher(int id, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.random = new Random();
    }

    public int getId() {
        return this.id;
    }

    public int getEatingCount() {
        return this.eatingCount;
    }

    private void think() throws InterruptedException {
        System.out.println("Философ " + this.id + " размышляет.");
        Thread.sleep((long)this.random.nextInt(100));
    }

    private void eat() throws InterruptedException {
        System.out.println("Философ " + this.id + " ест.");
        Thread.sleep((long)this.random.nextInt(100));
        ++this.eatingCount;
    }

    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()) {
                this.think();
                synchronized(eatingMonitor) {
                    while(this.eatingCount >= 2000) {
                        eatingMonitor.wait();
                    }

                    ++this.eatingCount;
                }

                this.leftFork.lock();

                try {
                    if (this.rightFork.tryLock()) {
                        try {
                            this.eat();
                        } finally {
                            this.rightFork.unlock();
                        }
                    }
                } finally {
                    this.leftFork.unlock();
                }

                synchronized(eatingMonitor) {
                    --this.eatingCount;
                    eatingMonitor.notifyAll();
                }
            }
        } catch (InterruptedException var20) {
            Thread.currentThread().interrupt();
        }

    }
}
