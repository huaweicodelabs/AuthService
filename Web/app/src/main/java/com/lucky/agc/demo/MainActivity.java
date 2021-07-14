package com.lucky.agc.demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.HWGameAuthProvider;
import com.huawei.agconnect.auth.HwIdAuthProvider;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.PlayersClient;
import com.huawei.hms.jos.games.player.Player;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final int HUAWEIID_SIGNIN = 8000;
    private static final int HUAWEIGAME_SIGNIN = 7000;

    private Button anonymousBtn;
    private Button hwidBtn;
    private Button gameBtn;
    private TextView resultText;

    private String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anonymousBtn = findViewById(R.id.anonymous_button);
        hwidBtn = findViewById(R.id.hwid_button);
        gameBtn = findViewById(R.id.game_button);
        resultText = findViewById(R.id.result_text);

        anonymousBtn.setOnClickListener(view -> AGConnectAuth.getInstance().signInAnonymously()
                .addOnSuccessListener(signInResult -> {
                    //获取重定向url
                    String url="https://accounts.google.com/o/oauth2/v2/auth?client_id=342746900306-vmpmbn8dgulp7eun1i8haiu86kocn8t6.apps.googleusercontent.com&redirect_uri=https://www.baidu.com&response_type=code&scope=profile+email+openid";
                    try {
                        HttpsURLConnection connection = (HttpsURLConnection)new URL(url).openConnection();
                        connection.setInstanceFollowRedirects(false);
                        connection.setConnectTimeout(5000);
                        String redirectURL = connection.getHeaderField("Location");
                        Log.i("redirectURL", redirectURL);
                    } catch (Exception e) {
                        Log.i("redirectURL", "exception" + e.getMessage());
                    }

                    //获取值
//                    String url = "https://oauth2.example.com/code?code=4%2FzAFKKtVkMm8h_G14uTroEL-xpTq99urf7Clehh7o-aRa8x1il8y7J_WwnoVr3TZSK89DnBWCryJjgFOjbqw1Ub8&scope=email+profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+openid&authuser=0&prompt=none";
//                    Map<String, String> params = new HashMap<>();
//                    params = MainActivity.urlSplit(url);
//                    Log.i("value111", params.get("code"));

                })
        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Anonymous SignIn Failed", Toast.LENGTH_LONG).show()));

        hwidBtn.setOnClickListener(view -> {
            HuaweiIdAuthParams authParams = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setIdToken().createParams();
            HuaweiIdAuthService service = HuaweiIdAuthManager.getService(MainActivity.this, authParams);
            startActivityForResult(service.getSignInIntent(), HUAWEIID_SIGNIN);
        });

        gameBtn.setOnClickListener(view -> {
            HuaweiIdAuthParams authParams = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM_GAME).createParams();
            HuaweiIdAuthService service = HuaweiIdAuthManager.getService(this, authParams);
            startActivityForResult(service.getSignInIntent(), HUAWEIGAME_SIGNIN);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HUAWEIID_SIGNIN) {
            Task<AuthHuaweiId> authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data);
            if (authHuaweiIdTask.isSuccessful()){
                AuthHuaweiId huaweiAccount = authHuaweiIdTask.getResult();
                String accessToken = huaweiAccount.getAccessToken();
                AGConnectAuthCredential credential = HwIdAuthProvider.credentialWithToken(accessToken);
                AGConnectAuth.getInstance().getCurrentUser().link(credential)
                        .addOnSuccessListener(signInResult -> {
                            AGConnectUser user = signInResult.getUser();
                            Toast.makeText(MainActivity.this, user.getUid(), Toast.LENGTH_LONG).show();
                            if (user.getUid().equals(uId)) {
                                resultText.setText(R.string.success_sign);
                                resultText.setTextColor(0xff00ff00);
                                resultText.setVisibility(View.VISIBLE);
                                gameBtn.setVisibility(View.VISIBLE);
                            }else{
                                resultText.setText(R.string.fail_sign);
                                resultText.setTextColor(0xffff0000);
                                resultText.setVisibility(View.VISIBLE);
                            }
                        }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Link failed", Toast.LENGTH_LONG).show());
            }else{
                Toast.makeText(MainActivity.this, "HwID signIn failed" + (authHuaweiIdTask.getException()).getMessage(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == HUAWEIGAME_SIGNIN) {
            if (data == null) {
                Toast.makeText(MainActivity.this, "Game Signin Intent is null", Toast.LENGTH_LONG).show();
                return;
            }
            Task<AuthHuaweiId> task = HuaweiIdAuthManager.parseAuthResultFromIntent(data);

            task.addOnSuccessListener(signInHuaweiId -> {
                PlayersClient client = Games.getPlayersClient(MainActivity.this, signInHuaweiId);
                Task<Player> playerTask = client.getCurrentPlayer();

                playerTask.addOnSuccessListener(player -> {
                    String imageUrl = player.hasHiResImage() ? player.getHiResImageUri().toString()
                            : player.getIconImageUri().toString();
                    AGConnectAuthCredential credential =
                            new HWGameAuthProvider.Builder().setPlayerSign(player.getPlayerSign())
                                    .setPlayerId(player.getPlayerId())
                                    .setDisplayName(player.getDisplayName())
                                    .setImageUrl(imageUrl)
                                    .setPlayerLevel(player.getLevel())
                                    .setSignTs(player.getSignTs())
                                    .build();

                    AGConnectAuth.getInstance().getCurrentUser().link(credential).addOnSuccessListener(signInResult -> {
                        AGConnectUser user = signInResult.getUser();
                        Toast.makeText(MainActivity.this, user.getUid(), Toast.LENGTH_LONG).show();
                    }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Link failed" + e.getMessage(), Toast.LENGTH_LONG).show());

                }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Huawei Game failed" + e.getMessage(), Toast.LENGTH_LONG).show());

            }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "HwID signIn failed" + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     * @author lzf
     */
    private static String TruncateUrlPage(String strURL){
        String strAllParam=null;
        String[] arrSplit=null;
        strURL=strURL.trim().toLowerCase();
        arrSplit=strURL.split("[?]");
        if(strURL.length()>1){
            if(arrSplit.length>1){
                for (int i=1;i<arrSplit.length;i++){
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     * @author lzf
     */
    public static Map<String, String> urlSplit(String URL){
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit=null;
        String strUrlParam=TruncateUrlPage(URL);
        if(strUrlParam==null){
            return mapRequest;
        }
        arrSplit=strUrlParam.split("[&]");
        for(String strSplit:arrSplit){
            String[] arrSplitEqual=null;
            arrSplitEqual= strSplit.split("[=]");
            //解析出键值
            if(arrSplitEqual.length>1){
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            }else{
                if(arrSplitEqual[0]!=""){
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

}



