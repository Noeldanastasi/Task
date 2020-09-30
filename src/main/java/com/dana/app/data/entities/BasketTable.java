package com.dana.app.data.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
@Entity
@Table(name="BasketTable")

public class BasketTable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Userid;
    @Column
    @NotBlank
    String ProductName;
    @Column
    @NotBlank
    Long UserID;


    public Long getbasketId() {
        return Userid;
    }

    public void setbasketid(Long Userid) {
        this.Userid = Userid;
    }

    public String getProductName() {
        return ProductName;
    }
    public void setProductName(String ProdctName) {
        this.ProductName = ProductName;
    }
    public Long getUserID() {
        return UserID;
    }
    public void setUserID(Long UserID) {
        this.UserID = UserID;
    }


}
