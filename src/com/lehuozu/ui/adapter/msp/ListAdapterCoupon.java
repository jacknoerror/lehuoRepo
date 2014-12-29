/**
 * 
 */
package com.lehuozu.ui.adapter.msp;

import java.util.Currency;
import java.util.List;

import android.widget.FrameLayout;
import android.widget.TextView;

import com.lehuozu.MyApplication;
import com.lehuozu.R;
import com.lehuozu.ui.custom.list.ListItemImpl;
import com.lehuozu.ui.custom.list.MspAdapter;
import com.lehuozu.vo.Coupon;

/**
 * @author taotao
 *
 */
public class ListAdapterCoupon extends MspAdapter {

	public ListAdapterCoupon() {
		super();
	}
	final int[] BGS = new int[]{R.color.coupon_bg1,R.color.coupon_bg2,R.color.coupon_bg3,R.color.coupon_bg4};
final static long a = System.currentTimeMillis()&10;//0610
	public class CouponViewHolder extends ViewHolderImpl{

		TextView tv_name,tv_desc,tv_timestart,tv_timeends;//tv_righttop;
		
		@Override
		public void init() {
			tv_name= (TextView)getHolderView().findViewById(R.id.tv_itemcoupon_name);
			tv_desc= (TextView)getHolderView().findViewById(R.id.tv_itemcoupon_desc);
			tv_timestart= (TextView)getHolderView().findViewById(R.id.tv_itemcoupon_timestart);
			tv_timeends= (TextView)getHolderView().findViewById(R.id.tv_itemcoupon_timeends);
//			tv_righttop= (TextView)getHolderView().findViewById(R.id.tv_itemc);
		}

		@Override
		public void setup(int position) {
			Coupon itm = (Coupon)getItem(position);
			tv_name.setText(itm.getType_name());
			tv_desc.setText("µÖ¿Û½ð¶î"+itm.getType_money());//
			tv_timestart.setText(itm.getUse_start_date());
			tv_timeends.setText(itm.getUse_end_date());
			try {
				((FrameLayout) getHolderView()).getChildAt(0).setBackgroundColor(
						MyApplication.app().getResources()
								.getColor(BGS[((int)a+position) % BGS.length]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public int getLayoutId() {
			return R.layout.item_coupon;
		}
		
	}
	
	@Override
	public ViewHolderImpl getHolderInstance() {
		return new CouponViewHolder();
	}
}
