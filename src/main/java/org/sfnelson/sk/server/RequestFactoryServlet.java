package org.sfnelson.sk.server;

import com.google.gwt.requestfactory.server.ExceptionHandler;
import com.google.gwt.requestfactory.shared.ServerFailure;

@SuppressWarnings("serial")
public class RequestFactoryServlet extends com.google.gwt.requestfactory.server.RequestFactoryServlet {

	public RequestFactoryServlet() {
		super(new ExceptionHandler() {

			@Override
			public ServerFailure createServerFailure(Throwable throwable) {
				throwable.printStackTrace();
				return new ServerFailure(throwable.getMessage());
			}

		});
	}
}
