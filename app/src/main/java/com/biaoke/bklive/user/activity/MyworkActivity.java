package com.biaoke.bklive.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.biaoke.bklive.R;
import com.biaoke.bklive.base.BaseActivity;
import com.biaoke.bklive.message.AppConsts;
import com.biaoke.bklive.user.eventbus.Event_mywork;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class MyworkActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_nickName)
    EditText etNickName;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywork);
        ButterKnife.bind(this);
    }

    @Override
    protected String getPowerBarColors() {
        return AppConsts.POWER_BAR_BACKGROUND;
    }


    @OnClick({R.id.back, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_save:
                String myWork = etNickName.getText().toString();
                EventBus.getDefault().post(new Event_mywork(myWork));
                finish();
                break;
        }
    }
}
