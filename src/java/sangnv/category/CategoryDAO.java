/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.category;

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
public class CategoryDAO implements Serializable {
    
    List<CategoryDTO> list;
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
    
    public CategoryDAO() {
    }
    
    public List<CategoryDTO> getList() {
        return list;
    }
    
    public void getCategories() throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name"
                        + " FROM Category"
                        + " ORDER BY Id";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new CategoryDTO(rs.getInt("Id"), rs.getString("Name")));
                }
            }
        } finally {
            closeDB();
        }
    }
    
}
