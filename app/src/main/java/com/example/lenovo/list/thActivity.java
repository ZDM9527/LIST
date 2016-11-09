package com.example.lenovo.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

/**
 * Created by zm on 2016/10/30.
 */

public class thActivity extends Activity {
    //private ImageView brown=null;
    private ImageButton clear = null;
    private HandWrite surface = null;
    private  View lastSelectpen=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        clear = (ImageButton) findViewById(R.id.clear);
        surface = (HandWrite) findViewById(R.id.handWriting);
        lastSelectpen=findViewById(R.id.black);
        lastSelectpen.startAnimation(AnimationUtils.loadAnimation(thActivity.this,R.anim.anim2));


        //接收图片
        Intent it = getIntent();
        Bundle bd = it.getExtras();
        //String fileName=bd.getString("pic");
        // Bitmap bm = BitmapFactory.decodeFile(fileName); //设置图片
        //show.setImageBitmap(bm);
       surface.imageId= bd.getInt("pic");




    }

    private void playAnim(View v) {
        if(lastSelectpen!=null)
        {
            lastSelectpen.startAnimation(AnimationUtils.loadAnimation(thActivity.this,R.anim.anim));
            v.startAnimation(AnimationUtils.loadAnimation(thActivity.this,R.anim.anim2));

        }
        lastSelectpen=v;

    }

    public void onclick(View v) {
        switch (v.getId()) {
       case R.id.black:
           playAnim(v);
           surface.mpath.reset();
           surface.mPaint.setColor(Color.rgb(255,0,255));
           break;
           case R.id.blue:
               playAnim(v);
               surface.mpath.reset();
               surface.mPaint.setColor(getResources().getColor(R.color.colorPrimary));
               break;
           case R.id.brown:
               playAnim(v);
               surface.mpath.reset();
               surface.mPaint.setColor(Color.rgb(255,204,153));
               break;
           case R.id.gray:
               playAnim(v);
             // surface.setYellowPaint();
               surface.mpath.reset();
               surface.mPaint.setColor(Color.rgb(128,128,128));
               break;
           case R.id.pink:
               playAnim(v);
               surface.mpath.reset();
               surface.mPaint.setColor(Color.rgb(255,0,255));
               break;
           case R.id.orange:
               playAnim(v);
               surface.mpath.reset();
               surface.mPaint.setColor(Color.rgb(153,204,0));
               break;
           case R.id.red:
               playAnim(v);
               surface.mpath.reset();
               surface.mPaint.setColor(Color.rgb(255,0,0));
               break;
           case R.id.wathet:
               playAnim(v);
               surface.mpath.reset();
               surface.mPaint.setColor(Color.rgb(51,51,153));
               break;
           case R.id.violet:
               playAnim(v);
               surface.mpath.reset();
               surface.mPaint.setColor(Color.rgb(128,0,128));
               break;
           case R.id.yellow:
               playAnim(v);
               surface.mpath.reset();
               surface.mPaint.setColor(Color.rgb(255,255,0));
               break;
            case R.id.clear:
                surface.clean();
                break;
            case R.id.eraser:
                //surface.eraser();
                break;
            case R.id.home:
                finish();
                break;
            case R.id.preservation:
                surface.save(this,surface.cache_bitmip);
                break;
            case R.id.line:
                surface.mpath.reset();
                surface.mPaint.setStyle(Paint.Style.STROKE);
                surface.mPaint.setStrokeWidth(30);
                break;
            case R.id.dot:
                surface.mpath.reset();
                surface.mPaint.setStyle(Paint.Style.STROKE);
                surface.mPaint.setStrokeWidth(10);
                break;


        }
    }

   /* @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }*/

    @Override
    public void finish() {
        super.finish();
    }
}
