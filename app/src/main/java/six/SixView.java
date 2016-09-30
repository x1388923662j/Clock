package six;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * 作者：徐智军
 * 创建时间：2016年09月30日 13:45
 */

public class SixView extends View {

    private Paint mPaint;

    private int sixLength;
    private int length;

    private Point p1, p2, p3;
    private Point pr1, pr2, pr3;

    private Path mPath1 = new Path();
    private Path mPath2 = new Path();
    private float rotate;
    private RotateAnimation mRotateAnimation = new RotateAnimation();

    public SixView(Context context) {
        super(context);
        init();
    }

    public SixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SixView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);

        p1 = new Point();
        p2 = new Point();
        p3 = new Point();
        pr1 = new Point();
        pr2 = new Point();
        pr3 = new Point();

        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setDuration(10000);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        startAnimation(mRotateAnimation);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        length = Math.min(getHeight(), getWidth());
        sixLength = length - 80;

        p1.set(0, -(sixLength / 2));
        pr1.set(0, (sixLength / 2));

        p2.set((int) (Math.sqrt(3) / 4 * sixLength), sixLength / 4);
        pr2.set((int) (Math.sqrt(3) / 4 * sixLength), -sixLength / 4);

        p3.set(-(int) (Math.sqrt(3) / 4 * sixLength), sixLength / 4);
        pr3.set(-(int) (Math.sqrt(3) / 4 * sixLength), -sixLength / 4);

        mPath1.moveTo(p1.x, p1.y);
        mPath1.lineTo(p2.x, p2.y);
        mPath1.lineTo(p3.x, p3.y);
        mPath1.close();

        mPath2.moveTo(pr1.x, pr1.y);
        mPath2.lineTo(pr2.x, pr2.y);
        mPath2.lineTo(pr3.x, pr3.y);
        mPath2.close();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        drawCircle(canvas);
        canvas.rotate(rotate);
        drawTriangle(canvas);
        drawLine(canvas);
    }

    private void drawCircle(Canvas canvas) {
        mPaint.setStrokeWidth(5);
        canvas.drawCircle(0, 0, sixLength / 2 + 5 / 2, mPaint);
        canvas.drawCircle(0, 0, length / 2 - 5 / 2, mPaint);

        mPaint.setStrokeWidth(2);
        canvas.save();
        canvas.rotate(-rotate / 3);
        for (int i = 0; i < 360; i = i + 20) {
            canvas.drawLine(0, sixLength / 2 + 5 / 2, 0, length / 2 - 5 / 2, mPaint);
            canvas.rotate(20);
        }
        canvas.restore();

    }

    private void drawTriangle(Canvas canvas) {
        mPaint.setStrokeWidth(5);
        canvas.drawPath(mPath1, mPaint);
        canvas.drawPath(mPath2, mPaint);
    }

    private void drawLine(Canvas canvas) {
        mPaint.setStrokeWidth(2);
        canvas.drawLine(0, 0, p1.x, p1.y, mPaint);
        canvas.drawLine(0, 0, p2.x, p2.y, mPaint);
        canvas.drawLine(0, 0, p3.x, p3.y, mPaint);
        canvas.drawLine(0, 0, pr1.x, pr1.y, mPaint);
        canvas.drawLine(0, 0, pr2.x, pr2.y, mPaint);
        canvas.drawLine(0, 0, pr3.x, pr3.y, mPaint);

        mPaint.setStrokeWidth(3);
        canvas.drawLine(p1.x, p1.y, pr3.x, pr3.y, mPaint);
        canvas.drawLine(pr2.x, pr2.y, p2.x, p2.y, mPaint);
        canvas.drawLine(p2.x, p2.y, pr1.x, pr1.y, mPaint);
        canvas.drawLine(pr1.x, pr1.y, p3.x, p3.y, mPaint);
        canvas.drawLine(p3.x, p3.y, pr3.x, pr3.y, mPaint);
        canvas.drawLine(pr2.x, pr2.y, p1.x, p1.y, mPaint);
    }

    class RotateAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            rotate = 360 * interpolatedTime;
            invalidate();
        }
    }
}
