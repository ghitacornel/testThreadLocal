package tests;

import manager.ThreadResourceManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import resource.SpecificThreadResource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestThreadLocal {

    /**
     * Usually the container will control the thread local instance
     */
    final private static ThreadResourceManager THREAD_LOCAL_RESOURCE_MANAGER = new ThreadResourceManager();

    /**
     * Usually the container will control the thread pool
     */
    final private static int NUMBER_OF_THREADS = 100;
    private List<Thread> threadPool = new ArrayList<>();

    final private static CountDownLatch doneSignal = new CountDownLatch(NUMBER_OF_THREADS);

    @Before
    public void before() {
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

        // verify shared values do not overlap
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

            SpecificThreadResource specificThreadResource;

            // access thread local variable first time
            specificThreadResource = THREAD_LOCAL_RESOURCE_MANAGER.get();
            System.out.println(Thread.currentThread().getName() + " thread accessed first time his thread local variable = " + specificThreadResource);
            specificThreadResource.setData(Thread.currentThread().getName());

            // do some important work
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // should access the same thread local variable second time
            specificThreadResource = THREAD_LOCAL_RESOURCE_MANAGER.get();
            System.out.println(Thread.currentThread().getName() + " thread accessed second time his thread local variable = " + specificThreadResource);
            specificThreadResource.setData(specificThreadResource.getData() + Thread.currentThread().getName());

            // again do some important work
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // should access the same thread local variable and now remove it
            specificThreadResource = THREAD_LOCAL_RESOURCE_MANAGER.get();
            specificThreadResource.setData(specificThreadResource.getData() + Thread.currentThread().getName());
            System.out.println("removing thread specific variable for " + Thread.currentThread().getName() + " and variable " + specificThreadResource);

            Thread.currentThread().setName(specificThreadResource.getData());
            THREAD_LOCAL_RESOURCE_MANAGER.remove();

            doneSignal.countDown();

        }

    }

}
