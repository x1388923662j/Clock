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
import net.wicp.kamisama.clock.drawable.LinePointer;
import net.wicp.kamisama.clock.drawable.PointGraduation;
import net.wicp.kamisama.clock.drawable.TrianglePointer;
import net.wicp.kamisama.clock.face.BaseGraduation;
import net.wicp.kamisama.clock.face.BasePointer;
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

    private ClockDial mDial;
    private BaseGraduation mGraduation = new PointGraduation();

    private BasePointer mHourPointer   = new LinePointer();
    private BasePointer mMinutePointer = new LinePointer();
    private BasePointer mSecPointer    = new LinePointer();

    //    private Timer     mTimer;
    //    private ClockTask mClockTask;

    private int     dialColor          = Color.BLACK;
    private int     graduationColor    = Color.WHITE;
    private int     hourPointerColor   = Color.WHITE;
    private int     minutePointerColor = Color.WHITE;
    private int     secondPointerColor = Color.WHITE;
    private boolean noSecondPointer    = false;
    private int     freshTime          = 64;
    private int     graduationCount    = 60;

    private ClockType mType = ClockType.LINE;

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
            graduationCount = typedArray.getInt(R.styleable.ClockView_graduationCount, 60);
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
            switch (typedArray.getInt(R.styleable.ClockView_type, 0)) {
                case 1:
                    mType = ClockType.TRISGLE;
                    mHourPointer = new TrianglePointer();
                    mMinutePointer = new TrianglePointer();
                    mSecPointer = new TrianglePointer();
                    break;
            }

            typedArray.recycle();
        }

        mPointCenter = new Point();
        mPaint.setAntiAlias(true);
        mDial = new ClockDial();
        mDial.setDialColor(dialColor);

        mGraduation.setGraduationColor(graduationColor);
        mGraduation.setGraduationCount(graduationCount);

        mHourPointer.setPointType(PointType.HOUR);
        mHourPointer.setPointWidth(ScreenUtils.dp2px(3));
        mHourPointer.setRadio(90);
        mHourPointer.setPointColor(hourPointerColor);

        mMinutePointer.setPointType(PointType.MINUTE);
        mMinutePointer.setPointWidth(ScreenUtils.dp2px(2f));
        mMinutePointer.setRadio(0);
        mMinutePointer.setPointColor(minutePointerColor);

        mSecPointer.setPointType(PointType.SECOND);
        mSecPointer.setRadio(225);
        mSecPointer.setPointWidth(ScreenUtils.dp2px(1f));
        mSecPointer.setPointColor(secondPointerColor);

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
        mPointCenter.set(getWidth() / 2, getHeight() / 2);
        int radio = Math.min(height / 2, width / 2);

        mDial.setBounds(mPointCenter.x - radio, mPointCenter.y - radio, mPointCenter.x + radio, mPointCenter.y + radio);

        mGraduation.setBounds(mPointCenter.x - radio, mPointCenter.y - radio, mPointCenter.x + radio, mPointCenter.y + radio);

        int hourLength, minuteLength, secondLength;
        secondLength = radio / 20 * 15;
        minuteLength = radio / 20 * 12;
        if (mType == ClockType.TRISGLE)
            hourLength = radio / 20 * 6;
        else
            hourLength = radio / 20 * 10;

        mHourPointer.setMaxRadio(radio);
        mHourPointer.setBounds(mPointCenter.x - radio, mPointCenter.y - radio, mPointCenter.x + radio, mPointCenter.y + radio);
        mHourPointer.setPointLength(hourLength);

        mMinutePointer.setMaxRadio(radio);
        mMinutePointer.setBounds(mPointCenter.x - radio, mPointCenter.y - radio, mPointCenter.x + radio, mPointCenter.y + radio);
        mMinutePointer.setPointLength(minuteLength);

        mSecPointer.setMaxRadio(radio);
        mSecPointer.setBounds(mPointCenter.x - radio, mPointCenter.y - radio, mPointCenter.x + radio, mPointCenter.y + radio);
        mSecPointer.setPointLength(secondLength);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int       height         = ScreenUtils.dp2px(200);
        int       width          = ScreenUtils.dp2px(200);
        final int widthSpecMode  = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSpecSize  = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, height);
        }
    }

    int      hour;
    int      minute;
    int      second;
    int      millisecond;
    float    hourRadio;
    float    minuteRadio;
    float    secondRadio;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calendar.setTime(Calendar.getInstance().getTime());
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        millisecond = calendar.get(Calendar.MILLISECOND);
        hourRadio = 360 * hour / 12f + minute * 30 / 60f;
        minuteRadio = 360 * minute / 60f + second * 6 / 60f;
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
        } else {
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
