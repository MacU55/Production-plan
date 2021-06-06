package org.example.order;

import org.example.dto.OrderDTO;
import org.example.exception.ServiceException;
import org.example.exception.WebAppException;
import org.example.service.IOrderService;
import org.example.service.OrderService;
import org.example.utils.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(
        name = "orderServlet",
        urlPatterns = RouteHelper.ORDER_URL_PATTERN
)
public class OrderServlet extends HttpServlet {

    private final IOrderService orderService = OrderService.get();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            OrderDTO order = orderService.findById(id);
            String orderView = this.getOrderView(request, order);
            RouteHelper.forwardToHomePage(request, response, orderView);
        } catch (ServiceException e) {
            throw new WebAppException(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            OrderDTO order = this.getOrder(request);
            if (order.getId() == 0) {
                orderService.save(order);
            }
            else {
                orderService.update(order);
            }
            RouteHelper.redirectTo(request, response, RouteHelper.getURL(request, RouteHelper.ORDERS_URL_PATTERN));
        } catch (ServiceException e) {
            throw new WebAppException(e);
        }
    }

    private OrderDTO getOrder(HttpServletRequest request) {
        String sid = request.getParameter("id");
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        OrderDTO order = new OrderDTO();
        if (sid != null) {
            order.setId(Integer.parseInt(sid));
        }
        order.setName(name);
        order.setQuantity(quantity);
        return order;
    }

    private String getOrderView(HttpServletRequest request, OrderDTO user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h1>Update Order</h1>");
        stringBuilder.append("<form action='").append(RouteHelper.getURL(request, RouteHelper.ORDER_URL_PATTERN))
                .append("' method='post'>");
        stringBuilder.append("<table>");
        stringBuilder.append("<tr><td></td><td><input type='hidden' name='id' value='")
                .append(user.getId()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Name:</td><td><input type='text' name='name' value='")
                .append(user.getName()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Quantity:</td><td><input type='text' name='quantity' value='")
                .append(user.getQuantity()).append("'/></td></tr>");
        stringBuilder.append("</td></tr>");
        stringBuilder.append("<tr><td colspan='2'><input type='submit' value='Save'/></td></tr>");
        stringBuilder.append("</table>");
        stringBuilder.append("</form>");
        stringBuilder.append("<a href='").append(RouteHelper.getURL(request, RouteHelper.ORDERS_URL_PATTERN)).append("Orders</a>");
        return stringBuilder.toString();
    }
}