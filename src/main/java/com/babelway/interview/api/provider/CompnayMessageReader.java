package com.babelway.interview.api.provider;

import com.babelway.interview.api.ApiMessage;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.xml.XmlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompnayMessageReader {

    List<ApiMessage> apiMessageList = new ArrayList<>();



    public Map<String, List<ApiMessage>> readMessageFile(String path) throws IOException {

        try (Stream<String> stream = Files.lines(Paths.get(path))) {


            stream.forEach(item -> {

                // read json message
                if (item.contains("{")) {
                    try {
                        this.readJsonMessage(item.trim());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // read xml message
                } else if (item.contains("<")) {
                    try {
                        this.readXmlMessage(item);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // read csv
                else {
                    try {
                        this.readCsvMessage(item);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, List<ApiMessage>> messageByCompany =
                apiMessageList.stream().collect(Collectors.groupingBy(x -> x.getCompany()));

        return messageByCompany;
    }

    private ApiMessage readXmlMessage(String xmlMessaage) throws IOException {

        XmlMapper xmlMapper = new XmlMapper();
        ApiMessage apiMessage = xmlMapper.
                readValue(xmlMessaage,
                        ApiMessage.class);
        //update the list of message
        apiMessageList.add(apiMessage);
        return apiMessage;

    }

    private ApiMessage readJsonMessage(String jsonMessaage) throws IOException {


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        ApiMessage apiMessage = mapper.readValue(jsonMessaage.replace("'", "\""), ApiMessage.class);

        //update the list of message
        apiMessageList.add(apiMessage);
        return apiMessage;
    }

    private ApiMessage readCsvMessage(String csvMessage) throws IOException {


        String[] messages = csvMessage.split(",");

        String id = messages[0];
        String company = messages[1];
        String content = messages[2];
        //update the list of message
        ApiMessage apiMessage = new ApiMessage(UUID.fromString(id), company, content);
        apiMessageList.add(apiMessage);
        return apiMessage;
    }
}