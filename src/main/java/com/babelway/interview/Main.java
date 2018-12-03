package com.babelway.interview;

import com.babelway.interview.api.ApiMessage;
import com.babelway.interview.api.callers.BabelwayInterviewCompanyApiCaller;
import com.babelway.interview.api.callers.CompanyApiCaller;
import com.babelway.interview.api.provider.CompnayMessageReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.util.List;

/**
 * Copyright Toktoo 2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        // I disable this as I Pass message from the code
        //        if(args.length != 1){
//            System.out.println("You should provide a working directory where your messages.txt and expected_results.xml are presents. ");
//            return;
//        }
//        String workingDirectory = args[0];

        // iterate messages text file
        CompnayMessageReader compnayMessageReader = new CompnayMessageReader();
        //load message and convert each line related to messagage format json, xml, csv
        List<ApiMessage> allMessages =  compnayMessageReader.readMessageFile("...\\babelway\\messageAlanyzer\\src\\main\\resources\\messages.txt");

        // l
        // TODO to convert list of the message to xml file
       // CompanyApiCaller apiCaller = new BabelwayInterviewCompanyApiCaller(workingDirectory+"expected_result.xml");
        
        /**
         * Your working directory (passed as argument of this main application) contains two files you should care about :
         *
         *  -> messages.txt <-
         *
         *  1) This file contains 1 message per line.
         *  2) Each message is either written in XML, in JSON or CSV format.
         *  3) Number of lines is between 1 and 100.000.000
         *
         *  -> expected_result.xml <-
         *
         *  1) This file is used by our API caller to know how it should react to your calls.
         *  2) This is the exact same file you need to create at the end of the process (Just don't copy paste it :p)
         *      This should help you see if you are doing good or not.
         *
         *  What we expect the code to :
         *
         *  1) Read the messages from the messages.txt file
         *  2) Extract the information from those messages (id,companyName,content) regardless of the format they are in.
         *  3) Send the messages via the apiCaller provided above with the following rules :
         *      - messages should be sent to the appropriate company via : apiCaller.call(String companyName , ApiMessage message);
         *      - if the call to the API fails (technical or business failure), you should :
         *          a) retry the transfer of that message after at least 10 seconds.
         *          b) after 3 retries, you should stop trying and save that message as failed message.
         *      - The api calls are not instantaneous and take some time. You might want to use threads to do thing in parallel.
         *  4) When every message is processed by your application, you should create a file in the working directory called :
         *
         *          -> result.xml <-
         *
         *     containing the following information :
         *
         *          <?xml version="1.0" encoding="UTF-8" standalone="no" ?>
         *          <Result>
         *              <Companies>
         *                  <Company name="{companyname}">
         *                      <Success>{number}</Success>
         *                      <Failure>{number}</Failure>
         *                      <FailedMessages>
         *                          <Message>{UUID of message}</Message>
         *                          ...
         *                      </FailedMessages>
         *                  </Company>
         *                  ...
         *              </Companies>
         *          </Result>
         *
         *  5) We would like you to start the exercise by parsing only one message format of your choice (XML, CSV or JSON).
         *  Once you have completed the exercise with one message format, meaning that you have created the file result.xml,
         *  you can finish it by handling the two other formats.
         *
         *
         *  What we expect from you from a coding perspective:
         *
         *  1) Clean code and modularity.
         *  2) Performance and memory usage (Don't forget that your input file might contain millions of messages).
         *  3) Have fun :)
         *
         * */

    }
}
