package net.wicp.kamisama.clock;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import net.wicp.kamisama.clock.drawable.ClockDial;
import net.wicp.kamisama.clock.drawable.Graduation;
import net.wicp.kamisama.clock.drawable.LinePointer;
import net.wicp.kamisama.clock.tools.ScreenUtils;

import java.util.Calendar;

/**
 * 作者：xuzhijun
 * 创建时间：2016年06月16日 20:24
 */

public class ClockView extends View {

    private int height;
    private int width;

    private Point mPointCenter;
    private Paint mPaint = new Paint();

    private ClockDial  mDial;
    private Graduation mGraduation;

    private LinePointer mHourPointer;
    private LinePointer mMinutePointer;
    private LinePointer mSecPointer;

    //    private Timer     mTimer;
    //    private ClockTask mClockTask;

    private int     dialColor          = Color.BLACK;
    private int     graduationColor    = Color.WHITE;
    private int     hourPointerColor   = Color.WHITE;
    private int     minutePointerColor = Color.WHITE;
    private int     secondPointerColor = Color.WHITE;
    private boolean noSecondPointer    = false;
    private int     freshTime          = 64;

    public ClockView(Context context) {
        super(context);
        init(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
            dialColor = typedArray.getColor(R.styleable.ClockView_dialColor, Color.BLACK);
            graduationColor = typedArray.getColor(R.styleable.ClockView_graduationColor, Color.WHITE);
            hourPointerColor = typedArray.getColor(R.styleable.ClockView_hourPointerColor, Color.WHITE);
            minutePointerColor = typedArray.getColor(R.styleable.ClockView_minutePointerColor, Color.WHITE);
            secondPointerColor = typedArray.getColor(R.styleable.ClockView_secondPointerColor, Color.WHITE);
            noSecondPointer = typedArray.getBoolean(R.styleable.ClockView_noSecondPointer, false);
            switch (typedArray.getInt(R.styleable.ClockView_freshTime, 64)) {
                case 0:
                    freshTime = 64;
                    break;
                case 1:
                    freshTime = 128;
                    break;
                case 2:
                    freshTime = 1000;
                    break;
            }
            typedArray.recycle();
        }

        mPointCenter = new Point();
        mPaint.setAntiAlias(true);
        mDial = new ClockDial();
        mDial.setDialColor(dialColor);
        mGraduation = new Graduation();
        mGraduation.setGraduationColor(graduationColor);

        mHourPointer = new LinePointer();
        mHourPointer.setPointWidth(ScreenUtils.dp2px(3));
        mHourPointer.setRadio(90);
        mHourPointer.setPointColor(hourPointerColor);

        mMinutePointer = new LinePointer();
        mMinutePointer.setPointWidth(ScreenUtils.dp2px(2f));
        mMinutePointer.setRadio(0);
        mMinutePointer.setPointColor(minutePointerColor);

        mSecPointer = new LinePointer();
        mSecPointer.setRadio(225);
        mSecPointer.setPointWidth(ScreenUtils.dp2px(1f));
        mMinutePointer.setPointColor(secondPointerColor);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //        mTimer = new Timer();
        //        mClockTask = new ClockTask();
        //        mTimer.schedule(mClockTask, 0, 160);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = getHeight() - getPaddingTop() - getPaddingBottom();
        width = getWidth() - getPaddingLeft() - getPaddingRight();
        mPointCenter.set((right + left) / 2, (bottom + top) / 2);
        int radio = Math.min(height / 2, width / 2);

        mDial.setBounds(mPointCenter.x - radio, mPointCenter.y - radio, mPointCenter.x + radio, mPointCenter.y + radio);
        mGraduation.setBounds(mPointCenter.x - radio, mPointCenter.y - radio, mPointCenter.x + radio, mPointCenter.y + radio);

        mHourPointer.setBounds(mPointCenter.x - radio, mPointCenter.y - radio, mPointCenter.x + radio, mPointCenter.y + radio);
        mHourPointer.setPointLength(mDial.getRadio() / 3);

        mMinutePointer.setBounds(mPointCenter.x - radio, mPointCenter.y - radio, mPointCenter.x + radio, mPointCenter.y + radio);
        mMinutePointer.setPointLength(mDial.getRadio() / 5 * 3);

        mSecPointer.setBounds(mPointCenter.x - radio, mPointCenter.y - radio, mPointCenter.x + radio, mPointCenter.y + radio);
        mSecPointer.setPointLength(mDial.getRadio() / 5 * 4);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int       height         = 200;
        int       width          = 200;
        final int widthSpecMode  = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSpecSize  = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSpecSize = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Calendar calendar    = Calendar.getInstance();
        int      hour        = calendar.get(Calendar.HOUR);
        int      minute      = calendar.get(Calendar.MINUTE);
        int      second      = calendar.get(Calendar.SECOND);
        int      millisecond = calendar.get(Calendar.MILLISECOND);
        float    hourRadio   = 360 * hour / 12f + minute * 6 / 60f;
        float    minuteRadio = 360 * minute / 60f + second * 6 / 60f;
        float    secondRadio;
        if (noSecondPointer || freshTime == 1000)
            secondRadio = 360 * second / 60f;
        else
            secondRadio = 360 * second / 60f + millisecond * 6 / 1000f;
        mHourPointer.setRadio(hourRadio);
        mMinutePointer.setRadio(minuteRadio);
        mSecPointer.setRadio(secondRadio);

        mDial.draw(canvas);
        mGraduation.draw(canvas);
        mHourPointer.draw(canvas);
        mMinutePointer.draw(canvas);
        if (noSecondPointer) {
            postInvalidateDelayed(1000);
        }else {
            mSecPointer.draw(canvas);
            postInvalidateDelayed(freshTime);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //        mClockTask.cancel();
        //        mClockTask = null;
        //        mTimer.cancel();
        //        mTimer = null;
    }

    //    private class ClockTask extends TimerTask {
    //
    //        @Override
    //        public void run() {
    //            postInvalidate();
    //        }
    //    }

}
