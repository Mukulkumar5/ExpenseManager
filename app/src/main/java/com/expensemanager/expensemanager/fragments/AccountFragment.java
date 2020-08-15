package com.expensemanager.expensemanager.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.expensemanager.expensemanager.R;
import com.expensemanager.expensemanager.adapters.TransactionaListAdapter;
import com.expensemanager.expensemanager.database.DBHelper;
import com.expensemanager.expensemanager.models.TransactionModel;
import com.expensemanager.expensemanager.utils.IConstants;

import java.util.List;

public class AccountFragment extends Fragment {

    TextView textviewtotalexpense,textviewtotalincome;

    Spinner spinnercategory;
    ListView llistviewTrasaction;

    TextView textViewBudget;

    SharedPreferences sharedPreferences;

    DBHelper dbHelper;

    Float budget;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);


    sharedPreferences = getActivity().getSharedPreferences(IConstants.SP_NAME, Context.MODE_PRIVATE);

    budget  =  sharedPreferences.getFloat(IConstants.BUDGET,0);

        dbHelper = new DBHelper(getActivity());


        textviewtotalexpense = view.findViewById(R.id.textview_totalexpense);
        textviewtotalincome = view.findViewById(R.id.textview_totalincome);

        textViewBudget = view.findViewById(R.id.textView_budget);

        llistviewTrasaction = view.findViewById(R.id.listview_transaction);
        spinnercategory = view.findViewById(R.id.spinner_category);

        textViewBudget.setText("Monthly Budget" + "\n" + String.valueOf(budget) );

        textviewtotalincome.setText("Monthly Income" + "\n" + String.valueOf(dbHelper.getTotal(IConstants.TRANSACTION_TYPE_CREDIT)));
        textviewtotalexpense.setText("Monthly Expense" + "\n" + String.valueOf(dbHelper.getTotal(IConstants.TRANSACTION_TYPE_DEBIT)));

        final List<String> allCtegories = dbHelper.getAllCtegories();
        spinnercategory.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, allCtegories));

        spinnercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String categoryName = allCtegories.get(i);

                List<TransactionModel> allTransactionByCategory = dbHelper.getAllTransactionByCategory(categoryName);

                llistviewTrasaction.setAdapter(new TransactionaListAdapter(getActivity(),allTransactionByCategory));


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return  view;
    }
}
