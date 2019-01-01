package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int price = 5;
    final int PRICE_OF_CREAM = 1;
    final int PRICE_OF_CHOCOLATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText editText = (EditText) findViewById(R.id.name_edit_text);
        String name = editText.getText().toString();

        CheckBox creamBox = (CheckBox) findViewById(R.id.cream_check_box);
        boolean creamChecked = creamBox.isChecked();

        CheckBox chocolateBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean chocolateChecked = chocolateBox.isChecked();

        int total = calculatePrice(creamChecked, chocolateChecked);

        String message = createOrderSummary(total, creamChecked, chocolateChecked, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void increment(View view) {

        if (quantity == 100) {
            Toast toast = Toast.makeText(getApplicationContext(), "Cannot go above 100 cups of " +
                    "coffee", Toast
                    .LENGTH_SHORT);
            toast.show();
            return;
        }

        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {

        if (quantity == 1) {
            Toast toast = Toast.makeText(getApplicationContext(), "Cannot go below 1 cup of " +
                    "coffee", Toast
                    .LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);

    }


    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {

        int base_price = price;

        if (hasWhippedCream) {
            base_price += PRICE_OF_CREAM;
        }
        if (hasChocolate) {
            base_price += PRICE_OF_CHOCOLATE;
        }
        return quantity * base_price;
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate,
                                      String name) {
        String ret = "";
        ret += "Name: " + name;
        ret += "\nAdd whipped cream? " + hasWhippedCream;
        ret += "\nAdd chocolate? " + hasChocolate;
        ret += "\nQuantity: " + quantity;
        ret += "\nTotal: $" + price;
        ret += "\nThank you!";
        return ret;
    }


}