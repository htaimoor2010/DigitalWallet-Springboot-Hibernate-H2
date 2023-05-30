package com.hubpay.walletApp.controller;

import com.hubpay.walletApp.model.CommonResponse;
import com.hubpay.walletApp.model.CreditFundsRequest;
import com.hubpay.walletApp.model.DebitFundsRequest;
import com.hubpay.walletApp.entities.Wallet;
import com.hubpay.walletApp.service.ValidationErrorService;
import com.hubpay.walletApp.service.WalletService;
import com.hubpay.walletApp.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    ValidationErrorService validationService;

    @PostMapping
    @RequestMapping("/createWallet")
    public ResponseEntity<?> create(@RequestBody Wallet wallet, BindingResult result){

        ResponseEntity errors= validationService.validate(result);
        if(errors!=null){ return errors; }
        Wallet walletSaved= walletService.createWallet(wallet);
        return new ResponseEntity<Wallet>(walletSaved, HttpStatus.CREATED);
    }

    @GetMapping
    @RequestMapping("/getAllWallets")
    public ResponseEntity<?> getAllWallets(){
        return new ResponseEntity<>(walletService.getAll(), HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping("/creditFunds")
    public ResponseEntity<?> creditFunds(@RequestBody CreditFundsRequest request, BindingResult result){
        ResponseEntity errors= validationService.validate(result);
        if(errors!=null){ return errors;}
        CommonResponse response=walletService.creditFunds(request);
        return CommonUtils.mapResponse(response);
    }

    @PutMapping
    @RequestMapping("/debitFunds")
    public ResponseEntity<?> debitFunds(@RequestBody DebitFundsRequest request, BindingResult result){
        ResponseEntity errors= validationService.validate(result);
        if(errors!=null){ return errors;}
        CommonResponse response=walletService.debitFunds(request);
        return CommonUtils.mapResponse(response);
    }

}
