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


public class startFuelDetails extends AppCompatActivity {

    public static final String ORDER_DET_CONSTANT = "com.example.anshulkumar.shellapplication.ORDER_DET_CONSTANT";
    public static final String FUEL_TYPE_PETROL = "Petrol";
    public static final String FUEL_TYPE_DIESEL = "Diesel";
    public static final String AMOUNT = "Amount";
    public static final String QUANTITY = "Quantity";
    public static final double DOUBLE_ZERO = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_fuel_pg);

        final OrderDetails orderDetails = new OrderDetails();
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String fuelType = intent.getStringExtra(StartActivity.EXTRA_MESSAGE);
        final String editFlag = intent.getStringExtra("Edit Type");

        String category ="";
        final double fuelQty = DOUBLE_ZERO;
        final double fuelAmount=DOUBLE_ZERO;
        final boolean fullTank;
        orderDetails.setFuelType(fuelType);
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.fuelTypeView);
        textView.setText(fuelType+ " "+ "Details");
        if(intent != null) {
            final Resources res = getResources();

            final RadioGroup fuelCategoryRadioGroup = (RadioGroup) findViewById(R.id.fuelCategoryRadioGroup);
            final RadioButton fuelCategoryVPower = (RadioButton) findViewById(R.id.fuelCategoryVPower);
            final RadioButton fuelCategoryUnleaded = (RadioButton) findViewById(R.id.fuelCategoryUnleaded);
            final RadioGroup fuelQuantityRadioGroup = (RadioGroup) findViewById(R.id.fuelQuantityRadioGroup);
            final RadioButton fullTankRadioBtn = (RadioButton) findViewById(R.id.fullTankRadioBtn);
            final RadioButton amountRadioBtn = (RadioButton) findViewById(R.id.amountRadioBtn);
            final RadioButton quantityRadioBtn = (RadioButton) findViewById(R.id.quantityRadioBtn);
            final TextView enterQty = (TextView) findViewById(R.id.enterQty);
            final EditText editQty = (EditText) findViewById(R.id.editQty);
            final Button submitButton = (Button) findViewById(R.id.submitButton);

            fuelQuantityRadioGroup.setVisibility(View.INVISIBLE);
            enterQty.setVisibility(View.INVISIBLE);
            editQty.setVisibility(View.INVISIBLE);
            orderDetails.setFuelAmount(DOUBLE_ZERO);
            submitButton.setVisibility(View.INVISIBLE);

            if (fuelType.equals(FUEL_TYPE_PETROL)) { //Petrol
                fuelCategoryVPower.setText(String.format(res.getString(R.string.fuelVpowerStr) + " " + FUEL_TYPE_PETROL));
                fuelCategoryUnleaded.setText(String.format(res.getString(R.string.fuelUnleadedStr) + " " + FUEL_TYPE_PETROL));
            } else {    //Diesel
                fuelCategoryVPower.setText(String.format(res.getString(R.string.fuelVpowerStr) + " " + FUEL_TYPE_DIESEL));
                fuelCategoryUnleaded.setText(String.format(res.getString(R.string.fuelUnleadedStr) + " " + FUEL_TYPE_DIESEL));
            }

            fuelCategoryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    fuelQuantityRadioGroup.setVisibility(View.VISIBLE);
                    if(fuelCategoryVPower.isChecked()){
                        orderDetails.setCategory(fuelCategoryVPower.getText().toString());
                    }
                    else if(fuelCategoryUnleaded.isChecked()){
                        orderDetails.setCategory(fuelCategoryUnleaded.getText().toString());
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

                            /*Intent launchOrderReview= new Intent(startFuelDetails.this,OrderReview.class);
                            launchOrderReview.putExtra(ORDER_DET_CONSTANT,orderDetails);
                            startActivity(launchOrderReview);*/

                            Intent launchModeOfPayment= new Intent(startFuelDetails.this,paymentMode.class);
                            launchModeOfPayment.putExtra(ORDER_DET_CONSTANT,orderDetails);
                            startActivity(launchModeOfPayment);

                        }
                    } );
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        return true;
    }
}
