package com.kaider.login.wxlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.kaider.login.R;
import com.kaider.login.wxapi.WXEntryActivity;

public class BaseLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void requestEntryActivity() {
        Intent intent = new Intent(BaseLoginActivity.this, WXEntryActivity.class);
        startActivity(intent);
//        finish();
    }

}
