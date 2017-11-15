package ua.admin.muravej.ua.helloworld;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends Activity implements View.OnTouchListener,
        SurfaceHolder.Callback {

    private SurfaceView mSurface;
    Path path;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    SurfaceHolder surfaceHolder;

    private static final String fileName = "hello.JPEG";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mSurface = (SurfaceView) findViewById(R.id.surfaceView1);
        mSurface.setOnTouchListener(this);
        mSurface.getHolder().addCallback(this);
    }

    public void onClick(View v) {
        for (int x = 0; x <= 3; x = x + 1){
            clearCanvas();
        }
    }

    public void clearCanvas() {
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public void onClick2(View v) {

        //Canvas canvas = null;
        //Bitmap bmpBase = null;
        //bmpBase = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        //canvas = new Canvas(bmpBase);
        // draw what ever you want canvas.draw...
        //paint.setStyle(Paint.Style.FILL);
        //paint.setColor(Color.WHITE);
        //canvas.drawPaint(paint);
        //canvas.drawCircle(40,40, 20, paint);
        //mSurface.
        //Bitmap bmpBase = mSurface.getDrawingCache();
        //Canvas canvas = new Canvas(bmpBase);
        //canvas.drawBitmap(bmpBase, 0, 0, null);
        //saveImageToMediaStore(bmpBase, fileName, "test");

        Canvas canvas = surfaceHolder.lockCanvas();
        Bitmap gb = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas gc = new Canvas(gb);


        if (canvas != null) {
            try {
                //v.draw(gc);
                canvas.drawBitmap(gb, 0, 0, null);
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

        //Запись 1
        /*File myFile = new File(Environment.getExternalStorageDirectory().toString() + "/" + fileName);
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(myFile);
            gb.compress(Bitmap.CompressFormat.PNG, 75, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        //Запись 2
        saveImageToMediaStore(gb, fileName, "test");


    }

    private String saveImageToMediaStore(Bitmap source, String title, String desc){
        return MediaStore.Images.Media.insertImage(getContentResolver(), source, title, desc);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path = new Path();
            path.moveTo(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            path.lineTo(event.getX(), event.getY());
        }

        if (path != null) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawPath(path, paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.WHITE);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

}