package com.github.willfordzhan.depthfieldcalculator;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private double distance = 0.0; // the distance of the subject to the camera lens
    private double focalLength = 0; // the focalLength of the lens
    private double aperture = 0; // the aperture of the lens
    private double diffuseRadius = 0.01; // mm
    private double doF = 0; // the result : depth of field

    private String[] apertureValueItems; // the array extract from the array.xml
    private double[] apertureValues; // the doubles converted from the apertureValueItems

    private ArrayAdapter<String> apertureValueAdapter;

    // get the references of the TextViews
    private TextView focalLengthTextView;
    private TextView DoFTextView;
    // the diffuse radius of the light circle on the CMOS
    // of Lumix lx100, the diffuse Radius on the nexus 6p's screen is 0.05mm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // inflate the GUI

        // get instances
        focalLengthTextView = (TextView) findViewById(R.id.focalLengthTextView);
        DoFTextView = (TextView) findViewById(R.id.DoFLabelTextView);

        // set focalLengthSeekbar's listener
        SeekBar focalLengthSeekbar = (SeekBar) findViewById(R.id.focalLengthSeekbar);
        focalLengthSeekbar.setOnSeekBarChangeListener(seekBarListener);

        // set apertureSpinner's listener
        Spinner apertureSpinner = (Spinner) findViewById(R.id.apertureSpinner);
        apertureSpinner.setOnItemSelectedListener(itemSelectedListener);
        // match the spinner's arrayList to the values in array.xml
        apertureValueItems = getResources().getStringArray(R.array.LumixApertureValues);
        // bind the data in apertureValueItems to the Adapter, the 2nd param is the layout of the folded spinner (default)
        apertureValueAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, apertureValueItems);
        // set the unfold spinner items' layout (default)
        apertureValueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < apertureValueItems.length; i++) {
            apertureValues[i] = Double.parseDouble(apertureValueItems[i]);
        }

        // set distanceEditText's listener
        EditText distanceEditText = (EditText) findViewById(R.id.distanceEditText);
        distanceEditText.addTextChangedListener(distanceTextChangedListener);
    }

    private final SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            double equivalentFocalLength = progress + 24; // set the minimum value of the seekBar
            String focalLengthString = String.valueOf(equivalentFocalLength) + "mm"; // the seekBar returns progress as String
            focalLengthTextView.setText(focalLengthString);
            // the converting factor of Lumix lx100's CMOS to full frame is 2.2935
            // to divide the converting factor to get the physical focal length
            focalLength = equivalentFocalLength / 2.2935;
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    private final TextWatcher distanceTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                // turn meter into millimeter
                distance = Double.parseDouble(s.toString()) * 1000.0;
            }catch (Exception e){

            }
            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private final AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            aperture = apertureValues[position];
            calculate();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void calculate(){
        if (aperture == 0 || distance == 0){
            return;
        }
        else{
            doF = (2 * focalLength * focalLength * aperture * 0.01 * distance * distance)
                    /(focalLength * focalLength * focalLength * focalLength - aperture * 0.0001 * distance * distance);
            double doFInMeter = doF / 1000;
            DoFTextView.setText(doFInMeter + "m");
        }
    }
}
