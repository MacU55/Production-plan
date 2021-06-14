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
        import java.util.List;

@WebServlet(
        name = "designsDeptServlet",
        urlPatterns = RouteHelper.DESIGNSDEPT_URL_PATTERN
)
public class DesignsdeptServlet extends HttpServlet {

    private final IDesignDeptService designDeptService = DesignDeptService.get();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("txt/html");
        response.setCharacterEncoding("utf-8");

        try {
            List<DesignDeptDTO> designDeptDTOS = designDeptService.findDesignDepts();//
            String designDeptGrid = getDesignDeptView(request, designDeptDTOS);
            RouteHelper.forwardToHomePage(request, response, designDeptGrid);
        } catch (ServiceException e) {
            throw new WebAppException(e);
        }

    }

    private static String getDesignDeptView(HttpServletRequest request, List<DesignDeptDTO> designDeptDTOS) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<a href='").append(RouteHelper.getPageURL(request, RouteHelper.CREATE_NEW_DESIGN_PAGE)).append("" +
            "' class=\"btn\">Зробити нове замовлення на креслення</a>");
        stringBuilder.append("<h1>Конструкторський відділ. Замовлення на креслення</h1>");
        stringBuilder.append("<table border='1' width='1000px'");
        stringBuilder.append("<tr><th style='width:30px;'>Номер замовлення на креслення</th><th style='width:130px;'>Номер замовлення на виробництво</th>" +
                "<th style='width:60px;'>Дата виконаного креслення</th><th style='width:130px;'>Позначення креслення</th>" +
                "<th style='width:80px;>Редагування</th></tr>");
        for (DesignDeptDTO designDeptDTO : designDeptDTOS) {
            stringBuilder.append("<tr>")
                    .append("<td>").append(designDeptDTO.getDesignId()).append("</td>")
                    .append("<td>").append(designDeptDTO.getOrderId()).append("</td>")
                    .append("<td>").append(designDeptDTO.getDateDesignCompleted()).append("</td>")
                    .append("<td>").append(designDeptDTO.getDesignName()).append("</td>")

                    .append("<td><a href='")
                    .append(RouteHelper.getURL(request, RouteHelper.DESIGN_URL_PATTERN)).append("?design_id=")
                    .append(designDeptDTO.getDesignId()).append("'>Редагувати</a></td>")
                    .append("</tr>");
        }
        stringBuilder.append("</table>");
        return stringBuilder.toString();

    }

}
