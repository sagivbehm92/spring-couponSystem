package core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.api.UtilApi;

public class LoginFilter implements Filter {
	private int count = 0;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// System.out.println("from filter LoginFilter get request number:" +(++count));
		// casting from ServletResponse too HttpServletResponse
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		// casting from ServletRequest too httpServletRequest
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// get string directions url httpServletRequest
		String pathRequest = httpServletRequest.getRequestURI().toString();
		// get cookie userId if exists else return null
		// in the next version the cookieUserId change to cookie token
		Cookie cookieUserId = UtilApi.getCookie(httpServletRequest, "userId");
		// if client not yet logged in and the path ends with /login
		// and if the string and with /coupon/getall does not matter the cookie id value
		// always access
		if (cookieUserId == null && (pathRequest.endsWith("/login") || pathRequest.endsWith("/coupon/getall"))) {
			chain.doFilter(httpServletRequest, httpServletResponse);
			// return when the request back to this filter after layer api
			return;
		}
		// if the cookieUserId not null and the string directions url not and ends
		// with(if the user logged in)
		if (cookieUserId != null && !(pathRequest.endsWith("/login"))) {

			// checking permission (in the next upgrade)

			chain.doFilter(httpServletRequest, httpServletResponse);
			// return when the request back to this filter after layer api
			return;

			// if the cookieUserId not null and the string directions url ends with
			// "/login"(if the user
			// logged in and try too go login again)
		} else if (cookieUserId != null && (pathRequest.endsWith("/login"))) {
			httpServletResponse.setStatus(420);
			httpServletResponse.setContentType("text/plain");
			httpServletResponse.getWriter().println("you already login in the system");
			// return when the request back to this filter after layer api
			return;
		}

		// if client not yet logged in and the path not ends with "/login" or
		// "/coupon/getall"
		System.out.println(pathRequest);
		httpServletResponse.setStatus(420);
		httpServletResponse.setContentType("text/plain");
		httpServletResponse.getWriter().println("you must first login ,You are not registered in the system");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
