package com.lrw.datastructure.test;

import com.lrw.datastructure.queue.CircleQueue;

public class MainQueue {
    public static void main(String[] args) {
//        Queue<Integer> queue = new Queue<>();
        CircleQueue<Integer> queue = new CircleQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }

        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }

        for (int i = 0; i < 5; i++) {
            queue.enQueue(10 + i);
        }

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }

    }
}
