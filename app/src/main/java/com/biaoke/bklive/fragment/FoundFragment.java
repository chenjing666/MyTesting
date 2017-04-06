package com.biaoke.bklive.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.biaoke.bklive.R;
import com.biaoke.bklive.activity.PLVideoViewActivity;
import com.biaoke.bklive.adapter.liveItemAdapter;
import com.biaoke.bklive.bean.live_item;
import com.biaoke.bklive.imagecycleview.ImageCycleView;
import com.biaoke.bklive.message.Api;
import com.xlibs.xrv.LayoutManager.XGridLayoutManager;
import com.xlibs.xrv.listener.OnLoadMoreListener;
import com.xlibs.xrv.listener.OnRefreshListener;
import com.xlibs.xrv.view.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.MediaType;


/**
 * Created by hasee on 2017/3/30.
 */

public class FoundFragment extends Fragment {
    @BindView(R.id.icv_topView)
    ImageCycleView mImageCycleView;
    Unbinder unbinder;
    @BindView(R.id.recyclerview_found)
    XRecyclerView recyclerviewFound;
    private List<live_item> recyclerDataList = new ArrayList<>();
    private View mHeaderView;
    private View mFooterView;
    private liveItemAdapter liveItemAdapter;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_un_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (!recyclerDataList.isEmpty()) {
            recyclerDataList.clear();
        }
        myImagecycleview();

        for (int i = 0; i < 3; i++) {
            live_item liveItem = new live_item("", "", "", "", "", i+i+"", "", "", "", "", "");
            recyclerDataList.add(liveItem);
        }

//        recyclerviewFound.setPullRefreshEnabled(false);
//        recyclerviewFound.setLoadingMoreEnabled(false);
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header, null);
        imageView = (ImageView) mHeaderView.findViewById(R.id.headiv_found);
        imageView.setBackgroundResource(R.drawable.header_found);
        AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
        anim.start();
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.footer, null);
        recyclerviewFound.addHeaderView(mHeaderView, 80);
        recyclerviewFound.addFootView(mFooterView, 50);

//        recyclerviewFound.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                recyclerviewFound.refreshComplete(); //下拉刷新完成
//            }
//
//            @Override
//            public void onLoadMore() {
//                recyclerviewFound.loadMoreComplete();//加载更多完成
//            }
//        });
        //设置布局管理器
//        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 3);
        recyclerviewFound.setLayoutManager(new XGridLayoutManager(getActivity(), 2));
        //设置适配器
        liveItemAdapter = new liveItemAdapter(getActivity());
        liveItemAdapter.bind(recyclerDataList);
        liveItemAdapter.setOnItemClickListener(listen);
        recyclerviewFound.setAdapter(liveItemAdapter);
        recyclerviewFound.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        recyclerviewFound.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        return view;
    }

    /**
     * refresh
     */
    private void refreshData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initRefreshData();
                recyclerviewFound.refreshComplate();
            }
        }, 2000);
    }

    private void initRefreshData() {
        for (int i = 0; i < 3; i++) {
            live_item liveItem = new live_item("", "", "", "", "", i+i+"", "", "", "", "", "");
            recyclerDataList.add(liveItem);
        }
    }

    /**
     * load more
     */
    private void loadMoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initLoadMoreData();
                recyclerviewFound.loadMoreComplate();
            }
        }, 2000);
    }

    private void initLoadMoreData() {
        for (int i = 0; i < 3; i++) {
            live_item liveItem = new live_item("", "", "", "", "", i+i+"", "", "", "", "", "");
            recyclerDataList.add(liveItem);
        }
    }

    private liveItemAdapter.OnItemClickListener listen = new liveItemAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int postion) {
            Toast.makeText(getActivity(), "发现页面" + postion, Toast.LENGTH_SHORT).show();
            Intent intent_video = new Intent(getActivity(), PLVideoViewActivity.class);
            startActivity(intent_video);
        }
    };


