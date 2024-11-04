package com.instantlabs.instant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
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
public class RegisterActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        TextView textView = (TextView) findViewById(R.id.register_text);
        final EditText editText = (EditText) findViewById(R.id.username_input);
        final EditText editText2 = (EditText) findViewById(R.id.password_input);
        final EditText editText3 = (EditText) findViewById(R.id.email_input);
        final EditText editText4 = (EditText) findViewById(R.id.pin_input);
        ((Button) findViewById(R.id.register_button)).setOnClickListener(new View.OnClickListener() { // from class: com.instantlabs.instant.RegisterActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String obj = editText.getText().toString();
                String obj2 = editText2.getText().toString();
                String obj3 = editText3.getText().toString();
                String obj4 = editText4.getText().toString();
                if (!obj.isEmpty() && !obj2.isEmpty() && !obj3.isEmpty() && !obj4.isEmpty()) {
                    RegisterActivity.this.register(obj, obj3, obj2, obj4);
                } else {
                    Toast.makeText(RegisterActivity.this, "Please Fill The Form!", 0).show();
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.instantlabs.instant.RegisterActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, (Class<?>) LoginActivity.class));
                RegisterActivity.this.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void register(String str, String str2, String str3, String str4) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", str);
        jsonObject.addProperty(NotificationCompat.CATEGORY_EMAIL, str2);
        jsonObject.addProperty("password", str3);
        jsonObject.addProperty("pin", str4);
        new OkHttpClient().newCall(new Request.Builder().url("http://mywalletv1.instant.htb/api/v1/register").post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())).build()).enqueue(new Callback() { // from class: com.instantlabs.instant.RegisterActivity.3
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // okhttp3.Callback
            public void onFailure(Call call, final IOException iOException) {
                RegisterActivity.this.runOnUiThread(new Runnable() { // from class: com.instantlabs.instant.RegisterActivity.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "Register Failed: " + iOException.getMessage(), 0).show();
                        System.out.println("Registration Failed ERROR : " + iOException.getMessage());
                    }
                });
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JsonParser.parseString(response.body().string()).getAsJsonObject().get("Description").getAsString();
                        Toast.makeText(RegisterActivity.this, "Your Account Has Been Registered!", 1).show();
                        RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, (Class<?>) LoginActivity.class));
                        RegisterActivity.this.finish();
                        return;
                    } catch (JsonSyntaxException unused) {
                        RegisterActivity.this.runOnUiThread(new Runnable() { // from class: com.instantlabs.instant.RegisterActivity.3.2
                            @Override // java.lang.Runnable
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "Something Went Wrong Couldn't Register!", 0).show();
                            }
                        });
                        return;
                    }
                }
                RegisterActivity.this.runOnUiThread(new Runnable() { // from class: com.instantlabs.instant.RegisterActivity.3.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "Registration Failed :" + response.message(), 0).show();
                        System.out.println("Registration Failed : " + response.message());
                    }
                });
            }
        });
    }
}
