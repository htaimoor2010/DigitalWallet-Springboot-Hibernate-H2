package com.hubpay.walletApp.model;

import com.hubpay.walletApp.entities.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class CommonResponse {
    String responseDescription;
    String responseCode;
    Double currentBalance;
    List<Transaction> transactionList;
    Long transId;
}
