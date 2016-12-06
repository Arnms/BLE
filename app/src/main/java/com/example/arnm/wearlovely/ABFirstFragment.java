package com.example.arnm.wearlovely;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016-08-11.
 */
public class ABFirstFragment extends Fragment {
    private EditText fafUuid;
    private EditText fafMajor;
    private EditText fafMinor;
    private EditText fafAlias;
    private Button fafOk;
    private Button fafCancel;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == PostCode.REQUEST_ADD_BEACON_CODE) {
                fafMajor.setText("");
                fafMinor.setText("");
                fafAlias.setText("");

                Toast.makeText(getActivity().getApplicationContext(), "비콘을 등록했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public ABFirstFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_first, container, false);
        fafUuid = (EditText) view.findViewById(R.id.faf_uuid);
        fafMajor = (EditText) view.findViewById(R.id.faf_major);
        fafMinor = (EditText) view.findViewById(R.id.faf_minor);
        fafAlias = (EditText) view.findViewById(R.id.faf_alias);
        fafOk = (Button) view.findViewById(R.id.faf_add_button);
        fafCancel = (Button) view.findViewById(R.id.faf_cancel_button);

        fafOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "/beacon/add";
                    JSONObject obj = new JSONObject();
                    obj.put("uuid", fafUuid.getText());
                    obj.put("major", fafMajor.getText());
                    obj.put("minor", fafMinor.getText());
                    obj.put("alias", fafAlias.getText());

                    SendPost sp = new SendPost(handler, obj, url, PostCode.REQUEST_SIGNIN_CODE);
                    Thread t = new Thread(sp);
                    t.start();
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        fafCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fafMajor.setText("");
                fafMinor.setText("");
                fafAlias.setText("");
            }
        });
        return view;
    }
}
