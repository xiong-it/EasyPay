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
 * Description: here is the description for this file.
 */

public class ThreadManager {
    private static ExecutorService mExecutors;

    public static void execute(Runnable runnable) {
        mExecutors = Executors.newSingleThreadExecutor();
        mExecutors.execute(runnable);
    }

    public static void shutdown() {
        if (mExecutors == null) return;
        if (mExecutors.isShutdown()) return;
        mExecutors.shutdownNow();
        mExecutors = null;
    }
}
