package analysis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class containing the thread manager for all the {@link EventAnalyser} objects.
 */
public class ThreadManager {

    public static final ExecutorService THREAD_MANAGER = Executors.newCachedThreadPool();

}