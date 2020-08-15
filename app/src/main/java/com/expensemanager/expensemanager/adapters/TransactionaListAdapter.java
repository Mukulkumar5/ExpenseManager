package com.expensemanager.expensemanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.expensemanager.expensemanager.R;
import com.expensemanager.expensemanager.models.TransactionModel;

import java.util.List;

public class TransactionaListAdapter extends ArrayAdapter<TransactionModel> {

    private List<TransactionModel> list;

    public TransactionaListAdapter(@NonNull Context context, List<TransactionModel> list/*(list is a object)*/) {
        super(context, R.layout.row_for_transaction_list, list);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_for_transaction_list, null);
        }

        TransactionModel transactionModel = list.get(position);

        if (transactionModel != null) {

            TextView textViewAmount = convertView.findViewById(R.id.textview_amount);
            textViewAmount.setText("₹ " + String.valueOf(transactionModel.getAmount()));


            TextView textViewDate = convertView.findViewById(R.id.textview_date);
            textViewDate.setText(String.valueOf(transactionModel.getDate()));


            TextView textViewCategory = convertView.findViewById(R.id.textview_category);
            textViewCategory.setText(String.valueOf(transactionModel.getCategory()));


            TextView textViewDescription = convertView.findViewById(R.id.textview_description);
            textViewDescription.setText( String.valueOf(transactionModel.getDescription()));
        }

        return convertView;

    }
}
