package com.example.gaoshengnan.mytestforgit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gaoshengnan on 2017/12/13.
 */

public class DefView extends View {
    private Paint paint;
    private float radius = 100;
    private int cirWidth;
    private int cirHeight;
    private Point point;

    /**
     * 从代码中创建自定义控件时调用
     * @param context
     */
    public DefView(Context context) {
        super(context);
        init();
    }

    /**
     * 从xml文件中创建自定义控件时调用此构造方法
     * @param context
     * @param attrs
     */
    public DefView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 从xml文件创建自定义控件时调用，并且带有style属性
     * attrs可以得到这个控件的所有属性
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public DefView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    public void init(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//得到屏幕
        cirWidth = displayMetrics.widthPixels / 2;
        cirHeight = displayMetrics.heightPixels / 2;
        point = new Point(cirWidth, cirHeight);
        paint = new Paint();
        paint.setDither(true);//防抖
        paint.setStrokeWidth(50);//画笔宽度
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100, widthMeasureSpec);
        int height = getMySize(100, heightMeasureSpec);
        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * 会被多次调用，因此不适合在这个方法中初始化对象
     * 打印Log查看调用次数
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("TAG", "Invoke onDraw()");
        canvas.drawCircle(point.x,point.y,radius,paint);
        canvas.save();
    }

    /**
     * 点击事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                point.x = (int) event.getX();
                point.y = (int) event.getY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
            default:
                break;
        }
        invalidate();
        return true;
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    /**
     * set paint's radius
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * set paint's color
     * @param color
     */
    public void setPaintColor(int color) {
        this.paint.setColor(color);
    }

    /**
     * set paint's width
     * @param width
     */
    public void getPaintWidth(int width) {
        this.paint.setStrokeWidth(width);
    }

    /**
     * get paint's radius
     */
    public float getRadius() {
        return this.radius;
    }

    /**
     * get paint's color
     */
    public int getPaintColor(){
        return this.paint.getColor();
    }

    /**
     * get paint's width
     */
    public float getPaintWidth(){
        return this.paint.getStrokeWidth();
    }
}
