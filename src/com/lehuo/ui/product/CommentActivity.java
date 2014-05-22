/**
 * 
 */
package com.lehuo.ui.product;

import android.view.View;
import android.widget.EditText;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.JackFinishActivityReceiver;
import com.lehuo.net.action.goods.AddCommentReq;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.vo.User;

/**
 * @author tao
 *
 */
public class CommentActivity extends MyTitleActivity {

	EditText edit;
	protected int goods_id;
	
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
				if(null==me||goods_id==0) return;
				ActionPhpRequestImpl actReq = new AddCommentReq(me.getUser_id(), goods_id, cmmt);//TODO
				ActionPhpReceiverImpl actRcv = new JackFinishActivityReceiver(CommentActivity.this);
				ActionBuilder.getInstance().request(actReq, actRcv);
				
			}
		});
		goods_id = getIntent().getIntExtra(NetConst.EXTRAS_GOODS_ID, 0);
		edit = (EditText) this.findViewById(R.id.et_comment);

	}

}
