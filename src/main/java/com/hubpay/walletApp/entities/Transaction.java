package com.hubpay.walletApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transId;
    private String serviceId;
    private Date transDate;
    private Double transAmount;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="wallet_id", nullable = false)
    @JsonIgnore
    private Wallet wallet;

    @PrePersist
    public void setTransDate(){
        this.transDate=new Date();
    }
}
