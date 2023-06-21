package com.example.reader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.sql.Connection;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button scanner;
    Button confirm;
    TextView isValid;
    TextView result;
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanner = findViewById(R.id.scanner);
        result = findViewById(R.id.result);
        isValid = findViewById(R.id.isValid);
        confirm = findViewById(R.id.confirmButton);
        confirm.setVisibility(View.INVISIBLE);
        confirm.setEnabled(false);


        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.setVisibility(View.INVISIBLE);
                confirm.setEnabled(false);
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("Scan QR Code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
           String contents = intentResult.getContents();
            try {
               contents = CryptoProvider.decode(CryptoProvider.readPrivateKey(),contents);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (contents != null) {
                result.setText(contents);
                String text = result.getText().toString();
                String[] lines = text.split("\r?\n|\r");
                String ticketId = lines[6];
                String flightId = lines[7];
                ticketId = ticketId.replace("ticket_id: ", "");
                flightId = flightId.replace("flight_id: ", "");
                isValid.setText(DatabaseConnection.read(DatabaseConnection.connection(), ticketId, flightId));
                if (isValid.getText().toString().equals("VALID")) {
                    isValid.setTextColor(Color.parseColor("#199e60"));
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setEnabled(true);
                    String finalTicketId = ticketId;
                    String finalFlightId = flightId;
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean flag = DatabaseConnection.update(DatabaseConnection.connection(),
                                    finalTicketId, finalFlightId);
                            if (flag) {
                                Toast.makeText(MainActivity.this, "Status changed",
                                        Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(MainActivity.this, "Unknown error",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    isValid.setTextColor(Color.parseColor("#e31740"));
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}