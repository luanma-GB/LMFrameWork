/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.android.global;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import cc.android.utils.CcStrUtil;

/**
 * 公共异常类
 * 
 *
 */
public class CcAppException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** 异常消息. */
	private String msg = null;

	/**
	 * 构造异常类.
	 *
	 * @param e
	 *            异常
	 */
	public CcAppException(Exception e) {
		super();

		try {
			if (e instanceof HttpHostConnectException) {
				msg = CcAppConfig.UNKNOWNHOSTEXCEPTION;
			} else if (e instanceof ConnectException) {
				msg = CcAppConfig.CONNECTEXCEPTION;
			} else if (e instanceof ConnectTimeoutException) {
				msg = CcAppConfig.CONNECTEXCEPTION;
			} else if (e instanceof UnknownHostException) {
				msg = CcAppConfig.UNKNOWNHOSTEXCEPTION;
			} else if (e instanceof SocketException) {
				msg = CcAppConfig.SOCKETEXCEPTION;
			} else if (e instanceof SocketTimeoutException) {
				msg = CcAppConfig.SOCKETTIMEOUTEXCEPTION;
			} else if (e instanceof NullPointerException) {
				msg = CcAppConfig.NULLPOINTEREXCEPTION;
			} else if (e instanceof ClientProtocolException) {
				msg = CcAppConfig.CLIENTPROTOCOLEXCEPTION;
			} else {
				if (e == null || CcStrUtil.isEmpty(e.getMessage())) {
					msg = CcAppConfig.NULLMESSAGEEXCEPTION;
				} else {
					msg = e.getMessage();
				}
			}
		} catch (Exception e1) {
		}

	}

	/**
	 * 用一个消息构造异常类.
	 *
	 * @param message
	 *            异常的消息
	 */
	public CcAppException(String message) {
		super(message);
		msg = message;
	}

	/**
	 * 描述：获取异常信息.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return msg;
	}

}
