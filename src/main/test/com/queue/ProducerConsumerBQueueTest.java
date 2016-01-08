package com.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.queue.api.BQueue;
import com.queue.api.ArrayBQueue;

public class ProducerConsumerBQueueTest {

	public static void main(String args[]) {
		ExecutorService producers = Executors.newFixedThreadPool(3, new ThreadFactoryPrefix("PRODUCER"));
		ExecutorService consumers = Executors.newFixedThreadPool(10, new ThreadFactoryPrefix("CONSUMER"));

		try {

			BQueue<Integer> blockingQueue = new ArrayBQueue<>(10);
			producers.execute(new Producer(blockingQueue));
			producers.execute(new Producer(blockingQueue));
			producers.execute(new Producer(blockingQueue));

			consumers.execute(new Consumer(blockingQueue));
			consumers.execute(new Consumer(blockingQueue));
			consumers.execute(new Consumer(blockingQueue));

		} finally {
			producers.shutdown();
			consumers.shutdown();
		}

	}
}
