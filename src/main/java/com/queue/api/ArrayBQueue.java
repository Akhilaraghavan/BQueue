package com.queue.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBQueue<E> implements BQueue<E> {

	private List<E> queue = new ArrayList<>();
	private ReentrantLock reentrantLock = new ReentrantLock();
	private final Condition queueNotFull = reentrantLock.newCondition();
	private final Condition queueNotEmpty = reentrantLock.newCondition();

	private int capacity;

	public ArrayBQueue(int capacity) {
		super();
		this.capacity = capacity;
	}

	@Override
	public void put(E e) throws InterruptedException {
		try {
			reentrantLock.lock();
			while (queue.size() == capacity) {
				System.out.println(
						"Queue is full " + Thread.currentThread().getName() + " is waiting , size: " + queue.size());

				queueNotEmpty.await();
			}

			boolean isAdded = queue.add(e);
			if (isAdded) {
				System.out.println(
						String.format("%s added to queue by producer %s", e, Thread.currentThread().getName()));
				if (queue.size() == capacity) {
					System.out.println(String.format("%s : Signalling that the queue may be full now",
							Thread.currentThread().getName()));
				}
				queueNotFull.signalAll();
			}

		} finally {
			reentrantLock.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		try {
			reentrantLock.lock();

			if (queue.size() == capacity)
				return false;

			return queue.add(e);
		} finally {
			reentrantLock.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit timeunit) throws InterruptedException {
		try {
			reentrantLock.lock();

			if (queue.size() == capacity && timeout != 0) {
				queueNotEmpty.await(timeout, timeunit);
			}

			if (queue.size() == capacity)
				return false;

			boolean isAdded = queue.add(e);
			if (isAdded) {
				System.out.println(
						String.format("%s added to queue by producer %s", e, Thread.currentThread().getName()));
				if (queue.size() == capacity) {
					System.out.println(String.format("%s : Signalling that the queue may be full now",
							Thread.currentThread().getName()));
				}
				queueNotFull.signalAll();
			}
			return isAdded;
		} finally {
			reentrantLock.unlock();
		}
	}

	@Override
	public void poll(long timeout, TimeUnit timeunit) throws InterruptedException {
		try {
			reentrantLock.lock();

			if (timeout != 0) {
				queueNotFull.await(timeout, timeunit);
			}

			if (queue.isEmpty())
				return;

			E e = queue.remove(0);
			if (e != null) {
				System.out.println(
						String.format("%s removed from queue by consumer %s", e, Thread.currentThread().getName()));
				if (queue.isEmpty()) {
					System.out.println(String.format("%s : Signalling that the queue may be empty now",
							Thread.currentThread().getName()));
				}
				queueNotEmpty.signalAll();
			}
		} finally {
			reentrantLock.unlock();
		}
	}

	@Override
	public void take() throws InterruptedException {
		try {
			reentrantLock.lock();

			while (queue.isEmpty()) {
				System.out.println(
						"Queue is empty " + Thread.currentThread().getName() + " is waiting ");
			
				queueNotFull.await();
			}

			E e = queue.remove(0);
			if (e != null) {
				System.out.println(
						String.format("%s removed from queue by consumer %s", e, Thread.currentThread().getName()));
				if (queue.isEmpty()) {
					System.out.println(String.format("%s : Signalling that the queue may be empty now",
							Thread.currentThread().getName()));
				}
				queueNotEmpty.signalAll();
			}

		} finally {
			reentrantLock.unlock();
		}
	}

}
