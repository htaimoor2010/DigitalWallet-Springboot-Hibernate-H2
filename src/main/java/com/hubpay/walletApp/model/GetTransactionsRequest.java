package com.hubpay.walletApp.model;

import lombok.Data;

@Data
public class GetTransactionsRequest {
    private Long walletId;
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
}
