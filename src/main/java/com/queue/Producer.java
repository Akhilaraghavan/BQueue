package com.queue;

import java.util.Random;

import com.queue.api.BQueue;

public class Producer implements Runnable {
	
	private static final Random THE_RANDOM = new Random(1);
	private BQueue<Integer> blockingQueue;

	public Producer(BQueue<Integer> queue) {
		this.blockingQueue = queue;

	}

	@Override
	public void run() {
		do {
			try {
				Thread.sleep(500);
				blockingQueue.put(THE_RANDOM.nextInt());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}
}
