package com.huawei.authcodelab;

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
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.PhoneAuthProvider;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskExecutors;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private Button anonymousBtn;
    private EditText countryCode;
    private EditText phoneNumeber;
    private EditText verfiCode;
    private Button getVerfiCode;
    private TextView anonymousResult;
    private Button linkPhone;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anonymousBtn = findViewById(R.id.anonymous_button);
        anonymousBtn.setOnClickListener(this);
        countryCode = findViewById(R.id.country_code);
        phoneNumeber = findViewById(R.id.phone_number);
        anonymousResult = findViewById(R.id.anonymous_result);
        verfiCode = findViewById(R.id.verification_code);
        getVerfiCode = findViewById(R.id.verification_code_obtain);
        getVerfiCode.setOnClickListener(this);
        linkPhone = findViewById(R.id.link_phone);
        linkPhone.setOnClickListener(this);
        result = findViewById(R.id.result_text);

        AGConnectAuth.getInstance().signOut();
    }



    public void anonymousLogin() {
        AGConnectAuth.getInstance().signInAnonymously().addOnSuccessListener(new OnSuccessListener<SignInResult>() {
            @Override
            public void onSuccess(SignInResult signInResult) {
                AGConnectUser user = signInResult.getUser();
                String uid = user.getUid();
                anonymousResult.setText("Uid: " + uid);
                anonymousResult.setVisibility(View.VISIBLE);
                Log.i(TAG, "UidValue: " + uid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                anonymousResult.setText("Anonymous SignIn failed");
                anonymousResult.setVisibility(View.VISIBLE);
                Log.e(TAG, "UidValue: " + e.getMessage());
            }
        });
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

    public void linkPhone() {
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
        AGConnectAuth.getInstance().getCurrentUser().link(credential).addOnSuccessListener(new OnSuccessListener<SignInResult>() {
            @Override
            public void onSuccess(SignInResult signInResult) {
                String phoneNumber = signInResult.getUser().getPhone();
                String uid = signInResult.getUser().getUid();
                result.setText("phone number: " + phoneNumber + ", uid: " + uid);
                result.setVisibility(View.VISIBLE);
                Log.i(TAG, "phone number: " + phoneNumber + ", uid: " + uid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                result.setText("Login error, please try again, error:" + e.getMessage());
                result.setVisibility(View.VISIBLE);
                Log.e(TAG, "Login error, please try again, error:" + e.getMessage());
            }
        });
    }

    private boolean notEmptyString(String string) {
        return string != null && !string.isEmpty() && !string.equals("");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.anonymous_button:
                anonymousLogin();
                break;

            case R.id.verification_code_obtain:
                sendVerifCode();
                break;

            case R.id.link_phone:
                linkPhone();
                break;

            default:
                break;
        }
    }
}