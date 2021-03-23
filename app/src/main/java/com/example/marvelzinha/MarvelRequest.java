package com.example.marvelzinha;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MarvelRequest extends AsyncTask<String,Integer,String> {

    String myUrl;
    Context context;

    MarvelRequest(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        myUrl = params[0];
        try {
            URL url = new URL(myUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception data) {
            data.printStackTrace();
            return data.toString();
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try{
            JSONObject js = new JSONObject(s);
            JSONArray data = js.getJSONObject("data").getJSONArray("results");

                String description = data.getJSONObject(0).getString("description");
                System.out.println(description);

                Toast.makeText(context, description, Toast.LENGTH_SHORT).show();

            int x = 1;

        }catch(JSONException data){
            data.printStackTrace();
        }
    }
}


