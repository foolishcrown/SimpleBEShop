/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sangnv.account.AccountDTO;
import sangnv.utils.DBHelper;

/**
 *
 * @author Shang
 */
public class ProductDAO implements Serializable {

    List<ProductDTO> list;
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

    public List<ProductDTO> getList() {
        return list;
    }

    public void getProductsOnName(int start, int total, String search) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name, Image, Description, Price, CreatedDate, ExpirationDate, LastUpdateDate, Quantity"
                        + " FROM Product"
                        + " WHERE Name LIKE N'%" + search + "%'"
                        + " AND Quantity != 0"
                        + " AND ExpirationDate > CAST(CURRENT_TIMESTAMP AS DATE)"
                        + " AND Status = 'True'"
                        + " ORDER BY ExpirationDate"
                        + " OFFSET " + start + " ROWS"
                        + " FETCH NEXT " + total + " ROWS ONLY";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ProductDTO product = new ProductDTO();
                    product.setId(rs.getInt("Id"));
                    product.setName(rs.getString("Name"));
                    product.setImageURL(rs.getString("Image"));
                    product.setDescription(rs.getString("Description"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setCreatedDate(rs.getDate("CreatedDate"));
                    product.setExpirationDate(rs.getDate("ExpirationDate"));
                    product.setLastUpdateDate(rs.getTimestamp("LastUpdateDate"));
                    product.setQuantity(rs.getInt("Quantity"));
                    list.add(product);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getProductsOnPrice(BigDecimal minPrice, BigDecimal maxPrice, int start, int total) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name, Image, Description, Price, CreatedDate, ExpirationDate, LastUpdateDate, Quantity"
                        + " FROM Product"
                        + " WHERE Price BETWEEN " + minPrice + " AND " + maxPrice
                        + " AND Quantity != 0"
                        + " AND ExpirationDate > CAST(CURRENT_TIMESTAMP AS DATE)"
                        + " AND Status = 'True'"
                        + " ORDER BY ExpirationDate"
                        + " OFFSET " + start + " ROWS"
                        + " FETCH NEXT " + total + " ROWS ONLY";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ProductDTO product = new ProductDTO();
                    product.setId(rs.getInt("Id"));
                    product.setName(rs.getString("Name"));
                    product.setImageURL(rs.getString("Image"));
                    product.setDescription(rs.getString("Description"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setCreatedDate(rs.getDate("CreatedDate"));
                    product.setExpirationDate(rs.getDate("ExpirationDate"));
                    product.setLastUpdateDate(rs.getTimestamp("LastUpdateDate"));
                    product.setQuantity(rs.getInt("Quantity"));
                    list.add(product);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getProductsOnCategory(int cateId, int start, int total) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name, Image, Description, Price, CreatedDate, ExpirationDate, LastUpdateDate, Quantity"
                        + " FROM Product"
                        + " WHERE CategoryId = " + cateId
                        + " AND Quantity != 0"
                        + " AND ExpirationDate > CAST(CURRENT_TIMESTAMP AS DATE)"
                        + " AND Status = 'True'"
                        + " ORDER BY ExpirationDate"
                        + " OFFSET " + start + " ROWS"
                        + " FETCH NEXT " + total + " ROWS ONLY";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ProductDTO product = new ProductDTO();
                    product.setId(rs.getInt("Id"));
                    product.setName(rs.getString("Name"));
                    product.setImageURL(rs.getString("Image"));
                    product.setDescription(rs.getString("Description"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setCreatedDate(rs.getDate("CreatedDate"));
                    product.setExpirationDate(rs.getDate("ExpirationDate"));
                    product.setLastUpdateDate(rs.getTimestamp("LastUpdateDate"));
                    product.setQuantity(rs.getInt("Quantity"));
                    list.add(product);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getProductsOnNameByAdmin(int start, int total, int statusId, String search) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        String status = "'True'";
        if (statusId == 2) {
            status = "'False'";
        }
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name, Image, Description, Price, CreatedDate, ExpirationDate, LastUpdateDate, Quantity"
                        + " FROM Product"
                        + " WHERE Name LIKE N'%" + search + "%'"
                        + " AND Status = " + status
                        + " ORDER BY ExpirationDate, Quantity"
                        + " OFFSET " + start + " ROWS"
                        + " FETCH NEXT " + total + " ROWS ONLY";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ProductDTO product = new ProductDTO();
                    product.setId(rs.getInt("Id"));
                    product.setName(rs.getString("Name"));
                    product.setImageURL(rs.getString("Image"));
                    product.setDescription(rs.getString("Description"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setCreatedDate(rs.getDate("CreatedDate"));
                    product.setExpirationDate(rs.getDate("ExpirationDate"));
                    product.setLastUpdateDate(rs.getTimestamp("LastUpdateDate"));
                    product.setQuantity(rs.getInt("Quantity"));
                    list.add(product);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getProductsOnPriceByAdmin(BigDecimal minPrice, BigDecimal maxPrice, int statusId, int start, int total) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        String status = "'True'";
        if (statusId == 2) {
            status = "'False'";
        }
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name, Image, Description, Price, CreatedDate, ExpirationDate, LastUpdateDate, Quantity"
                        + " FROM Product"
                        + " WHERE Price BETWEEN " + minPrice + " AND " + maxPrice
                        + " AND Status = " + status
                        + " ORDER BY ExpirationDate, Quantity"
                        + " OFFSET " + start + " ROWS"
                        + " FETCH NEXT " + total + " ROWS ONLY";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ProductDTO product = new ProductDTO();
                    product.setId(rs.getInt("Id"));
                    product.setName(rs.getString("Name"));
                    product.setImageURL(rs.getString("Image"));
                    product.setDescription(rs.getString("Description"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setCreatedDate(rs.getDate("CreatedDate"));
                    product.setExpirationDate(rs.getDate("ExpirationDate"));
                    product.setLastUpdateDate(rs.getTimestamp("LastUpdateDate"));
                    product.setQuantity(rs.getInt("Quantity"));
                    list.add(product);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getProductsOnCategoryByAdmin(int cateId, int statusId, int start, int total) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        String status = "'True'";
        if (statusId == 2) {
            status = "'False'";
        }
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name, Image, Description, Price, CreatedDate, ExpirationDate, LastUpdateDate, Quantity"
                        + " FROM Product"
                        + " WHERE CategoryId = " + cateId
                        + " AND Status = " + status
                        + " ORDER BY ExpirationDate, Quantity"
                        + " OFFSET " + start + " ROWS"
                        + " FETCH NEXT " + total + " ROWS ONLY";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ProductDTO product = new ProductDTO();
                    product.setId(rs.getInt("Id"));
                    product.setName(rs.getString("Name"));
                    product.setImageURL(rs.getString("Image"));
                    product.setDescription(rs.getString("Description"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setCreatedDate(rs.getDate("CreatedDate"));
                    product.setExpirationDate(rs.getDate("ExpirationDate"));
                    product.setLastUpdateDate(rs.getTimestamp("LastUpdateDate"));
                    product.setQuantity(rs.getInt("Quantity"));
                    list.add(product);
                }
            }
        } finally {
            closeDB();
        }
    }

    public void getProductsOnStatusByAdmin(int statusId, int start, int total) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        String status = "'True'";
        if (statusId == 2) {
            status = "'False'";
        }
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name, Description, Price, CreatedDate, ExpirationDate, LastUpdateDate, Quantity"
                        + " FROM Product"
                        + " WHERE Status = " + status
                        + " ORDER BY ExpirationDate, Quantity"
                        + " OFFSET " + start + " ROWS"
                        + " FETCH NEXT " + total + " ROWS ONLY";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    ProductDTO product = new ProductDTO();
                    product.setId(rs.getInt("Id"));
                    product.setName(rs.getString("Name"));
                    product.setDescription(rs.getString("Description"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setCreatedDate(rs.getDate("CreatedDate"));
                    product.setExpirationDate(rs.getDate("ExpirationDate"));
                    product.setLastUpdateDate(rs.getTimestamp("LastUpdateDate"));
                    product.setQuantity(rs.getInt("Quantity"));
                    list.add(product);
                }
            }
        } finally {
            closeDB();
        }
    }

    public int getTotalProductPageSearchName(String search, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(Id)"
                        + " FROM Product"
                        + " WHERE Name LIKE N'%" + search + "%'"
                        + " AND Quantity != 0"
                        + " AND ExpirationDate > CAST(CURRENT_TIMESTAMP AS DATE)"
                        + " AND Status = 'True'";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalProduct = rs.getInt(1);
                    if (totalProduct % numPerPage == 0) {
                        pages = totalProduct / numPerPage;
                    } else {
                        pages = totalProduct / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public int getTotalProductPageSearchPrice(BigDecimal minPrice, BigDecimal maxPrice, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(Id)"
                        + " FROM Product"
                        + " WHERE Price BETWEEN " + minPrice + " AND " + maxPrice
                        + " AND Quantity != 0"
                        + " AND ExpirationDate > CAST(CURRENT_TIMESTAMP AS DATE)"
                        + " AND Status = 'True'";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalProduct = rs.getInt(1);
                    if (totalProduct % numPerPage == 0) {
                        pages = totalProduct / numPerPage;
                    } else {
                        pages = totalProduct / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public int getTotalProductPageSearchCategory(int cateId, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(Id)"
                        + " FROM Product"
                        + " WHERE CategoryId = " + cateId
                        + " AND Quantity != 0"
                        + " AND ExpirationDate > CAST(CURRENT_TIMESTAMP AS DATE)"
                        + " AND Status = 'True'";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalProduct = rs.getInt(1);
                    if (totalProduct % numPerPage == 0) {
                        pages = totalProduct / numPerPage;
                    } else {
                        pages = totalProduct / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public int getTotalProductPageSearchNameByAdmin(String search, int statusId, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        String status = "'True'";
        if (statusId == 2) {
            status = "'False'";
        }
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(Id)"
                        + " FROM Product"
                        + " WHERE Name LIKE N'%" + search + "%'"
                        + " AND Status = " + status;
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalProduct = rs.getInt(1);
                    if (totalProduct % numPerPage == 0) {
                        pages = totalProduct / numPerPage;
                    } else {
                        pages = totalProduct / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public int getTotalProductPageSearchPriceByAdmin(BigDecimal minPrice, BigDecimal maxPrice, int statusId, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        String status = "'True'";
        if (statusId == 2) {
            status = "'False'";
        }
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(Id)"
                        + " FROM Product"
                        + " WHERE Price BETWEEN " + minPrice + " AND " + maxPrice
                        + " AND Status = " + status;
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalProduct = rs.getInt(1);
                    if (totalProduct % numPerPage == 0) {
                        pages = totalProduct / numPerPage;
                    } else {
                        pages = totalProduct / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public int getTotalProductPageSearchCategoryByAdmin(int cateId, int statusId, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        String status = "'True'";
        if (statusId == 2) {
            status = "'False'";
        }
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(Id)"
                        + " FROM Product"
                        + " WHERE CategoryId = " + cateId
                        + " AND Status = " + status;
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalProduct = rs.getInt(1);
                    if (totalProduct % numPerPage == 0) {
                        pages = totalProduct / numPerPage;
                    } else {
                        pages = totalProduct / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public int getTotalProductPageSearchStatusByAdmin(int statusId, int numPerPage) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        int pages = 0;
        String status = "'True'";
        if (statusId == 2) {
            status = "'False'";
        }
        try {
            if (conn != null) {
                String sql = "SELECT COUNT(Id)"
                        + " FROM Product"
                        + " WHERE Status = " + status
                        + " AND Quantity != 0"
                        + " AND ExpirationDate > CAST(CURRENT_TIMESTAMP AS DATE)";

                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int totalProduct = rs.getInt(1);
                    if (totalProduct % numPerPage == 0) {
                        pages = totalProduct / numPerPage;
                    } else {
                        pages = totalProduct / numPerPage + 1;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return pages;
    }

    public ProductDTO findCartProductById(int productId) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        ProductDTO dto = null;
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name, Quantity, Price"
                        + " FROM Product"
                        + " WHERE Quantity != 0"
                        + " AND ExpirationDate > CAST(CURRENT_TIMESTAMP AS DATE)"
                        + " AND Status = 'True'"
                        + " AND Id = " + productId;
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    dto = new ProductDTO();
                    dto.setId(rs.getInt("Id"));
                    dto.setName(rs.getString("Name"));
                    dto.setQuantity(rs.getInt("Quantity"));
                    dto.setPrice(rs.getBigDecimal("Price"));
                }
            }
        } finally {
            closeDB();
        }
        return dto;
    }

    public ProductDTO findEditProductById(int productId) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        ProductDTO dto = null;
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name, Image, Description, Price, CreatedDate, ExpirationDate, LastUpdateDate, LastUpdateUser, CategoryId, Quantity"
                        + " FROM Product"
                        + " WHERE Quantity != 0"
                        + " AND ExpirationDate > CAST(CURRENT_TIMESTAMP AS DATE)"
                        + " AND Status = 'True'"
                        + " AND Id = " + productId;
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    dto = new ProductDTO();
                    dto.setId(rs.getInt("Id"));
                    dto.setName(rs.getString("Name"));
                    dto.setImageURL(rs.getString("Image"));
                    dto.setDescription(rs.getString("Description"));
                    dto.setQuantity(rs.getInt("Quantity"));
                    dto.setPrice(rs.getBigDecimal("Price"));
                    dto.setCreatedDate(rs.getDate("CreatedDate"));
                    dto.setExpirationDate(rs.getDate("ExpirationDate"));
                    dto.setCateId(rs.getInt("CategoryId"));
                    dto.setLastUpdateDate(rs.getTimestamp("LastUpdateDate"));
                    dto.setLastUpdateUser(rs.getInt("LastUpdateUser"));
                }
            }
        } finally {
            closeDB();
        }
        return dto;
    }

    public boolean checkQuantity(int productId, int quantityCart) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        boolean result = true;
        try {
            if (conn != null) {
                String sql = "SELECT Quantity"
                        + " FROM Product"
                        + " WHERE Quantity != 0"
                        + " AND ExpirationDate > CAST(CURRENT_TIMESTAMP AS DATE)"
                        + " AND Status = 'True'"
                        + " AND Id = " + productId;
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int stock = rs.getInt("Quantity");
                    if (quantityCart > stock) {
                        result = false;
                    }
                } else {
                    result = false;
                }
            }
        } finally {
            closeDB();
        }
        return result;
    }

    public boolean createOrderGuest(String orderId, String accountName, String accountPhone, String accountAddress) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        boolean isCreated = false;
        try {
            String sql = "INSERT INTO [Order] (Id, OrderAt, Status, CustomerPhone, CustomerName, CustomerAddress, PaymentMethod)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            stm = conn.prepareStatement(sql);
            long currentTimeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(currentTimeMillis);
            stm.setString(1, orderId);
            stm.setTimestamp(2, timestamp);
            stm.setInt(3, 1);
            stm.setString(4, accountPhone);
            stm.setNString(5, accountName);
            stm.setNString(6, accountAddress);
            stm.setInt(7, 1);
            int insertCounter = stm.executeUpdate();
            if (insertCounter > 0) {
                isCreated = true;
            }
        } finally {
            closeDB();
        }
        return isCreated;
    }

    public boolean createOrderAccount(String orderId, AccountDTO dto) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        boolean isCreated = false;
        try {
            String sql = "INSERT INTO [Order] (Id, AccountId, OrderAt, Status, CustomerPhone, CustomerName, CustomerAddress, PaymentMethod)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stm = conn.prepareStatement(sql);
            long currentTimeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(currentTimeMillis);
            stm.setString(1, orderId);
            stm.setInt(2, dto.getId());
            stm.setTimestamp(3, timestamp);
            stm.setInt(4, 1);
            stm.setString(5, dto.getPhone());
            stm.setNString(6, dto.getName());
            stm.setNString(7, dto.getAddress());
            stm.setInt(8, 1);

            int insertCounter = stm.executeUpdate();
            if (insertCounter > 0) {
                isCreated = true;
            }
        } finally {
            closeDB();
        }
        return isCreated;
    }

    public boolean updateStock(List<ProductDTO> detailList) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        boolean result = true;
        try {
            if (detailList != null && conn != null) {
                String sql = "UPDATE Product"
                        + " SET Quantity = Quantity - ?"
                        + " WHERE Id = ?";
                stm = conn.prepareStatement(sql);
                for (ProductDTO productDTO : detailList) {
                    stm.setInt(1, productDTO.getQuantityCart());
                    stm.setInt(2, productDTO.getId());
                    stm.addBatch();
                }
                int[] updated = stm.executeBatch();
                for (int i : updated) {
                    if (i == PreparedStatement.EXECUTE_FAILED) {
                        result = false;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return result;
    }

    public boolean createDetail(List<ProductDTO> detailList, String orderId) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        boolean result = true;
        try {
            if (detailList != null && conn != null) {
                String sql = "INSERT INTO [OrderDetail] (OrderId, ProductId, Quantity, ProductPrice, SubTotal)"
                        + " VALUES (?, ?, ?, ?, ?)";
                stm = conn.prepareStatement(sql);

                for (ProductDTO productDTO : detailList) {
                    stm.setString(1, orderId);
                    stm.setInt(2, productDTO.getId());
                    stm.setInt(3, productDTO.getQuantityCart());
                    stm.setBigDecimal(4, productDTO.getPrice());
                    stm.setBigDecimal(5, productDTO.getSubtotalPrice());
                    stm.addBatch();
                }
                int[] inserted = stm.executeBatch();
                for (int i : inserted) {
                    if (i == PreparedStatement.EXECUTE_FAILED) {
                        result = false;
                    }
                }
            }
        } finally {
            closeDB();
        }
        return result;
    }

    public boolean createProduct(ProductDTO dto) throws NamingException, SQLException {
        boolean result = true;
        conn = DBHelper.getConnect();
        try {
            if (dto != null) {
                String sql = "INSERT INTO [Product] (Name, Image, Description, Price, CreatedDate, ExpirationDate, CategoryId, LastUpdateDate, LastUpdateUser, Status, Quantity)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stm = conn.prepareStatement(sql);
                long currentTimeMillis = System.currentTimeMillis();
                Timestamp timestamp = new Timestamp(currentTimeMillis);
                stm.setString(1, dto.getName());
                stm.setString(2, dto.getImageURL());
                stm.setString(3, dto.getDescription());
                stm.setBigDecimal(4, dto.getPrice());
                stm.setDate(5, dto.getCreatedDate());
                stm.setDate(6, dto.getExpirationDate());
                stm.setInt(7, dto.getCateId());
                stm.setTimestamp(8, timestamp);
                stm.setInt(9, dto.getLastUpdateUser());
                stm.setBoolean(10, true);
                stm.setInt(11, dto.getQuantity());

                int insertCounter = stm.executeUpdate();
                if (insertCounter > 0) {
                    result = true;
                }
            } else {
                result = false;
            }
        } finally {
            closeDB();
        }
        return result;
    }

    public boolean disableProduct(int productId) throws NamingException, SQLException {
        boolean result = false;
        conn = DBHelper.getConnect();
        try {
            String sql = "UPDATE Product"
                    + " SET Status = 'False'"
                    + " WHERE Id = " + productId;
            stm = conn.prepareStatement(sql);
            int updated = stm.executeUpdate();
            if (updated > 0) {
                result = true;
            }
        } finally {
            closeDB();
        }
        return result;
    }

    public boolean updateProduct(ProductDTO dto) throws NamingException, SQLException {
        boolean result = false;
        conn = DBHelper.getConnect();
        try {
            String sql1 = "UPDATE Product";
            long currentTimeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(currentTimeMillis);
            String sql2 = " SET Name = ?,"
                    + " Description = ?,"
                    + " Price = ?,"
                    + " CreatedDate = ?,"
                    + " ExpirationDate = ?,"
                    + " CategoryId = ?,"
                    + " LastUpdateDate = ?,"
                    + " LastUpdateUser = ?,"
                    + " Quantity = ?";
            if (dto.getImageURL() != null) {
                sql2 += ", Image = ?";
            }
            String sql = sql1 + sql2
                    + " WHERE Id = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getName());
            stm.setString(2, dto.getDescription());
            stm.setBigDecimal(3, dto.getPrice());
            stm.setDate(4, dto.getCreatedDate());
            stm.setDate(5, dto.getExpirationDate());
            stm.setInt(6, dto.getCateId());
            stm.setTimestamp(7, timestamp);
            stm.setInt(8, dto.getLastUpdateUser());
            stm.setInt(9, dto.getQuantity());
            if (dto.getImageURL() != null) {
                stm.setString(10, dto.getImageURL());
                stm.setInt(11, dto.getId());
            } else {
                stm.setInt(10, dto.getId());
            }

            int updated = stm.executeUpdate();
            if (updated > 0) {
                result = true;
            }
        } finally {
            closeDB();
        }
        return result;
    }
    
   public ProductDTO findOrderProductById(int productId) throws NamingException, SQLException {
        conn = DBHelper.getConnect();
        ProductDTO dto = null;
        try {
            if (conn != null) {
                String sql = "SELECT Id, Name, Image"
                        + " FROM Product"
                        + " WHERE Id = " + productId;
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    dto = new ProductDTO();
                    dto.setId(rs.getInt("Id"));
                    dto.setName(rs.getString("Name"));
                    dto.setImageURL(rs.getString("Image"));
                }
            }
        } finally {
            closeDB();
        }
        return dto;
    }

}
