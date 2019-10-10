package com.example.demo.demoreactivestreams;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import com.example.demo.demoreactivestreams.beans.Contractor;

public class ContractorSubscriber implements Subscriber<Contractor> {

	private Subscription subscription;
	
	private int counter = 0;
	
	@Override
	public void onSubscribe(Subscription subscription) {
		System.out.println("Subscribed for Contractor");
		this.subscription = subscription;
		this.subscription.request(1); //requesting data from publisher
		System.out.println("onSubscribe requested 1 item for Contractor");
	}

	@Override
	public void onNext(Contractor item) {
		System.out.println("Processing Contractor "+item);
		counter++;
		this.subscription.request(1);
	}

	@Override
	public void onError(Throwable e) {
		System.out.println("Some error happened in ContractorSubscriber");
		e.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("All Processing Done for ContractorSubscriber");
	}

	public int getCounter() {
		return counter;
	}

}

