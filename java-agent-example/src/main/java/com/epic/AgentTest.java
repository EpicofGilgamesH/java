package com.epic;

import java.util.concurrent.TimeUnit;

public class AgentTest {

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 1000; i++) {
			System.out.println("process result: " + process());
			TimeUnit.SECONDS.sleep(5);
		}
	}

	public static String process() {
		System.out.println("process!");
		return "success";
	}
}