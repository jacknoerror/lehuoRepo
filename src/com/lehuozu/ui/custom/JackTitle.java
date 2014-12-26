package com.lehuozu.ui.custom;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.data.MyData;


public class JackTitle extends RelativeLayout {
	View mView;
	
	TextView title;
	ImageView back;
	
	private Activity activity;
	
	public JackTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) { return; }
		setId(R.id.jacktitle);
		mView = LayoutInflater.from(context).inflate(R.layout.layout_titleblue	, this,true);
		title =(TextView)mView.findViewById(R.id.tv_title);
		back = (ImageView)mView.findViewById(R.id.btn_title_back);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				back();
			}
		});
		/*if (isInEditMode()) { return; }
		
		TypedArray a = context.obtainStyledAttributes(attrs,     
                R.styleable.JackTitle); 
		
		String text = a.getString(R.styleable.JackTitle_text);
		title.setText(text);
		
		a.recycle();*/
	}
	
	public void setStyle(){
		//TODO
	}
	
	private void back(){
		if(null!=activity){
			activity.finish();//
		}
	}
	public void setBackBtnActivity(Activity activity){
		this.activity = activity;
		if(null!=activity&&back.getVisibility()!=View.VISIBLE){
			back.setVisibility(View.VISIBLE);
		}
	}

	
	public static JackTitle getJackTitle(Activity activity){
		return (JackTitle) activity.findViewById(R.id.jacktitle);
	}
	
	
}
