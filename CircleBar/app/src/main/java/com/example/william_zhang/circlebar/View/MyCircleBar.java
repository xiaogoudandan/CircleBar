package com.example.william_zhang.circlebar.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.william_zhang.circlebar.R;
import com.example.william_zhang.circlebar.util.DensityUtil;

import java.util.Map;

/**
 * Created by william_zhang on 2017/7/18.
 */

public class MyCircleBar extends View {

    private Bitmap mHead;
    private Context mContext;
    private int begin=160;//范围
    private int end=220;//范围
    private String mPoint="10.0";//分数
    private String TAG="数据： ";
    //view高
    private int mHeight;
    //view宽
    private int mWeight;

    private int padding=20;
    private int paddingLeft=10;

    private int paddingRight=10;

    private int paddingTop=10;

    private int paddingButtom=10;

    private Paint mLinePaint;

    private Paint mBodyPaint;

    private Paint mMiddlePaint;

    private Paint mRunProgressPaint;

    private Paint mTextPaint;

    private Paint mBitmapPaint;

     //圆弧两边的距离
    private int mPdDistance = 50;
    //最外层半径
    private int outRadius;
    //中间层内01
    private int middleRadius;


    //灰色
    private int arcButtomColor;
    //淡蓝色
    private int arcBodyColor;



    public MyCircleBar(Context context) {
        this(context,null);
    }

