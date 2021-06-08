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
import java.util.List;

@WebServlet(
        name = "ordersServlet",
        urlPatterns = RouteHelper.ORDERS_URL_PATTERN
)
public class OrdersServlet extends HttpServlet {

    private final IOrderService orderService = OrderService.get();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("txt/html");
        response.setCharacterEncoding("utf-8");

        try {
            List<OrderDTO> orders = orderService.findOrders();//
            String ordersGrid = getOrdersView(request, orders);
            RouteHelper.forwardToHomePage(request, response, ordersGrid);
        } catch (ServiceException e) {
            throw new WebAppException(e);
        }

    }

    private static String getOrdersView(HttpServletRequest request, List<OrderDTO> orders) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<a href='").append(RouteHelper.getPageURL(request, RouteHelper.CREATE_NEW_ORDER_PAGE)).append("" +
                "' class=\"btn\">Зробити нове замовлення</a>");
        stringBuilder.append("<h1>Orders List</h1>");
        stringBuilder.append("<table border='1' width='1000px'");
        stringBuilder.append("<tr><th style='width:30px;'>Id</th><th style='width:130px;'>Name</th>" +
                "<th style='width:60px;'>Quantity</th><th style='width:130px;'>Date shipment contract</th>" +
                "<th style='width:130px;'>Date Design</th><th style='width:130px;'>Date Technology</th>" +
                "<th style='width:130px;'>Date Sustain</th>" +
                "<th style='width:130px;'>Date Shipment</th><th>Edit</th></tr>");
        for (OrderDTO order : orders) {
            stringBuilder.append("<tr>")
                    .append("<td>").append(order.getId()).append("</td>")
                    .append("<td>").append(order.getName()).append("</td>") 
                    .append("<td>").append(order.getQuantity()).append("</td>")
                    .append("<td>").append(order.getDateShipmentContract()).append("</td>")
                    .append("<td>").append(order.getDateDesign()).append("</td>")
                    .append("<td>").append(order.getDateTechnology()).append("</td>")
                    .append("<td>").append(order.getDateSustain()).append("</td>")
                    .append("<td>").append(order.getDateShipment()).append("</td>")
                    .append("<td><a href='")
                    .append(RouteHelper.getURL(request, RouteHelper.ORDER_URL_PATTERN)).append("?id=")
                    .append(order.getId()).append("'>Редагувати</a></td>")
                    .append("</tr>");
        }
        stringBuilder.append("</table>");
        return stringBuilder.toString();

    }

}
