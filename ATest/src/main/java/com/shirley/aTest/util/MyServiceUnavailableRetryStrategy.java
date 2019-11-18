package com.shirley.aTest.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;

public class MyServiceUnavailableRetryStrategy implements ServiceUnavailableRetryStrategy {

	private int executionCount;
	private int retryInterval;

	MyServiceUnavailableRetryStrategy(Builder builder) {
		this.executionCount = builder.executionCount;
		this.retryInterval = builder.retryInterval;
	}

	/**
	 * retry逻辑
	 */
	@Override
	public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
		if (response.getStatusLine().getStatusCode() == (408 | 502 | 503 | 504)
				&& executionCount <= this.executionCount) {
			return true;
		} else
			return false;
	}

	/**
	 * retry间隔时间
	 */
	@Override
	public long getRetryInterval() {
		return this.retryInterval;
	}

	public static final class Builder {
		private int executionCount;
		private int retryInterval;

		public Builder() {
			executionCount = 0;
			retryInterval = 0;
		}

		public Builder executionCount(int executionCount) {
			this.executionCount = executionCount;
			return this;
		}

		public Builder retryInterval(int retryInterval) {
			this.retryInterval = retryInterval;
			return this;
		}

		public MyServiceUnavailableRetryStrategy build() {
			return new MyServiceUnavailableRetryStrategy(this);
		}
	}

}