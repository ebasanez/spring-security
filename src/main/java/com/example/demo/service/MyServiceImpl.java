package com.example.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author ebasanez
 * @since 2021-02-08
 */
@Service
public class MyServiceImpl implements MyService {

	@Async
	@Override
	public void myAsyncMethod() {
		// Will i have access to security context in abnother thread? (Spring security context, by default, use per thread storage).
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		System.out.println(Thread.currentThread().getName());
	}

}
