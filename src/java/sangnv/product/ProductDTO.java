/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Shang
 */
public class ProductDTO implements Serializable {

    private int id,
            cateId,
            lastUpdateUser,
            quantity,
            quantityCart;
    private String name,
            imageURL,
            description;
    private BigDecimal price;
    private Date createdDate,
            expirationDate;
    private Timestamp lastUpdateDate;
    private boolean status;

    public ProductDTO() {
    }

    public ProductDTO(int id, int cateId, int lastUpdateUser, int quantity, String name, String imageURL, String description, BigDecimal price, Date createdDate, Date expirationDate, Timestamp lastUpdateDate, boolean status) {
        this.id = id;
        this.cateId = cateId;
        this.lastUpdateUser = lastUpdateUser;
        this.quantity = quantity;
        this.name = name;
        this.imageURL = imageURL;
        this.description = description;
        this.price = price;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public int getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(int lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPriceCurrency() {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(price);
    }

    public BigDecimal getSubtotalPrice() {
        return price.multiply(new BigDecimal(quantityCart));
    }

    public String getSubtotalPriceCurrency() {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(price.multiply(new BigDecimal(quantityCart)));
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
