package com.kadaijin.kadaijin.model.DAO;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Entity
@Table(name = "accounts")
public class AccountsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // untuk menghasilkan nilai id
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "register", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp register;

    @OneToMany(mappedBy = "accounts_id")
    @Fetch(FetchMode.JOIN)
    private List<PersonalDataModel> personalDataModels;

    @OneToMany(mappedBy = "accounts")
    @Fetch(FetchMode.JOIN)
    private List<LogModel> logs;

    public AccountsModel(Integer no) {
        this.id = no;
    }

    public AccountsModel() {

    }

}
