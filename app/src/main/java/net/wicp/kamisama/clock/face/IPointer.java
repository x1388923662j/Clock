package net.wicp.kamisama.clock.face;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import net.wicp.kamisama.clock.tools.ScreenUtils;

/**
 * 指针
 * <p>
 * 作者：xuzhijun
 * 创建时间：2016年06月17日 12:23
 */

public abstract class IPointer extends Drawable {

    protected int height;
    protected int width;
    protected int mPointLength;
    protected Paint  mPaint       = new Paint();
    protected PointF mPointCenter = new PointF();
    protected int    mPointWidth  = ScreenUtils.dp2px(1.5f);
    protected int    mPointColor  = Color.WHITE;

    private float mRadio = -90f;

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        height = bottom - top;
        width = right - left;
        mPointCenter.set((right + left) / 2, (bottom + top) / 2);
        if (mPointLength == 0)
            mPointLength = Math.min(height / 2, width / 2) / 3 * 2;
        mPaint.setStrokeWidth(mPointWidth);
    }

    public void setPointLength(int pointLength) {
        mPointLength = pointLength;
    }

    public int getPointLength() {
        return mPointLength;
    }

    public void setRadio(float radio) {
        mRadio = radio - 90;
    }

    public float getRadio() {
        return mRadio;
    }

    public void setPointWidth(int pointWidth) {
        mPointWidth = pointWidth;
        mPaint.setStrokeWidth(mPointWidth);
    }

    public int getPointWidth() {
        return mPointWidth;
    }

    public int getPointColor() {
        return mPointColor;
    }

    public void setPointColor(int pointColor) {
        mPointColor = pointColor;
        mPaint.setColor(mPointColor);
    }

    @Override
    public void draw(Canvas canvas) {
        float startX, startY, endX, endY;
        startX = mPointCenter.x;
        startY = mPointCenter.y;
        endX = mPointLength * (float) Math.cos(2 * Math.PI * (mRadio / 360)) + mPointCenter.x;
        endY = mPointLength * (float) Math.sin(2 * Math.PI * (mRadio / 360)) + mPointCenter.y;
        canvas.drawLine(startX, startY, endX, endY, mPaint);
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
