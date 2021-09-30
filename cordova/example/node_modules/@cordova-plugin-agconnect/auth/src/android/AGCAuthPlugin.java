/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.cordova.agconnect.auth;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.AGConnectUserExtra;
import com.huawei.agconnect.auth.EmailAuthProvider;
import com.huawei.agconnect.auth.EmailUser;
import com.huawei.agconnect.auth.PhoneAuthProvider;
import com.huawei.agconnect.auth.PhoneUser;
import com.huawei.agconnect.auth.ProfileRequest;
import com.huawei.agconnect.auth.SelfBuildProvider;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.agconnect.auth.TokenResult;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import com.huawei.agconnect.core.service.auth.OnTokenListener;
import com.huawei.agconnect.core.service.auth.TokenSnapshot;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskExecutors;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class echoes a string called from JavaScript.
 */
public class AGCAuthPlugin extends CordovaPlugin {
    private CallbackContext tokenStateCallback;

    @Override
    protected void pluginInitialize() {
        if (AGConnectInstance.getInstance() == null) {
            final Context context = this.cordova.getActivity().getApplicationContext();
            AGConnectInstance.initialize(context);
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("signIn".equals(action)) {
            this.signIn(callbackContext, args.getJSONObject(0));
            return true;
        } else if ("signInAnonymously".equals(action)) {
            this.signInAnonymously(callbackContext);
            return true;
        } else if ("deleteUser".equals(action)) {
            this.deleteUser(callbackContext);
            return true;
        } else if ("signOut".equals(action)) {
            this.signOut(callbackContext);
            return true;
        } else if ("getCurrentUser".equals(action)) {
            this.getCurrentUser(callbackContext);
            return true;
        } else if ("createEmailUser".equals(action)) {
            this.createEmailUser(callbackContext, args.getJSONObject(0));
            return true;
        } else if ("createPhoneUser".equals(action)) {
            this.createPhoneUser(callbackContext, args.getJSONObject(0));
            return true;
        } else if ("resetPasswordWithEmail".equals(action)) {
            this.resetPasswordWithEmail(callbackContext,
                    args.getString(0), args.getString(1), args.getString(2));
            return true;
        } else if ("resetPasswordWithPhone".equals(action)) {
            this.resetPasswordWithPhone(callbackContext, args.getString(0),
                    args.getString(1), args.getString(2), args.getString(3));
            return true;
        } else if ("link".equals(action)) {
            this.link(callbackContext, args.getJSONObject(0));
            return true;
        } else if ("unlink".equals(action)) {
            this.unlink(callbackContext, args.getInt(0));
            return true;
        } else if ("updateProfile".equals(action)) {
            this.updateProfile(callbackContext, args.getJSONObject(0));
            return true;
        } else if ("updateEmail".equals(action)) {
            if (args.length() >= 3) {
                this.updateEmail(callbackContext, args.getString(0), args.getString(1), args.getString(2));
            } else {
                this.updateEmail(callbackContext, args.getString(0), args.getString(1), null);
            }
            return true;
        } else if ("updatePhone".equals(action)) {
            if (args.length() >= 4) {
                this.updatePhone(callbackContext, args.getString(0), args.getString(1),
                        args.getString(2), args.getString(3));
            } else {
                this.updatePhone(callbackContext, args.getString(0), args.getString(1),
                        args.getString(2), null);
            }
            return true;
        } else if ("updatePassword".equals(action)) {
            this.updatePassword(callbackContext, args.getString(0), args.getString(1), args.getInt(2));
            return true;
        } else if ("getUserExtra".equals(action)) {
            this.getUserExtra(callbackContext);
            return true;
        } else if ("requestPhoneVerifyCode".equals(action)) {
            this.requestPhoneVerifyCode(callbackContext, args.getString(0), args.getString(1), args.getJSONObject(2));
            return true;
        } else if ("requestEmailVerifyCode".equals(action)) {
            this.requestEmailVerifyCode(callbackContext, args.getString(0), args.getJSONObject(1));
            return true;
        } else if ("addTokenListener".equals(action)) {
            this.addTokenListener(callbackContext);
            return true;
        } else if ("removeTokenListener".equals(action)) {
            this.removeTokenListener(callbackContext);
            return true;
        } else if ("getToken".equals(action)) {
            this.getToken(callbackContext, args.getBoolean(0));
            return true;
        } else {
            return false;
        }
    }

    private void signIn(CallbackContext callbackContext, final JSONObject params) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func signIn");
                    if (params.has("provider")) {
                        int provider = params.optInt("provider");
                        Log.d("AGC_LOG", "signIn provider:" + provider);
                        switch (provider) {
                            case AGConnectAuthCredential.Phone_Provider:
                                signInWithPhoneCredential(callbackContext, params);
                                break;
                            case AGConnectAuthCredential.Email_Provider:
                                signInWithEmailCredential(callbackContext, params);
                                break;
                            case AGConnectAuthCredential.SelfBuild_Provider:
                                signInWithSelfBuildCredential(callbackContext, params);
                                break;
                            default:
                                callbackContext.error("This provider is not support.");
                                break;
                        }
                    } else {
                        callbackContext.error("No provider");
                    }
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void signInWithPhoneCredential(CallbackContext callbackContext, final JSONObject params) {
        String countryCode = null;
        String phoneNumber = null;
        String password = null;
        String verifyCode = null;
        if (params.has("countryCode")) {
            countryCode = params.optString("countryCode");
        }
        if (params.has("phoneNumber")) {
            phoneNumber = params.optString("phoneNumber");
        }
        if (params.has("password")) {
            password = params.optString("password");
        }
        if (params.has("verifyCode")) {
            verifyCode = params.optString("verifyCode");
        }

        AGConnectAuthCredential phoneAuthCredential =
                PhoneAuthProvider.credentialWithVerifyCode(countryCode, phoneNumber, password, verifyCode);

        AGConnectAuth.getInstance().signIn(phoneAuthCredential)
                .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {
                        callbackContext.success();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                callbackContext.error(e.getMessage());
            }
        });
    }

    private void signInWithEmailCredential(CallbackContext callbackContext, final JSONObject params) {
        String email = null;
        String password = null;
        String verifyCode = null;
        if (params.has("email")) {
            email = params.optString("email");
        }
        if (params.has("password")) {
            password = params.optString("password");
        }
        if (params.has("verifyCode")) {
            verifyCode = params.optString("verifyCode");
        }

        AGConnectAuthCredential emailAuthCredential =
                EmailAuthProvider.credentialWithVerifyCode(email, password, verifyCode);

        AGConnectAuth.getInstance().signIn(emailAuthCredential)
                .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {
                        callbackContext.success();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                callbackContext.error(e.getMessage());
            }
        });
    }

    private void signInWithSelfBuildCredential(CallbackContext callbackContext, final JSONObject params) {
        String accessToken = null;
        Boolean autoCreateUser = true;
        if (params.has("accessToken")) {
            accessToken = params.optString("accessToken");
        }

        if (params.has("autoCreateUser")) {
            autoCreateUser = params.optBoolean("autoCreateUser");
        }

        if (accessToken == null) {
            callbackContext.error("No accessToken.");
            return;
        }

        AGConnectAuthCredential selfBuildAuthCredential =
                SelfBuildProvider.credentialWithToken(accessToken, autoCreateUser);

        AGConnectAuth.getInstance().signIn(selfBuildAuthCredential)
                .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {
                        callbackContext.success();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                callbackContext.error(e.getMessage());
            }
        });
    }

    private void signInAnonymously(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func signInAnonymously");
                    AGConnectAuth.getInstance().signInAnonymously()
                            .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                                @Override
                                public void onSuccess(SignInResult signInResult) {
                                    callbackContext.success();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            callbackContext.error(e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void deleteUser(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func deleteUser");
                    AGConnectAuth.getInstance().deleteUser();
                    callbackContext.success();
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void signOut(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func signOut");
                    AGConnectAuth.getInstance().signOut();
                    callbackContext.success();
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void getCurrentUser(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func getCurrentUser");
                    AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
                    if (user == null) {
                        callbackContext.error("User Null");
                    } else {
                        try {
                            JSONObject result = new JSONObject();
                            result.put("isAnonymous", user.isAnonymous());
                            result.put("uid", user.getUid());
                            result.put("displayName", user.getDisplayName());
                            result.put("photoUrl", user.getPhotoUrl());
                            result.put("email", user.getEmail());
                            result.put("phone", user.getPhone());
                            result.put("providerId", user.getProviderId());
                            result.put("emailVerified", user.getEmailVerified());
                            result.put("passwordSet", user.getPasswordSetted());

                            List<Map<String, String>> mapList = user.getProviderInfo();
                            if (mapList != null && mapList.size() > 0) {
                                JSONArray array = new JSONArray();
                                for (int i = 0; i < mapList.size(); i++) {
                                    array.put(mapList.get(i));
                                }
                                result.put("providerInfo", array);
                            } else {
                                result.put("providerInfo", null);
                            }

                            callbackContext.success(result);
                        } catch (Exception ex) {
                            callbackContext.error(ex.getMessage());
                        }
                    }
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void createEmailUser(CallbackContext callbackContext, final JSONObject params) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func createEmailUser");

                    String email = null;
                    String password = null;
                    String verifyCode = null;
                    if (params.has("email")) {
                        email = params.optString("email");
                    }
                    if (params.has("password")) {
                        password = params.optString("password");
                    }
                    if (params.has("verifyCode")) {
                        verifyCode = params.optString("verifyCode");
                    }

                    EmailUser emailUser = new EmailUser.Builder()
                            .setEmail(email)
                            .setPassword(password)
                            .setVerifyCode(verifyCode)
                            .build();
                    AGConnectAuth.getInstance().createUser(emailUser)
                            .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                                @Override
                                public void onSuccess(SignInResult signInResult) {
                                    callbackContext.success();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            callbackContext.error(e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void createPhoneUser(CallbackContext callbackContext, final JSONObject params) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func createPhoneUser");

                    String countryCode = null;
                    String phoneNumber = null;
                    String password = null;
                    String verifyCode = null;
                    if (params.has("countryCode")) {
                        countryCode = params.optString("countryCode");
                    }
                    if (params.has("phoneNumber")) {
                        phoneNumber = params.optString("phoneNumber");
                    }
                    if (params.has("password")) {
                        password = params.optString("password");
                    }
                    if (params.has("verifyCode")) {
                        verifyCode = params.optString("verifyCode");
                    }

                    PhoneUser phoneUser = new PhoneUser.Builder()
                            .setCountryCode(countryCode)
                            .setPhoneNumber(phoneNumber)
                            .setPassword(password)
                            .setVerifyCode(verifyCode)
                            .build();

                    AGConnectAuth.getInstance().createUser(phoneUser)
                            .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                                @Override
                                public void onSuccess(SignInResult signInResult) {
                                    callbackContext.success();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            callbackContext.error(e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void resetPasswordWithEmail(CallbackContext callbackContext, String email,
                                        String newPassword, String verifyCode) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func resetPasswordWithEmail");

                    AGConnectAuth.getInstance().resetPassword(email, newPassword, verifyCode)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    callbackContext.success();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            callbackContext.error(e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void resetPasswordWithPhone(CallbackContext callbackContext, String countryCode,
                                        String phoneNumber, String newPassword, String verifyCode) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func resetPasswordWithPhone");

                    AGConnectAuth.getInstance().resetPassword(countryCode, phoneNumber, newPassword, verifyCode)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    callbackContext.success();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            callbackContext.error(e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void link(CallbackContext callbackContext, final JSONObject params) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func link");
                    if (params.has("provider")) {
                        int provider = params.optInt("provider");
                        Log.d("AGC_LOG", "link provider:" + provider);
                        switch (provider) {
                            case AGConnectAuthCredential.Phone_Provider:
                                linkWithPhoneCredential(callbackContext, params);
                                break;
                            case AGConnectAuthCredential.Email_Provider:
                                linkWithEmailCredential(callbackContext, params);
                                break;
                            case AGConnectAuthCredential.SelfBuild_Provider:
                                linkWithSelfBuildCredential(callbackContext, params);
                                break;
                            default:
                                callbackContext.error("This provider is not support.");
                                break;
                        }
                    } else {
                        callbackContext.error("No provider");
                    }
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void linkWithPhoneCredential(CallbackContext callbackContext, final JSONObject params) {
        AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
        if (user != null) {
            String countryCode = null;
            String phoneNumber = null;
            String password = null;
            String verifyCode = null;
            if (params.has("countryCode")) {
                countryCode = params.optString("countryCode");
            }
            if (params.has("phoneNumber")) {
                phoneNumber = params.optString("phoneNumber");
            }
            if (params.has("password")) {
                password = params.optString("password");
            }
            if (params.has("verifyCode")) {
                verifyCode = params.optString("verifyCode");
            }

            AGConnectAuthCredential phoneAuthCredential =
                    PhoneAuthProvider.credentialWithVerifyCode(countryCode, phoneNumber, password, verifyCode);

            user.link(phoneAuthCredential).addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                @Override
                public void onSuccess(SignInResult signInResult) {
                    callbackContext.success();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    callbackContext.error(e.getMessage());
                }
            });
        } else {
            callbackContext.error("User Null");
        }
    }

    private void linkWithEmailCredential(CallbackContext callbackContext, final JSONObject params) {
        AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = null;
            String password = null;
            String verifyCode = null;
            if (params.has("email")) {
                email = params.optString("email");
            }
            if (params.has("password")) {
                password = params.optString("password");
            }
            if (params.has("verifyCode")) {
                verifyCode = params.optString("verifyCode");
            }

            AGConnectAuthCredential emailAuthCredential =
                    EmailAuthProvider.credentialWithVerifyCode(email, password, verifyCode);

            user.link(emailAuthCredential).addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                @Override
                public void onSuccess(SignInResult signInResult) {
                    callbackContext.success();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    callbackContext.error(e.getMessage());
                }
            });
        } else {
            callbackContext.error("User Null");
        }
    }

    private void linkWithSelfBuildCredential(CallbackContext callbackContext, final JSONObject params) {
        AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
        if (user != null) {
            String accessToken = null;
            if (params.has("accessToken")) {
                accessToken = params.optString("accessToken");
            }

            if (accessToken == null) {
                callbackContext.error("No accessToken.");
                return;
            }

            AGConnectAuthCredential selfBuildAuthCredential = SelfBuildProvider.credentialWithToken(accessToken);

            user.link(selfBuildAuthCredential).addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                @Override
                public void onSuccess(SignInResult signInResult) {
                    callbackContext.success();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    callbackContext.error(e.getMessage());
                }
            });
        } else {
            callbackContext.error("User Null");
        }
    }

    private void unlink(CallbackContext callbackContext, int provider) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func unlink");
                    AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        user.unlink(provider).addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                            @Override
                            public void onSuccess(SignInResult signInResult) {
                                callbackContext.success();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                callbackContext.error(e.getMessage());
                            }
                        });
                    } else {
                        callbackContext.error("User Null");
                    }
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void updateProfile(CallbackContext callbackContext, final JSONObject params) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func updateProfile");

                    AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String displayName = null;
                        String photoUrl = null;
                        if (params.has("displayName")) {
                            displayName = params.optString("displayName");
                        }
                        if (params.has("photoUrl")) {
                            photoUrl = params.optString("photoUrl");
                        }
                        ProfileRequest userProfile = new ProfileRequest.Builder()
                                .setDisplayName(displayName)
                                .setPhotoUrl(photoUrl)
                                .build();
                        user.updateProfile(userProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                callbackContext.success();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                callbackContext.error(e.getMessage());
                            }
                        });
                    } else {
                        callbackContext.error("User Null");
                    }
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void updateEmail(CallbackContext callbackContext, String newEmail, String newVerifyCode, String lang) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func updateEmail");
                    AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        Locale locale = getLocale(lang);
                        user.updateEmail(newEmail, newVerifyCode, locale)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        callbackContext.success();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                callbackContext.error(e.getMessage());
                            }
                        });
                    } else {
                        callbackContext.error("User Null");
                    }
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void updatePhone(CallbackContext callbackContext, String countryCode,
                            String phoneNumber, String newVerifyCode, String lang) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func updatePhone");
                    AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        Locale locale = getLocale(lang);
                        user.updatePhone(countryCode, phoneNumber, newVerifyCode, locale)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        callbackContext.success();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                callbackContext.error(e.getMessage());
                            }
                        });
                    } else {
                        callbackContext.error("User Null");
                    }
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void updatePassword(CallbackContext callbackContext, String newPassword, String verifyCode, int provider) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func updatePassword");
                    AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        user.updatePassword(newPassword, verifyCode, provider)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        callbackContext.success();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                callbackContext.error(e.getMessage());
                            }
                        });
                    } else {
                        callbackContext.error("User Null");
                    }
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void getUserExtra(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func getUserExtra");
                    AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        user.getUserExtra().addOnSuccessListener(new OnSuccessListener<AGConnectUserExtra>() {
                            @Override
                            public void onSuccess(AGConnectUserExtra agConnectUserExtra) {
                                try {
                                    JSONObject result = new JSONObject();
                                    result.put("createTime", agConnectUserExtra.getCreateTime());
                                    result.put("lastSignInTime", agConnectUserExtra.getLastSignInTime());
                                    callbackContext.success(result);
                                } catch (Exception ex) {
                                    callbackContext.error(ex.getMessage());
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                callbackContext.error(e.getMessage());
                            }
                        });
                    } else {
                        callbackContext.error("User Null");
                    }
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void requestPhoneVerifyCode(CallbackContext callbackContext, String countryCode,
                                        String phoneNumber, JSONObject params) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func requestPhoneVerifyCode");

                    VerifyCodeSettings settings = getVerifyCodeSettings(params);
                    if (settings.getAction() != VerifyCodeSettings.ACTION_REGISTER_LOGIN
                            && settings.getAction() != VerifyCodeSettings.ACTION_RESET_PASSWORD) {
                        callbackContext.error("VerifyCodeSettings action error");
                        return;
                    }
                    Task<VerifyCodeResult> task = PhoneAuthProvider.requestVerifyCode(countryCode,
                            phoneNumber, settings);
                    task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
                        @Override
                        public void onSuccess(VerifyCodeResult verifyCodeResult) {
                            try {
                                JSONObject result = new JSONObject();
                                result.put("shortestInterval", verifyCodeResult.getShortestInterval());
                                result.put("validityPeriod", verifyCodeResult.getValidityPeriod());

                                callbackContext.success(result);
                            } catch (Exception e) {
                                callbackContext.error(e.getMessage());
                            }
                        }
                    }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            callbackContext.error(e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void requestEmailVerifyCode(CallbackContext callbackContext, String email, JSONObject params) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func requestEmailVerifyCode");

                    VerifyCodeSettings settings = getVerifyCodeSettings(params);
                    if (settings.getAction() != VerifyCodeSettings.ACTION_REGISTER_LOGIN
                            && settings.getAction() != VerifyCodeSettings.ACTION_RESET_PASSWORD) {
                        callbackContext.error("VerifyCodeSettings action error");
                        return;
                    }
                    Task<VerifyCodeResult> task = EmailAuthProvider.requestVerifyCode(email, settings);
                    task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
                        @Override
                        public void onSuccess(VerifyCodeResult verifyCodeResult) {
                            try {
                                JSONObject result = new JSONObject();
                                result.put("shortestInterval", verifyCodeResult.getShortestInterval());
                                result.put("validityPeriod", verifyCodeResult.getValidityPeriod());

                                callbackContext.success(result);
                            } catch (Exception e) {
                                callbackContext.error(e.getMessage());
                            }
                        }
                    }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            callbackContext.error(e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private VerifyCodeSettings getVerifyCodeSettings(JSONObject params) {
        int action = 1001;
        String lang = null;
        int sendInterval = 0;
        if (params.has("action")) {
            action = params.optInt("action");
        }
        if (params.has("lang")) {
            lang = params.optString("lang");
        }
        if (params.has("sendInterval")) {
            sendInterval = params.optInt("sendInterval");
        }

        Locale locale = getLocale(lang);

        VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                .action(action)
                .locale(locale)
                .sendInterval(sendInterval)
                .build();

        return settings;
    }

    private Locale getLocale(String lang) {
        Locale locale = null;
        if (!TextUtils.isEmpty(lang)) {
            String[] locales = lang.split("_");
            if (locales != null && locales.length == 2) {
                if (!TextUtils.isEmpty(locales[0]) && !TextUtils.isEmpty(locales[1])) {
                    locale = new Locale(locales[0], locales[1]);
                }
            }
        }
        return locale;
    }

    private OnTokenListener listener = new OnTokenListener() {
        @Override
        public void onChanged(TokenSnapshot tokenSnapshot) {
            if (tokenStateCallback != null) {
                JSONObject result = new JSONObject();
                PluginResult pluginResult;
                try {
                    result.put("state", tokenSnapshot.getState().ordinal());
                    result.put("token", tokenSnapshot.getToken());

                    pluginResult = new PluginResult(PluginResult.Status.OK, result);
                    pluginResult.setKeepCallback(true);
                    tokenStateCallback.sendPluginResult(pluginResult);
                } catch (JSONException e) {
                    tokenStateCallback.error(e.getMessage());
                }
            }
        }
    };

    private void addTokenListener(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func addTokenListener");
                    if (tokenStateCallback != null) {
                        AGConnectAuth.getInstance().removeTokenListener(listener);
                    }
                    tokenStateCallback = callbackContext;
                    AGConnectAuth.getInstance().addTokenListener(listener);
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void removeTokenListener(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func removeTokenListener");
                    AGConnectAuth.getInstance().removeTokenListener(listener);
                    tokenStateCallback = null;
                    callbackContext.success();
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void getToken(CallbackContext callbackContext, boolean forceRefresh) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func getToken");
                    AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        user.getToken(forceRefresh).addOnSuccessListener(new OnSuccessListener<TokenResult>() {
                            @Override
                            public void onSuccess(TokenResult tokenResult) {
                                try {
                                    JSONObject result = new JSONObject();
                                    result.put("token", tokenResult.getToken());
                                    result.put("expirePeriod", tokenResult.getExpirePeriod());

                                    callbackContext.success(result);
                                } catch (Exception e) {
                                    callbackContext.error(e.getMessage());
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                callbackContext.error(e.getMessage());
                            }
                        });
                    } else {
                        callbackContext.error("User Null");
                    }
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }
}
