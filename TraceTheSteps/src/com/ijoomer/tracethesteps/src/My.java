package com.ijoomer.tracethesteps.src;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class My extends Activity {

	CheckBox chkFirst;
	CheckBox chkSecond;
	CheckBox chkThird;
	TextView txt;
	Button btnClick;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.z);
		
		chkFirst = (CheckBox) findViewById(R.id.chkFirst);
		chkSecond = (CheckBox) findViewById(R.id.chkSecond);
		chkThird = (CheckBox) findViewById(R.id.chkThird);
		txt = (TextView) findViewById(R.id.txt);
		btnClick = (Button) findViewById(R.id.btnClick);
		
		btnClick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				txt.setText("");
				
				if(chkFirst.isChecked()){
					txt.setText(txt.getText().toString()+" "+chkFirst.getText().toString());
				}
				if(chkSecond.isChecked()){
					txt.setText(txt.getText().toString()+" "+chkSecond.getText().toString());
				}
				if(chkThird.isChecked()){
					txt.setText(txt.getText().toString()+" "+chkThird.getText().toString());
				}
				
			}
		});
	}
}
