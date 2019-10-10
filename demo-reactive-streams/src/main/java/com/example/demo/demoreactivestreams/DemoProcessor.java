package com.example.demo.demoreactivestreams;

import java.util.List;
import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

import com.example.demo.demoreactivestreams.beans.Employee;
import com.example.demo.demoreactivestreams.beans.Contractor;

public class DemoProcessor extends SubmissionPublisher<Contractor> implements Processor<Employee, Contractor> {

	private Subscription subscription;
	private Function<Employee,Contractor> function;
	
	public DemoProcessor(Function<Employee,Contractor> function) {  
	    super();  
	    this.function = function;  
	  }  
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(Employee emp) {
		submit((Contractor) function.apply(emp));  
	    subscription.request(1);  
	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("Done");
	}

}

