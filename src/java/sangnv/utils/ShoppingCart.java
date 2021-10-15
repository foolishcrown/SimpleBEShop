/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import javax.naming.NamingException;
import sangnv.product.ProductDTO;

/**
 *
 * @author Shang
 */
public class ShoppingCart extends HashMap<String, ProductDTO> implements Serializable{
    
    BigDecimal total;
    
    public ShoppingCart() {
        super();
    }
    
    public String getTotal() {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(total);
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public void addCart(ProductDTO dto) throws NamingException, SQLException {
        String key = String.valueOf(dto.getId());
        if (this.containsKey(key)) {
            int oldQuantity = this.get(key).getQuantityCart();
            this.get(key).setQuantityCart(oldQuantity + 1);
        } else {
            dto.setQuantityCart(1);
            this.put(key, dto);
        }
        solveTotal();
    }
    
    public boolean removeCart(int id) {
        String key = String.valueOf(id);
        if (this.containsKey(key)) {
            this.remove(key);
            solveTotal();
            return true;
        }
        solveTotal();
        return false;
    }
    
    public boolean updateCart(int id, int quantity) {
        String key = String.valueOf(id);
        if (this.containsKey(key)) {
            this.get(key).setQuantityCart(quantity);
            solveTotal();
            return true;
        }
        solveTotal();
        return false;
    }
    
    public void solveTotal() {
        this.total = new BigDecimal(0);
        this.forEach((k, v)
                -> this.total = this.total.add(v.getPrice().multiply(new BigDecimal(v.getQuantityCart()))));
    }
    
    
    
}
