package com.gui.toledo.threadsample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private SpannableString spannableString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                spannableString = new SpannableString(msg.obj.toString());

                int start = "Message: Task".length();
                int end = msg.obj.toString().indexOf("completed");

                int start2 = msg.obj.toString().indexOf("Thread");
                int end2 = msg.obj.toString().length();

                spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, 0);
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.teal_700)), start, end, 0);

                spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start2, end2, 0);
                spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.teal_700)), start2, end2, 0);

                textView.setText(
                        spannableString
                );

                if (msg.arg1 == 5) {
                    BackgroundThread backgroundThread = new BackgroundThread(this);
                    backgroundThread.start();
                }

            }
        };

        button.setOnClickListener(view -> {
            button.setText(getString(R.string.sample_thread_run));
            button.setEnabled(false);
            BackgroundThread backgroundThread = new BackgroundThread(handler);
            backgroundThread.start();
        });
    }

}
