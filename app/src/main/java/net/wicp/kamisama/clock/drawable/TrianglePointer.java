package net.wicp.kamisama.clock.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import net.wicp.kamisama.clock.PointType;
import net.wicp.kamisama.clock.face.BasePointer;
import net.wicp.kamisama.clock.tools.ScreenUtils;

/**
 * 作者：xuzhijun
 * 创建时间：2016年06月20日 14:31
 */

public class TrianglePointer extends BasePointer {

    private Paint smallPaint;

    private int  smallWidth = ScreenUtils.dp2px(7);
    private int  space      = ScreenUtils.dp2px(5);
    private Path path2      = new Path();
    private Path path       = new Path();

    public TrianglePointer() {
        smallPaint = new Paint();
        smallPaint.setAntiAlias(true);
        smallPaint.setStrokeWidth(1);
        smallPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        smallPaint.setColor(getPointColor());
        smallWidth = mMaxRadio / 30;
        if (getPointType() == PointType.HOUR) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setShadowLayer(100, 20, 20, Color.parseColor("#10000000"));
            //                        mPaint.setStrokeWidth(1);
        } else {
            mPaint.setShadowLayer(15, 20, 20, Color.parseColor("#30000000"));
        }

        //        if (getPointType() == PointType.HOUR) {
        //            lengthX = mPointLength * 2;
        //            lengthY = mPointLength * 2;
        //        } else {
        lengthX = mPointCenter.x * 2;
        lengthY = mPointCenter.y * 2;
        //        }
    }

    float pointX, pointY;
    float pointX2, pointY2;
    float pointX3, pointY3;

    float lengthX;
    float lengthY;

    float pX, pY;
    float pX2, pY2;
    float pX3, pY3;
    float cX, cY;

    @Override
    public void draw(Canvas canvas) {

        pointX = (mPointLength - getPointWidth()) * (float) Math.cos(2 * Math.PI * (getRadio() / 360)) + mPointCenter.x;
        pointY = (mPointLength - getPointWidth()) * (float) Math.sin(2 * Math.PI * (getRadio() / 360)) + mPointCenter.y;
        pointX2 = (mPointLength - getPointWidth()) * (float) Math.cos(2 * Math.PI * ((getRadio() + 120) / 360)) + mPointCenter.x;
        pointY2 = (mPointLength - getPointWidth()) * (float) Math.sin(2 * Math.PI * ((getRadio() + 120) / 360)) + mPointCenter.y;
        pointX3 = (mPointLength - getPointWidth()) * (float) Math.cos(2 * Math.PI * ((getRadio() + 240) / 360)) + mPointCenter.x;
        pointY3 = (mPointLength - getPointWidth()) * (float) Math.sin(2 * Math.PI * ((getRadio() + 240) / 360)) + mPointCenter.y;

        path.reset();
        path.moveTo(pointX, pointY);
        path.lineTo(pointX2, pointY2);
        path.lineTo(pointX3, pointY3);
        path.close();

        if (getPointType() == PointType.HOUR) {
            cX = (mPointLength - getPointWidth() / 2 - smallWidth - getPointWidth()) * (float) Math.cos(2 * Math.PI * (getRadio() / 360)) + mPointCenter.x;
            cY = (mPointLength - getPointWidth() / 2 - smallWidth - getPointWidth()) * (float) Math.sin(2 * Math.PI * (getRadio() / 360)) + mPointCenter.y;
        } else {
            cX = (mPointLength - smallWidth - getPointWidth() - space) * (float) Math.cos(2 * Math.PI * (getRadio() / 360)) + mPointCenter.x;
            cY = (mPointLength - smallWidth - getPointWidth() - space) * (float) Math.sin(2 * Math.PI * (getRadio() / 360)) + mPointCenter.y;
        }

        pX = smallWidth * (float) Math.cos(2 * Math.PI * (getRadio() / 360)) + cX;
        pY = smallWidth * (float) Math.sin(2 * Math.PI * (getRadio() / 360)) + cY;

        pX2 = smallWidth * (float) Math.cos(2 * Math.PI * ((getRadio() + 120) / 360)) + cX;
        pY2 = smallWidth * (float) Math.sin(2 * Math.PI * ((getRadio() + 120) / 360)) + cY;

        pX3 = smallWidth * (float) Math.cos(2 * Math.PI * ((getRadio() + 240) / 360)) + cX;
        pY3 = smallWidth * (float) Math.sin(2 * Math.PI * ((getRadio() + 240) / 360)) + cY;

        if (getPointType() == PointType.HOUR) {
            path2.reset();
            path2.moveTo(pX2, pY2);
            path2.lineTo(pX3, pY3);
            path2.lineTo(pointX3, pointY3);
            path2.lineTo(pointX2, pointY2);
            path2.close();

            canvas.drawPath(path, mPaint);
            canvas.drawPath(path2, smallPaint);
        } else {
            path2.reset();
            path2.moveTo(pX, pY);
            path2.lineTo(pX2, pY2);
            path2.lineTo(pX3, pY3);
            path2.close();
            canvas.drawPath(path, mPaint);
            canvas.drawPath(path2, smallPaint);
        }

    }
}
