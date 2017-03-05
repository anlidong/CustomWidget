package com.anlddev.customwidget.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anlddev.customwidget.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/5 0005.
 */

public class ATabWidget extends RelativeLayout {

    @BindView(R.id.icon)
    protected ImageView icon;
    @BindView(R.id.text)
    protected TextView text;

    public ATabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setSelected(false);
    }

    public void setIcon(int resId) {
        icon.setImageResource(resId);
    }

    public void setText(String str) {
        text.setText(str);
    }

    public void setSelected(boolean selected) {
        icon.setSelected(selected);
        text.setSelected(selected);
    }
}
