package com.pop.test.jdk.concurrent;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest extends RecursiveTask<Long> {
    private static final int THREADSHOLD = 50000;
    private long[] array;
    private int low;
    private int hight;

    public ForkJoinTest(long[] array, int low, int hight) {
        this.array = array;
        this.low = low;
        this.hight = hight;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (hight - low < THREADSHOLD) {
            for (int i = low; i < hight; i++) {
                sum += array[i];
            }
        } else {
            int middle = (low + hight) >>> 1;
            ForkJoinTest left = new ForkJoinTest(array, low, middle);
            ForkJoinTest right = new ForkJoinTest(array, middle + 1, hight);

            left.fork();
            right.fork();

            sum = left.join() + right.join();
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long[] array = genArray(1000000);
        System.out.println(Arrays.toString(array));

        ForkJoinTest forkJoinTest = new ForkJoinTest(array, 0, array.length - 1);
        long begin = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(forkJoinTest);
        Long result = forkJoinTest.get();

        long end = System.currentTimeMillis();

        System.out.println(String.format("结果 %s 耗时 %sms", result, end - begin));
    }

    private static long[] genArray(int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Random().nextLong();
        }
        return array;
    }
}