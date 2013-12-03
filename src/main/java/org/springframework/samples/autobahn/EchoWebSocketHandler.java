package org.springframework.samples.autobahn;

import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;


public class EchoWebSocketHandler extends AbstractWebSocketHandler {

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		session.sendMessage(message);
	}

	@Override
	public boolean supportsPartialMessages() {
		return true;
	}

}
