package com.queue;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactoryPrefix implements ThreadFactory {

	private final String prefix;
	private final ThreadGroup group;
	private static final AtomicInteger threadNumber = new AtomicInteger(1);

	public ThreadFactoryPrefix(String prefix) {
		super();
		SecurityManager securityManager = System.getSecurityManager();
		group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
		this.prefix = prefix + "-";
	}

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(group, r, prefix + threadNumber.getAndIncrement(), 0);
	}

}
