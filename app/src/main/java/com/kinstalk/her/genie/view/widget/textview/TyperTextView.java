package com.kinstalk.her.genie.view.widget.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.kinstalk.her.genie.R;

import java.util.Random;

/**
 * @author Gc
 * @date 2019/7/24
 */
@SuppressLint("AppCompatCustomView")
public class TyperTextView extends TextView {

    public static final int INVALIDATE = 0x767;
    private Random random;
    private CharSequence mText;
    private Handler handler;
    private int charIncrease;
    private int typerSpeed;
    private AnimationListener mListener;

    public TyperTextView(Context context) {
        this(context, null);
    }

    public TyperTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TyperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TyperTextView);
        typerSpeed = typedArray.getInt(R.styleable.TyperTextView_typerSpeed, 100);
        charIncrease = typedArray.getInt(R.styleable.TyperTextView_charIncrease, 2);
        typedArray.recycle();

        random = new Random();
        mText = getText();
        handler = new Handler(msg -> {
            int currentLength = getText().length();
            if (currentLength < mText.length()) {
                if (currentLength + charIncrease > mText.length()) {
                    charIncrease = mText.length() - currentLength;
                }
                append(mText.subSequence(currentLength, currentLength + charIncrease));
                long randomTime = typerSpeed + random.nextInt(typerSpeed);
                Message message = Message.obtain();
                message.what = INVALIDATE;
                handler.sendMessageDelayed(message, randomTime);
                return false;
            } else {
                if (mListener != null) {
                    mListener.onAnimationEnd();
                }
            }
            return false;
        });
    }

    public void animateText(CharSequence text, AnimationListener listener) {
        if (text == null) {
            throw new RuntimeException("text must not  be null");
        }

        this.mListener = listener;
        mText = text;
        setText("");
        Message message = Message.obtain();
        message.what = INVALIDATE;
        handler.sendMessage(message);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeMessages(INVALIDATE);
    }

    public interface AnimationListener {
        void onAnimationEnd();
    }
}
