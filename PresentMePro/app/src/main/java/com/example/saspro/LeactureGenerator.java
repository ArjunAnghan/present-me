package com.example.saspro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class LeactureGenerator extends AppCompatActivity implements MultiSpinner.MultiSpinnerListener {

    EditText cXYZ06 = null;
    Spinner TDLl0w = null;
    ArrayAdapter<String> adapter = null;
    String slotNumber = null;
    String[] groups;
    String selectedGroups;
    MultiSpinner multiSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leacture_generator);

        Intent i = getIntent();
        groups = i.getStringExtra("groups").split(",");
        groups = Arrays.copyOfRange(groups, 1, groups.length);

        multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
        multiSpinner.setItems(Arrays.asList(groups), "All Slots", this);

        TDLl0w = findViewById(R.id.TDLl0w);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        TDLl0w.setAdapter(adapter);

        cXYZ06 = findViewById(R.id.cXYZ06);

        TDLl0w.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                slotNumber = (String) parent.getItemAtPosition(position);
                Toast.makeText(LeactureGenerator.this, slotNumber, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(LeactureGenerator.this, "Please Select Something!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendData(View view) {

        SharedPreferences prefs = getSharedPreferences(AttributeData.FILENAME, MODE_PRIVATE);

        // Professor user id
        String iTYz2f = prefs.getString("iTYz2f", null);

        // This is for slot and subject
        String TDLl0w = slotNumber;
        String CXYZ06 = cXYZ06.getText().toString();

        // This will send data to server
        BackgroundTask2 backgroundTask2 = new BackgroundTask2(LeactureGenerator.this);
        backgroundTask2.execute(iTYz2f, TDLl0w, CXYZ06, selectedGroups);
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
        ArrayList<String> tempGroups = new ArrayList<>();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                tempGroups.add(groups[i]);
            }
        }

        selectedGroups = android.text.TextUtils.join(",", tempGroups);
    }
}
