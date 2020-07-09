package sg.edu.rp.c346.id19030019.mybmicalculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight,etHeight;
    Button btnCalculate, btnReset;
    TextView tvBMI,tvDate, tvBMIresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight=findViewById(R.id.etWeight);
        etHeight=findViewById(R.id.etHeight);
        btnCalculate=findViewById(R.id.buttonCalculate);
        btnReset=findViewById(R.id.buttonReset);
        tvBMI=findViewById(R.id.tvCalc);
        tvDate=findViewById(R.id.tvDate);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight=Float.parseFloat(etWeight.getText().toString());
                float height=Float.parseFloat(etHeight.getText().toString());
                float bmi=weight/(height*height);
                Calendar now=Calendar.getInstance();
                String dateTime=now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvBMI.setText("Last Calculated BMI" + bmi);
                tvDate.setText("Last Calculated Date: " + dateTime);
                etHeight.setText("");
                etWeight.setText("");

                if(bmi < 18.5){
                    tvBMI.setText("You are underweight");
                }
                else if (bmi >= 18.5 && bmi <= 24.9){
                    tvBMI.setText("You BMI is normal");
                }
                else if (bmi >= 25 && bmi <=29.9){
                    tvBMI.setText("You are overweight");
                }
                else{
                    tvBMI.setText("You are obese");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHeight.setText("");
                etWeight.setText("");
                SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor edit=prefs.edit();
                edit.commit();
            }
        });
    }

    protected void onPause (){
        super.onPause();
        String dateTime=tvDate.getText().toString();
        float bmi=Float.parseFloat(tvBMI.getText().toString());

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor preEdit=prefs.edit();

        preEdit.putString("dateTime", dateTime);
        preEdit.putFloat("BMI value", bmi);

        preEdit.commit();

    }

    protected void onResume(){
        super.onResume();

        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);

        String dateTime=tvDate.getText().toString();
        float bmi=Float.parseFloat(tvBMI.getText().toString());
        tvDate.setText(dateTime);
        tvBMI.setText(bmi + "");
    }
}
