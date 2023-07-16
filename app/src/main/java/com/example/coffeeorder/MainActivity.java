package com.example.coffeeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** when we click Add button */
    public void increment(View view)
    {
        if(quantity==100){
            Toast.makeText(this ,"You cannot have more then 100 Coffee" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /** when we click Subtract button */
    public void decrement(View view)
    {
        if(quantity==0){
            Toast.makeText(this ,"You cannot have less then 1 Coffee" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity -1;
        display(quantity);
    }
    /** when we click order button */
    public void submitOrder(View view)
    {
        EditText customerNameField = (EditText) findViewById(R.id.customer_name_field);
        String name = customerNameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();
        //priceDisplay(quantity*5);
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Only email apps handle this.
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Cutomer Order For :" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(createOrderSummary(name, price, hasWhippedCream, hasChocolate));

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:30.157458!4d71.5249154"));
//        if(intent.resolveActivity(getPackageManager())!=null){
//            startActivity(intent);
//        }
    }
    private String createOrderSummary(String name, int price, boolean addWippedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\nAdd Whipped Cream ?" + addWippedCream;
        priceMessage += "\nAdd Choclate ?" + addChocolate;
        priceMessage +="\n Quantity: " + quantity;
        priceMessage +="\n Total: $" + price;
        priceMessage +="\n " + getString(R.string.thank_you);
        return priceMessage;
    }
    private int calculatePrice(boolean addWippedCream, boolean addChocolate){
        int basePrice = 5;
        if(addWippedCream){
            basePrice +=1;
        }
        if(addChocolate){
            basePrice +=2;
        }
        return quantity * basePrice;
    }
//    private void displayMessage(String message)
//    {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
    private void display(int number)
    {
        /** Finding the quantity initial value */
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        /**setting the new value to the quantity text view */
        quantityTextView.setText(" " + number);
    }
//    private  void priceDisplay(int number){
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }
}