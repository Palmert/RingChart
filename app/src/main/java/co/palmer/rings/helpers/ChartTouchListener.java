package co.palmer.rings.helpers;

import android.view.MotionEvent;

import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.PieRadarChartTouchListener;

/**
 * File created by Thom Palmer on 2015-03-18.
 */
public class ChartTouchListener extends PieRadarChartTouchListener {
    private OnChartGestureListener mOnChartGestureListener;
    public ChartTouchListener(PieRadarChartBase<?> ctx, OnChartGestureListener mOnChartGestureListener) {
        super(ctx);
        this.mOnChartGestureListener = mOnChartGestureListener;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        super.onSingleTapConfirmed(e);
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        mOnChartGestureListener.onChartFling(event1, event2, velocityX, velocityY);
        return true;
    }
}
