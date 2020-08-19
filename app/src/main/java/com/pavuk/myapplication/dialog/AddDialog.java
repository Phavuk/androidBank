package com.pavuk.myapplication.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pavuk.myapplication.R;

import java.lang.reflect.Array;

public class AddDialog extends DialogFragment {

    private AddDialogListener listener;
    private String heading;

    /** interface pre callback */
    public AddDialog(AddDialogListener listener, String heading){
        this.listener = listener;
        this.heading = heading;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** ziskanie buildera pre vybuildovanie alert dialogu */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /** setnutie vlastného layoutu do nášho dialogu */
        View viewToDialog = getLayoutInflater().inflate(R.layout.fragment_add_dialog,null);
        builder.setView(viewToDialog);

        /** setnutie hlavneho nadpisu v dialogu s textom ktory nám príde v konštruktore (vyššie) */
        final TextView heading = viewToDialog.findViewById(R.id.heading);
        heading.setText(this.heading);

        /** getnutie všetkych inputov z layoutu dialogu */
        final EditText name = viewToDialog.findViewById(R.id.nameField);
        final EditText price = viewToDialog.findViewById(R.id.priceField);
        final EditText category = viewToDialog.findViewById(R.id.categoryField);

        /** pole stringov v ktorom budeme posielať hodnoty z inputov */
        final String[] arr = new String[3];

        viewToDialog.findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    /** overenie inputu ktorá vkladáme */
                    if(TextUtils.isEmpty(name.getText().toString())){
                        name.setError("Empty name");
                    }else {
                        if(TextUtils.isEmpty(price.getText().toString())) {
                                price.setError("Empty price");
                        } else {
                            if(TextUtils.isEmpty(category.getText().toString())) {
                                category.setError("Empty category");
                            }else{
                                /** pridanie inputov do pola */
                                arr[0] = name.getText().toString();
                                arr[1] = price.getText().toString();
                                arr[2] = category.getText().toString();

                                /** pole dáme do callback funkcie z interfacu AddDialogListener */
                                listener.onPositiveClick(arr);
                                dismiss();
                            }
                        }
                    }
            }
        });

        return builder.create();
    }
}

