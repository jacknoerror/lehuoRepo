package com.lehuozu.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.util.JackUtils;

public class JackEditWam extends RelativeLayout {
	View mView;

	EditText mEdit;
	Button bLeft, bRight;

	private OnEditListener onAddListener;

	private OnEditListener onMinusListener;

	public JackEditWam(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) {
			return;
		}
		// setId(R.id.jacktitle);
		mView = LayoutInflater.from(context).inflate(
				R.layout.jack_edit_with_add_minus, this, true);
		bLeft = (Button) mView.findViewById(R.id.btn_jackedit_wam_m);
		bRight = (Button) mView.findViewById(R.id.btn_jackedit_wam_a);
		mEdit = (EditText) mView.findViewById(R.id.et_jackedit_wam);
		bLeft.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != onMinusListener)
					onMinusListener.edit(JackEditWam.this);
			}
		});
		bRight.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != onAddListener)
					onAddListener.edit(JackEditWam.this);
			}
		});

	}

	public int getNum() {
		return Integer.parseInt(mEdit.getText().toString());
	}

	public void setNum(int n) {
		if (n < 0)
			n = 0;
		mEdit.setText("" + n);
	}

	public void add() {
		setNum(getNum() + 1);
	}

	public void minus() {
		if (getNum() > 1) {
			setNum(getNum() - 1);
		} else {
			// JackUtils.showDialog(, hintContent, positiveListener)
			if (null != onZero)
				onZero.onClick(bLeft);
		}
	}

	OnClickListener onZero;

	public void setOnZeroListener(OnClickListener listener) {
		this.onZero = listener;
	}

	public void setOnAddListener(OnEditListener onEditListener) {
		this.onAddListener = onEditListener;
	}

	public void setOnMinusListener(OnEditListener onEditListener) {
		this.onMinusListener = onEditListener;
	}

	public interface OnEditListener {
		public void edit(JackEditWam jackEW);
	}

}
