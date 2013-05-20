package org.springframework.samples.autobahn;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;


public class EchoEndpoint extends Endpoint {


	@Override
	public void onOpen(final Session session, EndpointConfig endpointConfig) {

		session.addMessageHandler(new MessageHandler.Partial<String>() {

			@Override
			public void onMessage(String message, boolean isLast) {
				try {
					session.getBasicRemote().sendText(message, isLast);
				} catch (IOException e) {
					try {
						session.close();
					} catch (IOException e1) {
						// Ignore
					}
				}
			}
		});

		session.addMessageHandler(new MessageHandler.Partial<ByteBuffer>() {

			@Override
			public void onMessage(ByteBuffer buffer, boolean isLast) {
				try {
					session.getBasicRemote().sendBinary(buffer, isLast);
				} catch (IOException e) {
					try {
						session.close();
					} catch (IOException e1) {
						// Ignore
					}
				}
			}
		});

	}
}
