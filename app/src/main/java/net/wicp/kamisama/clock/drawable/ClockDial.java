package net.wicp.kamisama.clock.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

/**
 * 表盘
 * <p>
 * 作者：xuzhijun
 * 创建时间：2016年06月17日 09:21
 */

public class ClockDial extends Drawable {

    private int height;
    private int width;
    private int mRadio;
    private Paint  mPaint       = new Paint();
    private PointF mPointCenter = new PointF();

    private int mDialColor = Color.BLACK;


    public ClockDial() {
        mPaint.setColor(mDialColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        height = bottom - top;
        width = right - left;
        mPointCenter.set((right + left) / 2, (bottom + top) / 2);
        mRadio = Math.min(height / 2, width / 2);
    }

    public int getRadio() {
        return mRadio;
    }

    public int getDialColor() {
        return mDialColor;
    }

    public void setDialColor(int dialColor) {
        mDialColor = dialColor;
        mPaint.setColor(mDialColor);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mPointCenter.x, mPointCenter.y, mRadio, mPaint);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
