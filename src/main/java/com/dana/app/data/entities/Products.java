package com.dana.app.data.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="ProductTable")
public class Products {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    @NotBlank
    String Name;
    @Column
    @NotBlank
    String type;
    @Column
    Long UserID;




    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }


    public void setType(String type){
        this.type = type;
    }


    public String getType(){ return type; }

    public Long getUserID(){
        return UserID; }

    public void setUSerID(Long UserID){
        this.UserID = UserID;
    }

    public Long getID() {
        return id;
    }
}




}
