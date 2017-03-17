package io.github.xiong_it.easypay.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:{@see <a href="http://blog.csdn.net/xiong_it">http://blog.csdn.net/xiong_it</a>} | {@see <a href="https://xiong-it.github.io">https://xiong-it.github.io</a>}
 * github:{@see <a href="https://github.com/xiong-it">https://github.com/xiong-it</a>}
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
