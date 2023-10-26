package org.example;

import java.util.Arrays;

import static spark.Spark.*;

public class MathServices {
    public static void main(String... args){
        port(getPort());
        get("lucasseq", (req,res) -> {

            int n = Integer.parseInt(req.queryParams("value"));
            Integer[] resp = new Integer[n + 1];

            if (n >= 2){
                resp[0] = 2;
                resp[1] = 1;
                for (int i = 2; i <= n; i++) {
                    resp[i] = resp[i - 1] + resp[i - 2];
                }
            } else if (n == 1){
                resp[0] = 2;
                resp[1] = 1;
            } else {
                resp[0] = 2;
            }

            String json = "{" +
                    "opetarion: Secuencia de Lucas," +
                    "input:" + n + "," +
                    "output: " + Arrays.toString(resp) +
                    "}";

            return json;
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001;
    }
}