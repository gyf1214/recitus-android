package moe.zcstaff.recitus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class LauncherActivity extends Activity {
    static final private String TAG = "launcher";

    @BindView(R.id.list)
    ListView listView;

    private File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);


        files = this.getExternalFilesDir(null).listFiles();
        ArrayList<String> fileNames = new ArrayList<>();
        for (File file : files) {
            Log.d(TAG, file.getAbsolutePath());
            fileNames.add(file.getName());
        }
        Collections.sort(fileNames);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.list_select, R.id.list_item, fileNames);
        listView.setAdapter(adapter);
    }

    @OnItemClick(R.id.list)
    void onListClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, Integer.toString(i));

        Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
        intent.putExtra("path", files[i].getAbsolutePath());
        startActivity(intent);
    }
}
