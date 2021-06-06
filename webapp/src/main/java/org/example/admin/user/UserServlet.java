package org.example.admin.user;


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
        name = "userServlet",
        urlPatterns = RouteHelper.USER_URL_PATTERN
)
public class UserServlet extends HttpServlet {

    private final IUSerService userService = UserService.get();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            UserDTO user = userService.findById(id);
            String userView = this.getUserView(request, user);
            RouteHelper.forwardToHomePage(request, response, userView);
        } catch (ServiceException e) {
            throw new WebAppException(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDTO user = this.getUser(request);
            if (user.getId() == 0) {
                userService.save(user);
            }
            else {
                userService.update(user);
            }
            RouteHelper.redirectTo(request, response, RouteHelper.getURL(request, RouteHelper.USERS_URL_PATTERN));
        } catch (ServiceException e) {
            throw new WebAppException(e);
        }
    }

    private UserDTO getUser(HttpServletRequest request) {
        String sid  =request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String email = request.getParameter("email");
        UserDTO user = new UserDTO();
        if (sid != null) {
            user.setId(Integer.parseInt(sid));
        }
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setEmail(email);
        return user;
    }

    private String getUserView(HttpServletRequest request, UserDTO user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h1>Update User</h1>");
        stringBuilder.append("<form action='").append(RouteHelper.getURL(request, RouteHelper.USER_URL_PATTERN))
                .append("' method='post'>");
        stringBuilder.append("<table>");
        stringBuilder.append("<tr><td></td><td><input type='hidden' name='id' value='")
                .append(user.getId()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>First Name:</td><td><input type='text' name='firstName' value='")
                .append(user.getFirstName()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Second Name:</td><td><input type='text' name='secondName' value='")
                .append(user.getSecondName()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Email:</td><td><input type='email' name='email' value='")
                .append(user.getEmail()).append("'/></td></tr>");
        stringBuilder.append("</td></tr>");
        stringBuilder.append("<tr><td colspan='2'><input type='submit' value='Save'/></td></tr>");
        stringBuilder.append("</table>");
        stringBuilder.append("</form>");
        stringBuilder.append("<a href='").append(RouteHelper.getURL(request, RouteHelper.USERS_URL_PATTERN)).append("Users</a>");
        return stringBuilder.toString();
    }

}
