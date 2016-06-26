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

public class PointGraduation extends BaseGraduation {

    private int mGradLongLength  = ScreenUtils.dp2px(3);
    private int mGradShortLength = ScreenUtils.dp2px(2);

    private int spase = ScreenUtils.dp2px(1.5f);

    public PointGraduation() {
        super();
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mGradLongLength = mOutRadio / 80;
        mGradShortLength = mOutRadio / 120;
        //        mGradMinLength = mOutRadio / 150;
        spase = mGradLongLength * 2;
    }

    float startX, startY, angle;

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < mGraduationCount; i++) {
            startX = (mOutRadio - spase) * (float) Math.cos(2 * Math.PI / mGraduationCount * i) + mPointCenter.x;
            startY = (mOutRadio - spase) * (float) Math.sin(2 * Math.PI / mGraduationCount * i) + mPointCenter.y;
            angle = 360 / (float) mGraduationCount * i;
            if (angle % 90 == 0) {
                canvas.drawCircle(startX, startY, mGradLongLength, mPaint);
            } else if (angle % 30 == 0) {
                canvas.drawCircle(startX, startY, mGradShortLength, mPaint);
            }
            //            else {
            //                canvas.drawCircle(startX, startY, mGradMinLength, mPaint);
            //            }
        }

    }
}
