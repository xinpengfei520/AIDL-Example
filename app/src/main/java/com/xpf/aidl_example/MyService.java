package com.xpf.aidl_example;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by x-sir on 2019.01.12 :)
 * Function:
 */
public class MyService extends Service {

    private static final String TAG = "MyService";
    private IMyAidlActivity mCallback;

    @Override
    public void onCreate() {
        Log.e(TAG, "MyService.onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e(TAG, "MyService.onStart startId=" + startId);
    }

    @Override
    public IBinder onBind(Intent t) {
        Log.e(TAG, "MyService.onBind");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "MyService.onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "MyService.onUnbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        Log.e(TAG, "MyService.onRebind");
        super.onRebind(intent);
    }

    private String getNames() {
        Log.e(TAG, "MyService.getName");
        return "name from service";
    }

    private int getAges() {
        Log.e(TAG, "MyService.getAge");
        return 26;
    }

    private final IMyAidlService.Stub mBinder = new IMyAidlService.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.e(TAG, "MainActivity -> basicTypes()");
        }

        @Override
        public void registerTestCall(IMyAidlActivity cb) throws RemoteException {
            Log.e(TAG, "MyService.AIDLService.registerTestCall");
            mCallback = cb;
        }

        @Override
        public void invokeCallBack() throws RemoteException {
            Log.e(TAG, "MyService.AIDLService.invokeCallBack");
            RectBean rect = new RectBean();
            rect.bottom = -1;
            rect.left = -1;
            rect.right = 1;
            rect.top = 1;
            mCallback.performAction(rect);
        }

        @Override
        public String getName() throws RemoteException {
            Log.e(TAG, "MyService.AIDLService.getName");
            return getNames();
        }


        @Override
        public int getAge() throws RemoteException {
            Log.e(TAG, "MyService.AIDLService.getAge");
            return getAges();
        }
    };
}
