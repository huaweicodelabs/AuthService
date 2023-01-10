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
import android.widget.TextView;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button huaweiidButton;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result_text);
        huaweiidButton = findViewById(R.id.huaweiid_button);
        huaweiidButton.setOnClickListener(view -> AGConnectAuth.getInstance().signIn(MainActivity.this, AGConnectAuthCredential.HMS_Provider)
                .addOnSuccessListener(signInResult -> {
                    String uid = signInResult.getUser().getUid();
                    Log.i(TAG, "Uid: " + uid);
                    result.setText(uid);
                }).addOnFailureListener(e -> Log.e(TAG, "signin failed, error: " + e.getMessage())));

        AGConnectAuth.getInstance().signOut();
    }
}