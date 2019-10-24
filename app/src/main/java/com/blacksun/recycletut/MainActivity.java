package com.blacksun.recycletut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ScrollViewExt mScrollView;
//    LinearLayout mLinearLayout;

    ArrayList<TestItem> mTestList;
//    int index = 0;
//    int topIndex, bottomIndex = 0;
    int numView = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScrollView = findViewById(R.id.scrollView);
//        mScrollView.setScrollViewListener(this);
//        mLinearLayout = findViewById(R.id.linear);

//        mLinearLayout = new LinearLayout(this);
//        TextView text = new TextView(this);
//        text.setText("hello");
//        mLinearLayout.addView(text);
//        mScrollView.addView(mLinearLayout);
        mTestList = new ArrayList<>();

        for (int i = 0; i < numView; i++) {
            mTestList.add(new TestItem(i));
        }

        mScrollView.setViewList(mTestList);
    }


}
