package org.springframework.samples.autobahn;

import java.util.concurrent.CountDownLatch;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;


public class CloseLatchWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

	private final CountDownLatch latch;


	public CloseLatchWebSocketHandlerDecorator(WebSocketHandler delegate, CountDownLatch latch) {
		super(delegate);
		this.latch = latch;
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		super.afterConnectionClosed(session, closeStatus);
		this.latch.countDown();
	}

}
