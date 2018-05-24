package moe.zcstaff.recitus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {
    static final String TAG = "main";

    @BindView(R.id.text)
    TextView text;

    @BindView(R.id.buttons)
    LinearLayout buttons;

    @BindView(R.id.ok)
    Button ok;

    @BindView(R.id.ng)
    Button ng;

    private boolean showing = false;
    private ArrayList<Pair<String, String>> l1 = new ArrayList<>();
    private ArrayList<Pair<String, String>> l2 = new ArrayList<>();
    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        Log.i(TAG, path);

        File file = new File(path);

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String x = sc.nextLine();
                String y = sc.nextLine();
                l1.add(new Pair<>(x, y));
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, Integer.toString(l1.size()));
        Collections.shuffle(l1);
        showQuestion();
    }

    private void showQuestion() {
        showing = false;
        text.setText(l1.get(current).first);
        buttons.setVisibility(View.GONE);
    }

    private void showAnswer() {
        showing = true;
        text.setText(l1.get(current).second);
        buttons.setVisibility(View.VISIBLE);
    }

    private void next(boolean ng) {
        if (ng) {
            l2.add(l1.get(current));
        }
        current += 1;
        if (current >= l1.size()) {
            l1.clear();
            ArrayList<Pair<String, String>> t = l2;
            l2 = l1;
            l1 = t;
            current = 0;
            Collections.shuffle(l1);
        }
        if (l1.isEmpty()) {
            finish();
        } else {
            showQuestion();
        }
    }

    @OnClick(R.id.text)
    void onTextClick(View view){
        if (!showing) {
            showAnswer();
        } else {
            showQuestion();
        }
    }

    @OnClick(R.id.ok)
    void onOKClick(View view) {
        if (showing) {
            next(false);
        }
    }

    @OnClick(R.id.ng)
    void onNGClick(View view) {
        if (showing) {
            next(true);
        }
    }
}
