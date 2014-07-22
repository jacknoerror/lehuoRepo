package com.baidumap;

import android.os.SystemClock;
import android.util.Log;

import com.lehuo.data.MyData;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.user.SendCourierLocReq;
import com.lehuo.vo.User;

public class SendLocationThread extends Thread {
	public double[] locArr = new double[2];
	public boolean RUNNING  = true;
	
	
	@Override
	public void run() {
		while (RUNNING) {
			Log.i("locThread", "--running:" + locArr[0] + "+" + locArr[1]);
			// 定时重新获得提供器
			/*if (count % 8 == 7) {
				count -= 7;
				getProvider();
			}*/
			// 获取位置
				// tryLocation();
//			tryHandler.sendEmptyMessage(0);
			
			// 发请求
			if (locArr[0] != 0) {
//				searchByLatlon(locArr[0], locArr[1]);
				User me = MyData.data().getMe();
				if (null != me) {
					ActionPhpRequestImpl actReq = new SendCourierLocReq(
							GeoCoderActivity.addressname, locArr[0] + "", locArr[1] + "",
							me.getUser_id());
//					ActionPhpReceiverImpl actRcv = new JackShowToastReceiver(
//							null);
//					ActionBuilder.getInstance().request(actReq, actRcv);
					String result;
					try {
						result = NetStrategies.doHttpRequest(actReq.toHttpBody());
						Log.d("locThread", "service result:"+result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 通知修改位置
			/*if (null != aHandler) {
				aHandler.sendEmptyMessage(0);
			}*/

			SystemClock.sleep(1000 * 10);
//			count++;
		}
		super.run();
	}
	
}
