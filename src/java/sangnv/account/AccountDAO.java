/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.account;

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
public class AccountDAO implements Serializable {

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

    public AccountDTO authenticationAccount(String userId, String password) throws NamingException, SQLException {
        AccountDTO result = null;
        conn = DBHelper.getConnect();
        try {
            String sql = "SELECT Id, Name, Email, Address, Phone, Role"
                    + " FROM Account"
                    + " WHERE UserId LIKE ?"
                    + " AND Password LIKE ?"
                    + " AND IsAvailable = 'True'";
            stm = conn.prepareStatement(sql);
            stm.setString(1, userId);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = new AccountDTO();
                result.setId(rs.getInt("Id"));
                result.setName(rs.getString("Name"));
                result.setEmail(rs.getString("Email"));
                result.setAddress(rs.getString("Address"));
                result.setPhone(rs.getString("Phone"));
                result.setRole(rs.getInt("Role"));
            }
        } finally {
            closeDB();
        }
        return result;
    }

    public String getStringUserById(int id) throws NamingException, SQLException {
        String user = null;
        conn = DBHelper.getConnect();
        try {
            String sql = "SELECT Name, Email"
                    + " FROM Account"
                    + " WHERE Id = ?"
                    + " AND IsAvailable = 'True'";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                user = name + " ("+ email + ")";
            }
        } finally {
            closeDB();
        }
        
        return user;
    }

}
