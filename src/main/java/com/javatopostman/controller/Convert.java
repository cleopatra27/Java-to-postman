package com.javatopostman.controller;

import org.json.JSONArray;
import org.json.JSONObject;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

        List<String> files = new ArrayList<String>();
        files.add("/Users/cleopatradouglas/Desktop/Java-to-postman/src/main/java/com/javatopostman/controller/Test.java");
        files.add("/Users/cleopatradouglas/Desktop/Java-to-postman/src/main/java/com/javatopostman/controller/Test2.java");

        for (String file : files) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(
                        file));
                String line = reader.readLine();
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                String previousLine = null;
                boolean top = false;
                List data = new ArrayList();
                String base = null;
                HashMap<String, Object> map = new HashMap<>();
                while (line != null) {

                    //check if line has @path
                    if (line.contains("@Path")) {
                        Matcher m = p.matcher(line);
                        //check if there is / or ; before the path and append it to any other path
                        if (previousLine != null && (previousLine.endsWith("/") || previousLine.endsWith(";")))
                            top = true;

                        while (m.find()) {
                            if (top) {
                                base = m.group(1);
                                top = false;
                                map.put("path", base + m.group(1));

                            } else {
                                map.put("path", m.group(1));
                            }
                        }
                    }

                    if (line.contains("@Produces")) {
                        //return error if not json; not handled yet!
                        if (!line.contains("JSON")) {
                            System.out.println("\uD83D\uDC7F \uD83D\uDC7F sorry, only support JSON at the moment");
                        }
                    }

                    if (contains(line)) {
                        //set verb
                        if (line.contains("@")) {
                            map.put("verb", line.substring(line.lastIndexOf("@") + 1));
                        }
                    }
                    //check if line has object
                    if (line.contains("(") && line.contains(")") && line.contains("public")) {
                        String response = line.substring(line.lastIndexOf("public ") + 7, line.lastIndexOf(" "));
                        System.out.println("response: " + response);

                        BufferedReader readers;


                        readers = new BufferedReader(new FileReader(
                                "/Users/cleopatradouglas/Desktop/Java-to-postman/src/main/java/com/javatopostman/controller/" + response + ".java"));
                        String lined = readers.readLine();
                        while (lined != null) {
                            //check if an object data
                            Object value = null;
                            String key = null;
                            if (lined.contains(" class ")) {
                                map.put("className", lined.substring(lined.lastIndexOf("class ") + 6, lined.lastIndexOf(" {")));
                            }

                            if (lined.contains("private")) {
                                //get types and name

                                if (lined.contains("String")) {
                                    key = lined.substring(lined.lastIndexOf("String ") + 7, lined.lastIndexOf(";"));
                                    value = "Sample";
                                } else if (lined.contains("int")) {
                                    key = lined.substring(lined.lastIndexOf("int ") + 4, lined.lastIndexOf(";"));
                                    value = 7;
                                } else if (lined.contains("boolean")) {
                                    key = lined.substring(lined.lastIndexOf("boolean ") + 8, lined.lastIndexOf(";"));
                                    value = true;
                                } else if (lined.contains("double")) {
                                    key = lined.substring(lined.lastIndexOf("double ") + 7, lined.lastIndexOf(";"));
                                    value = 0.00;
                                } else {
                                    System.out.println("map: "+new JSONObject(map));
                                    data.add(new JSONObject(map));
                                    String sfj = new PostmanConverter().doPostmanConverter("TEST", data, "");
                                    return;
                                }
                                //create hashmap
                                map.put(key, value);
                            }

                            lined = readers.readLine();
                        }

                        readers.close();

                    }
                    //check return statement
                    if (line.length() != 0)
                        previousLine = line;

                    line = reader.readLine();
                }
                reader.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean contains(String test) {
        // System.out.println("test:" +test);

        for (verb c : verb.values()) {
            if (test.contains(c.name())) {
                return true;
            }
        }
        return false;
    }

    enum verb {
        POST,
        PUT,
        PATCH,
        FETCH
    }

}