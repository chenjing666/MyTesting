package com.biaoke.bklive.user.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biaoke.bklive.R;
import com.biaoke.bklive.base.BaseActivity;
import com.biaoke.bklive.message.AppConsts;
import com.biaoke.bklive.user.eventbus.Event_mywork;
import com.biaoke.bklive.user.eventbus.Event_nickname;
import com.biaoke.bklive.user.eventbus.Event_signture;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class EditUserActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.edit_head)
    RelativeLayout editHead;
    @BindView(R.id.edit_nickname)
    RelativeLayout editNickname;
    @BindView(R.id.edit_bkid)
    TextView editBkid;
    @BindView(R.id.edit_sex)
    RelativeLayout editSex;
    @BindView(R.id.edit_signture)
    RelativeLayout editSignture;
    @BindView(R.id.edit_impression)
    RelativeLayout editImpression;
    @BindView(R.id.edit_age)
    RelativeLayout editAge;
    @BindView(R.id.edit_emotion)
    RelativeLayout editEmotion;
    @BindView(R.id.edit_hometown)
    RelativeLayout editHometown;
    @BindView(R.id.edit_work)
    RelativeLayout editWork;
    @BindView(R.id.selectemotion)
    TextView selectemotion;
    @BindView(R.id.my_signture)
    TextView mySignture;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    @BindView(R.id.btn_home)
    TextView btnHome;
    @BindView(R.id.btn_work)
    TextView btnWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);//注册

    }

    @Override
    protected String getPowerBarColors() {
        return AppConsts.POWER_BAR_BACKGROUND;
    }

    Calendar c = Calendar.getInstance();
    int yearnow = c.get(Calendar.YEAR);

    private Handler myHandler = new Handler() {

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    int user_age = yearnow - (int) message.obj;
                    tvAge.setText(user_age + "");
                    break;
                case 2:
                    break;
            }
        }
    };

    @OnClick({R.id.back, R.id.edit_head, R.id.edit_nickname, R.id.edit_sex, R.id.edit_signture, R.id.edit_impression, R.id.edit_age, R.id.edit_emotion, R.id.edit_hometown, R.id.edit_work})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.edit_head:
                headPopupWindow(view);
                break;
            case R.id.edit_nickname:
                Intent intent_nickname = new Intent(this, NicknameActivity.class);
                startActivity(intent_nickname);
                break;
            case R.id.edit_sex:
                sexPop();
                sexPopw.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.edit_signture:
                Intent intent_signture = new Intent(this, SigntureActivity.class);
                startActivity(intent_signture);
                break;
            case R.id.edit_impression:
                break;
            case R.id.edit_age:

                new DatePickerDialog(EditUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        tvAge.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = year;   //从这里把你想传递的数据放进去就行了
                        myHandler.sendMessage(msg);
                    }
                }, 2000, 1, 2).show();

                break;
            case R.id.edit_emotion:
                emotionPop();
                emotionPopw.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.edit_hometown:
                selectHome();
                break;
            case R.id.edit_work:
                Intent intent_work = new Intent(this, MyworkActivity.class);
                startActivity(intent_work);
                break;
        }
    }

    //地区选择器
    private void selectHome() {
        CityPicker cityPicker = new CityPicker.Builder(EditUserActivity.this).textSize(20)
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .province("江苏省")
                .city("徐州市")
                .district("云龙区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .build();

        cityPicker.show();
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                btnHome.setText(citySelected[0] + citySelected[1]);
            }
//            tvResult.setText("选择结果：\n省：" + citySelected[0] + "\n市：" + citySelected[1] + "\n区："
//                    + citySelected[2] + "\n邮编：" + citySelected[3]);

            @Override
            public void onCancel() {
                Toast.makeText(EditUserActivity.this, "已取消", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void headPopupWindow(View v) {
        View headView = LayoutInflater.from(this).inflate(R.layout.item_pop_selectpic, null);
        LinearLayout button_sel = (LinearLayout) headView.findViewById(R.id.photo_sel);
        button_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headPopw.dismiss();
                selectPhoto();
            }
        });
        LinearLayout button_take = (LinearLayout) headView.findViewById(R.id.photo_take);
        button_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headPopw.dismiss();
                takePhoto();
            }
        });
        headPopw = new PopupWindow(headView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        headPopw.setTouchable(true);

        headPopw.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                return false;
            }
        });
        headPopw.setBackgroundDrawable(getResources().getDrawable(R.drawable.selectmenu_bg_downward));
        headPopw.showAsDropDown(v);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    ;

    private void selectPhoto() {
        final Intent intent = getPhotoPickIntent();
        startActivityForResult(intent, 200);
    }

    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        // 设置裁剪功能
        intent.putExtra("aspectX", 1);
        // 宽高比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);
        // 宽高值
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        // 返回裁剪结果
        return intent;
    }

    private void sexPop() {
        final View sex_View = LayoutInflater.from(this).inflate(R.layout.sex_select, null);
        sexPopw = new PopupWindow(sex_View, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ColorDrawable cd = new ColorDrawable(this.getResources().getColor(R.color.white));
        sexPopw.setBackgroundDrawable(cd);
        sexPopw.setOutsideTouchable(true);
        RelativeLayout rl_man = (RelativeLayout) sex_View.findViewById(R.id.rl_man);
        rl_man.setOnClickListener(sex);
        RelativeLayout rl_female = (RelativeLayout) sex_View.findViewById(R.id.rl_female);
        rl_female.setOnClickListener(sex);
        Button btn_cancel = (Button) sex_View.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(sex);
    }

    private View.OnClickListener sex = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_man:
                    ivSex.setImageResource(R.mipmap.man);
                    tvSex.setText("男");
                    sexPopw.dismiss();
                    break;
                case R.id.rl_female:
                    ivSex.setImageResource(R.mipmap.female);
                    tvSex.setText("女");
                    sexPopw.dismiss();
                    break;
                case R.id.btn_cancel:
                    sexPopw.dismiss();
                    break;
            }
        }
    };


    @Subscribe(threadMode = ThreadMode.MainThread)
