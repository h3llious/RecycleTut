package com.blacksun.recycletut;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.databinding.Bindable;

import java.util.ArrayList;
import java.util.Stack;

public class ScrollViewExt extends ScrollView {
    private ArrayList<TestItem> testModels;
    private LinearLayout mLinearLayout;
    private int topIndex, bottomIndex = 0;
    private int size;
    private Context context;
    private Stack<View> poolUp;
    private Stack<View> poolDown;

    private void init(Context context) {
        this.context = context;
        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        addView(mLinearLayout);
        poolUp = new Stack<>();
        poolDown = new Stack<>();
    }

    //    public interface ScrollViewListener {
//        void onScrollChanged(ScrollViewExt scrollView,
//                             int x, int y, int oldx, int oldy);
//    }
//    private ScrollViewListener scrollViewListener = null;
    public ScrollViewExt(Context context) {
        super(context);
        init(context);
    }

    public ArrayList<TestItem> getTestModels() {
        return testModels;
    }

    public void setTestModels(ArrayList<TestItem> testModels) {
        this.testModels = testModels;
        this.size = testModels.size();

        LinearLayout linearLayout =
                (LinearLayout) LayoutInflater.from(context).inflate(R.layout.test_view, null);
//        double viewHeight = linearLayout.findViewById(R.id.wrap_layout).getHeight();
//        long initialViewNum = Math.round((this.getHeight() / viewHeight)*1.2f);
        for (int i = 0; i < 12; i++) {

            TextView numOrder = linearLayout.findViewById(R.id.num);
            String text = "" + testModels.get(bottomIndex).numOrder;
            numOrder.setText(text);

            mLinearLayout.addView(linearLayout, mLinearLayout.getChildCount());
            bottomIndex++;
            linearLayout =
                    (LinearLayout) LayoutInflater.from(context).inflate(R.layout.test_view, null);
        }
    }

//    public void setViewList(ArrayList<TestItem> testModels) {
//
//        this.testModels = testModels;
//        this.size = testModels.size();
//
//        LinearLayout linearLayout =
//                (LinearLayout) LayoutInflater.from(context).inflate(R.layout.test_view, null);
////        double viewHeight = linearLayout.findViewById(R.id.wrap_layout).getHeight();
////        long initialViewNum = Math.round((this.getHeight() / viewHeight)*1.2f);
//        for (int i = 0; i < 12; i++) {
//
//            TextView numOrder = linearLayout.findViewById(R.id.num);
//            String text = "" + testModels.get(bottomIndex).numOrder;
//            numOrder.setText(text);
//
//            mLinearLayout.addView(linearLayout, mLinearLayout.getChildCount());
//            bottomIndex++;
//            linearLayout =
//                    (LinearLayout) LayoutInflater.from(context).inflate(R.layout.test_view, null);
//        }
//    }

    public ScrollViewExt(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ScrollViewExt(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

//    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
//        this.scrollViewListener = scrollViewListener;
//    }

//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        if (scrollViewListener != null) {
//            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
//        }
//    }

    @Override
    public void onScrollChanged(int x, int y, int oldx, int oldy) {
        // We take the last child in the scrollview
        View view = this.getChildAt(this.getChildCount() - 1);
        int diffTop = this.getScrollY();
        int diffBottom = (view.getBottom() - (this.getHeight() + this.getScrollY()));
        int viewHeight = view.findViewById(R.id.wrap_layout).getHeight();

        if (y - oldy > 0) { //scroll down
            // if diffBottom is zero, then the bottom has been reached
            if (diffBottom < 2 * viewHeight) {
//             do stuff

                for (int i = 0; i < 1; i++) {
                    if (bottomIndex < size) {
                        LinearLayout linearLayout;
                        if (poolDown.empty()) {
                            linearLayout =
                                    (LinearLayout) LayoutInflater.from(context).inflate(R.layout.test_view, null);
                            TextView numOrder = linearLayout.findViewById(R.id.num);
                            String text = "" + testModels.get(bottomIndex).numOrder;
                            numOrder.setText(text);
                        } else {
                            linearLayout = (LinearLayout) poolDown.pop();

                            Log.d("test", "poolDown");
                        }
                        mLinearLayout.addView(linearLayout, mLinearLayout.getChildCount());
                        bottomIndex++;
                    }
                }

                if (this.getScrollY() > 4 * viewHeight) {
                    for (int iDel = 1; iDel > 0; iDel--) {
//                        mLinearLayout.removeViewAt(mLinearLayout.getChildCount() - 15 - iDel);
                        if (poolUp.size() >= 5) {
                            poolUp.remove(0);
                        }
                        poolUp.push(mLinearLayout.getChildAt(0));
                        mLinearLayout.removeViewAt(0);
                        this.setScrollY(this.getScrollY() - viewHeight);
                        topIndex++;
                    }
                }
                Log.d("test", "bottom");
            }
        } else { //scroll up
            if (diffTop < 2 * viewHeight) {
                if (topIndex >= 0) {
                    LinearLayout linearLayout;
                    if (poolUp.empty()) {
                        linearLayout =
                                (LinearLayout) LayoutInflater.from(context).inflate(R.layout.test_view, null);
                        TextView numOrder = linearLayout.findViewById(R.id.num);
                        String text = "" + testModels.get(topIndex).numOrder;
                        numOrder.setText(text);
                    } else {
                        linearLayout = (LinearLayout) poolUp.pop();
                        Log.d("test", "poolUp");
                    }

                    mLinearLayout.addView(linearLayout, 0);
                    topIndex--;
                }

                if (diffBottom > 4 * viewHeight) {
                    for (int iDel = 1; iDel > 0; iDel--) {
//                mLinearLayout.removeViewAt(mLinearLayout.getChildCount() - 15 - iDel);
                        if (poolDown.size() >= 5) {
                            poolDown.remove(0);
                        }
                        poolDown.push(mLinearLayout.getChildAt(mLinearLayout.getChildCount() - 1));
                        mLinearLayout.removeViewAt(mLinearLayout.getChildCount() - 1);
                        this.setScrollY(this.getScrollY() + viewHeight);
                        bottomIndex--;
//                        index--;
                    }
                }
            }
        }

    }

}
