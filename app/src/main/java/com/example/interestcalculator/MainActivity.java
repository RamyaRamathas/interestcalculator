package com.example.interestcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private EditText initial_investment,regular_Payment, Annual_Intrest_Rate = null;
    private Button calculate;
    private Spinner regPayment;
    private TextView result,years;
    //float initial, interest, pay;
    private SeekBar Period;
    int min=1, max=15, current = 4;
    private double s;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial_investment =(EditText)findViewById(R.id.initial_investement);
        regular_Payment = (EditText)findViewById(R.id.regular_payment);

        Annual_Intrest_Rate=(EditText)findViewById(R.id.Interest_rate);

        Period=(SeekBar)findViewById(R.id.Interest);
        result = (TextView)findViewById(R.id.result);
        years=(TextView)findViewById(R.id.years);
        calculate = (Button)findViewById(R.id.button_calculate);
        regPayment=(Spinner)findViewById(R.id.spinner);

        List<String> spinnerArray = new ArrayList<String>();

        ArrayAdapter<CharSequence> valueAdapter = ArrayAdapter.createFromResource(this,R.array.deposit_freq, R.layout.spinner_item);

        valueAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        regPayment.setAdapter(valueAdapter);

        //spinner values

        regPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        s=12;
                        break;

                    case 1:
                        s=6;
                        break;

                    case 2:
                        s=2;
                        break;

                    case 3:
                        s=1;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Period.setMax(max);
        Period.setProgress(current);

        //seekbar set to valye between 1 to 15
        Period.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                current=progress;
                years.setText( current + " yrs");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //check if the value is not entered
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = initial_investment.getText().toString();
                String s2 = regular_Payment.getText().toString();
                String s3 = Annual_Intrest_Rate.getText().toString();

                if(s1 == null || s1.length() == 0)
                {
                    initial_investment.setError("Enter initial investement amount");
                    return;
                }

                if(s2==null || s2.length()==0)
                {
                    regular_Payment.setError("Enter Payment amount");
                    return;
                }

                if(s3==null || s3.length()==0)
                {
                    Annual_Intrest_Rate.setError("Enter the interest rate");
                    return;
                }
               double principal = Double.parseDouble(s1);
                double Deposit = Double.parseDouble(s2);
                double interest = Double.parseDouble(s3);
                double final_val = calculateInvestment(principal,Deposit,interest);

                result.setText(String.format("Compound Interest: $%.2f",final_val));




            }

            //a function to run the formula
            private double calculateInvestment(double principal, double deposit, double interest) {
                double compounding_freq =1;
                double p = compounding_freq * current;
                double r= (interest/100)*(1/compounding_freq);
                double Amount = principal * Math.pow(1+r,p) + ( deposit * s) * (((Math.pow(1+r,p) - 1) / r));

                return Amount;

            }
        });


    }
}