//事件的处理会在UI线程中执行。事件处理时间不能太长，这个不用说的，长了会ANR的，对应的函数名是onEventMainThread。
    public void onEvent_signture(Event_signture evt) {
        String signture = evt.getMsg();
//        Toast.makeText(this, evt.getMsg(), Toast.LENGTH_SHORT).show();
        mySignture.setText(signture);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent_nickname(Event_nickname evt_nickname) {
        String nickname = evt_nickname.getNickname();
        tvNickName.setText(nickname);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent_mywork(Event_mywork evt_mywork) {
        String mywork = evt_mywork.getMyWork();
        btnWork.setText(mywork);
    }


    private PopupWindow emotionPopw, sexPopw, headPopw;

    private void emotionPop() {
        final View emotion_View = LayoutInflater.from(this).inflate(R.layout.emotion, null);
        emotionPopw = new PopupWindow(emotion_View, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ColorDrawable cd = new ColorDrawable(this.getResources().getColor(R.color.white));
        emotionPopw.setBackgroundDrawable(cd);
        emotionPopw.setOutsideTouchable(true);

        Button btn_secret = (Button) emotion_View.findViewById(R.id.secret);
        Button btn_single = (Button) emotion_View.findViewById(R.id.single);
        Button btn_loving = (Button) emotion_View.findViewById(R.id.loving);
        Button btn_married = (Button) emotion_View.findViewById(R.id.married);
        Button btn_canael = (Button) emotion_View.findViewById(R.id.canael);
        btn_secret.setOnClickListener(emo);
        btn_single.setOnClickListener(emo);
        btn_loving.setOnClickListener(emo);
        btn_married.setOnClickListener(emo);
        btn_canael.setOnClickListener(emo);

    }

    private View.OnClickListener emo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.secret:
                    selectemotion.setText("保密");
                    emotionPopw.dismiss();
                    break;
                case R.id.single:
                    selectemotion.setText("单身");
                    emotionPopw.dismiss();
                    break;
                case R.id.loving:
                    selectemotion.setText("恋爱中");
                    emotionPopw.dismiss();
                    break;
                case R.id.married:
                    selectemotion.setText("已婚");
                    emotionPopw.dismiss();
                    break;
                case R.id.canael:
                    emotionPopw.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//释放
    }
}
