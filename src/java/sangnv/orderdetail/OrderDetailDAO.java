/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.orderdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sangnv.utils.DBHelper;

/**
 *
 * @author Shang
 */
public class OrderDetailDAO implements Serializable {

    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;
    private List<OrderDetailDTO> list;

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

    public OrderDetailDAO() {
    }

    public List<OrderDetailDTO> getList() {
        return list;
    }

    public void getOrderDetailsByOrderId(String orderId) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        try {
            String sql = "SELECT Id, ProductId, Quantity, ProductPrice, SubTotal"
                    + " FROM OrderDetail"
                    + " WHERE OrderId = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, orderId);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                OrderDetailDTO dto = new OrderDetailDTO();
                dto.setId(rs.getInt("Id"));
                dto.setProductId(rs.getInt("ProductId"));
                dto.setQuantity(rs.getInt("Quantity"));
                dto.setProductPrice(rs.getBigDecimal("ProductPrice"));
                dto.setSubTotal(rs.getBigDecimal("SubTotal"));
                list.add(dto);
            }
        } finally {
            closeDB();
        }

    }

}
