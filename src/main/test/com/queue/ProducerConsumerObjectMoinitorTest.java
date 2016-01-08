package com.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.queue.api.BQueue;
import com.queue.api.TraditionalBQueue;

public class ProducerConsumerObjectMoinitorTest {

	public static void main(String args[]) {
		ExecutorService producers = Executors.newFixedThreadPool(3, new ThreadFactoryPrefix("PRODUCER"));
		ExecutorService consumers = Executors.newFixedThreadPool(10, new ThreadFactoryPrefix("CONSUMER"));

		try {

			BQueue<Integer> queue = new TraditionalBQueue<>(10);
			producers.execute(new Producer(queue));
			producers.execute(new Producer(queue));
			producers.execute(new Producer(queue));

			consumers.execute(new Consumer(queue));
			consumers.execute(new Consumer(queue));
			consumers.execute(new Consumer(queue));

		} finally {
			producers.shutdown();
			consumers.shutdown();
		}

	}
}
