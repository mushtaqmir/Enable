package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;


public class startFuelDetails extends ToolBarActivity {

    public static final String ORDER_DET_CONSTANT = "com.example.anshulkumar.shellapplication.ORDER_DET_CONSTANT";
    public static final String FUEL_TYPE_PETROL = "Petrol";
    public static final String FUEL_TYPE_DIESEL = "Diesel";
    public static final String AMOUNT = "Amount";
    public static final String QUANTITY = "Quantity";
    public static final double DOUBLE_ZERO = 0.00;
    public static final int BUTTON_PRESED_DISABLED = 0;
    public static final int BUTTON_PRESED_ENABLED = 1;
    public static String fuelType = "";
    public static int buttonPressed = BUTTON_PRESED_DISABLED;
    String txtWindshield ="No";
    String txtFreeOil ="No";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_fuel_pg);
        //=----------------------------------
        final OrderDetails orderDetails = new OrderDetails();
        String category ="";
        final double fuelQty = DOUBLE_ZERO;
        final double fuelAmount=DOUBLE_ZERO;
        final boolean fullTank;
        final Resources res = getResources();

        final RadioGroup fuelQuantityRadioGroup = (RadioGroup) findViewById(R.id.fuelQuantityRadioGroup);
        final RadioButton fullTankRadioBtn = (RadioButton) findViewById(R.id.fullTankRadioBtn);
        final RadioButton amountRadioBtn = (RadioButton) findViewById(R.id.amountRadioBtn);
        final RadioButton quantityRadioBtn = (RadioButton) findViewById(R.id.quantityRadioBtn);
        final TextView enterQty = (TextView) findViewById(R.id.enterQty);
        final EditText editQty = (EditText) findViewById(R.id.editQty);
        final Button submitButton = (Button) findViewById(R.id.submitButton);

        final RadioGroup fuelTypeRadioGroup = (RadioGroup) findViewById(R.id.fuelTypeRadioGroup);
        final RadioButton fuelTypePetrolUnleaded = (RadioButton) findViewById(R.id.fuelTypePetrolUnleaded);
        final RadioButton fuelTypeVPowerPetrol = (RadioButton) findViewById(R.id.fuelTypeVPowerPetrol);
        final RadioButton fuelTypeVPowerDiesel = (RadioButton) findViewById(R.id.fuelTypeVPowerDiesel);
        final RadioButton fuelTypeDieselUnleaded = (RadioButton) findViewById(R.id.fuelTypeDieselUnleaded);

        final TextView WindshieldCleanTxt = findViewById(R.id.WindshieldCleanTxt);
        final RadioGroup WindshieldClean = (RadioGroup) findViewById(R.id.WindshieldClean);
        final RadioButton WindshieldCleanYes = (RadioButton) findViewById(R.id.WindshieldCleanYes);
        final RadioButton WindshieldCleanNo = (RadioButton) findViewById(R.id.WindshieldCleanNo);


        final TextView FreeOilChangeTxt = findViewById(R.id.FreeOilChangeTxt);
        final RadioGroup FreeOilChange = (RadioGroup) findViewById(R.id.FreeOilChange);
        final RadioButton FreeOilChangeYes = (RadioButton) findViewById(R.id.FreeOilChangeYes);
        final RadioButton FreeOilChangeNo = (RadioButton) findViewById(R.id.FreeOilChangeNo);

        fuelTypeRadioGroup.setVisibility(View.VISIBLE);
        fuelQuantityRadioGroup.setVisibility(View.INVISIBLE);
        enterQty.setVisibility(View.INVISIBLE);
        editQty.setVisibility(View.INVISIBLE);
        orderDetails.setFuelAmount(DOUBLE_ZERO);
        submitButton.setVisibility(View.INVISIBLE);
        WindshieldCleanTxt.setVisibility(View.INVISIBLE);
        WindshieldClean.setVisibility(View.INVISIBLE);
        FreeOilChangeTxt.setVisibility(View.INVISIBLE);
        FreeOilChange.setVisibility(View.INVISIBLE);


        fuelTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fuelQuantityRadioGroup.setVisibility(View.VISIBLE);
                FreeOilChange.setVisibility(View.VISIBLE);
                FreeOilChangeTxt.setVisibility(View.VISIBLE);
                WindshieldCleanTxt.setVisibility(View.VISIBLE);
                WindshieldClean.setVisibility(View.VISIBLE);
                enterQty.setVisibility(View.INVISIBLE);
                editQty.setVisibility(View.INVISIBLE);
                submitButton.setVisibility(View.INVISIBLE);
                fuelQuantityRadioGroup.clearCheck();

                if(fuelTypeVPowerPetrol.isChecked()){
                    fuelType = FUEL_TYPE_PETROL; //Petrol
                    orderDetails.setFuelType(fuelType);
                    orderDetails.setCategory(String.format(res.getString(R.string.fuelVpowerStr) + " " + fuelType));

                }
                else if(fuelTypePetrolUnleaded.isChecked()){
                    fuelType = FUEL_TYPE_PETROL; //Diesel
                    orderDetails.setCategory(String.format(res.getString(R.string.fuelUnleadedStr) + " " + fuelType));
                    orderDetails.setFuelType(fuelType);
                }
                else if(fuelTypeVPowerDiesel.isChecked()){
                    fuelType = FUEL_TYPE_DIESEL; //Diesel
                    orderDetails.setCategory(String.format(res.getString(R.string.fuelVpowerStr) + " " + fuelType));
                    orderDetails.setFuelType(fuelType);
                }
                else if(fuelTypeDieselUnleaded.isChecked()){
                    fuelType = FUEL_TYPE_DIESEL; //Diesel
                    orderDetails.setCategory(String.format(res.getString(R.string.fuelUnleadedStr) + " " + fuelType));
                    orderDetails.setFuelType(fuelType);
                }

            }
        });

        fuelQuantityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(fullTankRadioBtn.isChecked()){
                    submitButton.setVisibility(View.VISIBLE);
                    enterQty.setVisibility(View.INVISIBLE);
                    editQty.setVisibility(View.INVISIBLE);
                    orderDetails.setFullTank(true);
                    orderDetails.setFuelQty(DOUBLE_ZERO);
                    orderDetails.setFuelAmount(DOUBLE_ZERO);
                    orderDetails.setFuelQtyAmtIndentifier(0);

                }
                else if(amountRadioBtn.isChecked()){
                    submitButton.setVisibility(View.VISIBLE);
                    enterQty.setText(String.format(res.getString(R.string.enterQty) + " "+ res.getString((R.string.amountStr))));
                    enterQty.setVisibility(View.VISIBLE);
                    editQty.setVisibility(View.VISIBLE);
                    editQty.setText("");
                    orderDetails.setFullTank(false);
                    orderDetails.setFuelQty(DOUBLE_ZERO);
                    orderDetails.setFuelQtyAmtIndentifier(1);
                    }
                    else if(quantityRadioBtn.isChecked()){
                    submitButton.setVisibility(View.VISIBLE);
                    enterQty.setText(String.format(res.getString(R.string.enterQty) + " "+ res.getString((R.string.quantityStr))));
                    enterQty.setVisibility(View.VISIBLE);
                    editQty.setVisibility(View.VISIBLE);
                    editQty.setText("");
                    orderDetails.setFullTank(false);
                    orderDetails.setFuelAmount(DOUBLE_ZERO);
                    orderDetails.setFuelQtyAmtIndentifier(2);

                    }
                 }
            });

        WindshieldClean.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        if(WindshieldCleanYes.isChecked()){
                            txtWindshield = "Yes";
                        }
                        else{
                            txtWindshield = "No";
                        }


                    }
                });
        FreeOilChange.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        if(FreeOilChangeYes.isChecked()){
                            txtFreeOil="Yes";
                        }
                        else{
                            txtFreeOil="No";
                        }


                    }
                });
            //Button OnCLickLISTNER
            submitButton.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){

                            if(amountRadioBtn.isChecked()) {
                                if(TextToDouble(editQty.getText().toString()) == DOUBLE_ZERO
                                        || TextToDouble(editQty.getText().toString()) ==0.0) {
                                    Toast.makeText(getApplicationContext(), "Please enter a valid amount",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                orderDetails.setFuelAmount(TextToDouble(editQty.getText().toString()));
                            }
                            else if(quantityRadioBtn.isChecked()) {
                                orderDetails.setFuelQty(TextToDouble(editQty.getText().toString()));
                                if(TextToDouble(editQty.getText().toString()) == DOUBLE_ZERO
                                        || TextToDouble(editQty.getText().toString()) ==0.0) {
                                    Toast.makeText(getApplicationContext(), "Please enter a valid Quantity",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            Intent launchModeOfPayment= new Intent(startFuelDetails.this,paymentMode.class);
                            launchModeOfPayment.putExtra(ORDER_DET_CONSTANT,orderDetails);
                            launchModeOfPayment.putExtra("txtFreeOil",txtFreeOil);
                            launchModeOfPayment.putExtra("txtWindshield",txtWindshield);
                            startActivity(launchModeOfPayment);

                        }
                    } );
        }


   public double TextToDouble(String text) {
       double value = 0.00;
       if (!text.isEmpty())
           try {
               value = Double.parseDouble(text);
               // it means it is double
           } catch (Exception e1) {
               // this means it is not double
               e1.printStackTrace();
           }
           return value;
   }

}
