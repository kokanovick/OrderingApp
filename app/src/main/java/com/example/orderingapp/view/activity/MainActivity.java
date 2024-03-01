package com.example.orderingapp.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.orderingapp.R;
import com.example.orderingapp.databinding.ActivityMainBinding;
import com.example.orderingapp.util.Constants;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.orderingapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUi();
        checkIsDebugEnabled();
    }

    private void initUi() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setPrompt(getString(R.string.scan_qr));
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            passTableValue(intentResult);
        }
    }

    private void passTableValue(IntentResult intentResult) {
        String tableNumber = getTableNumber(intentResult);
        if (tableNumber == null) return;
        startNewActivity(tableNumber);
    }

    private void startNewActivity(String tableNumber) {
        Intent itemListIntent = new Intent(MainActivity.this, ItemListActivity.class);
        itemListIntent.putExtra(Constants.SCANNED_TABLE_NUMBER, tableNumber);
        startActivity(itemListIntent);
    }

    @Nullable
    private String getTableNumber(IntentResult intentResult) {
        String tableNumber = intentResult.getContents();
        if (checkForWrongInput(tableNumber)) return null;
        return tableNumber;
    }

    private boolean checkForWrongInput(String tableNumber) {
        isScanCanceled(tableNumber);
        return isQRTableNumber(tableNumber);
    }

    private boolean isQRTableNumber(String tableNumber) {
        if (!TextUtils.isDigitsOnly(tableNumber)) {
            Toast.makeText(getApplicationContext(), R.string.wrong_qr_code, Toast.LENGTH_SHORT).show();
            initUi();
            return true;
        }
        return false;
    }

    private void isScanCanceled(String tableNumber) {
        if (tableNumber == null) {
            Toast.makeText(getApplicationContext(), R.string.scan_canceled, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIsDebugEnabled() {
        if (Constants.IS_DEBUG_ENABLED) {
            startNewActivity(Constants.DEBUG_TABLE_NUMBER);
        }
    }
}
