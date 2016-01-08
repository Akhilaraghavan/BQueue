package com.queue.api;

import java.util.concurrent.TimeUnit;

public interface BQueue<E> {
	
	/**
	 * Add the element to queue. Wait if the queue is full and then add element to queue.
	 * @param e
	 * @throws InterruptedException
	 */
	void put(E e) throws InterruptedException;
	
	/**
	 * Add element to queue . If queue is full return
	 * @param e
	 * @return
	 * @throws InterruptedException
	 */
	boolean offer(E e) throws InterruptedException;
	
	/**
	 * Add element to queue with waiting for a given time if the queue is full.
	 * @param e
	 * @param timeout
	 * @param timeunit
	 * @return
	 * @throws InterruptedException
	 */
	boolean offer(E e, long timeout, TimeUnit timeunit) throws InterruptedException;
		
	/**
	 * Remove element from queue after waiting for the specified timeout. Return if queue is empty.
	 * @param timeout
	 * @param timeunit
	 * @throws InterruptedException
	 */
	void poll(long timeout, TimeUnit timeunit) throws InterruptedException;
	
	/**
	 * Remove element from queue. If queue is empty wait for element to be available for removal.
	 * @throws InterruptedException
	 */
	void take() throws InterruptedException;
		
}
