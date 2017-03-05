package com.anlddev.customwidget.demo.fragmenttabhost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.anlddev.customwidget.R;
import com.anlddev.customwidget.demo.fragmenttabhost.fragment.FragmentTab1;
import com.anlddev.customwidget.demo.fragmenttabhost.fragment.FragmentTab2;
import com.anlddev.customwidget.demo.fragmenttabhost.fragment.FragmentTab3;
import com.anlddev.customwidget.widget.AFragmentTabHost;
import com.anlddev.customwidget.widget.ATabWidget;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTabActivity extends AppCompatActivity {

    @BindView(R.id.tabhost)
    protected AFragmentTabHost mTabHost;

    private TabInfo tabInfos[] = {
            new TabInfo(FragmentTab1.class,
                    R.drawable.icon_tab_host,
                    "标签一"),
            new TabInfo(FragmentTab2.class,
                    R.drawable.icon_tab_host,
                    "标签二"),
            new TabInfo(FragmentTab3.class,
                    R.drawable.icon_tab_host,
                    "标签三")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab);
        ButterKnife.bind(this);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);
        initTab();
    }

    private void initTab() {
        for(TabInfo tabInfo : tabInfos) {
            mTabHost.addTab(
                    mTabHost.newTabSpec(tabInfo.text).setIndicator(getTabWidget(tabInfo)),
                    tabInfo.tabClass,
                    null
            );
        }
        mTabHost.setCurrentTab(1);
    }

    private View getTabWidget(TabInfo tabInfo) {
        ATabWidget tabWidget = (ATabWidget) View.inflate(this, R.layout.layout_tab_widget, null);

        tabWidget.setIcon(tabInfo.iconRes);
        tabWidget.setText(tabInfo.text);

        return tabWidget;
    }

    private static class TabInfo {
        Class tabClass;
        int iconRes;
        String text;

        public TabInfo(Class tabClass, int iconRes, String text) {
            this.tabClass = tabClass;
            this.iconRes = iconRes;
            this.text = text;
        }
    }
}
