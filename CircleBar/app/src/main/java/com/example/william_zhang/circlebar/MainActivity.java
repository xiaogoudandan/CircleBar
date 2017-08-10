package com.example.william_zhang.circlebar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.william_zhang.circlebar.View.MyCircleBar;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Math.random;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.circle_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.CircleBar)
    MyCircleBar mMyCircleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTop();

        setValueAnimator(100.0);
    }

    private void initTop() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);//绑定
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示左边按钮
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home: Toast.makeText(this,"退出",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.restart:
                Toast.makeText(this,"重测",Toast.LENGTH_SHORT).show();
               double  point =Math.random()*100;
                BigDecimal b   =   new   BigDecimal(point);
                double   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();
                Log.e("数值:" ,Double.toString(f1));
              //  mMyCircleBar.setPoint(Double.toString(f1));
                setValueAnimator(f1);
                return true;
        }
//        if(item.getItemId() == android.R.id.home)
//        {
//            Toast.makeText(this,"退出",Toast.LENGTH_SHORT);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_memu,menu);
        return true;
    }


    public void setValueAnimator(final double point)
    {
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(0,(float) point);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float number=(float)valueAnimator.getAnimatedValue();
                DecimalFormat fnum = new DecimalFormat("##0.0");
                String point=fnum.format(number);
                mMyCircleBar.setPoint(point);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mMyCircleBar.setPoint(Double.toString(point));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();

    }
}
