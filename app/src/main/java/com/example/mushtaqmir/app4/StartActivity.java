package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.anshulkumar.shellapplication.MESSAGE";
    public static final int BUTTON_PRESED_DISABLED = 0;
    public static final int BUTTON_PRESED_ENABLED = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        Button startButton = (Button) findViewById(R.id.startButton);
        final RadioGroup fuelTypeRadioGroup = (RadioGroup) findViewById(R.id.fuelTypeRadioGroup);
        final RadioButton fuelTypePetrol = (RadioButton) findViewById(R.id.fuelTypePetrol);
        final RadioButton fuelTypeDiesel = (RadioButton) findViewById(R.id.fuelTypeDiesel);


        //Button OnCLickLISTNER
        startButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent launchStartFuelDetails= new Intent(StartActivity.this,startFuelDetails.class);
                        String fuelType = "";
                        int buttonPressed = BUTTON_PRESED_DISABLED;
                        if(fuelTypePetrol.isChecked() == true){
                             fuelType = fuelTypePetrol.getText().toString(); //--petrol
                            buttonPressed =BUTTON_PRESED_ENABLED;
                        }
                        else if(fuelTypeDiesel.isChecked() ==true){
                             fuelType = fuelTypeDiesel.getText().toString(); //Diesel
                            buttonPressed = BUTTON_PRESED_ENABLED;
                        }
                        else{
                            //display in short period of time
                            Toast.makeText(getApplicationContext(),R.string.selectFuelTypeStr,
                                    Toast.LENGTH_SHORT).show();
                        }
                        if( buttonPressed == 1) {
                            launchStartFuelDetails.putExtra(EXTRA_MESSAGE, fuelType);
                            startActivity(launchStartFuelDetails);
                        }
                    }
        } );
    }


}
