package com.example.a01drawround.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.a01drawround.UIUtils.UIUtils;

/**
 * Created by chen on 2017/6/24.
 * 自定义视图1：绘制圆环比例图的
 */

public class RoundFillProgress extends View {


    //1. 测量尺寸--对于圆形
    private int width;//视图宽度(高度)
    private int hight;//视图高度==宽度
    private int roundCircleWidth = UIUtils.px2dp(this.getContext(), 40);

    //2. 设置颜色
    private int roundCircleColor = Color.GRAY; //圆环颜色
    private int roundProgressColor = Color.RED; //进度颜色
    private int roundTextColor = Color.BLUE;    //字体颜色

    //3. 字体大小
    private int roundTextSize = UIUtils.px2dp(this.getContext(), 80);
    private float roundMax = 100;
    private float roundProgress = 60f;

    //4. 画笔
    private Paint paint;


    public RoundFillProgress(Context context) {
        this(context, null);
    }

    public RoundFillProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundFillProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true); //画笔去除毛边
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMeasuredWidth(); //视图宽
        hight = this.getMeasuredHeight();//获取视图高
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //圆心位置
        int cx = width / 2;
        int cy = hight / 2;

        //半径
        int radios = width / 2 - roundCircleWidth / 2;

        //1. 绘制圆环
        paint.setColor(roundCircleColor);
        paint.setStyle(Paint.Style.FILL); //边界
        paint.setStrokeWidth(roundCircleWidth);

        canvas.drawCircle(cx, cy, radios, paint);


        //2. 绘制进度

        //绘制圆形对应的矩形的左上 右下坐标

        RectF oval = new RectF(roundCircleWidth / 2, roundCircleWidth / 2, width - roundCircleWidth / 2, hight - roundCircleWidth / 2);

        float startAngle = 0;
        float sweepAngle = roundProgress * 360 / roundMax;


        paint.setColor(roundProgressColor);
        canvas.drawArc(oval, startAngle, sweepAngle, true, paint);

        //3. 绘制文字
        String text = roundProgress + "%";

        //获取文字对应的包裹矩形的左下角坐标
        Rect textRect = new Rect();

        paint.setTextSize(roundTextSize);
        paint.setColor(roundTextColor);
        paint.setStrokeWidth(0);

        paint.getTextBounds(text, 0, text.length(), textRect);

        //文字包裹矩形的坐下标
        int textX = cx - textRect.width() / 2;
        int textY = cx + textRect.height() / 2;


        canvas.drawText(text, textX, textY, paint);
    }
}
