package com.instantlabs.instant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes.dex */
public class ProfileActivity extends AppCompatActivity {
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String SHARED_PREFS_NAME = "app_prefs";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_profile);
        final TextView textView = (TextView) findViewById(R.id.username_display);
        final TextView textView2 = (TextView) findViewById(R.id.email_display);
        final TextView textView3 = (TextView) findViewById(R.id.wallet_bal_display);
        final TextView textView4 = (TextView) findViewById(R.id.role_display);
        Button button = (Button) findViewById(R.id.make_txn_btn);
        String accessToken = getAccessToken();
        System.out.println(accessToken);
        if (accessToken != null) {
            new OkHttpClient().newCall(new Request.Builder().url("http://mywalletv1.instant.htb/api/v1/view/profile").addHeader("Authorization", accessToken).build()).enqueue(new Callback() { // from class: com.instantlabs.instant.ProfileActivity.1
                static final /* synthetic */ boolean $assertionsDisabled = false;

                @Override // okhttp3.Callback
                public void onFailure(Call call, final IOException iOException) {
                    ProfileActivity.this.runOnUiThread(new Runnable() { // from class: com.instantlabs.instant.ProfileActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(ProfileActivity.this, "Error Occured: " + iOException.getMessage(), 0).show();
                        }
                    });
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            JsonObject asJsonObject = JsonParser.parseString(response.body().string()).getAsJsonObject().getAsJsonObject("Profile");
                            String asString = asJsonObject.get("username").getAsString();
                            String asString2 = asJsonObject.get(NotificationCompat.CATEGORY_EMAIL).getAsString();
                            String asString3 = asJsonObject.get("wallet_balance").getAsString();
                            String asString4 = asJsonObject.get("role").getAsString();
                            textView.setText("Username: " + asString);
                            textView2.setText("Email: " + asString2);
                            textView3.setText("Balance: " + asString3);
                            textView4.setText("Role: " + asString4);
                            return;
                        } catch (JsonSyntaxException unused) {
                            ProfileActivity.this.runOnUiThread(new Runnable() { // from class: com.instantlabs.instant.ProfileActivity.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    Toast.makeText(ProfileActivity.this, "Invalid response format", 0).show();
                                }
                            });
                            return;
                        }
                    }
                    Toast.makeText(ProfileActivity.this, "Something Went Wrong!", 0).show();
                }
            });
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.instantlabs.instant.ProfileActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ProfileActivity.this.startActivity(new Intent(ProfileActivity.this, (Class<?>) TransactionActivity.class));
                ProfileActivity.this.finish();
            }
        });
    }

    private String getAccessToken() {
        return getSharedPreferences(SHARED_PREFS_NAME, 0).getString(ACCESS_TOKEN_KEY, null);
    }
}
