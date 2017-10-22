
package com.my.biz.config;


import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class ContextWrappedExecutorService implements ExecutorService {

    private ThreadPoolExecutor threadPoolExecutor;

    public ContextWrappedExecutorService(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return threadPoolExecutor.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return threadPoolExecutor.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return threadPoolExecutor.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return threadPoolExecutor.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return threadPoolExecutor.submit(wrap(task));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return threadPoolExecutor.submit(wrap(task), result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return threadPoolExecutor.submit(wrap(task));
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        throw new UnsupportedOperationException();

    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void execute(Runnable command) {
        threadPoolExecutor.execute(wrap(command));
    }

    private Runnable wrap(final Runnable task) {
//        final CPContext cpContext = CPContext.getContext();
        Runnable r = () -> {
//            CPContext.putContext(cpContext);
//            MDC.put(LogAutoConfiguration.LogProperties.GID_KEY, cpContext.getGid());
            try {
                task.run();
            } finally {
//                CPContext.removeContext();
//                MDC.remove(LogAutoConfiguration.LogProperties.GID_KEY);
            }
        };
        return r;
    }

    private Callable wrap(final Callable task) {
//        final CPContext cpContext = CPContext.getContext();
        Callable r = () -> {
//            CPContext.putContext(cpContext);
//            MDC.put(LogAutoConfiguration.LogProperties.GID_KEY, cpContext.getGid());
            try {
                return task.call();
            } finally {
//                CPContext.removeContext();
//                MDC.remove(LogAutoConfiguration.LogProperties.GID_KEY);
            }
        };
        return r;
    }
}
