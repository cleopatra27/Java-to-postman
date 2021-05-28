package com.javatopostman.controller;

import java.util.List;

public class PostmanConverter {

    public String doPostmanConverter(String projectName, List data){
        String collection_base  = "{\n" +
                "    \"collection\": {\n" +
                "        \"info\": {\n" +
                "            \"name\": \""+projectName+"\",\n" +
                "            \"description\": \"This is just a sample collection.\",\n" +
                "            \"schema\": \"https://schema.getpostman.com/json/collection/v2.1.0/collection.json\"\n" +
                "        },\n" +
                "        \"item\": [\n" +
                "            {\n" +
                "                \"name\": \"This is a folder\",\n" +
                "                \"item\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Sample POST Request\",\n" +
                "                        \"request\": {\n" +
                "                            \"url\": \"https://postman-echo.com/post\",\n" +
                "                            \"method\": \"POST\",\n" +
                "                            \"header\": [\n" +
                "                                {\n" +
                "                                    \"key\": \"Content-Type\",\n" +
                "                                    \"value\": \"application/json\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"body\": {\n" +
                "                                \"mode\": \"raw\",\n" +
                "                                \"raw\": \"{\\\"data\\\": \\\"123\\\"}\"\n" +
                "                            },\n" +
                "                            \"description\": \"This is a sample POST Request\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Sample GET Request\",\n" +
                "                \"request\": {\n" +
                "                    \"url\": \"https://postman-echo/get\",\n" +
                "                    \"method\": \"GET\",\n" +
                "                    \"description\": \"This is a sample GET Request\"\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        return null;
    }
}
