package com.example.android.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Path;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


    public class MyView extends View {
        Paint mPaint;
        coordinates currentpt=new coordinates();
        List<Path> totalPathList;
         boolean isend=true;
        private List<point> totalPathPts,currentPathPts;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path    mPath,npath;
        private Paint   mBitmapPaint,circlePaint;
        public MyView(Context c) {
            super(c);
            totalPathPts=new ArrayList<point>();
            currentPathPts=new ArrayList<point>();
            currentpt.setX1(0.0);
            currentpt.setY1(0.0);
            totalPathList=new ArrayList<Path>();
            circlePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
            circlePaint.setColor(Color.parseColor("Red"));
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            mBitmapPaint.setColor(Color.parseColor("Black"));
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setColor(Color.parseColor("Black"   ));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            // mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(10);

        }
public void onSubmit()
{
   currentpt.setX1(0);
   currentpt.setY1(0);
   totalPathList.add(mPath);
   Log.v("Points of current path:",""+currentPathPts.size());
   for(int i=0;i<currentPathPts.size();i++)
   {Log.v("point "+i+"=",currentPathPts.get(i).x+","+currentPathPts.get(i).y);}
    currentPathPts.clear();
    Log.v("Total no. of paths",""+totalPathList.size());

}
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }
private point checkpt(float x,float y)
{   point touse=new point(x,y);
    for(int i=0;i<totalPathPts.size();i++){
    float dx = Math.abs(x - totalPathPts.get(i).x);
    float dy = Math.abs(y - totalPathPts.get(i).y);
    if (dx <= TOUCH_TOLERANCE && dy <= TOUCH_TOLERANCE) {
touse.x=totalPathPts.get(i).x;
touse.y=totalPathPts.get(i).y;
return touse;
    }
    }
    return touse;
}
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(0xFFAAAAAA);
            mPath = new Path();
            npath=new Path();
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 200;
        private void touch_start(float x, float y) {
            mPath.reset();
 point current=checkpt(x,y);
 x=current.x;
 y=current.y;
         if(!isend)
         {mPath.moveTo(x, y);
            if(currentpt.getX1()==0) {
                currentpt.setX1(x);
                currentpt.setY1(y);
                mX=x;
                mY=y;
                point cpt=new point(x,y);
                totalPathPts.add(cpt);
                currentPathPts.add(cpt);

                npath.addCircle(x,y,6, Path.Direction.CCW);
                mCanvas.drawPath(npath, circlePaint);
            }
            else{
                currentpt.setX2(x);
                currentpt.setY2(y);
                touch_move(x,y);
                mX=x;
                mY=y;
            }
        }}

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                point cpt=new point(x,y);
                totalPathPts.add(cpt);
                currentPathPts.add(cpt);
            touch_up();
            }
            else{
                //Toast.makeText(canvas.this, "end!!", Toast.LENGTH_SHORT).show();
                isend=true;
                touch_up();
            }
        }
        private void touch_up() {
           if(isend) {
               int length1=(currentPathPts.size())-1;
               mX = currentPathPts.get(length1).x;
               mY = currentPathPts.get(length1).y;
               mPath.moveTo(mX,mY);
               mPath.lineTo(currentPathPts.get(0).x, currentPathPts.get(0).y);
               mCanvas.drawPath(mPath, mPaint);
           }
           else
           {   npath.addCircle(mX,mY,6,Path.Direction.CCW);
               mPath.lineTo(mX,mY);
               // commit the path to our offscreen
               mCanvas.drawPath(npath, circlePaint);
               mCanvas.drawPath(mPath, mPaint);
           }
               // kill this so we don't double draw
               mPath.reset();
        }
public void setIsend(boolean val)
{isend=val;}
        @Override
        public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!isend) {
                touch_start(x, y);
                invalidate();
                break;
        }
                else{
                    Toast.makeText(getContext(), "Not Recognised", Toast.LENGTH_SHORT).show();
                }
        return true;
    }return false;   }
    }

