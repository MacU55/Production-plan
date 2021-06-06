package org.example.auth;

import org.example.dto.UserDTO;
import org.example.exception.ServiceException;
import org.example.exception.WebAppException;
import org.example.service.IUSerService;
import org.example.service.UserService;
import org.example.utils.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "loginServlet",
        urlPatterns = RouteHelper.LOGIN_URL_PATTERN
)
public class LoginServlet extends HttpServlet {

    private final IUSerService userService = UserService.get();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        throw new WebAppException(new ServiceException("GET request is not supported"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            UserDTO user = userService.findByEmail(email);

            //call IAuthenticationService.login
            if (password.equals("admin123")) {
                RouteHelper.forwardToHomePage(request, response, user);
            } else {
                String message = "Combination of email and password was not found";
                RouteHelper.forwardToLoginPage(request, response, message);
            }
        } catch (ServiceException e) {
            throw new WebAppException(e);
        }
    }
}  