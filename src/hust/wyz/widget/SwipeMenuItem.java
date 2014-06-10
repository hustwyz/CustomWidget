
package hust.wyz.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SwipeMenuItem extends HorizontalScrollView {

    private static final int STATE_SCROLL_TO_LEFT = 1;

    private static final int STATE_SCROLL_TO_RIGHT = 2;

    private static final int STATE_SCROLL_BACK_RIGHT = 3;

    private Context mContext;

    private View mContent;
    
    private View mMenuDelete;
    
    private View mMenuTop;

    private int mOffset;

    private GestureDetector mGestureDetector;

    private int mState = -1;
    
    private boolean isOpen = false;
    
    public SwipeMenuItem(Context context) {
        super(context);
        init(context);
    }
    
    public SwipeMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setEvents();
        hiddenEffectEdge();
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        setHorizontalFadingEdgeEnabled(false);
        View root = View.inflate(mContext, R.layout.item_swipe_menu, this);
        mContent = root.findViewById(R.id.content);
        mMenuDelete = root.findViewById(R.id.menu_delete);
        mMenuTop = root.findViewById(R.id.menu_top);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        int menuWidth = screenW / 4;
        mContent.getLayoutParams().width = screenW;
        mMenuDelete.getLayoutParams().width = menuWidth;
        mMenuTop.getLayoutParams().width = menuWidth;
        mOffset = menuWidth / 2;
    }

    private void setEvents() {
        mGestureDetector = new GestureDetector(mContext, new OnMyGestureListener());
        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                int action = event.getAction();
                switch (action & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP: {
                        switch (mState) {
                            case STATE_SCROLL_TO_LEFT:
                                openMenu();
                                break;
                            case STATE_SCROLL_TO_RIGHT:
                                closeMenu();
                                break;
                            case STATE_SCROLL_BACK_RIGHT:
                                closeMenu();
                                break;
                        }
                        mState = -1;
                    }
                        break;
                }
                return false;
            }
        });
    }

    private void hiddenEffectEdge() {
        try {
            Method method = getClass().getMethod("setOverScrollMode", int.class);
            Field field = getClass().getField("OVER_SCROLL_NEVER");
            if (method != null && field != null) {
                method.invoke(this, field.getInt(View.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void closeMenu() {
        post(new Runnable() {
            public void run() {
                smoothScrollTo(0, 0);
                isOpen = false;
            }
        });
    }
    
    public void reset(){
        scrollTo(0, 0);
    }

    public void openMenu() {
        post(new Runnable() {
            public void run() {
                smoothScrollTo(10000, 0);
                isOpen = true;
            }
        });
    }

    class OnMyGestureListener extends SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            try {
                float offset = e2.getX() - e1.getX();
                if (offset < 0) {
                    if (offset > -mOffset) {
                        mState = STATE_SCROLL_TO_RIGHT;
                    } else {
                        mState = STATE_SCROLL_TO_LEFT;
                    }
                } else {
                    mState = STATE_SCROLL_BACK_RIGHT;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

    }

}
