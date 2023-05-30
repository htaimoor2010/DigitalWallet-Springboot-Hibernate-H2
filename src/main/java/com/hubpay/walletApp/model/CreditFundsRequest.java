package com.hubpay.walletApp.model;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreditFundsRequest {

    Long walletId;
    @NotNull
    Double amount;
    Boolean isExternalFundRequest=false;
    String gatewayId;
}
