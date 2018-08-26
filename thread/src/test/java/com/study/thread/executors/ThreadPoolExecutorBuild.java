package com.study.thread.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * $$
 * <p>
 * ThreadPoolExecutor 7大构造参数详解
 * <p>
 * <p>
 * <p>
 * 一个ExecutorService，它使用可能的几个合用线程之一(通常使用Executors工厂方法配置)来执行每个提交的任务。
 * <p>
 * 线程池解决了两个不同的问题:
 * 1). 它们通常在执行大量异步任务时提供更好的性能，这是由于减少了每个任务的调用开销;
 * 2). 它们还提供了一种方法来限制和管理执行任务集合时消耗的资源，包括线程。
 * <p>
 * 每个ThreadPoolExecutor还维护一些基本统计信息，例如已完成任务的数量。
 * <p>
 * 为了在广泛的上下文中有用，这个类提供了许多可调参数和可扩展性挂钩。
 * 然而，程序员需要使用更方便的执行器工厂方法
 * Executors.newcachedthreadpool()(无界线程池，带有自动线程回收)，
 * Executors.newfixedthreadpool (int)(固定大小的线程池),
 * Executors.newsinglethreadexecutor()(单后台线程)，这些方法可预配置最常见使用场景的设置。否则，在手动配置和调优该类时，请使用以下指南:
 * <p>
 * 1. 核心和最大池大小(Core and maximum pool sizes )
 * ThreadPoolExecutor将根据corePoolSize(请参阅getPoolSize())和maximumPoolSize(请参阅getMaximumPoolSize())设置的界限自动调整池大小(请参阅getPoolSize())。
 * 当在方法execute(Runnable)中提交新任务，且运行的corepoolSize大小的线程少于这个数量时，将创建一个新线程来处理请求，即使其他工作线程空闲。
 * 如果运行的线程大于corepoolSize，但小于maximumPoolSize，则只有在队列满时才会创建新线程。通过设置corePoolSize和maximumPoolSize相同，可以创建一个固定大小的线程池。
 * 通过将maximumPoolSize设置为一个基本无界的值，比如Integer.MAX_VALUE，允许池容纳任意数量的并发任务。
 * 最典型的情况是，只有在构建时才设置内核和最大池大小，但是也可以使用setCorePoolSize(int)和setMaximumPoolSize(int)动态更改它们。
 * <p>
 * 2. On-demand构造（On-demand construction）
 * 默认情况下，甚至核心线程都是在新任务到达时才创建和启动的，但是可以使用方法prestartCoreThread()或prestartAllCoreThreads()动态覆盖它。
 * 如果使用非空队列构造池，则可能需要预启动线程。
 * <p>
 * 3. 创建新线程（Creating new threads）
 * 使用ThreadFactory创建新的线程。如果没有另外指定，则使用Executors.defaultThreadFactory()，它创建的线程都位于同一个线程组中，具有相同的NORM_PRIORITY优先级和非daemon状态。
 * 通过提供不同的ThreadFactory，您可以更改线程的名称、线程组、优先级、守护进程状态等等。线程应该拥有“modifyThread”运行时权限。
 * 如果使用池的工作线程或其他线程不具有此权限，则服务可能会降级:配置更改可能不会及时生效，关闭池可能保持在可以终止但不能完成的状态。
 * <p>
 * 4. 保持存活时间（Keep-alive times）
 * 如果当前池中有超过corepoolsize大小的线程，如果空闲时间超过keepAliveTime(请参阅getKeepAliveTime(TimeUnit))，那么多余的线程将被终止。
 * 这提供了一种在没有积极使用池时减少资源消耗的方法。如果稍后池变得更活跃，就会构造新的线程。这个参数也可以使用setKeepAliveTime(长时间，时间单位)方法动态更改。
 * 使用Long值。MAX_VALUE TimeUnit。纳秒有效地禁止空闲线程在关闭之前终止。默认情况下，keep-alive策略只适用于有超过corepoosize大小的线程的情况。
 * 但是方法allowCoreThreadTimeOut(boolean)也可以用于将这个超时策略应用到核心线程，只要keepAliveTime值不为零。
 * <p>
 * 5. 队列（queuing）
 * 任何阻塞队列都可以用来传输和保存提交的任务。此队列的使用与池大小交互:
 * 1). 如果运行的线程少于corePoolSize大小，执行程序总是喜欢添加一个新线程，而不是排队。
 * 2). 如果corePoolSize或更多的线程正在运行，执行程序总是希望对请求进行排队，而不是添加新的线程。
 * 3). 如果一个请求不能加入队列，就会创建一个新的线程，除非这个线程的大小超过最大值，在这种情况下，任务将被拒绝。
 * <p>
 * 排队的一般策略有三种:
 * 1). 直接的传递。（Direct handoffs）
 * 工作队列的一个不错的默认选择是同步队列，它将任务传递给线程，而不以其他方式持有它们。
 * 在这里，如果没有立即可用的线程来运行任务，那么对任务排队的尝试将失败，因此将构建一个新的线程。
 * 在处理可能具有内部依赖关系的请求集时，该策略避免了锁定。直接移交通常需要无界的最大池大小，以避免拒绝新提交的任务。
 * 这反过来又承认，当命令继续执行时，可能会出现无限制的线程增长
 * <p>
 * 2). 无界队列。（Unbounded queues）
 * 使用无界队列(例如，没有预定义容量的LinkedBlockingQueue)会在所有corePoolSize线程繁忙时导致新的任务在队列中等待。
 * 因此，只会创建corepoolol大小的线程。(因此，最大值大小的值没有任何影响。)当每个任务完全独立于其他任务时，这可能是合适的，因此任务不能相互影响执行;
 * 例如，在web页面服务器中。虽然这种排队方式在平滑短暂的请求爆发时很有用，但它承认，当命令继续以平均比处理速度快的速度到达时，会出现无限的工作队列增长。
 * <p>
 * 3). 有界队列。(Bounded queues.)
 * 有限的队列(例如ArrayBlockingQueue)在使用有限的最大池大小时有助于防止资源耗尽，但更难于调优和控制。
 * 队列大小和最大池大小可以相互交换:使用大队列和小池可以最小化CPU使用量、OS资源和上下文切换开销，但可能导致人为的低吞吐量。
 * 如果任务经常阻塞(例如，如果它们是I/O绑定的)，系统可能会为更多的线程安排时间。
 * 使用小队列通常需要更大的池大小，这会使cpu更忙，但可能会遇到不可接受的调度开销，这也会降低吞吐量。
 * <p>
 * 6. 拒绝接受任务(Rejected tasks)
 * 在方法execute(Runnable)中提交的新任务将在执行器关闭时被拒绝，当执行器为最大线程和工作队列容量使用有限界限时也会被拒绝，并且已经饱和。
 * 在这两种情况下，execute方法都调用RejectedExecutionHandler。RejectedExecutionHandler的RejectedExecutionHandler.rejectedExecution(Runnable, ThreadPoolExecutor)方法。
 * 提供了四个预定义的处理程序策略:
 * 1). 在默认ThreadPoolExecutor.AbortPolicy（）中，处理程序在拒绝时抛出运行时RejectedExecutionException。
 * 2). 在ThreadPoolExecutor.CallerRunsPolicy（调用者运行策略）。调用execute本身的线程CallerRunsPolicy运行任务。这提供了一个简单的反馈控制机制，可以减慢新任务提交的速度。
 * 3). 在ThreadPoolExecutor.DiscardPolicy（丢弃策略），一个无法执行的任务就会被删除。
 * 4). 在ThreadPoolExecutor.DiscardOldestPolicy(丢弃老的策略)，如果执行器未关闭，则删除工作队列头部的任务，然后重试执行(这会再次失败，导致重复执行)。
 * 可以定义和使用其他类型的RejectedExecutionHandler类。这样做需要一些注意，特别是当策略设计为仅在特定容量或队列策略下工作时。
 * <p>
 * 7. 钩子方法 （Hook methods）
 * 该类提供了在执行每个任务之前和之后调用的可覆盖的protected before execute(线程，可运行的)和afterExecute(可运行的，可抛出的)方法。这些可以用来操作执行环境;
 * 例如，重新初始化threadlocal、收集统计信息或添加日志条目。
 * 另外，方法terminated()可以被重写来执行执行器完全终止之后需要执行的任何特殊处理。
 * 如果钩子或回调方法抛出异常，内部工作线程可能会失败并突然终止。
 * <p>
 * 8. 队列维护（Queue maintenance）
 * 方法getQueue()允许访问工作队列，用于监视和调试。强烈反对将此方法用于任何其他目的。
 * 当大量排队的任务被取消时，可以使用remove(Runnable)和purge()两种方法来帮助进行存储回收
 * <p>
 * 9. 终结（Finalization）
 * 在程序中不再引用且没有剩余线程的池将自动关闭。
 * 如果您想确保即使用户忘记调用shutdown()，也能回收未引用的池，那么您必须通过设置适当的保持生存时间，
 * 使用零核心线程的下限和/或设置allowCoreThreadTimeOut(boolean)来安排未使用的线程最终死去。
 *
 * @author donggua
 * @version 1.0
 * @create 2018/8/26
 */
