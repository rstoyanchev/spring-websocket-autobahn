/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.springframework.samples.autobahn;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.socket.server.endpoint.SpringConfigurator;

@ServerEndpoint(value = "/annotatedEchoEndpoint", configurator = SpringConfigurator.class)
public class AnnotatedEchoEndpoint {

	@OnMessage
	public void echoTextMessage(Session session, String message, boolean isLast) {
		try {
			if (session.isOpen()) {
				session.getBasicRemote().sendText(message, isLast);
			}
		} catch (IOException e) {
			try {
				session.close();
			} catch (IOException e1) {
				// Ignore
			}
		}
	}

	@OnMessage
	public void echoBinaryMessage(Session session, ByteBuffer buffer, boolean isLast) {
		try {
			if (session.isOpen()) {
				session.getBasicRemote().sendBinary(buffer, isLast);
			}
		} catch (IOException e) {
			try {
				session.close();
			} catch (IOException e1) {
				// Ignore
			}
		}
	}
}
