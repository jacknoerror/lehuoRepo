/**
 * 
 */
package com.lehuozu.ui.product;

import android.view.View;
import android.widget.EditText;

import com.lehuozu.R;
import com.lehuozu.data.MyData;
import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.JackFinishActivityReceiver;
import com.lehuozu.net.action.goods.AddCommentReq;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.vo.User;

/**
 * @author tao
 *
 */
public class CommentActivity extends MyTitleActivity {

	EditText edit;
	protected int goods_id;
	private int order_id;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_comment;
	}

	@Override
	public void initView() {
		titleManager.setTitleName("评论产品");
		titleManager.initTitleBack();
		titleManager.setRightText("提交", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String cmmt = edit.getText().toString();
				
				User me = MyData.data().getMe();
				if(null==me||goods_id==0||order_id==0) return;
				ActionPhpRequestImpl actReq = new AddCommentReq(me.getUser_id(), goods_id, cmmt,order_id);
				ActionPhpReceiverImpl actRcv = new JackFinishActivityReceiver(CommentActivity.this);
				ActionBuilder.getInstance().request(actReq, actRcv);
				
			}
		});
		goods_id = getIntent().getIntExtra(NetConst.EXTRAS_GOODS_ID, 0);
		order_id = getIntent().getIntExtra(NetConst.EXTRAS_ORDER_ID, 0);
		edit = (EditText) this.findViewById(R.id.et_comment);

	}

}
