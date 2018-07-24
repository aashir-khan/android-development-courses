/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

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

    public static final String TOTAL_$ = "\nTotal: $";
    public static final String QUANTITY = "\nQuantity: ";
    public static final String ADD_CHOCOLATE = "\nAdd chocolate? ";
    public static final String ADD_WHIPPED_CREAM = "\nAdd whipped cream? ";
    public static final String JUST_JAVA_ORDER_FOR = "JustJava order for ";
    public static final String MAILTO = "mailto:";
    public static final String YOU_CAN_T_ORDER_LESS_THAN_1_COFFEE = "You can't order less than 1 coffee.";
    public static final String YOU_CAN_T_ORDER_MORE_THAN_100_COFFEES = "You can't order more than 100 coffees.";
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {

        if (quantity == 100) {
            Toast.makeText(this, YOU_CAN_T_ORDER_MORE_THAN_100_COFFEES,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(this, YOU_CAN_T_ORDER_LESS_THAN_1_COFFEE,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        int price = calculatePrice(quantity, hasWhippedCream, hasChocolate);

        String messageToDisplay = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(MAILTO)); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, JUST_JAVA_ORDER_FOR + name);
        intent.putExtra(Intent.EXTRA_TEXT, messageToDisplay);
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


    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(int coffeeQuantity, boolean hasWhippedCream, boolean hasChocolate) {

        int basePrice = 5;
        if (hasWhippedCream) {
            basePrice += 1;
        }
        if (hasChocolate) {
            basePrice += 2;
        }

        return basePrice * quantity;

    }

    private String createOrderSummary(String name, int orderPrice, boolean addWhippedCream, boolean addChocolate) {
        String orderMessage = getString(R.string.order_summary_name, name);
        orderMessage += ADD_WHIPPED_CREAM + addWhippedCream;
        orderMessage += ADD_CHOCOLATE + addChocolate;
        orderMessage += QUANTITY + quantity;
        orderMessage += TOTAL_$ + orderPrice;
        orderMessage += "\n" + getString(R.string.thank_you);
        return orderMessage;

    }
}