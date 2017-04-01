package com.biaoke.bklive.bottombar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.biaoke.bklive.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 将状态条单独封装起来
 * <p>
 * 这种封装方式和封装类似iPhone的SegmentBar不太一样，不是在代码中生成Button。
 * 这里与布局文件相结合。通过inflater布局文件，来得到每个Item。
 *
 * @author MichaelYe
 * @since 2012-9-5
 */
public class BottomBar extends LinearLayout implements OnClickListener {

    private static final int TAG_0 = 0;
    private static final int TAG_1 = 1;
    private Context mContext;
    private TextView tvOne;
    private PopupWindow popupWindow_vedio;

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public BottomBar(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private List<View> itemList;

    /**
     * get the buttons from layout
     * <p>
     * 得到布局文件中的按钮
     */
    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.bottom_bar, null);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
//	    tvOne = (TextView)layout.findViewById(R.id.tv_warming);
        ImageView btnOne = (ImageView) layout.findViewById(R.id.main_unselect);
        ImageView btnTwo = (ImageView) layout.findViewById(R.id.main_me_unselect);
        ImageView btn_main = (ImageView) findViewById(R.id.menu_putvedio);
//        btn_main.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopWindow();
//                popupWindow_vedio.showAtLocation(v, Gravity.BOTTOM, 0, 0);
//            }
//        });
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnOne.setTag(TAG_0);
        btnTwo.setTag(TAG_1);
        itemList = new ArrayList<View>();
        itemList.add(btnOne);
        itemList.add(btnTwo);
        this.addView(layout);
    }

//    private void showPopWindow() {
//        final View contentView = LayoutInflater.from(mContext).inflate(R.layout.live_style, null);
//        popupWindow_vedio = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        //点击popwindow以外的布局让pop消失
//        popupWindow_vedio.setOutsideTouchable(true);
//    }

    @Override
    public void onClick(View v) {
        int tag = (Integer) v.getTag();
        switch (tag) {
            case TAG_0:
                setNormalState(lastButton);
                setSelectedState(tag);
                break;
            case TAG_1:
                setNormalState(lastButton);
                setSelectedState(tag);
                break;
        }
    }

    private int lastButton = -1;

    /**
     * set the default bar item of selected
     * <p>
     * 设置默认选中的Item
     */
    public void setSelectedState(int index) {
        if (index != -1 && onItemChangedListener != null) {
            if (index > itemList.size()) {
                throw new RuntimeException("the value of default bar item can not bigger than string array's length");
            }
            itemList.get(index).setSelected(true);
            onItemChangedListener.onItemChanged(index);
            lastButton = index;
        }
    }

    /**
     * set the normal state of the button by given index
     * <p>
     * 恢复未选中状态
     */
    private void setNormalState(int index) {
        if (index != -1) {
            if (index > itemList.size()) {
                throw new RuntimeException("the value of default bar item can not bigger than string array's length");
            }
            itemList.get(index).setSelected(false);
        }
    }

    /**
     * make the red indicate VISIBLE
     * <p>
     * 设置我执行按钮右上角的红色小图标的可见
     */
    public void showIndicate(int value) {
        tvOne.setText(value + "");
        tvOne.setVisibility(View.VISIBLE);
    }

    /**
     * make the red indicate GONE
     * <p>
     * 隐藏 我执行按钮右上角的红色小图标
     */
    public void hideIndicate() {
        tvOne.setVisibility(View.GONE);
    }

    public interface OnItemChangedListener {
        public void onItemChanged(int index);
    }

    private OnItemChangedListener onItemChangedListener;

    public void setOnItemChangedListener(OnItemChangedListener onItemChangedListener) {
        this.onItemChangedListener = onItemChangedListener;
    }
}
