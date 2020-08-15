package com.expensemanager.expensemanager.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.expensemanager.expensemanager.R;
import com.expensemanager.expensemanager.database.DBHelper;
import com.expensemanager.expensemanager.database.DatabaseContract;
import com.expensemanager.expensemanager.models.TransactionModel;
import com.expensemanager.expensemanager.utils.IConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    RadioGroup radioGroupTransactionType, radioGroupStatus;
    RadioButton radioButtonCredit, radioButtonDebit, radioButtonDone, radioButtonPending;
    TextView textViewDate,textviewtime;
    EditText edittextAmount,editTextDescription;
    Spinner spinnerCategory;
    Calendar calender;
    int year, month, day, hour, min, sec;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);


        ActionBar actionBar = getSupportActionBar();//From your activity, you can retrieve an instance of ActionBar by calling getSupportActionBar()

        if (actionBar != null) {

            actionBar.setTitle(R.string.add_new_transaction);
            actionBar.setDisplayHomeAsUpEnabled(true);//for <- (Home Icon) arrow
        }

        dbHelper = new DBHelper(AddTransactionActivity.this);
        radioGroupTransactionType = findViewById(R.id.radioGroup_transaction_type);
        radioGroupStatus = findViewById(R.id.radiogroup_status);
        radioButtonCredit = findViewById(R.id.radioButton_credit);
        radioButtonDebit = findViewById(R.id.radioButton_debit);
        radioButtonDone = findViewById(R.id.radiobutton_done);
        radioButtonPending = findViewById(R.id.radiobutton_pending);
        textViewDate = findViewById(R.id.textview_date);
        edittextAmount = findViewById(R.id.edittext_amount);
       editTextDescription = findViewById(R.id.edittext_description);
        spinnerCategory = findViewById(R.id.spinner_category);

        textviewtime = findViewById(R.id.textview_time);
        radioButtonDebit.setChecked(true);
        radioButtonDone.setChecked(true);

        List<String> allCategories= dbHelper.getAllCtegories();

         spinnerCategory.setAdapter(new ArrayAdapter<String>(
                 AddTransactionActivity.this,android.R.layout.simple_list_item_1,
                 allCategories
         ));
        calender = Calendar.getInstance();//returns a calender object whose calender felds have been initialized with the cuurent date and time
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH);
        day = calender.get(Calendar.DAY_OF_MONTH);
        hour = calender.get(Calendar.HOUR_OF_DAY);
        min = calender.get(Calendar.MINUTE);
        sec = calender.get(Calendar.SECOND);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddTransactionActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        textViewDate.setText(String.valueOf(day) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(year));

                    }
                },
                year,
                month,
                day);
        final TimePickerDialog timePickerDialog = new TimePickerDialog(
                AddTransactionActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        textviewtime.setText(String.valueOf(hour) + ":" + String.valueOf(min));

                    }
                },
                hour,
                min,

        false);

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               datePickerDialog.show();
            }
        });
        textviewtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });
        textViewDate.setText(String.valueOf(day) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(year));
        textviewtime.setText(String.valueOf(hour) + ":" + String.valueOf(min));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_transaction_activity, menu);//to show view
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//when user select an item from option menu the system call activity onOptionItemSelected
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AddTransactionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_save:
                saveData();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveData() {

        if(edittextAmount.getText().toString().equalsIgnoreCase("")){
            edittextAmount.setError(getString(R.string.required));
        } else{

            TransactionModel transactionModel = new TransactionModel();


            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            String[] split = textViewDate.getText().toString().split("-");

            String[] split2 = textviewtime.getText().toString().split(":");

            Date date = new Date(
                     year -1900,
                    Integer.parseInt(split[1])  -1,
                    Integer.parseInt(split[0]),
                    Integer.parseInt(split2[0]),
                    Integer.parseInt(split2[1])
            );

            String dateString = simpleDateFormat.format(date);

            transactionModel.setTransactionType(radioGroupTransactionType.getCheckedRadioButtonId() == R.id.radioButton_credit ? IConstants.TRANSACTION_TYPE_CREDIT:IConstants.TRANSACTION_TYPE_DEBIT);
            transactionModel.setDate(dateString);
            transactionModel.setAmount(Double.parseDouble(edittextAmount.getText().toString()));

            transactionModel.setDescription(editTextDescription.getText().toString());
            transactionModel.setCategory(spinnerCategory.getSelectedItem().toString());
            transactionModel.setStatus(radioGroupStatus.getCheckedRadioButtonId() == R.id.radiobutton_done ? IConstants.STATUS_DONE :IConstants.STATUS_PENDING);
            transactionModel.setRefImage(null);//TODO: 18-08-2018 Save  image
            boolean  result= dbHelper .saveTransaction(transactionModel);

            if(result){
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
                finish();

                Intent intent = new Intent(AddTransactionActivity.this,MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();

            }


             }

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(AddTransactionActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
