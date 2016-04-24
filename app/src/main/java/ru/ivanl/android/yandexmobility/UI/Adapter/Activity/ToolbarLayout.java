package ru.ivanl.android.yandexmobility.UI.Adapter.Activity;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;

/**
 * Created by Ivan on 24.04.2016.
 */
public class ToolbarLayout extends CollapsingToolbarLayout {
    public ToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int measuredWidth = widthSpecSize;
        switch (widthSpecMode) {
            case MeasureSpec.UNSPECIFIED:
                measuredWidth = 0;
                break;

            case MeasureSpec.AT_MOST:
                measuredWidth = widthSpecSize;
                break;

            case MeasureSpec.EXACTLY:
                measuredWidth = widthSpecSize;
                break;
        }

        int measuredHeight = heightSpecSize;
        switch (heightSpecMode) {
            case MeasureSpec.UNSPECIFIED:
                measuredHeight = measuredWidth;
                break;

            case MeasureSpec.AT_MOST:
                // в нашей задаче сюда попасть не должны
                // родитель говорит, что мы можем быть не выше, чем.
                measuredHeight = Math.min(measuredHeight, heightSpecSize);
                break;

            case MeasureSpec.EXACTLY:
                // в нашей задаче сюда попасть не должны
                // родитель точно указал высоту
                measuredHeight = heightSpecSize;
                break;
        }

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        super.onMeasure(MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));

//        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
