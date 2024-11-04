package com.instantlabs.instant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes.dex */
public class ForgotPasswordActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_forgot_password);
        ((Button) findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() { // from class: com.instantlabs.instant.ForgotPasswordActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ForgotPasswordActivity.this.startActivity(new Intent(ForgotPasswordActivity.this, (Class<?>) LoginActivity.class));
                ForgotPasswordActivity.this.finish();
            }
        });
    }
}