    public MyCircleBar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyCircleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView();
        intiAttributes(context, attrs);

    }

    private void initView() {
        paddingLeft=getPaddingLeft();
        paddingRight=getPaddingRight();
        paddingTop=getPaddingTop();
        paddingButtom=getPaddingBottom();
        arcButtomColor=getResources().getColor(R.color.line);
        arcBodyColor=getResources().getColor(R.color.circleColor);
        //画底部灰色部分的线
        mLinePaint=new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(arcButtomColor);
        mLinePaint.setStrokeWidth((float) 2.0f);
        mLinePaint.setStyle(Paint.Style.STROKE);
        //灰色线之上的蓝色
        mBodyPaint=new Paint();
        mBodyPaint.setStyle(Paint.Style.STROKE);
        mBodyPaint.setAntiAlias(true);
        mBodyPaint.setColor(arcBodyColor);
        mBodyPaint.setStrokeWidth((float) 5.0f);

        //灰色线之上的蓝色
        mMiddlePaint=new Paint();
        mMiddlePaint.setStyle(Paint.Style.STROKE);
        mMiddlePaint.setAntiAlias(true);
        mMiddlePaint.setColor(arcBodyColor);
        mMiddlePaint.setStrokeWidth((float) 1.5*mPdDistance);

        //写字的笔
        mRunProgressPaint=new Paint();
        mRunProgressPaint.setStyle(Paint.Style.FILL);
        mRunProgressPaint.setAntiAlias(true);
        mRunProgressPaint.setTextSize(176);
        mRunProgressPaint.setColor(arcBodyColor);

        mTextPaint=new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(76);
        mTextPaint.setColor(arcButtomColor);

        mBitmapPaint=new Paint();
        mHead= BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.head);



    }

    private void intiAttributes(Context context, AttributeSet attrs) {
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.MyCircleBar);
        //中间是获得属性
        a.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOut(canvas);
        drawMiddle(canvas);
        drawGrade(canvas,mPoint);
        drawRunString(canvas);
    }

    private void drawRunString(Canvas canvas) {
        mTextPaint.setTextSize(48);
        canvas.drawText("风险得分", mWeight/2 - mTextPaint.measureText("风险得分") / 2,
                mHeight/2  - (mTextPaint.descent() + mTextPaint.ascent()) / 2 - 80 , mTextPaint);

        canvas.drawText(mPoint, mHeight/2 - mRunProgressPaint.measureText(mPoint) / 2,
                mHeight/2  - (mRunProgressPaint.descent() + mRunProgressPaint.ascent()) / 2 + 40, mRunProgressPaint);


    }

    private void drawMiddle(Canvas canvas) {
        mTextPaint.setTextSize(40);
        RectF rectf=new RectF(padding+(float)1.5*mPdDistance,padding+(float)1.5*mPdDistance,0+mWeight-padding-(float)1.5*mPdDistance,0+mWeight-padding-(float)1.5*mPdDistance);
        float x=(float)Math.sin(Math.PI*(begin-90)/180)*(middleRadius-(float) 1.5*mPdDistance);
        float y=(float)Math.cos(Math.PI*(begin-90)/180)*(middleRadius-(float) 1.5*mPdDistance);
        canvas.drawText("0", mHeight/2-x - mTextPaint.measureText("0") / 2,
                             mHeight/2+y - (mTextPaint.descent() + mTextPaint.ascent()) / 2, mTextPaint);
        for(int i=0;i<10;i++){
              canvas.drawArc(rectf,i*22+begin,(float) 21.5,false,mMiddlePaint);
            x=(float)Math.sin(Math.PI*(begin+(i+1)*22-90)/180)*(middleRadius-(float) 1.5*mPdDistance);
            y=(float)Math.cos(Math.PI*(begin+(i+1)*22-90)/180)*(middleRadius-(float) 1.5*mPdDistance);
            canvas.drawText(Integer.toString((i+1)*10), mHeight/2-x - mTextPaint.measureText(Integer.toString((i+1)*10)) / 2,
                    mHeight/2+y - (mTextPaint.descent() + mTextPaint.ascent()) / 2, mTextPaint);
//            canvas.drawText(Integer.toString((i+1)*10), mHeight/2 - mTextPaint.measureText("风险得分") / 2,
//                    mHeight/2  - (mRunProgressPaint.descent() + mTextPaint.ascent()) / 2 -40, mTextPaint);
        }

    }

    private void drawGrade(Canvas canvas, String mPoint) {
       // RectF rectf=new RectF(0,0,0+mWeight,0+mWeight);
        RectF rectf=new RectF(padding,padding,0+mWeight-padding,0+mWeight-padding);
      try {
          float grade=Float.valueOf(mPoint);
          float length=(float) (grade*1.0)*end/100;
          Log.e(TAG,"长度："+length);
          canvas.drawArc(rectf,begin,length,false,mBodyPaint);
          //旋转
          Matrix matrix=new Matrix();
          matrix.postRotate(length+begin-90+180,0,mHead.getHeight()/2);
          float x=(float)Math.sin(Math.PI*(begin+length-90)/180)*((mWeight-2*padding)/2);
          Log.e("head:height:" ,Float.toString(mHead.getHeight()));
          Log.e("head:width:" ,Float.toString(mHead.getWidth()));
          float w=mHeight/2-x-mHead.getWidth()/3;
          float y=(float)Math.cos(Math.PI*(begin+length-90)/180)*((mWeight-2*padding)/2);
          float h=mHeight/2+y-mHead.getHeight()/2;


          Matrix matrix1 = new Matrix();
          int offsetX = mHead.getWidth() / 2;
          int offsetY = mHead.getHeight() / 2;
          matrix1.postTranslate(-offsetX, -offsetY);
          matrix1.postRotate(length+begin-90+180);
          matrix1.postTranslate(mHeight/2-x,mWeight/2+y);
          canvas.drawBitmap(mHead, matrix1, mBitmapPaint);





//          Bitmap bitmap=Bitmap.createBitmap(mHead,0,0,mHead.getWidth(),mHead.getHeight(),matrix,true);
//          canvas.drawBitmap(bitmap,w,h,mBitmapPaint);
//          canvas.drawPoint(w,h,mBodyPaint);
          Paint p=new Paint();
          int color=0x000000;
          p.setColor(getResources().getColor(R.color.black));
          p.setStrokeWidth(5.0f);
          p.setStyle(Paint.Style.STROKE);
          p.setAntiAlias(true);
          canvas.drawPoint(mHeight/2-x,mHeight/2+y,p);
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    private void drawOut(Canvas canvas) {
        RectF rectf=new RectF(padding,padding,0+mWeight-padding,0+mWeight-padding);
        canvas.drawArc(rectf,begin,end,false,mLinePaint);
    }

    public void setPoint(String point)
    {
        mPoint=point;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int WH[]=getWindowWH();
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode==MeasureSpec.EXACTLY)//给定值
        {

            mWeight = width;
        }else //wrapment or matchment
        {
            mWeight=Math.min(WH[0],WH[1]);//两个的最小值
        }
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode==MeasureSpec.EXACTLY)//给定值
        {
            mHeight = height;
        }else //wrapment or matchment 
        {
            mHeight= Math.min(WH[0]-2*mPdDistance,WH[1]-2*mPdDistance);//两个的最小值
        }
        Log.i(TAG,"Width: "+mWeight+" Height: "+mHeight);
        int wh=Math.min(mHeight,mWeight);
        mHeight=wh;
        mWeight=wh;
        outRadius=(mHeight-2*padding)/2;//设置最外层半径
        middleRadius=(mWeight-2*padding-3*mPdDistance)/2;
        setMeasuredDimension(mWeight,mHeight);
    }
    /**
     * 获得屏幕的宽度
     * @return
     */
    private int[]getWindowWH()
    {
        WindowManager windowManager=(WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return new int[]{displayMetrics.widthPixels,displayMetrics.heightPixels};
    }
}
