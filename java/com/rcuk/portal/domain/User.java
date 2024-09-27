package com.rcuk.portal.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class User {

    @Id private String id;

    private String email;
    private String password;
    private String fname;
    private String lname;
    private String phone;
    private String role;

    @CreationTimestamp private Date created;
    @UpdateTimestamp private Date updated;
}
