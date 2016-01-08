package com.queue.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TraditionalBQueue<E> implements BQueue<E> {

	private List<E> queue = new ArrayList<>();
	private int capacity;

	public TraditionalBQueue(int capacity) {
		super();
		this.capacity = capacity;
	}

	@Override
	public void put(E e) throws InterruptedException {
		synchronized (queue) {
			while (queue.size() == capacity) {

				System.out.println(
						"Queue is full " + Thread.currentThread().getName() + " is waiting , size: " + queue.size());
				queue.wait();
			}

			boolean isAdded = queue.add(e);

			if (isAdded) {
				System.out.println(String.format("%s added to queue by %s", e, Thread.currentThread().getName()));

				if (queue.size() == capacity) {
					System.out.println(String.format("Signalling that queue might be full : %s", e,
							Thread.currentThread().getName()));
				}
				queue.notifyAll();
			}
		}

	}

	@Override
	public boolean offer(E e) throws InterruptedException {
		synchronized (queue) {
			if (queue.size() == capacity)
				return false;

			return queue.add(e);
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit timeunit) throws InterruptedException {
		synchronized (queue) {
			if (queue.size() == capacity && timeout != 0) {
				queue.wait(timeunit.toMillis(timeout));
			}

			if (queue.size() == capacity)
				return false;

			boolean isAdded = queue.add(e);

			if (isAdded) {
				System.out.println(String.format("%s added to queue by %s", e, Thread.currentThread().getName()));

				if (queue.size() == capacity) {
					System.out.println(String.format("Signalling that queue might be full : %s", e,
							Thread.currentThread().getName()));
				}
				queue.notifyAll();
			}
			return isAdded;
		}
	}

	@Override
	public void poll(long timeout, TimeUnit timeunit) throws InterruptedException {
		synchronized (queue) {
			if (queue.isEmpty() && timeout != 0) {
				queue.wait(timeunit.toMillis(timeout));
			}

			if (queue.isEmpty())
				return;

			E e = queue.remove(0);

			if (e != null) {
				System.out.println(String.format("%s removed from queue by %s", e, Thread.currentThread().getName()));

				if (queue.isEmpty()) {
					System.out.println(String.format("Signalling that queue might be empty : %s", e,
							Thread.currentThread().getName()));
				}
				queue.notifyAll();
			}
		}
	}

	@Override
	public void take() throws InterruptedException {
		synchronized (queue) {
			while (queue.isEmpty()) {
				System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting ");
				queue.wait();
			}

			E e = queue.remove(0);

			if (e != null) {
				System.out.println(String.format("%s removed from queue by %s", e, Thread.currentThread().getName()));

				if (queue.isEmpty()) {
					System.out.println(String.format("Signalling that queue might be empty : %s", e,
							Thread.currentThread().getName()));
				}
				queue.notifyAll();
			}
		}
	}

}
