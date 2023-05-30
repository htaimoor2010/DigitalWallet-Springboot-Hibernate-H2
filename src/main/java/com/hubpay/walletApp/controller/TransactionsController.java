package com.hubpay.walletApp.controller;

import com.hubpay.walletApp.model.CommonResponse;
import com.hubpay.walletApp.model.GetTransactionsRequest;
import com.hubpay.walletApp.service.TransactionService;
import com.hubpay.walletApp.service.ValidationErrorService;
import com.hubpay.walletApp.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    ValidationErrorService validationService;

    @GetMapping
    public ResponseEntity<?> getTransactions(@RequestParam(value="walletId")Long wallet_id,
    @RequestParam(value="pageNumber",required = false) Integer pageNumber,
    @RequestParam(value="pageSize",required = false) Integer pageSize){

        return new ResponseEntity<>(transactionService.getAll(wallet_id,pageNumber,pageSize), HttpStatus.OK);
    }

    @PostMapping
    @RequestMapping("/getTransactions")
    public ResponseEntity<?> getTransactions(@RequestBody GetTransactionsRequest request, BindingResult result){
        ResponseEntity errors= validationService.validate(result);
        if(errors!=null){ return errors;}
        CommonResponse response=transactionService.getAll(request.getWalletId(),request.getPageNumber(), request.getPageSize());
        return CommonUtils.mapResponse(response);
    }
}
