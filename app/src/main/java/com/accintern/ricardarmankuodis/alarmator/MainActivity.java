package com.accintern.ricardarmankuodis.alarmator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mSetAlarmButton;
    RadioGroup mRadioGroup;
    EditText mInputText;
    RecyclerView mRecycler;

    LogAdapter mLogAdapter;

    List<LogMessage> logMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ButterKnife.bind(this);
        mSetAlarmButton = (Button) findViewById(R.id.startAlarmBtn);
        mSetAlarmButton.setOnClickListener(this);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mInputText = (EditText) findViewById(R.id.editText);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        logMessages = new ArrayList<>();
        logMessages.add(new LogMessage("Activity created", -1));

        mLogAdapter = new LogAdapter(logMessages,this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mLogAdapter);

        // register our battery change broadcast receiver
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        BatteryChangeReceiver bReceiver = new BatteryChangeReceiver();
        registerReceiver(bReceiver, ifilter);


    }

    private void setAlarm(int time) {

    }

    @Override
    public void onClick(View view) {
        String inputText = mInputText.getText().toString();
        if (inputText.isEmpty()) {
            mInputText.setError("No input!");
            return;
        }
        int input = Integer.parseInt(mInputText.getText().toString());
        int time;
        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rbtnMs:
                time = input;
                break;
            case R.id.rbtnS:
                time = input * 1000;
                break;
            case R.id.rbtnM:
                time = input * 1000 * 60;
                break;
            case R.id.rbtnH:
                time = input * 1000 * 60 * 60;
                break;
            default:
                mInputText.setError("No category!");
                return;
        }
        Toast.makeText(this, "Time value ms: " + time, Toast.LENGTH_SHORT).show();

//        setAlarm(time);
        AlarmTask alarmTask = new AlarmTask();
        alarmTask.execute(time, null, null);
    }

    private class AlarmTask extends AsyncTask<Integer, Void, Integer> {

        protected Integer doInBackground(Integer... time) {

            try {
                Thread.sleep(time[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return time[0];
        }

        @Override
        protected void onPostExecute(Integer time) {
            Toast.makeText(MainActivity.this, "Time expired!", Toast.LENGTH_SHORT).show(); //TODO add to RecyclerView
            // TODO add time?
            appendToLog(1,"Alarm end");
        }
    }

    private class BatteryChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = level / (float) scale;
            Toast.makeText(context, "Battery Changed: " + batteryPct, Toast.LENGTH_SHORT).show();
            appendToLog(2, "Battery Changed: " + (int)(batteryPct*100)+"%");
        }
    }

    private void appendToLog(int category, String text) {
        LogMessage logMsg = new LogMessage(text, category);
        logMessages.add(logMsg);
        // TODO replace with notifyItemAdded or etc.
        mLogAdapter.notifyDataSetChanged();
    }

}
