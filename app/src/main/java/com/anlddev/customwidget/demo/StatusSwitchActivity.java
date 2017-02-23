package com.anlddev.customwidget.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anlddev.customwidget.R;
import com.anlddev.customwidget.widget.StatusSwitch;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusSwitchActivity extends AppCompatActivity {

    @BindView(R.id.StatusSwitch)
    protected StatusSwitch mStatusSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_switch);
        ButterKnife.bind(this);
        mStatusSwitch.setMode(StatusSwitch.OnModeChangedListener.MODE_LOW);
        mStatusSwitch.setOnModeChangedListener(new StatusSwitch.OnModeChangedListener() {
            @Override
            public void onModeChanged(int mode) {
                switch (mode) {
                    case MODE_LOW:
                        Toast.makeText(StatusSwitchActivity.this, "当前模式：低", Toast.LENGTH_SHORT).show();
                        break;
                    case MODE_NORMAL:
                        Toast.makeText(StatusSwitchActivity.this, "当前模式：中", Toast.LENGTH_SHORT).show();
                        break;
                    case MODE_HIGH:
                        Toast.makeText(StatusSwitchActivity.this, "当前模式：高", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
