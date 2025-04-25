//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {


    
    public static void main(String[] args) throws InterruptedException {
        int NUM_PHILOSOPHERS = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Lock[] forks = new ReentrantLock[5];
        Philosopher[] philosophers = new Philosopher[5];

        for(int i = 0; i < 5; ++i) {
            forks[i] = new ReentrantLock();
        }



        for(int i = 0; i < 5; ++i) {
            Lock leftFork = forks[i];
                Lock rightFork = forks[(i + 1) % 5];
            philosophers[i] = new Philosopher(i, leftFork, rightFork);
            executorService.execute(philosophers[i]);
        }

        Thread.sleep(50000L);
        executorService.shutdownNow();
        executorService.awaitTermination(1L, TimeUnit.SECONDS);

        for(Philosopher philosopher : philosophers) {
            PrintStream var10000 = System.out;
            int var10001 = philosopher.getId();
            var10000.println("Философ " + var10001 + " ел " + philosopher.getEatingCount() + " раз.");
        }

        System.out.println("Программа завершена.");
    }
}
