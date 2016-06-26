package net.wicp.kamisama.clock.face;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

/**
 * 刻度
 * <p>
 * 作者：xuzhijun
 * 创建时间：2016年06月17日 09:54
 */

public abstract class BaseGraduation extends Drawable {

    protected int height;
    protected int width;
    protected int mOutRadio;
    protected int mGraduationCount = 60;

    protected Paint  mPaint           = new Paint();
    protected PointF mPointCenter     = new PointF();
    protected int    mGraduationColor = Color.WHITE;

    public BaseGraduation() {
        mPaint.setColor(mGraduationColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        height = bottom - top;
        width = right - left;
        mPointCenter.set((right + left) / 2, (bottom + top) / 2);
        mOutRadio = Math.min(height / 2, width / 2);
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

    public int getGraduationCount() {
        return mGraduationCount;
    }

    public void setGraduationCount(int graduationCount) {
        mGraduationCount = graduationCount;
    }

    public int getGraduationColor() {
        return mGraduationColor;
    }

    public void setGraduationColor(int graduationColor) {
        mGraduationColor = graduationColor;
        mPaint.setColor(mGraduationColor);
    }
}
