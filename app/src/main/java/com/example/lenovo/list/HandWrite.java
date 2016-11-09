package com.example.lenovo.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.path;

/**
 * Created by lenovo on 2016/11/1.
 */

public class HandWrite extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private boolean mIsDrawing;//判断是否处于绘制状态

    private SurfaceHolder mHolder;//帮助类

    private Canvas mCanvas;//画布

    public Path mpath;//路径

    public Paint mPaint;//画笔

    private Canvas cacheCanvas;//保存当前绘制的画布

    public Bitmap cache_bitmip;//用于保存当前绘制的内容

    public int imageId;


    public HandWrite(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public HandWrite(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HandWrite(Context context) {
        super(context);
        initView();
    }


    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);

        mpath = new Path();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
    }

    //触摸事件处理器
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mpath.moveTo(x, y);
            case MotionEvent.ACTION_MOVE:
                mpath.lineTo(x, y);
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        initCacheBitmapAndDrawBackground(true);
        new Thread(this).start();

    }

    private void initCacheBitmapAndDrawBackground(boolean isFirst) {
        int view_width = getWidth();//当前Surface的宽度
        int view_height = getHeight();

        Bitmap bg_bitmip = BitmapFactory.decodeResource(getResources(), imageId);
        int bgBitmapWidth = bg_bitmip.getWidth();
        int bgBitmapHeight = bg_bitmip.getHeight();
        if (isFirst) {
            cache_bitmip = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            cacheCanvas = new Canvas();
            cacheCanvas.setBitmap(cache_bitmip);

        }
        Matrix matrix = new Matrix();
        float scale = 1.0f;
        int left, top;
        if (bgBitmapWidth <= view_width && bgBitmapHeight <= view_height) {
            left = (view_width - bgBitmapWidth)/2;//计算左边位置
            top = (view_height - bgBitmapHeight)/2;//计算上边位置
        } else {
            double viewRatio = view_height / (double) view_width;
            double bitmapRatio = bgBitmapHeight / (double) bgBitmapWidth;
            if (bitmapRatio > viewRatio) {
                //说明图片是一个相对屏幕view来说很高  很窄的图片
                top = 0;
                left = (int) ((view_width - view_height / (double) bitmapRatio)/2);
                scale = view_height / (float) bgBitmapHeight;
            } else {
                //相对屏幕是很宽 很矮的图片
                left = 0;
                top = (int) ((view_height - bitmapRatio * view_width)/2);
                scale = view_width / (float) bgBitmapWidth;
            }
        }
        matrix.postScale(scale, scale);//等比例缩放
        matrix.postTranslate(left, top);//缩放后再移位

        cacheCanvas.drawColor(Color.WHITE);//绘制背景色
        cacheCanvas.drawBitmap(bg_bitmip, matrix, null);//绘制图片

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;

    }

    //线程work函数
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while (mIsDrawing) {
            draw();
        }
        long end = System.currentTimeMillis();
        if (end - start < 100) {
            try {
                Thread.sleep(100 - (end - start));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            cacheCanvas.drawPath(mpath, mPaint);
            mCanvas.drawBitmap(cache_bitmip, 0, 0, new Paint());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    //清除内容
    public void clean() {
        initView();
        initCacheBitmapAndDrawBackground(false);
    }
    public void save(Context context,Bitmap bmp){
        File appDir=new File(Environment.getExternalStorageDirectory(),"boohee");
        if(!appDir.exists()){
            appDir.mkdir();
        }
        String fileName=System.currentTimeMillis()+".jpg";
        File file =new File(appDir,fileName);
        try{
            FileOutputStream fos=new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        try{
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(),fileName,null);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://"+path)));
    }



}


