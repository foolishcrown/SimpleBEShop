/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.account;

import java.io.Serializable;

/**
 *
 * @author Shang
 */
public class AccountDTO implements Serializable {

    private int id, role;
    private String usetId,
            password,
            name,
            email,
            address,
            phone,
            token;

    public AccountDTO() {
    }

    public AccountDTO(int id, int role, String usetId, String password, String name, String email, String address, String phone, String token) {
        this.id = id;
        this.role = role;
        this.usetId = usetId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUsetId() {
        return usetId;
    }

    public void setUsetId(String usetId) {
        this.usetId = usetId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
