package com.hubpay.walletApp.service;

import com.hubpay.walletApp.model.CommonResponse;
import com.hubpay.walletApp.entities.Transaction;
import com.hubpay.walletApp.entities.Wallet;
import com.hubpay.walletApp.repository.TransactionRepository;
import com.hubpay.walletApp.repository.WalletRepository;
import com.hubpay.walletApp.utils.ResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WalletRepository walletRepository;


    public Transaction logTransaction(Transaction transaction){
        transactionRepository.save(transaction);
        return transaction;
    }

    public CommonResponse getAll(Long walletId,Integer pageNumber, Integer pageSize){//we can add sort query params as well
        CommonResponse response= new CommonResponse();
        if(walletId==null){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("WalletId not provided");
            return response;
        }
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if(!wallet.isPresent()){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("No wallet found against given id");
        }else {
            Pageable paging=null;
            if(pageSize!=null && pageNumber!=null){
                 paging = PageRequest.of(pageNumber, pageSize, Sort.by("transDate").descending());
            }
            response.setTransactionList(transactionRepository.findByWallet(wallet.get(),paging));
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setResponseDescription("SUCCESS");
        }
        return response;
    }

}
