package com.example.demo.nettytest.client;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;
import io.netty.util.concurrent.PromiseCombiner;
import org.testng.collections.Lists;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Test {
    private static final int THREAD_NUM = 5;

    private static ExecutorService[] executor;

    final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());


    static {
        executor = new ExecutorService[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i++) {
            executor[i] = Executors.newSingleThreadExecutor();
        }
    }


    public void dividePeach(int num, int sum) {
        if (num == 0) {
            System.out.println("sum:" + sum);
            return;
        }
        sum = sum * 5 + 1;
        num--;
        dividePeach(num, sum);
    }

    public static ExecutorService getExecutorService(int dispatcher) {
        return executor[dispatcher];
    }

    public int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
        Map<String, Integer> key2Int = new HashMap<>();
        key2Int.put("type", 0);
        key2Int.put("color", 1);
        key2Int.put("name", 2);
        Integer integer = key2Int.get(ruleKey);
        List<List<String>> collect = items.stream().filter(list -> {
            return list.get(integer).equals(ruleValue);
        }).collect(Collectors.toList());
        return collect.size();
    }

    public int nearestValidPoint(int x, int y, int[][] points) {
        Map<Integer, List<Integer>> distance2Index = new HashMap<>();
        for (int[] point : points) {
            if (point[0] == x || point[1] == y) {
                int absX = Math.abs(x - point[0]);
                int absY = Math.abs(y - point[1]);
                int sum = absX + absY;
                distance2Index.computeIfAbsent(sum, k -> new ArrayList<>()).add(point[0]);
            }
        }
        if (distance2Index.isEmpty()) {
            return -1;
        }
        Optional<Integer> min = distance2Index.keySet().stream().min(Integer::compareTo);

        List<Integer> integerList = distance2Index.get(min.get());

        if (integerList.size() == 1) {
            return min.get();
        } else {
            return integerList.stream().min(Integer::compareTo).orElse(0);
        }

    }

    public int a(int x, int y, int[][] points) {
        Map<Integer, List<Integer>> distance2Index = new HashMap<>();
        for (int[] point : points) {
            if (point[0] == x || point[1] == y) {
                int absX = Math.abs(x - point[0]);
                int absY = Math.abs(y - point[1]);
                int sum = absX + absY;
                List<Integer> integers = distance2Index.computeIfAbsent(sum, k -> new ArrayList<>());
                if (!distance2Index.get(sum).contains(point[0])) {
                    integers.add(point[0]);
                }
            }
        }
        if (distance2Index.isEmpty()) {
            return -1;
        }
        int mapMin = 999999999;
        for (Integer integer : distance2Index.keySet()) {
            if (mapMin > integer) {
                mapMin = integer;
            }
        }

        List<Integer> integerList = distance2Index.get(mapMin);
        int minList = 99999999;
        for (Integer integer : integerList) {
            if (minList > integer) {
                minList = integer;
            }
        }
        return minList;
//        if (integerList.size() == 1) {
//            return mapMin;
//        } else {
//
//        }
    }

    public static ListenableFuture<?> task1() {
        return Test.service.submit(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("task1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public Future<?> task2() {
        return Test.getExecutorService(1).submit(() -> {
            try {
                Thread.sleep(500);
                System.out.println("task2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public Future<?> task3() {
        return Test.getExecutorService(1).submit(() -> {
            System.out.println("task3");
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ListenableFuture<?> listenableFuture = Test.task1();
        listenableFuture.addListener(() -> {
            System.out.println("结束");
        }, Test.getExecutorService(1));
        System.out.println("你好");
//        PromiseCombiner promiseCombiner = new PromiseCombiner(ImmediateEventExecutor.INSTANCE);
//        List<Future<?>> futures = Lists.newLinkedList();
//        FutureTask<?> futureTask = new FutureTask<>(Test::task1);
//        futures.forEach(future -> promiseCombiner.add(future));



//        Person person = new Person();
//        person.setAge(1);
//        Test.getExecutorService(1).submit(() -> {
//            System.out.println(person.getAge());
//        });
//        Thread.sleep(1000);
//        person.setAge(10);
//        List<String> list = new ArrayList<>();
//        int i = 1;
//        list.add("232");
//        list.add("23");
//        list.add("232");
//
//        list.remove("232");
//        System.out.println(list.toString());
//          Test test = new Test();
//          int[][] point = {{1,2},{3,1},{2,4},{2,3},{4,4}};
//        int[][] point1 = {{1,2},{3,3},{3,3}};
//        System.out.println(test.a(1, 1, point1));
//          test.dividePeach(4, 6);
//          System.out.println(test.mergeString("word", "qwersdsd"));
//        CountDownLatch countDownLatch = new CountDownLatch(2);
//        Test.getExecutorService(1).submit(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("A");
//            countDownLatch.countDown();
//        });
//        Test.getExecutorService(2).submit(() -> {
//            System.out.println("B");
//            countDownLatch.countDown();
//        });
////        Test.getExecutorService(1).submit(() -> {
////            System.out.println("C");
////            countDownLatch.countDown();
////        });
//        try {
//            countDownLatch.await();
//            System.out.println("线程执行完毕");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Future<?> submit = Test.getExecutorService(1).submit(Test::testA);
//        submit.get();
//        Future<?> submit1 = Test.getExecutorService(2).submit(Test::testB);
//        submit1.get();
//        Future<?> submit2 = Test.getExecutorService(3).submit(Test::testC);
//
//        CompletableFuture<Void> f = CompletableFuture.runAsync(Test::testA, Test.getExecutorService(1));
//        f.whenCompleteAsync((v, e) -> {
//            Test.testB();
//        }, Test.getExecutorService(2)).whenCompleteAsync((v, e) -> {
//           Test.testC();
//        }, Test.getExecutorService(3));
    }

    public String mergeString(String str1, String str2) {
        StringBuilder mergeString = new StringBuilder();
        int length = 0;
        if (str1.length() <= str2.length()) {
            length = str1.length();
        } else {
            length = str2.length();
        }
        for (int i = 0; i < length; i++) {
            mergeString.append(str1.charAt(i)).append(str2.charAt(i));
        }
        if (str1.length() < str2.length()) {
            mergeString.append(str2.substring(length));
        } else if (str1.length() > str2.length()) {
            mergeString.append(str1.substring(length));
        } else {
            return mergeString.toString();
        }
        return mergeString.toString();


    }

    public static void testA() {
        try {
            System.out.println(Thread.currentThread());
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("A");
    }

    public static void testB() {
        System.out.println(Thread.currentThread());
        System.out.println("B");
    }

    public static void testC() {
        System.out.println(Thread.currentThread());
        System.out.println("C");
    }

    public static <T> void completeWith(CompletableFuture<T> future, Callable<T> callable) {
        try {
            T result = callable.call();
            future.complete(result);
        } catch (Exception e) {
            future.completeExceptionally(e);
        }

    }
}
