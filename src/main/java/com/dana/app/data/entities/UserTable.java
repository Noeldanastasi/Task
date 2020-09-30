package com.dana.app.data.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
@Entity
@Table(name="UserTable")
public class UserTable {

        @Id
        @Column
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long Userid;
        @Column
        @NotBlank
        String UserName;





        public void setUserID(Long Userid) {
            this.Userid = Userid;
        }

        public String getUserName() {
            return UserName;
        }
        public void setUserName(String UserName) {
            this.UserName = UserName;
        }


    public Long getUserID() {
            return Userid;
    }
}




}

}
