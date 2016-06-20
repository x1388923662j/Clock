package net.wicp.kamisama.clock.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import net.wicp.kamisama.clock.tools.ScreenUtils;

/**
 * 刻度
 * <p>
 * 作者：xuzhijun
 * 创建时间：2016年06月17日 09:54
 */

public class Graduation extends Drawable {

    private int height;
    private int width;
    private int mOutRadio;
    private int mGraduationCount = 60;

    private int mGradLongLength  = ScreenUtils.dp2px(30);
    private int mGradShortLength = ScreenUtils.dp2px(15);
    private int mGradMinLength   = ScreenUtils.dp2px(6);
    private int mGradWidth       = ScreenUtils.dp2px(2);
    private int mGradMinWidth    = ScreenUtils.dp2px(1.5f);

    private Paint  mPaint           = new Paint();
    private PointF mPointCenter     = new PointF();
    private int    mGraduationColor = Color.WHITE;

    public Graduation() {
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
    public void draw(Canvas canvas) {

        for (int i = 0; i < mGraduationCount; i++) {
            float startX, startY, endX, endY;
            startX = mOutRadio * (float) Math.cos(2 * Math.PI / mGraduationCount * i) + mPointCenter.x;
            startY = mOutRadio * (float) Math.sin(2 * Math.PI / mGraduationCount * i) + mPointCenter.y;
            float angle = 360 / (float) mGraduationCount * i;
            if (angle % 90 == 0) {
                mPaint.setStrokeWidth(mGradWidth);
                endX = (mOutRadio - mGradLongLength) * (float) Math.cos(2 * Math.PI / mGraduationCount * i) + mPointCenter.x;
                endY = (mOutRadio - mGradLongLength) * (float) Math.sin(2 * Math.PI / mGraduationCount * i) + mPointCenter.y;
                canvas.drawLine(startX, startY, endX, endY, mPaint);
            } else if (angle % 30 == 0) {
                mPaint.setStrokeWidth(mGradWidth);
                endX = (mOutRadio - mGradShortLength) * (float) Math.cos(2 * Math.PI / mGraduationCount * i) + mPointCenter.x;
                endY = (mOutRadio - mGradShortLength) * (float) Math.sin(2 * Math.PI / mGraduationCount * i) + mPointCenter.y;
                canvas.drawLine(startX, startY, endX, endY, mPaint);
            } else {
                mPaint.setStrokeWidth(mGradMinWidth);
                endX = (mOutRadio - mGradMinLength) * (float) Math.cos(2 * Math.PI / mGraduationCount * i) + mPointCenter.x;
                endY = (mOutRadio - mGradMinLength) * (float) Math.sin(2 * Math.PI / mGraduationCount * i) + mPointCenter.y;
                canvas.drawLine(startX, startY, endX, endY, mPaint);
            }
        }

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

    public int getGraduationColor() {
        return mGraduationColor;
    }

    public void setGraduationColor(int graduationColor) {
        mGraduationColor = graduationColor;
        mPaint.setColor(mGraduationColor);
    }
}
