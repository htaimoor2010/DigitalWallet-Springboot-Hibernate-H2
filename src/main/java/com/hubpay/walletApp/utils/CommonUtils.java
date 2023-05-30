
package com.hubpay.walletApp.utils;

import com.hubpay.walletApp.model.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtils {

      public static ResponseEntity<?> mapResponse(CommonResponse response){
          if(ResponseCodes.SUCCESS.equals(response.getResponseCode())){
              return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
          }
          return new ResponseEntity<CommonResponse>(response, HttpStatus.BAD_REQUEST);
      }
}
