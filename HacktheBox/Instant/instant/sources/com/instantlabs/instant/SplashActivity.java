package com.instantlabs.instant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes.dex */
public class SplashActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() { // from class: com.instantlabs.instant.SplashActivity.1
            @Override // java.lang.Runnable
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, (Class<?>) LoginActivity.class));
                SplashActivity.this.finish();
            }
        }, 5000L);
    }
}
