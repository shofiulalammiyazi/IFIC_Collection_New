package com.csinfotechbd.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

	private static final long serialVersionUID = -4791426876836859922L;
	private String module;

	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		module = request.getParameter("module");
	}

	public String getModudleName() {
		return module;
	}

}
