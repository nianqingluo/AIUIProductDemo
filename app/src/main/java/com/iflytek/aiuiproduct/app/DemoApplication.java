package com.iflytek.aiuiproduct.app;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;

import com.iflytek.aiui.utils.log.DebugLog;
import com.iflytek.aiuilauncher.client.DeamonClient;
import com.iflytek.aiuilauncher.service.aidl.StartAction;
import com.iflytek.aiuiproduct.constant.ProductConstant;
import com.iflytek.aiuiproduct.exception.CrashCollector;

/**
 * Demo的Application对象。
 * 
 * @author <a href="http://www.xfyun.cn">讯飞开放平台</a>
 * @date 2016年6月28日 下午10:07:00 
 *
 */
public class DemoApplication extends Application {
	private static final String TAG = ProductConstant.TAG;

	private DeamonClient mDeamonClient;
	
	@Override
	public void onCreate() {
		CrashCollector collector = CrashCollector.getInstance();
		collector.init(getApplicationContext());
		
		// 注册守护
		registerDeamon();

		AudioManager mAudioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);

		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		System.out.println("maxVolume:"+maxVolume+"currentVolume:"+currentVolume);

		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 8, AudioManager.FLAG_SHOW_UI);

		DebugLog.LogD(TAG, "onCreate");

		super.onCreate();
	}
	
	private void registerDeamon() {
        StartAction startAction = new StartAction("service", "com.iflytek.aiuiproduct.action.DemoService");
		
		mDeamonClient = new DeamonClient();
		mDeamonClient.register(getApplicationContext(), startAction);
		
		DebugLog.LogD(TAG, "registerDeamon");
	}
	
}
