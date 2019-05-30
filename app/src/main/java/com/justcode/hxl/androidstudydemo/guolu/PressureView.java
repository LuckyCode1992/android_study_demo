package com.justcode.hxl.androidstudydemo.guolu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PressureView extends View {

    private Paint paint;
    private int pressure;

    {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public PressureView(Context context) {
        super(context);
    }

    public PressureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PressureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PressureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setPressure(int pressure) {
        this.pressure = pressure;
//        invalidate(); 主线程调用
        postInvalidate();//子线程调用 ondraw会执行
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //如果压力值大于220，绘制文本，显示警告
        //小于220 显示柱状图，
        //小于200 显示绿色
        //大于200显示红色

        if (pressure > 220) {
            canvas.drawText("警告，警告，要爆炸了", 10, getHeight() / 2, paint);
        } else {
            paint.setColor(Color.GRAY);
            canvas.drawRect(10,10,60,260,paint);
            if (pressure >= 200) {
                paint.setColor(Color.RED);
                canvas.drawRect(10,260-pressure,60,260,paint);
            } else {
                paint.setColor(Color.GREEN);
                canvas.drawRect(10,260-pressure,60,260,paint);
            }
        }
    }
}
