package com.github.willfordzhan.depthfieldcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private double distance = 0.0; // the distance of the subject to the camera lens
    private double focalLength = 0; // the focalLength of the lens
    private double aperture = 0; // the aperture of the lens
    private double diffuseRadius = 0.01; // mm
    private double DoF = 0; // the result : depth of field
    private TextView focalLengthTextView;
    private TextView distanceTextView;
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
        //apertureSpinner.setOnItemClickListener(itemSelectedListener);

        // set distanceEditText's listener
        EditText distanceEditText = (EditText) findViewById(R.id.distanceEditText);
        distanceEditText.addTextChangedListener(distanceTextChangedListener);
    }

    private final SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            focalLength = progress + 24; // set the minimum value of the seekBar
            String focalLengthString = String.valueOf(progress) + "mm";
            focalLengthTextView.setText(focalLengthString);
            //calculate();
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
            distance = Double.parseDouble(s.toString());
            distanceTextView.setText(s + "m");
            //calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