public class ThreadPoolExecutorBuild {


    public static void main(String[] args) {

        ThreadPoolExecutor executorService = (ThreadPoolExecutor) buildThreadPoolExecutor();

        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            if (activeCount != executorService.getActiveCount()
                    || queueSize != executorService.getQueue().size()) {
                System.out.println("==========开始监控============");
                // 返回池中同时存在的最大线程数。
                System.out.println("正在执行的线程数量：\t" + executorService.getActiveCount());
                // 返回此执行程序使用的任务队列。对任务队列的访问主要用于调试和监视。此队列可能正在使用中。检索任务队列不会阻止排队的任务执行。
                System.out.println("任务队列数：\t" + executorService.getQueue().size());
                System.out.println("核心线程数：\t" + executorService.getCorePoolSize());
                System.out.println("最大线程数: \t" + executorService.getMaximumPoolSize());

                activeCount = executorService.getActiveCount();
                queueSize = executorService.getQueue().size();
            }

        }


    }

    public static ExecutorService buildThreadPoolExecutor() {
        /**
         * <p>
         *  ThreadPoolExecutor 7大构造参数详解
         * </p>
         *
         * 使用给定的初始参数创建一个新的{@code ThreadPoolExecutor}。
         *
         * @param int corePoolSize  除非设置allowCoreThreadTimeOut，否则池中要保留的线程数，即使它们是空闲的
         *
         * @param int maximumPoolSize 池中允许的最大线程数
         *
         * @param long keepAliveTime  当线程数大于核心数时，这是空闲线程在终止之前等待新任务的最大时间。
         *
         * @param TimeUnit unit {@code keepAliveTime}参数的时间单元
         *
         * @param BlockingQueue<Runnable> workQueue   在执行任务之前用来保存任务的队列。此队列将仅保存由execute方法提交的可运行任务。
         *
         * @param ThreadFactory threadFactory  executor 创建新线程时要使用的工厂
         *
         * @param RejectedExecutionHandler handler 执行被阻塞时要使用的处理程序，因为线程边界和队列容量已经达到
         *
         * @throws IllegalArgumentException 如下列其中一项成立:<br>
         *                                      {@code corePoolSize < 0}<br>
         *                                      {@code keepAliveTime < 0}<br>
         *                                      {@code maximumPoolSize <= 0}<br>
         *                                      {@code maximumPoolSize < corePoolSize}
         *
         * @throws NullPointerException if {@code workQueue} or {@code threadFactory} or {@code handler} is null
         */


        /**
         * 几个测试案例：
         *  1. corePoolSize = 1; maxPoolSize = 2; BlockingQueue = 1; submit task = 3
         *  2. corePoolSize = 1; maxPoolSize = 2; BlockingQueue = 5; submit task = 7
         *  3. corePoolSize = 1; maxPoolSize = 2; BlockingQueue = 5; submit task = 8
         */
        int corePoolSize = 1;
        int maxPoolSize = 4;
        int blockingQueueCapacity = 5;
        int taskSize = 9;
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(blockingQueueCapacity),
                r -> {
                    Thread thread = new Thread(r);
                    return thread;
                },
                new ThreadPoolExecutor.AbortPolicy());

        System.out.println("==> 线程池创建完成。。。");

        for (int i = 0; i < taskSize; i++) {
            executorService.execute(() -> sleepSeconds(10));
        }

        return executorService;
    }


    public static void sleepSeconds(long sleepTimes) {
        try {
            System.out.println("======" + Thread.currentThread().getName() + "=====");
            TimeUnit.SECONDS.sleep(sleepTimes);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
