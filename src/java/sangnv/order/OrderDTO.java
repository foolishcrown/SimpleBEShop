/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.order;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Shang
 */
public class OrderDTO implements Serializable{
    private int accountId,
            status,
            paymentMethod;
    private Timestamp orderAt,
            doneAt;
    String id,
            customerPhone,
            customerName,
            customerAddress;
    

    public OrderDTO() {
    }

    public OrderDTO(String id, int accountId, int status, int paymentMethod, Timestamp orderAt, Timestamp doneAt, String customerPhone, String customerName, String customerAddress) {
        this.id = id;
        this.accountId = accountId;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.orderAt = orderAt;
        this.doneAt = doneAt;
        this.customerPhone = customerPhone;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Timestamp getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(Timestamp orderAt) {
        this.orderAt = orderAt;
    }

    public Timestamp getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(Timestamp doneAt) {
        this.doneAt = doneAt;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
        
    
    
            
}
