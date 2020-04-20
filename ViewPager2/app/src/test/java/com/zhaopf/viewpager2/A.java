package com.zhaopf.viewpager2;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class A {

    class User {
    }

    @Test
    public void test() {
        ExecutorService es = new ThreadPoolExecutor(1, 10,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(@NonNull Runnable r) {
                        return new Thread(r);
                    }
                });
        Future<User> submit = es.submit(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return new User();
            }
        });
        try {
            User user = submit.get();
            System.out.println("user: " + user);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}