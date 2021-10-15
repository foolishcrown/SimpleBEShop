/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.order;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import sangnv.utils.DBHelper;

/**
 *
 * @author Shang
 */
public class OrderDAO implements Serializable {

    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;

    public void closeDB() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public OrderDAO() {
    }
    
    

    public OrderDTO getOrderByOrderIdOnAccount(String orderId, int accountId) throws NamingException, SQLException {
        OrderDTO dto = null;
        conn = DBHelper.getConnect();
        try {
            String sql = "SELECT Id, OrderAt, DoneAt, Status, CustomerPhone, CustomerName, CustomerAddress, PaymentMethod"
                    + " FROM [Order]"
                    + " WHERE Id = ?"
                    + " AND AccountId = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, orderId);
            stm.setInt(2, accountId);
            rs = stm.executeQuery();
            if (rs.next()) {
                dto = new OrderDTO();
                dto.setId(rs.getString("Id"));
                dto.setOrderAt(rs.getTimestamp("OrderAt"));
                dto.setDoneAt(rs.getTimestamp("DoneAt"));
                dto.setStatus(rs.getInt("Status"));
                dto.setCustomerAddress(rs.getString("CustomerAddress"));
                dto.setCustomerName(rs.getString("CustomerName"));
                dto.setCustomerPhone(rs.getString("CustomerPhone"));
                dto.setPaymentMethod(rs.getInt("PaymentMethod"));
            }
        } finally {
            closeDB();
        }
        return dto;
    }

}
