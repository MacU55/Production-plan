package org.example.utils;

import org.example.dto.UserDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RouteHelper {
    public static final String LOGIN_URL_PATTERN = "/login";
    public static final String LOGOUT_URL_PATTERN = "/logout";
    
    public static final String LOGIN_PAGE = "login.jsp";
    public static final String HOME_PAGE = "loggedInHome.jsp";
    
    //orders
    public static final String ORDERS_URL_PATTERN = "/orders";
    public static final String DESIGNSDEPT_URL_PATTERN = "/designs";
    public static final String ORDER_URL_PATTERN = "/order";
    public static final String CREATE_NEW_ORDER_PAGE = "createOrder.html";

    //users
    public static final String USERS_URL_PATTERN = "/users";
    public static final String USER_URL_PATTERN = "/user";
    public static final String CREATE_NEW_USER_PAGE = "createUser.html";
    
    //attributes
    public static final String ATTRIBUTE_NAME = "name";
    public static final String ATTRIBUTE_CONTAINER = "container";
    
    private RouteHelper() {

    }

    public static void forwardToLoginPage(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        if (message != null) {
            request.setAttribute("message", message);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(LOGIN_PAGE);
        dispatcher.forward(request, response);
    }
    
    public static void forwardToLoginPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToLoginPage(request, response, null);
    }
    
    public static void forwardToHomePage(HttpServletRequest request, HttpServletResponse response, UserDTO user) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String name = UserHelper.formatUserName(user);
        session.setAttribute(RouteHelper.ATTRIBUTE_NAME, name);
        forwardTo(request, response, HOME_PAGE);
    }

    public static void forwardToHomePage(HttpServletRequest request, HttpServletResponse response, String containerContent) throws ServletException, IOException {
        request.setAttribute(ATTRIBUTE_CONTAINER, containerContent);
        forwardTo(request, response, HOME_PAGE);
    }

    public static void forwardTo(HttpServletRequest request, HttpServletResponse response, String urlPattern) throws ServletException, IOException {
        request.getRequestDispatcher(urlPattern).forward(request, response);
    }

    public static void redirectTo(HttpServletRequest request, HttpServletResponse response, String urlPattern) throws ServletException, IOException {
        response.sendRedirect(urlPattern);
    }
    
    public static boolean isLoggedIn(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        return (session != null && session.getAttribute(ATTRIBUTE_NAME) != null);
    }
    
    public static String getURL(HttpServletRequest httpRequest, String urlPattern) {
        return httpRequest.getContextPath() + urlPattern;
    }

    public static String getPageURL(HttpServletRequest httpRequest, String page) {
        return httpRequest.getContextPath() + "/" + page;
    }
}
