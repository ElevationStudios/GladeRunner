package com.elevationstudios.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;

import com.elevationstudios.framework.Input.TouchEvent;
import com.elevationstudios.framework.Pool;
import com.elevationstudios.framework.Pool.PoolObjectFactory;

public class TouchHandler implements OnTouchListener {
    private static final int MAX_TOUCHPOINTS = 10;
    boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    int[] touchX = new int[MAX_TOUCHPOINTS];
    int[] touchY = new int[MAX_TOUCHPOINTS];
    Pool<TouchEvent> touchEventPool;
    List<TouchEvent> touchEvents = new ArrayList<>();
    List<TouchEvent> touchEventsBuffer = new ArrayList<>();
    float scaleX;
    float scaleY;

    int action;
    int pointerIndex;
    int pointerId;
    TouchEvent touchEvent;
    private GestureDetector gestureDetector;

    public TouchHandler(View view, float scaleX, float scaleY, Context c) {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<>(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;

        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            action = MotionEventCompat.getActionMasked(event);
            pointerIndex = MotionEventCompat.getActionIndex(event);
            pointerId = event.getPointerId(pointerIndex);

            switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                touchEvent = touchEventPool.newObject();
                touchEvent.type = TouchEvent.TOUCH_DOWN;
                touchEvent.pointer = pointerId;
                touchEvent.x = touchX[pointerId] = (int) (event.getX(pointerIndex) * scaleX);
                touchEvent.y = touchY[pointerId] = (int) (event.getY(pointerIndex) * scaleY);
                isTouched[pointerId] = true;
                touchEventsBuffer.add(touchEvent);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                touchEvent = touchEventPool.newObject();
                touchEvent.type = TouchEvent.TOUCH_UP;
                touchEvent.pointer = pointerId;
                touchEvent.x = touchX[pointerId] = (int) (event.getX(pointerIndex) * scaleX);
                touchEvent.y = touchY[pointerId] = (int) (event.getY(pointerIndex) * scaleY);
                isTouched[pointerId] = false;
                touchEventsBuffer.add(touchEvent);
                break;

            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    pointerIndex = i;
                    pointerId = event.getPointerId(pointerIndex);

                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[pointerId] = (int) (event.getX(pointerIndex) * scaleX);
                    touchEvent.y = touchY[pointerId] = (int) (event.getY(pointerIndex) * scaleY);
                    touchEventsBuffer.add(touchEvent);
                }
                break;
            }
            return gestureDetector.onTouchEvent(event);
        }
    }
    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            Log.d("TouchHandler", "Swipe Right");
                            setSwipeSuccess(e2, "RIGHT");
                        } else {
                            Log.d("TouchHandler", "Swipe Left");
                            setSwipeSuccess(e2, "LEFT");
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            Log.d("TouchHandler", "Swipe Down");
                            setSwipeSuccess(e2, "DOWN");
                        } else {
                            Log.d("TouchHandler", "Swipe Up");
                            setSwipeSuccess(e2, "UP");
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void setSwipeSuccess(MotionEvent event, String string){
        action = MotionEventCompat.getActionMasked(event);
        pointerIndex = MotionEventCompat.getActionIndex(event);
        pointerId = event.getPointerId(pointerIndex);

        touchEvent = touchEventPool.newObject();
        switch(string){
            case "UP":
                touchEvent.type = TouchEvent.TOUCH_SWIPED_UP;
                break;
            case "RIGHT":
                touchEvent.type = TouchEvent.TOUCH_SWIPED_RIGHT;
                break;
            case "DOWN":
                touchEvent.type = TouchEvent.TOUCH_SWIPED_DOWN;
                break;
            case "LEFT":
                touchEvent.type = TouchEvent.TOUCH_SWIPED_LEFT;
                break;
            default:
                Log.d("TouchHandler", "SwitchSwipe default");
                touchEvent.type = TouchEvent.TOUCH_UP;
                break;
        }
        touchEvent.pointer = pointerId;
        touchEvent.x = touchX[pointerId] = (int) (event.getX(pointerIndex) * scaleX);
        touchEvent.y = touchY[pointerId] = (int) (event.getY(pointerIndex) * scaleY);
        isTouched[pointerId] = false;
        touchEventsBuffer.add(touchEvent);
    }



    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= MAX_TOUCHPOINTS)
                return false;
            else
                return isTouched[pointer];
        }
    }

    public int getTouchX(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchX[pointer];
        }
    }

    public int getTouchY(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchY[pointer];
        }
    }

    public List<TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents.size();
            for (int i = 0; i < len; i++)
                touchEventPool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

}
