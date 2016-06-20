package net.wicp.kamisama.clock.drawable;

import android.graphics.Canvas;

import net.wicp.kamisama.clock.face.IPointer;

/**
 * 指针
 * <p>
 * 作者：xuzhijun
 * 创建时间：2016年06月17日 12:23
 */

public class LinePointer extends IPointer {

    public LinePointer() {
        mPaint.setColor(mPointColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        float startX, startY, endX, endY;
        startX = mPointCenter.x;
        startY = mPointCenter.y;
        endX = mPointLength * (float) Math.cos(2 * Math.PI * (getRadio() / 360)) + mPointCenter.x;
        endY = mPointLength * (float) Math.sin(2 * Math.PI * (getRadio() / 360)) + mPointCenter.y;
        canvas.drawLine(startX, startY, endX, endY, mPaint);
    }
}
