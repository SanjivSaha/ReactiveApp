package com.example.demo.demoreactivestreams.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.demoreactivestreams.beans.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.demo.demoreactivestreams.ContractorSubscriber;
import com.example.demo.demoreactivestreams.DemoProcessor;
import com.example.demo.demoreactivestreams.EmployeeHelper;
import com.example.demo.demoreactivestreams.beans.Contractor;

@RestController
public class DemoController {

	@GetMapping("/get/employee/all")
	 public List<Contractor> getAllEmployee() throws InterruptedException{
		        
		List<Contractor> contractor = new ArrayList<Contractor>();
		        // Create End Publisher
				SubmissionPublisher<Employee> publisher = new SubmissionPublisher<>();

				// Create Processor
				DemoProcessor transformProcessor = new DemoProcessor(s -> {
					contractor.add(new Contractor(s.getId(), s.getId() + 100, s.getName()));
					return new Contractor(s.getId(), s.getId() + 100, s.getName());
				});

				//Create End Subscriber
				ContractorSubscriber subs = new ContractorSubscriber();

				//Create chain of publisher, processor and subscriber
				publisher.subscribe(transformProcessor); // publisher to processor
				transformProcessor.subscribe(subs); // processor to subscriber

				List<Employee> emps = EmployeeHelper.getEmps();

				// Publish items
				System.out.println("Publishing Items to Subscriber");
				emps.stream().forEach(i -> publisher.submit(i));

				// Logic to wait for messages processing to finish
				while (emps.size() != subs.getCounter()) {
					Thread.sleep(10);
				}

				// Closing publishers
				publisher.close();
				transformProcessor.close();

				System.out.println("Exiting the app");
				return contractor;
	 }

}
