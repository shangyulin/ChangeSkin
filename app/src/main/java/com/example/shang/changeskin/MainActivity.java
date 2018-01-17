package com.example.shang.changeskin;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SkinManager.getInstance().init(this);
        File file = new File(Environment.getExternalStorageDirectory(), "skin.apk");
        SkinManager.getInstance().loadSkin(file.getAbsolutePath());


        Button change = (Button) findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinFactory.apply();
            }
        });
    }
}
