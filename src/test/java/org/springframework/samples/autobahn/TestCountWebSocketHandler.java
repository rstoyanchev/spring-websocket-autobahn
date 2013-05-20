package org.springframework.samples.autobahn;

import java.util.concurrent.CountDownLatch;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.TextWebSocketHandlerAdapter;


public class TestCountWebSocketHandler extends TextWebSocketHandlerAdapter {

	private int testCount = -1;

	private CountDownLatch latch = new CountDownLatch(1);


	public int getTestCount() throws InterruptedException {
		this.latch.await();
		return this.testCount;
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		this.testCount = Integer.valueOf(message.getPayload()).intValue();
		this.latch.countDown();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		this.latch.countDown();
	}

}
