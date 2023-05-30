package com.hubpay.walletApp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String accountNumber;
    private Double currentBalance;
    @OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY,mappedBy = "wallet")
    @Basic(fetch=FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions;

@PrePersist
    public void setCurrentBalance(){
    this.currentBalance= new Double(0);
}

}
