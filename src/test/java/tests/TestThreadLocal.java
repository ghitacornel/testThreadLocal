package tests;

import manager.ResourceManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestThreadLocal {

    /**
     * Usually the container will control the thread local instance
     */
    final private static ResourceManager THREAD_LOCAL_RESOURCE_MANAGER = new ResourceManager();

    /**
     * Usually the container will control the thread pool
     */
    final private static int NUMBER_OF_THREADS = 100;
    private List<Thread> threadPool = new ArrayList<>();

    final private static CountDownLatch doneSignal = new CountDownLatch(NUMBER_OF_THREADS);

    @Before
    public void before() {

        //  initialize the thread pool
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.setName("thread " + i);
            threadPool.add(thread);
        }
    }

    @Test
    public void testThreadLocal() throws Exception {

        // now the container starts its threadPool
        for (Thread thread : threadPool) {
            thread.start();
        }

        // wait for all threadPool to complete
        doneSignal.await();

        // verify thread linked resources were not overlapped
        int i = 0;
        for (Thread thread : threadPool) {
            String expected = "thread " + i;
            Assert.assertEquals(expected + expected + expected, thread.getName());
            i++;
        }

    }

    /**
     * My CUSTOM code goes here
     */
    final private static class MyRunnable implements Runnable {

        @Override
        public void run() {

            Resource resource;

            // access thread local variable first time
            resource = THREAD_LOCAL_RESOURCE_MANAGER.get();
            System.out.println(Thread.currentThread().getName() + " thread accessed first time his thread local variable = " + resource);
            resource.setData(Thread.currentThread().getName());

            // do some important work
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // should access the same thread local variable second time
            resource = THREAD_LOCAL_RESOURCE_MANAGER.get();
            System.out.println(Thread.currentThread().getName() + " thread accessed second time his thread local variable = " + resource);
            resource.setData(resource.getData() + Thread.currentThread().getName());

            // again do some important work
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // should access the same thread local variable and now remove it
            resource = THREAD_LOCAL_RESOURCE_MANAGER.get();
            resource.setData(resource.getData() + Thread.currentThread().getName());
            System.out.println("removing thread specific variable for " + Thread.currentThread().getName() + " and variable " + resource);

            Thread.currentThread().setName(resource.getData());
            THREAD_LOCAL_RESOURCE_MANAGER.remove();

            doneSignal.countDown();

        }

    }

}
