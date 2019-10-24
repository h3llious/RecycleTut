package com.blacksun.recycletut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.blacksun.recycletut.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ScrollViewExt mScrollView;
//    LinearLayout mLinearLayout;

    ArrayList<TestItem> testModels;
//    int index = 0;
//    int topIndex, bottomIndex = 0;
    int numView = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //still working
//        setContentView(R.layout.activity_main);
//        mScrollView = findViewById(R.id.scrollView);


//        mScrollView.setScrollViewListener(this);
//        mLinearLayout = findViewById(R.id.linear);

//        mLinearLayout = new LinearLayout(this);
//        TextView text = new TextView(this);
//        text.setText("hello");
//        mLinearLayout.addView(text);
//        mScrollView.addView(mLinearLayout);

        testModels = new ArrayList<>();

        for (int i = 0; i < numView; i++) {
            testModels.add(new TestItem(i));
        }

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        CustomViewModel viewModel = new CustomViewModel();
        binding.setVariable(BR.vm, viewModel);
        viewModel.setTestModels(testModels);
        binding.executePendingBindings();


    }


}
