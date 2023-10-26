package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.*;
public class ServiceProxy {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String... args){
        port(getPort());
        staticFiles.location("/public");
        get("hello", (req,res) -> {

            String GET_URL = "https://ec2-54-158-98-164.compute-1.amazonaws.com:5001/lucasseq?value=" + req.queryParams("name");
            URL obj = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            //The following invocation perform the connection implicitly before getting the code
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);

            StringBuffer response = null;
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
            } else {
                System.out.println("GET request not worked");
            }
            return response.toString();
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }

    }

