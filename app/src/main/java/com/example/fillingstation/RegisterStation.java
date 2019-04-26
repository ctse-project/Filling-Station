package com.example.fillingstation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterStation extends AppCompatDialogFragment {

    Button clsbtn;
    Button sub;
    EditText chainName;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.register, null);

        clsbtn = mView.findViewById(R.id.close);
        sub = mView.findViewById(R.id.submit);
        chainName = mView.findViewById(R.id.chain);


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname = chainName.getText().toString();
                if (cname != ""){
                    Log.e("Not empty ", cname);

                }

            }
        });

        mBuilder.setView(mView).setTitle("Register Filling Station");

        clsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        return mBuilder.create();
    }
}
