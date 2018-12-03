package com.babelway.interview.api.callers;

import com.babelway.interview.api.ApiMessage;
import com.babelway.interview.api.ApiResponse;

/**
 * Copyright Babelway 2017.
 */
public interface CompanyApiCaller {
     /**
      * Calls API of the company specified by this company name.
      * @param company Company Name
      * @param message Api message to send to that company
      * @return ApiResponse OK if everything went well, ApiResponse NOTOK if something is wrong with the message.
      * @throws ApiCallException if something unexpected happens.
      */
     ApiResponse call(String company, ApiMessage message);

}
