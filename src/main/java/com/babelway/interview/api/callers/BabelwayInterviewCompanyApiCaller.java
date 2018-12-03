package com.babelway.interview.api.callers;

import com.babelway.interview.api.ApiMessage;
import com.babelway.interview.api.ApiResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

/**
 * You do not have to look at this class. This is the implementation of the {@link CompanyApiCaller}.
 *
 * Copyright Babelway 2017.
 */
public class BabelwayInterviewCompanyApiCaller implements CompanyApiCaller {
    private HashMap<String,Set<UUID>> companiesMessageWillFail = new HashMap<String, Set<UUID>>();
    private HashSet<UUID> failedOnlyOne = new HashSet<>();

    public BabelwayInterviewCompanyApiCaller create(String configDirectory){
        return new BabelwayInterviewCompanyApiCaller(configDirectory);
    }

    /**
     * Loads a fake api caller with the expected results provided in the argument xml.
     * @param expectedResultPath path to the expected result xml. This will help the api caller to know how to react
     *                           to calls.
     */
    public BabelwayInterviewCompanyApiCaller(String expectedResultPath) {
        File xml = new File(expectedResultPath);
        if(!xml.exists()){
            throw new RuntimeException("XML expected_result.xml doesn't exist.");
        }
        loadFromXmlExpectedResult(xml);
    }

    private void loadFromXmlExpectedResult(File xml) {

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();

            /* Iterate over companies */
            NodeList companyListNodes = doc.getElementsByTagName("Company");

            for (int temp = 0; temp < companyListNodes.getLength(); temp++) {
                Element companyNode = (Element) companyListNodes.item(temp);
                String companyName = companyNode.getAttributes().getNamedItem("name").getTextContent();

                Set<UUID> failedMessages = new HashSet<>();
                NodeList failedMessagesList = companyNode.getElementsByTagName("FailedMessages").item(0).getChildNodes();
                for(int i=0 ; i< failedMessagesList.getLength() ; i++){
                    if(failedMessagesList.item(i) instanceof Element){
                        failedMessages.add(UUID.fromString(failedMessagesList.item(i).getTextContent()));
                    }
                }
                companiesMessageWillFail.put(companyName,failedMessages);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while parsing expected result");
        }
    }

    /**
     * Calls API of the company specified by this company name.
     * @param company Company Name
     * @param message Api message to send to that company
     * @return ApiResponse OK if everything went well, ApiResponse NOTOK if something is wrong with the message.
     * @throws ApiCallException if something unexpected happens.
     */
    @Override
    public ApiResponse call(String company, ApiMessage message) {
        try {
            Thread.sleep(randomBetween(1,150));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(!supportsCompany(company)){
            throw new ApiCallException("Unsupported company '"+company+"'",null);
        }
        /* Expected failure */
        if(companiesMessageWillFail.get(company).contains(message.getId()) || !message.getCompany().equals(company)){
            return ApiResponse.NOTOK;
        }
        /* Unexpected failure */
        if(randomBetween(1,100) == 50 && !failedOnlyOne.contains(message.getId())){
            failedOnlyOne.add(message.getId());
            throw new ApiCallException("Unexpected error", null);
        }
        failedOnlyOne.remove(message.getId());
        return ApiResponse.OK;
    }
    
    private static Random r = new Random();
    private static int randomBetween(int a, int b){
        return r.nextInt(b-a+1) + a;
    }

    /**
     * check if this API caller supports the specified company.
     * @param company company name
     * @return true if the company is supported, false if the company is not supported
     */
    private Boolean supportsCompany(String company) {
        return companiesMessageWillFail.containsKey(company);
    }
}
