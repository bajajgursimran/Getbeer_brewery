package com.news.getbeer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class HttpConnect {

    public static String getDataFromApi(String ApiLink)
    {
        String total = "";
//comment added for git
        try {
            URL url = new URL(ApiLink);
            HttpURLConnection urlConnection = (HttpURLConnection)url
                    .openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0");
            int responseCode = urlConnection.getResponseCode();
            int status = urlConnection.getResponseCode();
            System.out.println("status "+status);
            System.out.println("responsecode "+responseCode);
            InputStream error = urlConnection.getErrorStream();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            System.out.println("error "+error);
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            String x = "";
            x = r.readLine();
            while (x != null) {
                total += x;
                x = r.readLine();
                break;
            }
            System.out.println("total "+total);
        } catch (Exception ex) {
            System.out.println("Exception" + ex.toString());
            total = "Exception";
        }
        return total;
    }
}
