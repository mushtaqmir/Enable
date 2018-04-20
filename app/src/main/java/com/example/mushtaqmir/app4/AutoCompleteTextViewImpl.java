package com.example.mushtaqmir.app4;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by Mushtaq.Mir on 4/20/2018.
 */

class AutoCompleteTextViewImpl extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    public AutoCompleteTextViewImpl(Context context) {
        super(context);
    }

    public AutoCompleteTextViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoCompleteTextViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void getWindowVisibleDisplayFrame(Rect outRect) {
        outRect.bottom = -3000;
        super.getWindowVisibleDisplayFrame(outRect);

    }
}