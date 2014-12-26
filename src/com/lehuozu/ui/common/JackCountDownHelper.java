package com.lehuozu.ui.common;

import android.os.SystemClock;
import android.widget.TextView;

import com.lehuozu.entity.MyEvent;

import de.greenrobot.event.EventBus;

/**
 * 获取验证码(30) 
 * 调去start(int)来开始计时，中断则用stop()
 * @author taotao
 * @Date 2014-10-21
 */
public class JackCountDownHelper  {
	private TextView cdTextView;
	
	int counting;

	private CharSequence origText;

	private EventBus bus;

	public JackCountDownHelper(TextView cdTextView) {
		super();
		this.cdTextView = cdTextView;
		bus = EventBus.getDefault();
	}
	
	public void start(int sec){
		if(null==cdTextView) return;
		if(counting>0) return; // 已经在计时
		
		try{
			bus.register(this);
		}catch(Exception e){}
		counting = 30;
		origText = cdTextView.getText();
		
		bus.post(new MyEvent("going"));
		cdTextView.setEnabled(false);
	}

	public void stop(){
		counting = 0;
		if(null!=cdTextView)cdTextView.setEnabled(true);
		if(null!=origText)cdTextView.setText(origText);
		try{
			bus.unregister(this);
		}catch(Exception e){
		}
	}

	private void updateText() {
		if(null==cdTextView||null==origText) return;
		cdTextView.setText(String.format("%s(%d)", origText,counting));
		
	}

	public void onEventMainThread(MyEvent event) {
		if(event.equalsMsg("going")){
			updateText();
			if(counting>0)bus.post(new MyEvent("waiting"));
			else stop();
		}
		
	}

	public void onEventBackgroundThread(MyEvent event) {
		if(event.equalsMsg("waiting")){
			SystemClock.sleep(1000);
			counting--;
			bus.post(new MyEvent("going"));
		}
	}
}
