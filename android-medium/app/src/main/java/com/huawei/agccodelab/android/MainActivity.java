package com.huawei.agccodelab.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.HWGameAuthProvider;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView anonymousResult;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button anonymousBtn = findViewById(R.id.anonymous_button);
        anonymousResult = findViewById(R.id.anonymous_result);
        result = findViewById(R.id.result_text);
        Button huaweiidButton = findViewById(R.id.huaweiid_button);

        anonymousBtn.setOnClickListener(view -> AGConnectAuth.getInstance().signInAnonymously()
                .addOnSuccessListener(signInResult -> {
            AGConnectUser user = signInResult.getUser();
            String uid = user.getUid();
            anonymousResult.setText(uid);
            Log.i(TAG, "UidValue: " + uid);
        }).addOnFailureListener(e -> {
            anonymousResult.setText("Anonymous SignIn failed");
            Log.e(TAG, "UidValue: " + e.getMessage());
        }));

        huaweiidButton.setOnClickListener(view -> AGConnectAuth.getInstance().getCurrentUser().link(MainActivity.this, AGConnectAuthCredential.HMS_Provider)
                .addOnSuccessListener(signInResult -> {
                    String hwUid = signInResult.getUser().getUid();
                    result.setText(hwUid);
                    Log.i(TAG, "UidValue: " + hwUid);
                }).addOnFailureListener(e -> {
                    result.setText("HWID SignIn failed");
                    Log.e(TAG, "UidValue: " + e.getMessage());
                }));

        AGConnectAuth.getInstance().signOut();
    }
}