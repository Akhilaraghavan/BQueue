package com.queue;

import com.queue.api.BQueue;

public class Consumer implements Runnable {

	private BQueue<Integer> blockingQueue;

	public Consumer(BQueue<Integer> queue) {
		this.blockingQueue = queue;
	}

	@Override
	public void run() {
		do {
			try {
				Thread.sleep(550);
				blockingQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

}
