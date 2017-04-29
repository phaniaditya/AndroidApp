package com.example.a5e025799h.next_genshopping;

import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 5E025799H on 4/19/2017.
 */


public class httpcall extends AsyncTask<Object, Integer, String> {
    public AsyncResponse delegate = null;
    private final OkHttpClient client = new OkHttpClient();
    public static Context contextOfApplication;
    protected String doInBackground(Object... inputObj) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        String url = (String) inputObj[0];

        String httpData = "";
        //contextOfApplication = getApplicationContext();


        Gson gson = new Gson();
        String jso = (String) inputObj[1];
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> arrayList = gson.fromJson(jso, type);


        RequestBody body = new FormEncodingBuilder()
                .add("Menu", arrayList.get(0))
                .add("Name",arrayList.get(1))
                .add("Address", arrayList.get(2))
                .add("Email", arrayList.get(4))
                .add("PhoneNumber",arrayList.get(3))
                .add("Date", arrayList.get(5))
                .add("Time", arrayList.get(6))
                .build();

        System.out.println(body.toString());
        Request request = new Request.Builder()
                .url(url).post(body).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if (!response.isSuccessful()) try {
//            throw new IOException("Unexpected code " + response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Headers responseHeaders = response.headers();
//        for (int i = 0; i < responseHeaders.size(); i++) {
//            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//        }
//
//        try {
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        InputStream inputStream = null;
//        HttpURLConnection httpURLConnection = null;
//        try {
//            URL url = new URL(googlePlacesUrl);
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            System.out.println(httpURLConnection.getResponseCode());
//            httpURLConnection.connect();
//            System.out.println(httpURLConnection.getResponseCode());
//
//
//        } catch (Exception e) {
//            Log.d("Exception", e.getMessage());
//        } finally {
//            try {
//                if(inputStream!=null)
//                    inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            httpURLConnection.disconnect();
//        }

        return httpData;
    }

    protected void onPostExecute(String result) {
        try {

            delegate.processFinish(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
