package com.example.arnm.wearlovely;

/**
 * Created by Arnm on 2016-08-26.
 */

import android.os.AsyncTask;
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

public class SendPost extends AsyncTask<JSONObject, Void, Void> {
    private Handler handler;

    public SendPost(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected Void doInBackground(JSONObject... params) {
        try {
            URL url = new URL("http://192.168.78.1:3000/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDefaultUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outputStreamWriter);
            writer.write(params[0].toString());
            writer.flush();

            InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String str;

            while((str = bufferedReader.readLine()) != null) {
                builder.append(str + "\n");
            }

            JSONObject obj = new JSONObject(builder.toString());
            Message msg = Message.obtain(handler, 0, params[0]);
            handler.sendMessage(msg);
        } catch(MalformedURLException | ProtocolException exception) {
            exception.printStackTrace();
        } catch(IOException io){
            io.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
