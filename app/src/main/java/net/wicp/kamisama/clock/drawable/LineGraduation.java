package net.wicp.kamisama.clock.drawable;

import android.graphics.Canvas;

import net.wicp.kamisama.clock.face.BaseGraduation;
import net.wicp.kamisama.clock.tools.ScreenUtils;

/**
 * 刻度
 * <p>
 * 作者：xuzhijun
 * 创建时间：2016年06月17日 09:54
 */

public class LineGraduation extends BaseGraduation {

    private int mGradLongLength  = ScreenUtils.dp2px(30);
    private int mGradShortLength = ScreenUtils.dp2px(15);
    private int mGradMinLength   = ScreenUtils.dp2px(3f);
    private int mGradWidth       = ScreenUtils.dp2px(1.5f);
    private int mGradMinWidth    = ScreenUtils.dp2px(1f);

    public LineGraduation() {
        super();
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mGradLongLength = mOutRadio / 5;
        mGradShortLength = mOutRadio / 10;
        mGradMinLength = mOutRadio / 20;
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
}
