package com.github.willfordzhan.depthfieldcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private double distance = 0.0; // the distance of the subject to the camera lens
    private double focalLength = 0; // the focalLength of the lens
    private double aperture = 0; // the aperture of the lens
    private double diffuseRadius = 0.01; // mm
    // the diffuse radius of the light circle on the CMOS
    // of Lumix lx100, the diffuse Radius on the nexus 6p's screen is 0.05mm
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
