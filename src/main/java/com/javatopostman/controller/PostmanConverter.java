package com.javatopostman.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.List;

public class PostmanConverter {

    public String doPostmanConverter(String projectName, List data, String description) throws JsonProcessingException {
       // System.out.println(data);
        if(description == null || description == ""){
            description = "Generated description";
        }
        String collection_base  = "{\n" +
                "    \"collection\": {\n" +
                "        \"info\": {\n" +
                "            \"name\": \""+projectName+"\",\n" +
                "            \"description\": \""+description+"\",\n" +
                "            \"schema\": \"https://schema.getpostman.com/json/collection/v2.1.0/collection.json\"\n" +
                "        },\n" +
                "        \"item\": [\n" +
                "            {\n";
                for(int i = 0; i < data.size(); i++) {
                    JSONObject gs = (JSONObject) data.get(i);
                    collection_base = collection_base+"                \"name\": \""+gs.optString("className")+"\",\n" +
                            "                        \"request\": {\n" +
                            "                            \"url\": \"{base_url}"+gs.optString("path")+"\",\n" +
                            "                            \"method\": \""+gs.optString("verb")+"\",\n" +
                            "                            \"header\": [\n" +
                            "                                {\n" +
                            "                                    \"key\": \"Content-Type\",\n" +
                            "                                    \"value\": \"application/json\"\n" +
                            "                                }\n" +
                            "                            ],\n" ;
                    gs.remove("className");
                    gs.remove("path");
                    gs.remove("verb");

                    ObjectMapper Obj = new ObjectMapper();
                    String jsonStr  = Obj.writeValueAsString(gs.toString());
                    collection_base = collection_base+
                            "        \"body\": {\n" +
                            "            \"mode\": \"raw\",\n" +
                            "            \"raw\": "+ jsonStr +",\n" +
                            "            \"options\": {\n" +
                            "                \"raw\": {\n" +
                            "                    \"language\": \"json\"\n" +
                            "                }\n" +
                            "            }\n" +
                            "        }"+
                            ",";

                    collection_base = collection_base+ "                            \"description\": \""+description+"\"\n" +
                            "                        }\n" +
                            "                    }\n" +
                            "                ]\n" +
                            "            }\n";
                }
        collection_base = collection_base +
                "}";
        System.out.println("collection_base: " + collection_base);

        return null;
    }
}
