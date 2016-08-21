package com.example.arnm.wearlovely;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    private JSONObject jsonObject;
    private EditText username;
    private EditText useremail;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        jsonObject = new JSONObject();
        username = (EditText) findViewById(R.id.lg_username);
        useremail = (EditText) findViewById(R.id.lg_useremail);
        password = (EditText) findViewById(R.id.lg_password);
    }

    public void onClick_lg_login(View v) {
        /*try {
            jsonObject.put("id", username.getText().toString());
            jsonObject.put("pw", password.getText().toString());

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://110.11.84.215:3000/users");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        conn.setDefaultUseCaches(false);
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");

                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                        PrintWriter writer = new PrintWriter(outputStreamWriter);
                        writer.write(jsonObject.toString());
                        writer.flush();

                        InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        StringBuilder builder = new StringBuilder();
                        String str;

                        while((str = bufferedReader.readLine()) != null) {
                            builder.append(str + "\n");
                        }

                        JSONObject obj = new JSONObject(builder.toString());
                        Log.d("test", obj.get("_id").toString() + " " + obj.get("id").toString() + " " + obj.get("pw").toString());
                    } catch(MalformedURLException | ProtocolException exception) {
                        exception.printStackTrace();
                    } catch(IOException io){
                        io.printStackTrace();
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();*/

            /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick_lg_join(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://wearlovely.herokuapp.com"));
        startActivity(intent);
    }
}
