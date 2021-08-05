package cn.carbs.android.expandabletextview.library;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

/**
 * @author yeqing
 * @des
 * @date 2021/7/12 18:48
 */
public class SpExpandTextView3 extends RelativeLayout {
    //展开文本
    private ExpandableTextView mOriginView;
    //收起文本
    private LinearLayout mLLShrink, mLLExpend;
    private LinearLayout mTextShrink, mTextExpend;
    private TextView mDesView2;

    private int lineCount1, lineCount2;
    private boolean initFlag, initFlag2;
    private int desMaxline;


    public SpExpandTextView3(Context context) {
        super(context);
        init();
    }

    public SpExpandTextView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpExpandTextView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpExpandTextView3(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Resources resources = getContext().getResources();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_new_expandtextview3, null);
        addView(view);

        mOriginView = (ExpandableTextView) view.findViewById(R.id.tv_content);
        mDesView2 = (TextView) view.findViewById(R.id.tv_des2);
        mLLShrink = (LinearLayout) view.findViewById(R.id.ll_shrink);
        mLLExpend = (LinearLayout) view.findViewById(R.id.ll_expand);
        mTextShrink = (LinearLayout) view.findViewById(R.id.tv_shrink);
        mTextExpend = (LinearLayout) view.findViewById(R.id.tv_expand);


        //收起的点击监听
        mTextShrink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lineCount1 >= mOriginView.getmMaxLinesOnShrink()) {
                    mOriginView.toggle();
                    mDesView2.setVisibility(View.GONE);
                } else {
                    mDesView2.setVisibility(View.VISIBLE);
                    if (mTemptLineCount <= desMaxline) {
                        mLLExpend.setVisibility(View.GONE);
                        mDesView2.setMaxLines(10);
                    } else {
                        mDesView2.setMaxLines(desMaxline);
                        mDesView2.setEllipsize(TextUtils.TruncateAt.END);
                        mLLExpend.setVisibility(View.VISIBLE);
                    }
                    mLLShrink.setVisibility(GONE);
                    mLLExpend.setVisibility(VISIBLE);
                    mDesView2.invalidate();
                }
            }
        });
        //展开监听
        mTextExpend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lineCount1 >= mOriginView.getmMaxLinesOnShrink()) {
                    mOriginView.toggle();
                    mDesView2.setVisibility(View.VISIBLE);
                    mDesView2.setMaxLines(10);
                    mDesView2.invalidate();
                } else {
                    mDesView2.setVisibility(View.VISIBLE);
                    mDesView2.setMaxLines(10);
                    mDesView2.invalidate();
                    mLLExpend.setVisibility(GONE);
                    mLLShrink.setVisibility(VISIBLE);
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

    }

    public void setContent(String text1, final String text2) {
        if (!TextUtils.isEmpty(text1)) {
            mOriginView.setText(text1);
            mOriginView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
                @Override
                public void onDraw() {
                    lineCount1 = mOriginView.getLineCount();
                    if (lineCount1 > 0) {
                        if (!initFlag) {
                            if (lineCount1 > mOriginView.getmMaxLinesOnShrink()) {
                                mLLExpend.setVisibility(View.VISIBLE);
                                mDesView2.setVisibility(View.GONE);
                            } else if (lineCount1 == mOriginView.getmMaxLinesOnShrink() && !TextUtils.isEmpty(text2)) {
                                mLLExpend.setVisibility(View.VISIBLE);
                                mDesView2.setVisibility(View.GONE);
                            } else {
                                desMaxline = 4 - lineCount1;
                                Log.d("yeqing", "setmMaxLinesOnShrink----" + desMaxline);
                                mDesView2.setVisibility(View.VISIBLE);
                                mLLExpend.setVisibility(View.GONE);

                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setDesText(text2);
//                                        mDesView.setText(text2);
                                    }
                                });
                            }
                            initFlag = true;
                        }
                    }
                }
            });
        } else {
            lineCount1 = 0;
            desMaxline = 4;
        }

        if (!TextUtils.isEmpty(text2)) {
            Log.d("yeqing", "MaxLines----" + desMaxline);
            setDesText(text2);
        }

    }

    private int mTemptLineCount;
    private void setDesText(final String text2) {
        mDesView2.setText(text2);
        mDesView2.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                mTemptLineCount = mDesView2.getLineCount();
                if(!initFlag2){
                    if (mTemptLineCount <= desMaxline) {
                        mLLExpend.setVisibility(View.GONE);
                        mDesView2.setMaxLines(10);
                    } else {
                        mDesView2.setMaxLines(desMaxline);
                        mDesView2.setEllipsize(TextUtils.TruncateAt.END);
                        mLLExpend.setVisibility(View.VISIBLE);
                    }
                    initFlag2 = true;
                }
            }
        });
    }

}
