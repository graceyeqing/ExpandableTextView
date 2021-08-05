package cn.carbs.android.expandabletextview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import cn.carbs.android.expandabletextview.library.ExpandableEndTextView;
import cn.carbs.android.expandabletextview.library.ExpandableTextView;
import cn.carbs.android.expandabletextview.library.SpExpandTextView;
import cn.carbs.android.expandabletextview.library.SpExpandTextView2;
import cn.carbs.android.expandabletextview.library.SpExpandTextView3;

/**
 * Created by carbs on 2016/7/23.
 */
public class ActivityMain extends AppCompatActivity implements View.OnClickListener{

    private TextView mTVComparison;
    private Button mBtnUpdateText;
    private Button mBtnToListView;
    private ExpandableEndTextView spExpandTextView;
    private SpExpandTextView2 spExpandTextView2;
    private SpExpandTextView3 spExpandTextView3;

    private ExpandableTextView mETV;
    private CharSequence[] mPoems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPoems = getResources().getStringArray(R.array.poems);

        mTVComparison = (TextView)this.findViewById(R.id.tv_comparison);
        mBtnUpdateText = (Button)this.findViewById(R.id.button_update_text);
        mBtnToListView = (Button)this.findViewById(R.id.button_to_list_view);
//        mETV = (ExpandableTextView)this.findViewById(R.id.etv);
        spExpandTextView = findViewById(R.id.sp_etv);
        spExpandTextView2 = findViewById(R.id.sp_etv2);
        spExpandTextView3 = findViewById(R.id.sp_etv3);

        spExpandTextView.setText("此原中中国料，未此原料是参考文献：\n" +
                "1、《已使用化妆品原料名\n称目录》Review. Cosmetic ingredients found safe as " +
                "2、Cosmetic Ingredient Review. Cosmetic ingredients found safe as ");
//        spExpandTextView3.setContent("此原中中是此料是此原中中国原料是此料是此原中中国原料是此料是此原中中国原料是此料是","参考文献：\n" +
//                "1、《已使用化妆品原料名称目录》Review. Cosmetic ingredients found safe as \n" +
//                "2、《已使用化妆品原料名称目录》Review. Cosmetic ingredients found safe as \n" +
//                "3、Cosmetic Ingredient Review. Cosmetic ingredients found safe as ");

//        spExpandTextView.setContent("此原料是中国已知的化妆品原料，未收录在《化妆品安此原料是中国已知的化妆品原料，未收录在《化妆品安此原料是中国已知的化妆品原料，未收录在参考文献：" +
//                "1、《已使用化妆品原料名称目录》（2015）此原料是中国已知的化妆品原料，未收录在" +
//                "2、Cosmetic Ingredient Review. Cosmetic ingredients found safe as " +
//                "used (2015) http://www.cir-safety.org/sites/default/files/Safe_092" +
//                "posted110415.pdf","");

        mBtnUpdateText.setOnClickListener(this);
        mBtnToListView.setOnClickListener(this);

        // 测试添加OnClickListener的情况，功能正常。添加外部的onClick事件后，原来的点击toggle功能自动屏蔽，
        // 点击尾部的ClickableSpan仍然有效
        /*mETV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (mETV.getExpandState()){
                    case ExpandableTextView.STATE_SHRINK:
                        Toast.makeText(getApplicationContext(),"ExpandableTextView clicked, STATE_SHRINK",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case ExpandableTextView.STATE_EXPAND:
                        Toast.makeText(getApplicationContext(),"ExpandableTextView clicked, STATE_EXPAND",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });*/
//        mETV.setText(mPoems[0]);//在ExpandableTextView在创建完成之前改变文字，功能正常
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_to_list_view:
                gotoCheckInListView();
                break;
            case R.id.button_update_text:
                updateText();
                break;
        }
    }

    private void gotoCheckInListView(){
        Intent intent = new Intent(ActivityMain.this, ActivityListView.class);
        startActivity(intent);
    }

    private Random mRandom = new Random();
    private int prevRandomInt = -1;
    private int currRandomInt = -1;

    private void updateText(){
        currRandomInt = mRandom.nextInt(mPoems.length);
        while (prevRandomInt == currRandomInt){
            currRandomInt = mRandom.nextInt(mPoems.length);
        }
        prevRandomInt = currRandomInt;
        CharSequence newCS = mPoems[currRandomInt];

        mTVComparison.setText(newCS);//作为对比示例
        mETV.setText(newCS);//效果显示
    }
}