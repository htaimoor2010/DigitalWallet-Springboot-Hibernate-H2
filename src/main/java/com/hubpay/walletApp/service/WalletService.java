package com.hubpay.walletApp.service;

import com.hubpay.walletApp.entities.Transaction;
import com.hubpay.walletApp.entities.Wallet;
import com.hubpay.walletApp.model.CommonResponse;
import com.hubpay.walletApp.model.CreditFundsRequest;
import com.hubpay.walletApp.model.DebitFundsRequest;
import com.hubpay.walletApp.repository.WalletRepository;
import com.hubpay.walletApp.utils.ResponseCodes;
import com.hubpay.walletApp.utils.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionService logTransactionService;

    public Wallet createWallet(Wallet wallet){
        walletRepository.save(wallet);
        return wallet;
    }

    public List<Wallet> getAll(){
        return walletRepository.findAll();
    }

    public CommonResponse creditFunds(CreditFundsRequest request){
        CommonResponse response= new CommonResponse();
        response.setResponseCode(ResponseCodes.SUCCESS);
        response.setResponseDescription("SUCCESS");

        if(null==request.getAmount() || request.getWalletId()==null){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("Amount/walletId missing in request");
            return response;
        }

        if(Double.compare(request.getAmount(),10)<0){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("Amount less than 10 cannot be credited");
            return response;
        }

        if(Double.compare(request.getAmount(),10000)>0){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("Amount greater than 10000 cannot be credited");
            return response;
        }
        Optional<Wallet> wallet = walletRepository.findById(request.getWalletId());
        if(!wallet.isPresent()){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("Wallet not found with given ID");
            return response;
        }
        Double updatedBalance=wallet.get().getCurrentBalance()+request.getAmount();
        wallet.get().setCurrentBalance(updatedBalance);
        walletRepository.save(wallet.get());
        response.setCurrentBalance(wallet.get().getCurrentBalance());
        Transaction transaction = new Transaction();
        transaction.setServiceId(Services.CREDIT_FUNDS);
        transaction.setTransAmount(request.getAmount());
        transaction.setWallet(wallet.get());
        logTransactionService.logTransaction(transaction);
        response.setTransId(transaction.getTransId());
        return response;
    }

    public CommonResponse debitFunds(DebitFundsRequest request){

        CommonResponse response= new CommonResponse();
        response.setResponseCode(ResponseCodes.SUCCESS);
        response.setResponseDescription("SUCCESS");

        if(null==request.getAmount() || request.getWalletId()==null){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("Amount/walletId missing in request");
            return response;
        }

        if(Double.compare(request.getAmount(),0)<=0){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("Invalid amount provided");
            return response;
        }

        if(Double.compare(request.getAmount(),5000)>0){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("Amount greater than 5000 cannot be withdrawn.");
            return response;
        }

        Optional<Wallet> wallet = walletRepository.findById(request.getWalletId());
        if(!wallet.isPresent()){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("Wallet not found with given ID");
            return response;        }

        if(Double.compare(request.getAmount(),wallet.get().getCurrentBalance())>0){
            response.setResponseCode(ResponseCodes.INVALID_TRANS);
            response.setResponseDescription("Insufficient Funds");
            return response;
        }

        Double updatedBalance=wallet.get().getCurrentBalance()-request.getAmount();
        wallet.get().setCurrentBalance(updatedBalance);
        walletRepository.save(wallet.get());
        response.setCurrentBalance(wallet.get().getCurrentBalance());
        Transaction transaction = new Transaction();
        transaction.setServiceId(Services.DEBIT_FUNDS);
        transaction.setTransAmount(request.getAmount());
        transaction.setWallet(wallet.get());
        logTransactionService.logTransaction(transaction);
        response.setTransId(transaction.getTransId());
        return response;
    }
}
