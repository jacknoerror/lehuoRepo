/**
 * 
 */
package com.lehuo.ui.product;

import android.view.View;
import android.widget.EditText;

import com.lehuo.R;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.JackFinishActivityReceiver;
import com.lehuo.ui.MyTitleActivity;

/**
 * @author tao
 *
 */
public class CommentActivity extends MyTitleActivity {

	EditText edit;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_comment;
	}

	@Override
	public void initView() {
		titleManager.setTitleName("评论产品");
		titleManager.setRightText("提交", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String cmmt = edit.getText().toString();
				
				ActionPhpRequestImpl actReq = null;//TODO
				ActionPhpReceiverImpl actRcv = new JackFinishActivityReceiver(CommentActivity.this);
				ActionBuilder.getInstance().request(actReq, actRcv);
				
			}
		});
		
		edit = (EditText) this.findViewById(R.id.et_comment);

	}

}