//    @Override
//    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
//        super.onMultiWindowModeChanged(isInMultiWindowMode);
//
//        imageView.setImageResource(R.drawable.header_found);
//        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
//        animationDrawable.start();
//    }

    private void myImagecycleview() {
        //		mImageCycleView.setAutoCycle(false); //关闭自动播放
//        mImageCycleView.setCycleDelayed(2000);//设置自动轮播循环时间
//
//		mImageCycleView.setIndicationStyle(ImageCycleView.IndicationStyle.COLOR,
//				Color.BLUE, Color.RED, 1f);

//		mImageCycleView.setIndicationStyle(ImageCycleView.IndicationStyle.IMAGE,
//				R.drawable.dian_unfocus, R.drawable.dian_focus, 1f);

        List<ImageCycleView.ImageInfo> list = new ArrayList<ImageCycleView.ImageInfo>();

        //res图片资源
        list.add(new ImageCycleView.ImageInfo(R.drawable.a1, "111111111111", ""));
        list.add(new ImageCycleView.ImageInfo(R.drawable.a2, "222222222222222", ""));
        list.add(new ImageCycleView.ImageInfo(R.drawable.a3, "3333333333333", ""));

        //SD卡图片资源
//		list.add(new ImageCycleView.ImageInfo(new File(Environment.getExternalStorageDirectory(),"a1.jpg"),"11111",""));
//		list.add(new ImageCycleView.ImageInfo(new File(Environment.getExternalStorageDirectory(),"a2.jpg"),"22222",""));
//		list.add(new ImageCycleView.ImageInfo(new File(Environment.getExternalStorageDirectory(),"a3.jpg"),"33333",""));


        //使用网络加载图片
//		list.add(new ImageCycleView.ImageInfo("http://img.lakalaec.com/ad/57ab6dc2-43f2-4087-81e2-b5ab5681642d.jpg","11","eeee"));
//		list.add(new ImageCycleView.ImageInfo("http://img.lakalaec.com/ad/cb56a1a6-6c33-41e4-9c3c-363f4ec6b728.jpg","222","rrrr"));
//		list.add(new ImageCycleView.ImageInfo("http://img.lakalaec.com/ad/e4229e25-3906-4049-9fe8-e2b52a98f6d1.jpg", "333", "tttt"));

        mImageCycleView.setOnPageClickListener(new ImageCycleView.OnPageClickListener() {
            @Override
            public void onClick(View imageView, ImageCycleView.ImageInfo imageInfo) {
                Toast.makeText(getActivity(), "你点击了" + imageInfo.value.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        mImageCycleView.loadData(list, new ImageCycleView.LoadImageCallBack() {
            @Override
            public ImageView loadAndDisplay(ImageCycleView.ImageInfo imageInfo) {

                //本地图片
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
                return imageView;


//				//使用SD卡图片
//				SmartImageView smartImageView=new SmartImageView(MainActivity.this);
//				smartImageView.setImageURI(Uri.fromFile((File)imageInfo.image));
//				return smartImageView;

//				//使用SmartImageView，既可以使用网络图片也可以使用本地资源
//				SmartImageView smartImageView=new SmartImageView(MainActivity.this);
//				smartImageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
//				return smartImageView;

                //使用BitmapUtils,只能使用网络图片
//				BitmapUtils bitmapUtils = new BitmapUtils(MainActivity.this);
//				ImageView imageView = new ImageView(MainActivity.this);
//				bitmapUtils.display(imageView, imageInfo.image.toString());
//				return imageView;


            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getVideo(String content) {
        OkHttpUtils
                .postString()
                .url(Api.ENCRYPT64)
                .content(content)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("失败的返回", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.d("成功的返回", response);
//                        Okhttputils(Api.LOGIN,response);
                        OkHttpUtils.postString()
                                .url(Api.VIDEO_LIVE)
                                .content(response)
                                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        Log.d("失败的返回", e.getMessage());
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
//                                        Log.d("成功的返回", response);
                                        OkHttpUtils.postString()
                                                .url(Api.UNENCRYPT64)
                                                .content(response)
                                                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                                .build()
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onError(Call call, Exception e, int id) {
                                                        Log.d("失败的返回", e.getMessage());
                                                    }

                                                    @Override
                                                    public void onResponse(String response, int id) {
                                                        Log.d("成功的返回", response);
                                                        try {
                                                            JSONObject jsonobject = new JSONObject(response);
//                                                                    "Protocol":"Explore",
//                                                                    "UserId":"0",		// 用户ＩＤ
//                                                                    "NickName":"test1",		//用户昵称
//                                                                    "IconUrl":"http://server-test.bk5977.com:8800/BK/icon.png",		//用户头像
//                                                                    "Exp":"0",		//热度 心形后面的数字
//                                                                    "Title":"0",		//视频标题
//                                                                    "SnapshotUrl":"0",		//封面URL
//                                                                    "VideoUrl":"0",		//视频URL
//                                                                    "Format":"mp4",		//视频格式，mp4 m3u8
//                                                                    "HV":"H"	//H 竖屏 V 横屏
//                                                                    "Type":"1"	//1 直播 2视频
                                                            String Protocol = jsonobject.getString("Protocol");
                                                            String UserId = jsonobject.getString("UserId");
                                                            String NickName = jsonobject.getString("NickName");
                                                            String IconUrl = jsonobject.getString("IconUrl");//用户头像
                                                            String Exp = jsonobject.getString("Exp");//热度 心形后面的数字
                                                            String Title = jsonobject.getString("Title");
                                                            String SnapshotUrl = jsonobject.getString("SnapshotUrl");//封面URL
                                                            String VideoUrl = jsonobject.getString("VideoUrl");
                                                            String Format = jsonobject.getString("Format");
                                                            String HV = jsonobject.getString("HV");
                                                            String Type = jsonobject.getString("Type");

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }
}
