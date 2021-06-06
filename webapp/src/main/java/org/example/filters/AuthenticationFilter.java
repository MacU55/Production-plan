package org.example.filters;

import org.example.utils.RouteHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private static final Function<String, Pattern> CHECK_CONTEXT_PATH = s -> Pattern.compile("^" + s + "(/)?$");

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        boolean isLoggedIn = RouteHelper.isLoggedIn(httpRequest);
        boolean isLoginAttempt = isLoginAttempt(httpRequest);
        boolean isOnStartPage = isOnStartPage(httpRequest);

        if (isLoggedIn || isOnStartPage || isLoginAttempt) {
            chain.doFilter(request, response);
        } else {
            RouteHelper.forwardToLoginPage(httpRequest, httpResponse);
        }
    }

    private static boolean isOnStartPage(HttpServletRequest httpRequest) {
        boolean endsWiIthIndex = httpRequest.getRequestURI().endsWith("index.html");
        Matcher matcher = CHECK_CONTEXT_PATH.apply(httpRequest.getContextPath())
                .matcher(httpRequest.getRequestURI());
        return endsWiIthIndex || matcher.matches();
    }

    private static boolean isLoginAttempt(HttpServletRequest httpRequest) {
        String loginURI = RouteHelper.getURL(httpRequest, RouteHelper.LOGIN_URL_PATTERN);
        String loginURIPage = RouteHelper.getPageURL(httpRequest, RouteHelper.LOGIN_PAGE);
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isOnLoginPage = httpRequest.getRequestURI().equals(loginURIPage);
        return isLoginRequest || isOnLoginPage;
    }
}
