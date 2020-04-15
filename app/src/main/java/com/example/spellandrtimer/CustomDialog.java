package com.example.spellandrtimer;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomDialog extends Dialog {

    Spinner spinner1,spinner2;
    Button btn;
    ArrayList arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    int line1, line2;
    private Context context;
    private CustomDialogListener customDialogListener;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    interface CustomDialogListener{
        void getData(int line1, int line2);
        void onNegativeClicked();
    }

    public void setDialogListner(CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog);

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        btn = findViewById(R.id.btn);

        arrayList.add("탑");
        arrayList.add("정글");
        arrayList.add("미드");
        arrayList.add("원딜");
        arrayList.add("서폿");

        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinner1.setAdapter(arrayAdapter);
        spinner2.setAdapter(arrayAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                line1 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                line2 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(line1 != line2) {
                    customDialogListener.getData(line1, line2);
                    dismiss();
                }
                else {
                    Toast.makeText(getContext(), "라인을 정확히 선택해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
