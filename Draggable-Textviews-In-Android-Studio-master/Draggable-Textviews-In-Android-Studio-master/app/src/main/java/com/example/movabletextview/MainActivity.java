package com.example.movabletextview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String texts[] = {"The", "Lord", "is", "my", "Shepherd."};
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String TAG = "onCreate";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (RelativeLayout) findViewById(R.id.linear);

        layout.measure(0, 0);
        int width = layout.getWidth();
        Log.d(TAG, "layout width " + width);

        generateTv(texts);
        Context c = getApplicationContext();
        Log.d(TAG, "size :" + getScreenRes(c));
    }

    public static Point getScreenResolution(Context context) {
// get window managers
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        // get width and height
        int width = point.x;
        int height = point.y;

        return point;
    }

    private static String getScreenRes(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return "{" + width + "," + height + "}";
    }

    private void generateTv(String texts[]) {
        final String TAG = "generateTV";

        int Xposition = 150;
        int Yposition = 500;

        layout = (RelativeLayout) findViewById(R.id.linear);

        layout.measure(0, 0);
        int width = layout.getWidth();
        Log.d(TAG, "layout width " + width);

        for (int i = 0; i < texts.length; i++) {

            TextView tv = new TextView(this);
            tv.setText("" + texts[i]);
            tv.setX(Xposition);
            tv.setY(Yposition);
            tv.setTextColor(Color.RED);
            tv.setTextSize(33);
            tv.setOnTouchListener(mOnTouchListenerTv2);

            layout.addView(tv);
            Xposition += 100;

            Log.d(TAG, "length " + tv.length());
            Log.d(TAG, "TypeFace " + tv.getTypeface());
            tv.measure(0,0); // this is needed to getMeasuredWidth or getMeasuredHeight
            Log.d(TAG, "width " + tv.getMeasuredWidth() + " height " + tv.getMeasuredHeight());
            Log.d(TAG, "getPaint().getTextSize() " + tv.getPaint().getTextSize());
        }
    }


    public final View.OnTouchListener mOnTouchListenerTv2 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);
                    return true;
                }
            }
            return false;
        }
    };
}
