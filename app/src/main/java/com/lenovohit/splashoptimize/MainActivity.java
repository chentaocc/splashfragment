package com.lenovohit.splashoptimize;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private SplashFragment splashFragment;
    private ViewStub viewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashFragment = new SplashFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, splashFragment);
        transaction.commit();

        viewStub = (ViewStub)findViewById(R.id.content_viewstub);
        //1.判断当窗体加载完毕的时候,就把我们真正的布局加载进来
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                // 开启延迟加载
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        //将viewstub加载进来
                        viewStub.inflate();
                    }
                } );
            }
        });


        //2.判断当窗体加载完毕的时候执行,延迟一段时间是为了模拟做动画的耗时。
        getWindow().getDecorView().post(new Runnable() {

            @Override
            public void run() {
                // 开启延迟加载,也可以不用延迟可以立马执行（我这里延迟是为了实现fragment里面的动画效果的耗时）
                mHandler.postDelayed(new DelayRunnable(MainActivity.this, splashFragment) ,2000);
            }
        });
        //3.同时进行异步加载数据


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    static class DelayRunnable implements Runnable{
        private WeakReference<Context> contextRef;
        private WeakReference<SplashFragment> fragmentRef;

        public DelayRunnable(Context context, SplashFragment f) {
            contextRef = new WeakReference<>(context);
            fragmentRef = new WeakReference<>(f);
        }

        @Override
        public void run() {
            // 移除fragment
            if(contextRef!=null){
                SplashFragment splashFragment = fragmentRef.get();
                if(splashFragment==null){
                    return;
                }
                FragmentActivity activity = (FragmentActivity) contextRef.get();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.remove(splashFragment);
                transaction.commit();
            }
        }
    }
}
