package org.example.admin.user;


import org.example.dto.UserDTO;
import org.example.exception.ServiceException;
import org.example.exception.WebAppException;
import org.example.service.IUSerService;
import org.example.service.UserService;
import org.example.utils.RouteHelper;
import org.example.utils.UserHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "usersServlet",
        urlPatterns = RouteHelper.USERS_URL_PATTERN
)
public class UsersServlet extends HttpServlet {
    private final IUSerService userService = UserService.get();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            List<UserDTO> users = userService.findUsers();
            String usersGrid = getUsersView(request, users);
            RouteHelper.forwardToHomePage(request, response, usersGrid);
        } catch (ServiceException e) {
            throw new WebAppException(e);
        }
    }

    private String getUsersView(HttpServletRequest request, List<UserDTO> users) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<a href='").append(RouteHelper.getPageURL(request, RouteHelper.CREATE_NEW_USER_PAGE)).append("'>Add New User</a>");
        stringBuilder.append("<h1>Users List</h1>");
        stringBuilder.append("<table border='1' width='100%'");
        stringBuilder.append("<tr><th>Id</th><th>Name</th><th>Email</th><th>Edit</th></tr>");
        for (UserDTO user : users) {
            stringBuilder.append("<tr>")
                    .append("<td>").append(user.getId()).append("</td>")
                    .append("<td>").append(UserHelper.formatUserName(user)).append("</td>")
                    .append("<td>").append(user.getEmail()).append("</td>")
                    .append("<td>").append("<a href='").append(RouteHelper.getURL(request, RouteHelper.USER_URL_PATTERN)).append("?id=").append(user.getId()).append("'>edit</a></td>")
                    .append("</tr>");
        }
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }

}
