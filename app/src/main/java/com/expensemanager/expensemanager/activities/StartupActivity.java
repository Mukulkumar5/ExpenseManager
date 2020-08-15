package com.expensemanager.expensemanager.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.expensemanager.expensemanager.R;
import com.expensemanager.expensemanager.utils.IConstants;

import static com.expensemanager.expensemanager.utils.IConstants.SP_NAME;

public class StartupActivity extends AppCompatActivity {


    EditText edittextName, editTextBudget;
    Button buttonProceed;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        edittextName = findViewById(R.id.edittext_name);
        editTextBudget = findViewById(R.id.edittext_budget);

        buttonProceed = findViewById(R.id.button_proceed);

        sharedPreferences = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE/*By setting this mode, the file can only be accessed using calling application*/);

        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edittextName.getText().toString().equals("")) {
                    edittextName.setError(getString(R.string.required));
                } else if (editTextBudget.getText().toString().equals("")) {
                    editTextBudget.setError(getString(R.string.required));
                } else {
                    String name = edittextName.getText().toString();
                    float budget = Float.parseFloat(editTextBudget.getText().toString());


                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    editor.putString(IConstants.NAME, name);
                    editor.putFloat(IConstants.BUDGET, budget);

                    editor.apply();

                    Intent intent = new Intent(StartupActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }
}
