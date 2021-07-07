package cn.carbs.android.expandabletextview.library;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author yeqing
 * @des
 * @date 2021/7/7 18:48
 */
public class SpExpandTextView extends RelativeLayout {
    //展开文本
    private ExpandableTextView mOriginView;
    //不一样字体文本
    private TextView mDesView;
    //收起文本
    private LinearLayout mLLShrink;
    private TextView mShrinkView;

    public SpExpandTextView(Context context) {
        super(context);
        init();
    }

    public SpExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpExpandTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resources = getContext().getResources();

        View view = inflater.inflate(R.layout.layout_new_expandtextview, null);
        addView(view);

        mOriginView = (ExpandableTextView) view.findViewById(R.id.tv_content);
        mDesView = (TextView) view.findViewById(R.id.tv_des);
        mShrinkView = (TextView) view.findViewById(R.id.tv_shrink);
        mLLShrink = (LinearLayout) view.findViewById(R.id.ll_shrink);
        //收起的点击监听
        mShrinkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOriginView.toggle();
            }
        });

        mOriginView.setExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(ExpandableTextView view) {
                mDesView.setVisibility(VISIBLE);
                mLLShrink.setVisibility(VISIBLE);
            }

            @Override
            public void onShrink(ExpandableTextView view) {
                mDesView.setVisibility(GONE);
                mLLShrink.setVisibility(GONE);
            }
        });

    }

}
