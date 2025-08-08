package com.example.barcodescanner;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanItemFragment extends Fragment {
    ImageView imgScan;
    EditText edtBarcode;


    public ScanItemFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ScanItemFragment.
     */
    public static ScanItemFragment newInstance() {
        ScanItemFragment fragment = new ScanItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan_item, container, false);
        imgScan = view.findViewById(R.id.imageBarcodeSI);
        edtBarcode = view.findViewById(R.id.editTextBarcodeSI);
        imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ScanItemFragment.this.getActivity());
//                intentIntegrator.setPrompt("For flash use volume up button");
//                intentIntegrator.setBeepEnabled(true);
//                intentIntegrator.setOrientationLocked(true);
//                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.forSupportFragment(ScanItemFragment.this)
                        .setCaptureActivity(Capture.class)
                        .setPrompt("For flash use volume up button")
                        .setOrientationLocked(true)
                        .setBeepEnabled(true)
                        .initiateScan();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult.getContents() != null){
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle("Result");
//            builder.setMessage(intentResult.getContents());
            Log.d("Scanner: ", intentResult.getContents());
            edtBarcode.setText(intentResult.getContents());

        } else {
            Log.d("Scanner: ", "Test");
            Toast.makeText(getContext(), "Scan is incomplete", Toast.LENGTH_SHORT).show();
        }
    }
}