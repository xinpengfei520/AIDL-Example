package com.xpf.aidl_example;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.text.MessageFormat;

/**
 * Created by x-sir on 2019.01.12 :)
 * Function:
 */
public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String ACTION = "com.xpf.aidl_example.action.AIDLService";
    private Button btn_bindService;
    private Button btn_unbindService;
    private Button btnCallBack;
    private Button btn_get_data;
    private IMyAidlService mService;
    private boolean isBind = false;

    @Override
    public void onCreate(Bundle icicle) {
        Log.e(TAG, "MainActivity -> onCreate");
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        initView();
        setClickListener();
    }

    /**
     * 设置点击事件
     */
    private void setClickListener() {
        btn_bindService.setOnClickListener(this);
        btn_unbindService.setOnClickListener(this);
        btnCallBack.setOnClickListener(this);
        btn_get_data.setOnClickListener(this);
    }

    /**
     * 初始化布局文件
     */
    private void initView() {
        btn_bindService = (Button) findViewById(R.id.btn_bindService);
        btn_unbindService = (Button) findViewById(R.id.btn_unbindService);
        btnCallBack = (Button) findViewById(R.id.btn_call_back);
        btn_get_data = (Button) findViewById(R.id.btn_get_data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bindService:
                Log.e(TAG, "MainActivity.点击了btn_bindService");
                bindService();
                break;
            case R.id.btn_unbindService:
                Log.e(TAG, "MainActivity.点击了btn_unbindService");
                unBindService();
                break;
            case R.id.btn_call_back:
                Log.e(TAG, "MainActivity.点击了btn_unbindService");
                try {
                    mService.invokeCallBack();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_get_data:
                Log.e(TAG, "MainActivity.点击了btn_get_data");
                if (mService == null) {
                    Toast.makeText(getApplicationContext(), "服务还没有绑定", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String dataFromService = mService.getName() + "---" + mService.getAge();
                    Log.e(TAG, "MainActivity.dataFromService==" + dataFromService);
                    Toast.makeText(getApplicationContext(), dataFromService, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void bindService() {
        if (!isBind) {
            Bundle args = new Bundle();
            Intent intent = new Intent(this, MyService.class);
            intent.setAction(ACTION);
            intent.putExtras(args);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            startService(intent);
            isBind = true;
            Toast.makeText(MainActivity.this, "服务绑定成功~", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "服务已经绑定，请不要重复绑定！", Toast.LENGTH_SHORT).show();
        }
    }

    private void unBindService() {
        if (isBind) {
            unbindService(mConnection);
            //stopService(intent);
            isBind = false;
            Toast.makeText(MainActivity.this, "服务解绑成功~", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "服务没有绑定，请先绑定服务再解绑！", Toast.LENGTH_SHORT).show();
        }
    }

    private IMyAidlActivity mCallback = new IMyAidlActivity.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.e(TAG, "MainActivity -> basicTypes()");
        }

        @Override
        public void performAction(RectBean rect) throws RemoteException {
            Log.e(TAG, "MainActivity -> performAction()");
            Log.e(TAG, MessageFormat.format("MainActivity.rect[bottom={0},top={1},left={2},right={3}]",
                    rect.bottom, rect.top, rect.left, rect.right));
            Toast.makeText(MainActivity.this,
                    "这个吐司是由 Service 回调 Activity 弹出来的", Toast.LENGTH_SHORT).show();
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(TAG, "MainActivity.onServiceConnected");
            mService = IMyAidlService.Stub.asInterface(service);
            try {
                mService.registerTestCall(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.e(TAG, "MainActivity.onServiceDisconnected");
            mService = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindService();
    }
}