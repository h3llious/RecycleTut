package com.blacksun.recycletut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ScrollViewExt.ScrollViewListener {

    ScrollViewExt mScrollView;
    LinearLayout mLinearLayout;

    LinearLayoutManager f;

    ArrayList<TestItem> mTestList;
    int index = 0;
    int topIndex, bottomIndex = 0;
    int numView = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScrollView = findViewById(R.id.scrollView);
        mScrollView.setScrollViewListener(this);
        mLinearLayout = findViewById(R.id.linear);
        mTestList = new ArrayList<TestItem>();

        for (int i = 0; i < numView; i++) {
            mTestList.add(new TestItem(i));
        }

        for (int i = 0; i < 8; i++) {
            LinearLayout linearLayout =
                    (LinearLayout) LayoutInflater.from(this).inflate(R.layout.test_view, null);
            TextView numOrder = linearLayout.findViewById(R.id.num);
            numOrder.setText("" + mTestList.get(index).numOrder);

            mLinearLayout.addView(linearLayout, mLinearLayout.getChildCount() - 1);
            index++;
        }
        bottomIndex = index;

    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        // We take the last child in the scrollview
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diffTop = scrollView.getScrollY();
        int diffBottom = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        int viewHeight = view.findViewById(R.id.wrap_layout).getHeight();

        if (y - oldy > 0) { //scroll down
            // if diffBottom is zero, then the bottom has been reached
            if (diffBottom < 2 * viewHeight) {
//             do stuff

                for (int i = 0; i < 1; i++) {
                    if (index < numView) {
                        LinearLayout linearLayout =
                                (LinearLayout) LayoutInflater.from(this).inflate(R.layout.test_view, null);
                        TextView numOrder = linearLayout.findViewById(R.id.num);
                        numOrder.setText("" + mTestList.get(index).numOrder);

                        mLinearLayout.addView(linearLayout, mLinearLayout.getChildCount() - 1);
                        index++;
                        bottomIndex = index;
                    }
                }

                if (scrollView.getScrollY() > 4 * viewHeight) {
                    for (int iDel = 1; iDel > 0; iDel--) {
//                mLinearLayout.removeViewAt(mLinearLayout.getChildCount() - 15 - iDel);
                        mLinearLayout.removeViewAt(0);
                        scrollView.setScrollY(scrollView.getScrollY() - viewHeight);
                        topIndex++;
                    }
                }
                Log.d("test", "bottom");
            }
        } else { //scroll up
            if (diffTop < 2 * viewHeight){
                if (topIndex >= 0) {
                    LinearLayout linearLayout =
                            (LinearLayout) LayoutInflater.from(this).inflate(R.layout.test_view, null);
                    TextView numOrder = linearLayout.findViewById(R.id.num);
                    numOrder.setText("" + mTestList.get(topIndex).numOrder);

                    mLinearLayout.addView(linearLayout, 0);
                    topIndex--;
                }

                if (diffBottom > 4 * viewHeight) {
                    for (int iDel = 1; iDel > 0; iDel--) {
//                mLinearLayout.removeViewAt(mLinearLayout.getChildCount() - 15 - iDel);
                        mLinearLayout.removeViewAt(mLinearLayout.getChildCount() -1);
                        scrollView.setScrollY(scrollView.getScrollY() + viewHeight);
                        bottomIndex--;
                        index--;
                    }
                }
            }
        }

    }
}
