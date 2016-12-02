package com.android.toma.practice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    EditText editBill;
    SeekBar seekBar;
    TextView percent;
    TextView percentValue;
    TextView totalSum;
    float tips = 0;
    float money;

    final String LOG_TAG = "Log";
    final String KEY_TIPS = "TIPS";
    final String KEY_MONEY = "MONEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d(LOG_TAG, "onCreate");


        editBill = (EditText) findViewById(R.id.editBill);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        percent = (TextView) findViewById(R.id.percent);
        percentValue = (TextView) findViewById(R.id.percentValue);
        totalSum = (TextView) findViewById(R.id.totalSum);

        seekBar.setOnSeekBarChangeListener(this);



        editBill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if(input.contains(".") && s.charAt(s.length()-1) != '.'){
                    if(input.indexOf(".") + 3 <= input.length()-1){
                        String formatted = input.substring(0, input.indexOf(".") + 3);
                        editBill.setText(formatted);
                        editBill.setSelection(formatted.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                int tipsPercent = Integer.parseInt(String.valueOf(seekBar.getProgress()));
                countMoney(tipsPercent);
            }
        });

    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        percentValue.setText(savedInstanceState.get(KEY_TIPS).toString());
        totalSum.setText(savedInstanceState.get(KEY_MONEY).toString());
        Log.d(LOG_TAG, "onRestoreInstanceState");
    }

    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume ");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TIPS, percentValue.getText().toString());
        outState.putString(KEY_MONEY, totalSum.getText().toString());
        Log.d(LOG_TAG, "onSaveInstanceState");
    }

    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

        percent.setText(String.valueOf(progress) + "%");
        int tipsPercent = Integer.parseInt(String.valueOf(seekBar.getProgress()));
        countMoney(tipsPercent);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void countMoney(int tipsPercent) {
        money = getBill(editBill);
        tips = money * tipsPercent / 100;
        percentValue.setText(String.format("%.2f", tips));
        totalSum.setText(String.format("%.2f", tips + money));
    }


    private float getBill (EditText editBill){
        float bill;
        if (editBill.length() == 0){
            return 0;
        }else {
            bill = Float.parseFloat(String.valueOf(editBill.getText()));
            if (bill < 0){
                Toast.makeText(getApplicationContext(), "Your bill is incorrect!", Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
        return bill;
    }
}
