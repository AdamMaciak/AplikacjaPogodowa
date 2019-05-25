package com.example.aplikacjapogodowa;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class Connection_manager {

    public String get_json(String page)
    {
        URLConnection url;
        String json="";
        try
        {
            url = new URL(page).openConnection();
            InputStream is = url.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;

            while ((line = reader.readLine()) != null)
            {
                json+=line;
            }
            is.close();
            return json;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return json;
    }

}
