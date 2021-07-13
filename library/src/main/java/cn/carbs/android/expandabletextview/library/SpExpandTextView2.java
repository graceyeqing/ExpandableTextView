package cn.carbs.android.expandabletextview.library;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import static cn.carbs.android.expandabletextview.library.ExpandableTextView.STATE_SHRINK;

/**
 * @author yeqing
 * @des
 * @date 2021/7/12 18:48
 */
public class SpExpandTextView2 extends RelativeLayout {
    //展开文本
    private ExpandableTextView mOriginView;
    //不一样字体文本
    private ExpandableTextView mDesView;
    //收起文本
    private LinearLayout mLLShrink, mLLExpend;
    private TextView mShrinkView;

    private int lineCount1, lineCount2;
    private boolean initFlag, initFlag2;

    public SpExpandTextView2(Context context) {
        super(context);
        init();
    }

    public SpExpandTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpExpandTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpExpandTextView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resources = getContext().getResources();

        View view = inflater.inflate(R.layout.layout_new_expandtextview2, null);
        addView(view);

        mOriginView = (ExpandableTextView) view.findViewById(R.id.tv_content);
        mDesView = (ExpandableTextView) view.findViewById(R.id.tv_des);
        mShrinkView = (TextView) view.findViewById(R.id.tv_shrink);
        mLLShrink = (LinearLayout) view.findViewById(R.id.ll_shrink);
        mLLExpend = (LinearLayout) view.findViewById(R.id.ll_expand);

        mOriginView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                lineCount1 = mOriginView.getLineCount();
                if (!initFlag) {
                    if (lineCount1 > 4) {
                        mLLExpend.setVisibility(View.VISIBLE);
                        mDesView.setVisibility(View.GONE);
                    } else {
                        mDesView.setmMaxLinesOnShrink(4 - lineCount1);
                        mDesView.setVisibility(View.VISIBLE);
                        mLLExpend.setVisibility(View.GONE);
                    }
                    initFlag = true;
                }

            }
        });

        mDesView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                lineCount2 = mDesView.getmTextLineCount();
                if (!initFlag2) {
                    if(lineCount2 <= mDesView.getmMaxLinesOnShrink()){
                        mLLExpend.setVisibility(View.GONE);
                    }else{
                        mLLExpend.setVisibility(View.VISIBLE);
                    }

                    initFlag2 = true;
                }

            }
        });

        //收起的点击监听
        mLLShrink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lineCount1 > 4) {
                    mOriginView.toggle();
                } else {
                    mDesView.toggle();
                }
            }
        });
        //展开监听
        mLLExpend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lineCount1 > 4) {
                    mOriginView.toggle();
                } else {
                    mDesView.toggle();
                }
            }
        });

        mOriginView.setExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(ExpandableTextView view) {
                mLLExpend.setVisibility(GONE);
                mLLShrink.setVisibility(VISIBLE);
            }

            @Override
            public void onShrink(ExpandableTextView view) {
                mLLShrink.setVisibility(GONE);
                mLLExpend.setVisibility(VISIBLE);
            }
        });

        mDesView.setExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(ExpandableTextView view) {
                mLLExpend.setVisibility(GONE);
                mLLShrink.setVisibility(VISIBLE);
            }

            @Override
            public void onShrink(ExpandableTextView view) {
                mLLShrink.setVisibility(GONE);
                mLLExpend.setVisibility(VISIBLE);
            }
        });

    }

    public static void setTextStyle(Context context, String text1, String text2, int style1, int style2, ExpandableTextView textView) {
        try {
            String content = text1 + "\n\n" + text2;
            SpannableString styledText = new SpannableString(content);
            styledText.setSpan(new TextAppearanceSpan(context, style1), 0, text1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(context, style2), text1.length(), content.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            textView.setText(styledText, TextView.BufferType.SPANNABLE);
        } catch (Exception e) {

        }
    }

    public void setContent(String text1, String text2) {
        if (!TextUtils.isEmpty(text1)) {
            mOriginView.setText(text1);
        }

        if (!TextUtils.isEmpty(text2)) {
            mDesView.setText(text2);
        }
    }
}
