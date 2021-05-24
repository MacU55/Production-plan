package org.exampe.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

//5. создай репозиторий к продукту, типо ProductRepo
interface OrderRepo {

    Order find (int orderId);
}

class JDBCOrderRepo implements OrderRepo {

    Order order = new Order();

    public Order find (int orderId) {

        try(Connection connection = DBConnection.connect()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM orders;");

            if (result.getInt("OrderId") == orderId) {

                order.orderId = result.getInt("OrderId");
                order.orderName = result.getString("OrderName");
                order.orderQuantity = result.getInt("orderQuantity");
                order.dateShipmentContract = result.getDate("dateShipmentContract");
                order.dateDesign = result.getDate("dateDesign");
                order.dateTechnology = result.getDate("dateTechnology");
                order.dateSustain = result.getDate("dateSustain");
                order.dateShipment = result.getDate("dateShipment");

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return order;
    }
}