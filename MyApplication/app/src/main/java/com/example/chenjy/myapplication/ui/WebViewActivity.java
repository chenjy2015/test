package com.example.chenjy.myapplication.ui;

import android.util.Log;
import android.webkit.WebSettings;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityWebViewBinding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class WebViewActivity extends BaseUIActivity<ActivityWebViewBinding> {

    private String url = "http://61.191.30.195:8091/index.html#/detail/7a2d4c5fe5c8435a865b488bb338b391?messageid=373&pushid=1687&textid=7a2d4c5fe5c8435a865b488bb338b391&relay=111&url=http://bi.sunriver.cn:8070/WebReport/xmjd.html?rptname=%5B4e01%5D%5B8d85%5D%5B6d4b%5D%5B8bd5%5D%2F%5B9879%5D%5B76ee%5D%5B8282%5D%5B70b9%5D%5B63d0%5D%5B9192%5D%2F%5B9879%5D%5B76ee%5D%5B8282%5D%5B70b9%5D%5B63d0%5D%5B9192%5D.cpt%26fr_check_url=WedOct1711:10:31CST2018%26id=02a7ce2c-926a-4367-bef7-f0d184159eb6%26zzguid=07AAFD7E-5169-E911-93B9-0050568C13E9&accountid=12&accountname=源服务&articledes=2222&articletitle=外部链接--1&articletime=2019-07-04 20:57:21&userid=492896&secret=null&token=null&usercode=v_liminsheng";
    @Override
    public int getLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView() {


        //支持javascript
        mViewDataBinding.web.getSettings().setJavaScriptEnabled(true);
        //设置支持获取手势焦点。
        mViewDataBinding.web.requestFocusFromTouch();
        mViewDataBinding.web.getSettings().setDefaultTextEncodingName("UTF-8");
        mViewDataBinding.web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mViewDataBinding.web.getSettings().setAllowFileAccess(true);
        // 设置可以支持缩放
        mViewDataBinding.web.getSettings().setSupportZoom(false);
        mViewDataBinding.web.getSettings().setLoadsImagesAutomatically(true);
//      mViewDataBinding.web.getSettings().setPluginsEnabled(true);
        //扩大比例的缩放
        mViewDataBinding.web.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mViewDataBinding.web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mViewDataBinding.web.getSettings().setLoadWithOverviewMode(true);

        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        mViewDataBinding.web.getSettings().setBlockNetworkImage(true);
        //开启缓存机制
        mViewDataBinding.web.getSettings().setAppCacheEnabled(true);

        try {
            String uri = URLDecoder.decode(url,"UTF-8");
            mViewDataBinding.web.loadUrl(uri);
            Log.d("Web",uri);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }
}
