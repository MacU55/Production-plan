package org.example.designDept;

import org.example.dto.DesignDeptDTO;
import org.example.exception.ServiceException;
import org.example.exception.WebAppException;
import org.example.service.DesignDeptService;
import org.example.service.IDesignDeptService;
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
        name = "designDeptServlet",
        urlPatterns = RouteHelper.DESIGN_URL_PATTERN
)
public class DesignDeptServlet extends HttpServlet {

    private final IDesignDeptService designDeptService = DesignDeptService.get();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        try {
            int id = Integer.parseInt(request.getParameter("design_id"));
            DesignDeptDTO designDeptDTO = designDeptService.findByDesignId(id);
            String designDeptView = this.getDesignDeptView(request, designDeptDTO);
            RouteHelper.forwardToHomePage(request, response, designDeptView);
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
            DesignDeptDTO designDeptDTO = this.getDesignDeptDTO(request);
            if (designDeptDTO.getDesignId() == 0) {
                designDeptService.save(designDeptDTO);
            }
            else {
                designDeptService.update(designDeptDTO);
            }
            RouteHelper.redirectTo(request, response, RouteHelper.getURL(request, RouteHelper.DESIGNSDEPT_URL_PATTERN));
        } catch (ServiceException e) {
            throw new WebAppException(e);
        }
    }

    private DesignDeptDTO getDesignDeptDTO(HttpServletRequest request) {
        String sid = request.getParameter("design_id");
        DesignDeptDTO designDeptDTO = new DesignDeptDTO();

        if (sid != null) {
            designDeptDTO.setDesignId(Integer.parseInt(sid));
        }

        String drawingName = request.getParameter("drawing_name");
        int orderId = Integer.parseInt(request.getParameter("order_id"));
        String stringDateDesignCompleted = request.getParameter("date_design_completed");
        SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd");

        try{
            Date dateDesignCompleted = myformat.parse(stringDateDesignCompleted);
            designDeptDTO.setDateDesignCompleted(dateDesignCompleted);

        }
        catch (ParseException ex){
            ex.printStackTrace();
        }

        designDeptDTO.setDesignName(drawingName);
        designDeptDTO.setOrderId(orderId);
        return designDeptDTO;

    }

    private String getDesignDeptView(HttpServletRequest request, DesignDeptDTO designDeptDTO) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h1>Редагування замовлення на креслення</h1>");
        stringBuilder.append("<form action='").append(RouteHelper.getURL(request, RouteHelper.DESIGN_URL_PATTERN))
                .append("' method='post'>");
        stringBuilder.append("<table>");
        stringBuilder.append("<tr><td>Номер замовлення на креслення</td><td><input readonly type='text' name='design_id' value='")
                .append(designDeptDTO.getDesignId()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Номер замовлення на виробництво</td><td><input type='text' name='order_id' value='")
                .append(designDeptDTO.getOrderId()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Дата виконаного креслення</td><td><input type='text' name='date_design_completed' value='")
                .append(designDeptDTO.getDateDesignCompleted()).append("'/></td></tr>");
        stringBuilder.append("<tr><td>Позначення креслення</td><td><input type='text' name='drawing_name' value='")
                .append(designDeptDTO.getDesignName()).append("'/></td></tr>");


        stringBuilder.append("</td></tr>");
        stringBuilder.append("<tr><td colspan='2'><input type='submit' value='Save'/></td></tr>");
        stringBuilder.append("</table>");
        stringBuilder.append("</form>");
        stringBuilder.append("<a href='").append(RouteHelper.getURL(request, RouteHelper.DESIGNSDEPT_URL_PATTERN)).append("Конструкторський відділ</a>");

        return stringBuilder.toString();

    }
}
