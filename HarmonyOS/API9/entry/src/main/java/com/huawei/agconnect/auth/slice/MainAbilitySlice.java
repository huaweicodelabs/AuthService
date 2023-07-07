package com.huawei.agconnect.auth.slice;

import com.huawei.agconnect.auth.ResourceTable;
import com.huawei.hmf.tasks.*;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.PhoneAuthProvider;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.Locale;

public class MainAbilitySlice extends AbilitySlice {
    private final static HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x0001,"authDemo");

    private TextField countryCode;
    private TextField phoneNumeber;
    private TextField verfiCode;
    private Text anonymousResult;
    private Text result;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button anonymousBtn = (Button) findComponentById(ResourceTable.Id_anonymous_button);
        anonymousBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                anonymousLogin();
            }
        });

        Button getVerfiCode = (Button) findComponentById(ResourceTable.Id_verification_code_obtain);
        getVerfiCode.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                 sendVerifCode();
            }
        });

        Button linkPhone = (Button) findComponentById(ResourceTable.Id_link_phone);
        linkPhone.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                linkPhone();
            }
        });

        countryCode = (TextField) findComponentById(ResourceTable.Id_country_code);
        phoneNumeber = (TextField) findComponentById(ResourceTable.Id_phone_number);
        verfiCode = (TextField) findComponentById(ResourceTable.Id_verification_code);

        anonymousResult = (Text) findComponentById(ResourceTable.Id_anonymous_result);
        result = (Text) findComponentById(ResourceTable.Id_result_text);

    }


    private void anonymousLogin() {
        AGConnectAuth.getInstance().signInAnonymously().addOnSuccessListener(new OnSuccessListener<SignInResult>() {
            @Override
            public void onSuccess(SignInResult signInResult) {
                AGConnectUser user = signInResult.getUser();
                String uid = user.getUid();
                anonymousResult.setText("Uid: " + uid);
                log("UidValue: " + uid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                anonymousResult.setText("Anonymous SignIn failed");
                log("UidValue: " + e.getMessage());
            }
        });
    }

    private void sendVerifCode() {
        VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
                .sendInterval(30)
                .locale(Locale.SIMPLIFIED_CHINESE)
                .build();
        String countCode = countryCode.getText().toString().trim();
        String phoneNumber = phoneNumeber.getText().toString().trim();
        if (notEmptyString(countCode) && notEmptyString(phoneNumber)) {
            HarmonyTask<VerifyCodeResult> task = AGConnectAuth.getInstance().requestVerifyCode(countCode, phoneNumber, settings);
            task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
                @Override
                public void onSuccess(VerifyCodeResult verifyCodeResult) {
                    log("verify code has been sent.");
                }
            }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    log("requestVerifyCode fail:" + e);
                }
            });
        } else {
            log("Please enter the phone number and country code");
        }
    }

    private void linkPhone() {
        log("Login start");
        String phoneNumber = phoneNumeber.getText().toString().trim();
        String countCode = countryCode.getText().toString().trim();
        String verifyCode = verfiCode.getText().toString().trim();
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
                log("phone number: " + phoneNumber + ", uid: " + uid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                result.setText("Login error, please try again, error:" + e.getMessage());
                log("Login error, please try again, error:" + e.getMessage());
            }
        });
    }

    private boolean notEmptyString(String string) {
        return string != null && !string.isEmpty();
    }

    private void log(String format, Object... args) {
        HiLog.debug(LABEL,format, args);
    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
