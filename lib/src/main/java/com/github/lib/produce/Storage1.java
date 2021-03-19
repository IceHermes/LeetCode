package com.github.lib.produce;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright (c) 2021 Tencent. All rights reserved.
 * 类功能描述:
 *
 * @author flynnjiao
 * @date 2021/3/19
 */
public class Storage1 {
    private static final int CAPACITY = 5;
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition full = lock.newCondition();
    private static Condition empty = lock.newCondition();

    public static void main(String[] args) {

        Queue<Integer> queue = new LinkedList<Integer>();

        Thread producer1 = new Producer("P-1", queue, CAPACITY);
        Thread producer2 = new Producer("P-2", queue, CAPACITY);
        Thread consumer1 = new Consumer("C1", queue, CAPACITY);
        Thread consumer2 = new Consumer("C2", queue, CAPACITY);
        Thread consumer3 = new Consumer("C3", queue, CAPACITY);

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }

    public static class Producer extends Thread {
        private Queue<Integer> queue;
        String name;
        int maxSize;
        int i = 0;

        public Producer(String name, Queue<Integer> queue, int maxSize) {
            super(name);
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    lock.lock();
                    while (queue.size() >= maxSize) {
                        System.out.println("Queue is full, Producer[" + name + "] thread waiting for " + "consumer to take something from queue.");
                        full.await();
                    }
                    System.out.println("[" + name + "] Producing value : +" + i);
                    queue.offer(i++);
                    empty.signalAll();

                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static class Consumer extends Thread {
        private Queue<Integer> queue;
        String name;
        int maxSize;

        public Consumer(String name, Queue<Integer> queue, int maxSize) {
            super(name);
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    lock.lock();
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("Queue is empty, Consumer[" + name + "] thread is waiting for Producer");
                            empty.await();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    int x = queue.poll();
                    System.out.println("[" + name + "] Consuming value : " + x);
                    full.signalAll();

                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }


            }
        }
    }

}
