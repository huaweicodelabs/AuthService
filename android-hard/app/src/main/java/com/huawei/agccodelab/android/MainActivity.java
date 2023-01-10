/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.huawei.agccodelab.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.PhoneAuthProvider;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskExecutors;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private EditText countryCode;
    private EditText phoneNumeber;
    private EditText verfiCode;
    private Button getVerfiCode;
    private TextView anonymousResult;
    private Button phoneLogin;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countryCode = findViewById(R.id.country_code);
        phoneNumeber = findViewById(R.id.phone_number);
        verfiCode = findViewById(R.id.verification_code);
        getVerfiCode = findViewById(R.id.verification_code_obtain);
        getVerfiCode.setOnClickListener(this);
        phoneLogin = findViewById(R.id.phone_login);
        phoneLogin.setOnClickListener(this);
        result = findViewById(R.id.result_text);

        AGConnectAuth.getInstance().signOut();
    }

    public void sendVerifCode() {
        VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
                .sendInterval(30) //shortest send interval ，30-120s
                .locale(Locale.SIMPLIFIED_CHINESE) //optional,must contain country and language eg:zh_CN
                .build();
        String countCode = countryCode.getText().toString().trim();
        String phoneNumber = phoneNumeber.getText().toString().trim();
        if (notEmptyString(countCode) && notEmptyString(phoneNumber)) {
            Task<VerifyCodeResult> task = PhoneAuthProvider.requestVerifyCode(countCode, phoneNumber, settings);
            task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
                @Override
                public void onSuccess(VerifyCodeResult verifyCodeResult) {
                    Toast.makeText(MainActivity.this, "verify code has been sent.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(MainActivity.this, "Send verify code failed.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "requestVerifyCode fail:" + e);
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Please enter the phone number and country code", Toast.LENGTH_SHORT).show();
        }
    }

    public void phoneLogin() {
        Log.i(TAG, "Login start");
        String phoneNumber = phoneNumeber.getText().toString().trim();
        String countCode = countryCode.getText().toString().trim();
        String verifyCode = verfiCode.getText().toString().trim();
        //登录逻辑
        AGConnectAuthCredential credential = PhoneAuthProvider.credentialWithVerifyCode(
                countCode,
                phoneNumber,
                null, // password, can be null
                verifyCode);
        AGConnectAuth.getInstance().signIn(credential).addOnSuccessListener(signInResult -> {
            String phoneNumber1 = signInResult.getUser().getPhone();
            String uid = signInResult.getUser().getUid();
            result.setText("phone number: " + phoneNumber1 + ", uid: " + uid);
            result.setVisibility(View.VISIBLE);
            Log.i(TAG, "phone number: " + phoneNumber1 + ", uid: " + uid);
        }).addOnFailureListener(e -> {
            result.setText("Login error, please try again, error:" + e.getMessage());
            result.setVisibility(View.VISIBLE);
            Log.e(TAG, "Login error, please try again, error:" + e.getMessage());
        });
    }

    private boolean notEmptyString(String string) {
        return string != null && !string.isEmpty() && !string.equals("");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verification_code_obtain:
                sendVerifCode();
                break;

            case R.id.phone_login:
                phoneLogin();
                break;

            default:
                break;
        }
    }
}