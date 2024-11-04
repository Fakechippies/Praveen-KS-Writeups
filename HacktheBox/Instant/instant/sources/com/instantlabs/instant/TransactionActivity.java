package com.instantlabs.instant;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class TransactionActivity extends AppCompatActivity {
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String SHARED_PREFS_NAME = "app_prefs";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_transaction);
        final EditText editText = (EditText) findViewById(R.id.wallet_id_input);
        final EditText editText2 = (EditText) findViewById(R.id.amount_input);
        final EditText editText3 = (EditText) findViewById(R.id.note_input);
        final EditText editText4 = (EditText) findViewById(R.id.pin_input);
        Button button = (Button) findViewById(R.id.send_btn);
        final String accessToken = getAccessToken();
        System.out.println(accessToken);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.instantlabs.instant.TransactionActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String obj = editText.getText().toString();
                String obj2 = editText2.getText().toString();
                String obj3 = editText3.getText().toString();
                String obj4 = editText4.getText().toString();
                if (!obj.isEmpty() && !obj2.isEmpty() && !obj3.isEmpty()) {
                    TransactionActivity.this.sendFunds(obj, obj2, obj3, accessToken, obj4);
                } else {
                    Toast.makeText(TransactionActivity.this, "Input Required", 0).show();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendFunds(String str, String str2, String str3, String str4, String str5) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("receiver", str);
        jsonObject.addProperty("amount", str2);
        jsonObject.addProperty("note", str3);
        new OkHttpClient().newCall(new Request.Builder().url("http://mywalletv1.instant.htb/api/v1/initiate/transaction").addHeader("Authorization", str4).post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())).build()).enqueue(new AnonymousClass2(str5, str4));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.instantlabs.instant.TransactionActivity$2, reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass2 implements Callback {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final /* synthetic */ String val$access_token;
        final /* synthetic */ String val$pin;

        AnonymousClass2(String str, String str2) {
            this.val$pin = str;
            this.val$access_token = str2;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, final IOException iOException) {
            TransactionActivity.this.runOnUiThread(new Runnable() { // from class: com.instantlabs.instant.TransactionActivity.2.1
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(TransactionActivity.this, "Transaction Failed", 0).show();
                    System.out.println("Transaction Failed : " + iOException.getMessage());
                }
            });
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                try {
                    if (JsonParser.parseString(response.body().string()).getAsJsonObject().get("Description").getAsString().equals("Transaction Pending! Waiting For Pin!")) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("pin", this.val$pin);
                        new OkHttpClient().newCall(new Request.Builder().url("http://mywalletv1.instant.htb/api/v1/confirm/pin").header("Authorization", this.val$access_token).post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())).build()).enqueue(new Callback() { // from class: com.instantlabs.instant.TransactionActivity.2.2
                            static final /* synthetic */ boolean $assertionsDisabled = false;

                            @Override // okhttp3.Callback
                            public void onFailure(Call call2, IOException iOException) {
                                TransactionActivity.this.runOnUiThread(new Runnable() { // from class: com.instantlabs.instant.TransactionActivity.2.2.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        Toast.makeText(TransactionActivity.this, "Pin Is Incorrect!", 0).show();
                                    }
                                });
                            }

                            @Override // okhttp3.Callback
                            public void onResponse(Call call2, Response response2) throws IOException {
                                if (response2.isSuccessful()) {
                                    try {
                                        if (JsonParser.parseString(response2.body().string()).getAsJsonObject().get("Description").getAsString().equals("Transaction Successful")) {
                                            Toast.makeText(TransactionActivity.this, "Transaction Completed", 0).show();
                                        }
                                    } catch (JsonSyntaxException e) {
                                        Toast.makeText(TransactionActivity.this, "Something Went Wrong", 0).show();
                                        System.out.println("Error Occurred: " + e.getMessage());
                                    }
                                }
                            }
                        });
                    }
                } catch (JsonSyntaxException e) {
                    Toast.makeText(TransactionActivity.this, "Something Went Wrong", 0).show();
                    System.out.println("Error Occurred: " + e.getMessage());
                }
            }
        }
    }

    private String getAccessToken() {
        return getSharedPreferences(SHARED_PREFS_NAME, 0).getString(ACCESS_TOKEN_KEY, null);
    }
}
