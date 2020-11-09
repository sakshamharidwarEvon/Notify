package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    TextView Title,Message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Title=(TextView)findViewById(R.id.text_title);
        Message=(TextView)findViewById(R.id.text_message);


        Log.d("ui", String.valueOf(getIntent().getExtras()));
        Log.d("Token", FirebaseInstanceId.getInstance().getToken());

        LocalBroadcastManager.getInstance(this).registerReceiver(mHandler,new IntentFilter("com.example.notify_FCM-MESSAGE"));
        if(getIntent().getExtras()!=null){
            for(String key:getIntent().getExtras().keySet()){
                Log.d("uo", String.valueOf(getIntent().getExtras().keySet()));
                if(key.equals("title")){
                    Log.d("uh",getIntent().getExtras().getString("title"));
                    Title.setText(getIntent().getExtras().getString("title"));}
                else
                    if(key.equals("message")){
                        Log.d("uj",getIntent().getExtras().getString("message"));
                    Message.setText(getIntent().getExtras().getString("message"));

                }
            }
        }

    }
    private BroadcastReceiver mHandler=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title=intent.getStringExtra("title");
            String message=intent.getStringExtra("message");
            Title.setText(title);
            Message.setText(message);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandler);
    }
}