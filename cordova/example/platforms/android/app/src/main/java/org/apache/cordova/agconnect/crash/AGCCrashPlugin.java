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

package org.apache.cordova.agconnect.crash;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.crash.AGConnectCrash;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This class echoes a string called from JavaScript.
 */
public class AGCCrashPlugin extends CordovaPlugin {
    @Override
    protected void pluginInitialize() {
        if (AGConnectInstance.getInstance() == null) {
            final Context context = this.cordova.getActivity().getApplicationContext();
            AGConnectInstance.initialize(context);
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("log")) {
            this.log(callbackContext, args.getString(0));
            return true;
        } else if (action.equals("testIt")) {
            this.testIt(callbackContext);
            return true;
        } else if (action.equals("enableCrashCollection")) {
            this.enableCrashCollection(callbackContext, args.getBoolean(0));
            return true;
        } else if (action.equals("setUserId")) {
            this.setUserId(callbackContext, args.getString(0));
            return true;
        } else if (action.equals("setCustomKey")) {
            this.setCustomKey(callbackContext, args.getString(0), args.getString(1));
            return true;
        } else if (action.equals("logWithLevel")) {
            this.logWithLevel(callbackContext, args.getInt(0), args.getString(1));
            return true;
        } else if (action.equals("recordException")) {
            this.recordException(callbackContext, args.getString(0), args.getString(1));
            return true;
        } else {
            return false;
        }
    }

    private void testIt(CallbackContext callbackContext) {
        final Context context = this.cordova.getActivity().getApplicationContext();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d("AGC_LOG", "func testIt");
                AGConnectCrash.getInstance().testIt(context);
            }
        });
    }

    private void enableCrashCollection(CallbackContext callbackContext, boolean enable) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func enableCrashCollection");
                    AGConnectCrash.getInstance().enableCrashCollection(enable);
                    callbackContext.success();
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void setUserId(CallbackContext callbackContext, String userId) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func setUserId, userid=" + userId);
                    AGConnectCrash.getInstance().setUserId(userId);
                    callbackContext.success();
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void setCustomKey(CallbackContext callbackContext, String key, String value) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func setCustomKey, key=" + key + ", value=" + value);
                    AGConnectCrash.getInstance().setCustomKey(key, value);
                    callbackContext.success();
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void logWithLevel(CallbackContext callbackContext, int level, String message) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func logWithLevel, level=" + Integer.toString(level) + ", message=" + message);
                    AGConnectCrash.getInstance().log(level + 1, message);
                    callbackContext.success();
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void log(CallbackContext callbackContext, String message) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Log.d("AGC_LOG", "func log, message=" + message);
                    AGConnectCrash.getInstance().log(message);
                    callbackContext.success();
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }

    private void recordException(CallbackContext callbackContext,  String summary, String stack) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    String[] lines = stack.split(System.lineSeparator());
                    StackTraceElement[] stackTraceElements = new StackTraceElement[lines.length - 1];

                    for (int i = 0; i < lines.length - 1; i++) {
                        final String[] lineParts = lines[i + 1].trim().split("\\s+");
                        String parts1 = lineParts[1] != null ? lineParts[1] : "";
                        String parts2 = lineParts[2] != null ? lineParts[2] : "";
                        stackTraceElements[i] = new StackTraceElement("", parts1, parts2, -1);
                    }

                    Exception throwable = new JavaScriptError(summary);
                    throwable.setStackTrace(stackTraceElements);
                    AGConnectCrash.getInstance().recordException(throwable);
                    callbackContext.success();
                } catch (Exception e) {
                    callbackContext.error(e.getMessage());
                }
            }
        });
    }
}
