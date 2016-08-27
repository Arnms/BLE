package com.example.arnm.wearlovely;

/**
 * Created by Arnm on 2016-08-26.
 */

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SendPost implements Runnable {
    private JSONObject mObj;
    private Handler mHandler;
    private String mUrl;
    private int mCode;

    public SendPost(Handler mHandler, JSONObject mObj, String mUrl, int mCode) {
        this.mHandler = mHandler;
        this.mObj = mObj;
        this.mUrl = mUrl;
        this.mCode = mCode;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDefaultUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outputStreamWriter);
            writer.write(mObj.toString());
            writer.flush();

            InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String str;

            while((str = bufferedReader.readLine()) != null) {
                builder.append(str + "\n");
            }

            JSONObject obj = new JSONObject(builder.toString());
            Message msg = Message.obtain(mHandler, mCode, obj);
            mHandler.sendMessage(msg);
        } catch(MalformedURLException | ProtocolException exception) {
            exception.printStackTrace();
        } catch(IOException io){
            io.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
