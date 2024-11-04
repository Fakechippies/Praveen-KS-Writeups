package com.instantlabs.instant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes.dex */
public class LoginActivity extends AppCompatActivity {
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String SHARED_PREFS_NAME = "app_prefs";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        final EditText editText = (EditText) findViewById(R.id.username_input);
        final EditText editText2 = (EditText) findViewById(R.id.password_input);
        TextView textView = (TextView) findViewById(R.id.forgot_password_text);
        TextView textView2 = (TextView) findViewById(R.id.register_text);
        ((Button) findViewById(R.id.login_button)).setOnClickListener(new View.OnClickListener() { // from class: com.instantlabs.instant.LoginActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String obj = editText.getText().toString();
                String obj2 = editText2.getText().toString();
                if (!obj.isEmpty() && !obj2.isEmpty()) {
                    LoginActivity.this.login(obj, obj2);
                } else {
                    Toast.makeText(LoginActivity.this, "Input required", 0).show();
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.instantlabs.instant.LoginActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, (Class<?>) ForgotPasswordActivity.class));
                LoginActivity.this.finish();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.instantlabs.instant.LoginActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, (Class<?>) RegisterActivity.class));
                LoginActivity.this.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void login(String str, String str2) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", str);
        jsonObject.addProperty("password", str2);
        new OkHttpClient().newCall(new Request.Builder().url("http://mywalletv1.instant.htb/api/v1/login").post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())).build()).enqueue(new Callback() { // from class: com.instantlabs.instant.LoginActivity.4
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // okhttp3.Callback
            public void onFailure(Call call, final IOException iOException) {
                LoginActivity.this.runOnUiThread(new Runnable() { // from class: com.instantlabs.instant.LoginActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Login Failed: " + iOException.getMessage(), 0).show();
                        System.out.println("Login Failed : " + iOException.getMessage());
                    }
                });
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        LoginActivity.this.storeAccessToken(JsonParser.parseString(response.body().string()).getAsJsonObject().get("Access-Token").getAsString());
                        LoginActivity.this.navigateToProfile();
                        return;
                    } catch (JsonSyntaxException unused) {
                        LoginActivity.this.runOnUiThread(new Runnable() { // from class: com.instantlabs.instant.LoginActivity.4.2
                            @Override // java.lang.Runnable
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Invalid response format", 0).show();
                            }
                        });
                        return;
                    }
                }
                LoginActivity.this.runOnUiThread(new Runnable() { // from class: com.instantlabs.instant.LoginActivity.4.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Incorrect Username/Password", 0).show();
                        System.out.println("Login Failed : " + response.message());
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void storeAccessToken(String str) {
        SharedPreferences.Editor edit = getSharedPreferences(SHARED_PREFS_NAME, 0).edit();
        edit.putString(ACCESS_TOKEN_KEY, str);
        edit.apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void navigateToProfile() {
        startActivity(new Intent(this, (Class<?>) ProfileActivity.class));
        finish();
    }
}
