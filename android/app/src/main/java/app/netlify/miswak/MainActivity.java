package app.netlify.miswak;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleDeepLink(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleDeepLink(intent);
    }

    private void handleDeepLink(Intent intent) {
        if (intent == null) return;
        Uri data = intent.getData();
        if (data == null) return;

        String url = data.toString();

        if (url.contains("access_token") || url.contains("code=") || url.contains("error=")) {
            bridge.getWebView().post(() -> {
                bridge.getWebView().loadUrl(
                    "javascript:window.dispatchEvent(new CustomEvent('authDeepLink', { detail: '" + url.replace("'", "\\'") + "' }))"
                );
            });
        }
    }
}
