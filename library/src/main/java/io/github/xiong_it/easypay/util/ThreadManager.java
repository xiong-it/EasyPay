package io.github.xiong_it.easypay.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description: 简易线程池管理工具.
 */

public class ThreadManager {
    private static ExecutorService mExecutors = Executors.newSingleThreadExecutor();

    public static void execute(Runnable runnable) {
        if (mExecutors == null) {
            mExecutors = Executors.newSingleThreadExecutor();
        }
        mExecutors.execute(runnable);
    }

    public static void shutdown() {
        if (mExecutors == null) return;
        if (mExecutors.isShutdown()) return;
        mExecutors.shutdownNow();
        mExecutors = null;
    }
}
