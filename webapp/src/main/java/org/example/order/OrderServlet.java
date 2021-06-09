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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet(
        name = "orderServlet",
        urlPatterns = RouteHelper.ORDER_URL_PATTERN
)
public class OrderServlet extends HttpServlet {

    private final IOrderService orderService = OrderService.get();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

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
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
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
        OrderDTO order = new OrderDTO();

        if (sid != null) {
            order.setId(Integer.parseInt(sid));
        }
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        String stringDateShipmentContract = request.getParameter("date_shipment_contract");
        String stringDateDesign = request.getParameter("date_design");
        String stringDateTechnology = request.getParameter("date_technology");
        String stringDateSustain = request.getParameter("date_sustain");
        String stringDateShipment = request.getParameter("date_shipment");

        SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd");

        try{
           Date dateShipmentContract = myformat.parse(stringDateShipmentContract);
           Date DateDesign = myformat.parse(stringDateDesign);
           Date DateTechnology = myformat.parse(stringDateTechnology);
           Date DateSustain = myformat.parse(stringDateSustain);
           Date DateShipment = myformat.parse(stringDateShipment);

           order.setDateShipmentContract(dateShipmentContract);
           order.setDateDesign(DateDesign);
           order.setDateTechnology(DateTechnology);
           order.setDateSustain(DateSustain);
           order.setDateShipment(DateShipment);
        }
        catch (ParseException ex){
            ex.printStackTrace();
        }

        order.setName(name);
        order.setQuantity(quantity);
        return order;

    }

    private String getOrderView(HttpServletRequest request, OrderDTO orderDTO) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h1>Редагування замовлення</h1>");
        stringBuilder.append("<form action='").append(RouteHelper.getURL(request, RouteHelper.ORDER_URL_PATTERN))
                .append("' method='post'>");
        stringBuilder.append("<table>");
        stringBuilder.append("<tr><td>Номер замовлення</td><td><input readonly type='text' name='id' value='")
                .append(orderDTO.getId()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Назва замовлення</td><td><input type='text' name='name' value='")
                .append(orderDTO.getName()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Кількість</td><td><input type='text' name='quantity' value='")
                .append(orderDTO.getQuantity()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Термін відвантаження згідно контракту</td><td><input type='text' name='date_shipment_contract' value='")
                .append(orderDTO.getDateShipmentContract()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Термін виконання креслень</td><td><input type='text' name='date_design' value='")
                .append(orderDTO.getDateDesign()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Термін виконання технології</td><td><input type='text' name='date_technology' value='")
                .append(orderDTO.getDateTechnology()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Термін виконання підготовки</td><td><input type='text' name='date_sustain' value='")
                .append(orderDTO.getDateSustain()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Термін відвантаження</td><td><input type='text' name='date_shipment' value='")
                .append(orderDTO.getDateShipment()).append("'/></td></tr>");

        stringBuilder.append("</td></tr>");
        stringBuilder.append("<tr><td colspan='2'><input type='submit' value='Save'/></td></tr>");
        stringBuilder.append("</table>");
        stringBuilder.append("</form>");
        stringBuilder.append("<a href='").append(RouteHelper.getURL(request, RouteHelper.ORDERS_URL_PATTERN)).append("Замовлення</a>");
        return stringBuilder.toString();

    }
}