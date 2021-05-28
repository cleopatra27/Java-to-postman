package com.javatopostman.controller;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Command(name = "checksum", mixinStandardHelpOptions = true, version = "checksum 4.0",
//        description = "Prints the checksum (MD5 by default) of a file to STDOUT.")
//class CheckSum implements Callable<Integer> {
//
//    @Parameters(index = "0", description = "The file whose checksum to calculate.")
//    private File file;
//
//    @Option(names = {"-a", "--algorithm"}, description = "MD5, SHA-1, SHA-256, ...")
//    private String algorithm = "MD5";
//
//    @Override
//    public Integer call() throws Exception { // your business logic goes here...
//        byte[] fileContents = Files.readAllBytes(file.toPath());
//        byte[] digest = MessageDigest.getInstance(algorithm).digest(fileContents);
//        System.out.printf("%0" + (digest.length*2) + "x%n", new BigInteger(1, digest));
//        return 0;
//    }



    @Command(name = "make-collection", mixinStandardHelpOptions = true, version = "make-collection 4.0",
            description = "Generates a postman collection from the input and output objects in your class.")
    class Collection implements Callable<Integer> {

        @Parameters(index = "0", description = "The file to drop the collection json code.")
        private File file;

        @Option(names = {"-a", "--classes"}, description = "MD5, SHA-1, SHA-256, ...")
        private String algorithm = "MD5";

        @Override
        public Integer call() throws Exception {
            // read file
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(
                        "/Users/pankaj/Downloads/myfile.txt"));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);

                    // read next line
                    //check if line has @path
                    //check if line has object
                    //check return statement
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }



    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
//        int exitCode = new CommandLine(new Collection()).execute(args);
//        System.exit(exitCode);

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "/controller/Test.java"));
            String line = reader.readLine();
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            String previousLine = null;
            boolean top = false;
            String base = null;
            while (line != null) {
              // System.out.println(line);

                //check if line has @path
                if(line.contains("@Path")){
                    Matcher m = p.matcher(line);
                    //check if there is / or ; before the path and append it to any other path
                    if(previousLine != null && previousLine.endsWith("/") || previousLine != null && previousLine.endsWith(";")){
                        top = true;
                    }
                    while (m.find()) {
                        if(top){
                            System.out.println("base: "  + m.group(1));
                            base = m.group(1);
                            top = false;
                        }else {
                            System.out.println("path: " + base + m.group(1));
                        }
                    }
                   // System.out.println("path: "+ line);
                }

                if(line.contains("@Produces")){
                    //return error if not json; not handled yet!
                    if(!line.contains("JSON")) {
                        System.out.println("\uD83D\uDC7F \uD83D\uDC7F sorry, only support JSON at the moment");
                    }
                }

                if(contains(line)){
                    //set verb
                   // System.out.println("Verb: "+line);
                }
                 //check if line has object
                if(line.contains("(") && line.contains(")") && line.contains("public")){
                    String response =  line.substring(line.lastIndexOf("public ") + 7, line.lastIndexOf(" "));
                    System.out.println("response: "+ response);

                    BufferedReader readers;
                    readers = new BufferedReader(new FileReader(
                            "/controller/"+response +".java"));
                    String lined = readers.readLine();
                    while (lined != null) {
                       // System.out.println(lined);
                        //check if an object data
                        HashMap<String, Object> map = new HashMap<>();
                        if(lined.contains("private")) {
                            //get types and name
                            //create hashmap
                            map.put(lined.substring(lined.lastIndexOf("private ") + 7), "");

                            System.out.println(map);
                        }
                        lined = readers.readLine();
                    }
                    readers.close();
                }
                //check return statement

                previousLine = line;

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static boolean contains(String test) {
            //System.out.println("test:" +test);

            for (verb c : verb.values()) {
                if (c.name().equals(test)) {
                    return true;
                }
            }

            return false;
        }

    enum verb{
            POST,
            PUT,
           PATCH,
           FETCH
    }

}