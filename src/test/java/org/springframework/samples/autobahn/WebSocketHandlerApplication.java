package org.springframework.samples.autobahn;

import java.util.concurrent.CountDownLatch;

import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.adapter.WebSocketHandlerAdapter;
import org.springframework.web.socket.client.endpoint.StandardWebSocketClient;


public class WebSocketHandlerApplication {

	private static final String AGENT = "SpringWebSocketHandler";

	private static final String BASE_URI = "ws://127.0.0.1:9001";


	public static void main(String[] args) throws InterruptedException {

		StandardWebSocketClient client = new StandardWebSocketClient();

		int testCount = getTestCaseCount(client);

		try {
			WebSocketHandler echoHandler = new EchoWebSocketHandler();

			for (int i=1 ; i <= testCount; i++) {
				CountDownLatch closeLatch = new CountDownLatch(1);
				echoHandler = new CloseLatchWebSocketHandlerDecorator(echoHandler, closeLatch);
				client.doHandshake(echoHandler, BASE_URI + "/runCase?case=" + i + "&agent=" + AGENT);

				System.out.println("Waiting for test case " + i + " to complete");
				closeLatch.await();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			updateReports(client);
		}

		System.out.println("Done");
		System.exit(0);
	}

	private static int getTestCaseCount(StandardWebSocketClient client) throws InterruptedException {

		TestCountWebSocketHandler testCountHandler = new TestCountWebSocketHandler();
		client.doHandshake(testCountHandler, BASE_URI + "/getCaseCount");

		int testCount = testCountHandler.getTestCount();
		Assert.isTrue(testCount != -1, "Failed to obtain test case count");

		return testCount;
	}

	private static void updateReports(StandardWebSocketClient client) throws InterruptedException {

		CountDownLatch closeLatch = new CountDownLatch(1);

		WebSocketHandler reportHandler = new WebSocketHandlerAdapter();
		reportHandler = new CloseLatchWebSocketHandlerDecorator(reportHandler, closeLatch);
		client.doHandshake(reportHandler, BASE_URI + "/updateReports?agent=" + AGENT);

		System.out.println("Waiting for report generation to complete");
		closeLatch.await();
	}

}
