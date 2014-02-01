package com.datals.foundation.interactor.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.datals.foundation.interactor.api.Task;
import com.datals.foundation.interactor.api.TaskContext;
import com.datals.foundation.interactor.api.TaskState;

/**
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class TaskThreadPoolExecutor extends ThreadPoolExecutor {

    private ThreadLocal<TaskContext> localTaskContext = new InheritableThreadLocal<TaskContext>();

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    @Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		TaskContext taskContext = ((Task) r).getTaskContext();
		taskContext.getTaskStatus().setTaskState(TaskState.RUNNING);
		localTaskContext.set(taskContext);
	}

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        localTaskContext.set(null);
    }

    public TaskContext getCurrentContext() {
        return localTaskContext.get();
    }

}
