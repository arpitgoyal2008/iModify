package labs.stemma.imodify;

import java.util.Random;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NamePicker_activity extends Activity implements OnClickListener, TextWatcher {

	private Button mButton_cancel;
	private Button mButton_set;

	private Button mButton_reset;
	private Button mButton_random;
	private String text;
	private EditText mEditText;
	private Random random;
	private String s[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name);
		setTitle("Write a message");

		mButton_cancel = (Button) findViewById(R.id.cancel);
		mButton_set = (Button) findViewById(R.id.set);
		mButton_reset = (Button) findViewById(R.id.button_reset);
		mButton_random = (Button) findViewById(R.id.button_random);
		mEditText = (EditText) findViewById(R.id.editText1);
		random = new Random();
		s = getResources().getStringArray(R.array.random_name_list);
	}

	@Override
	protected void onResume() {
		mButton_cancel.setOnClickListener(this);
		mButton_set.setOnClickListener(this);
		mButton_reset.setOnClickListener(this);
		mButton_random.setOnClickListener(this);
		mEditText.addTextChangedListener(this);
		mButton_set.setEnabled(false);
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			finish();
		case R.id.set:
			text = mEditText.getText().toString();
			Intent i = this.getIntent();
			i.putExtra("text", text);
			setResult(RESULT_OK, i);
			finish();
		case R.id.button_reset:
			mEditText.setText(null);
			break;
		case R.id.button_random:
			mEditText.setText(s[random.nextInt(s.length)]);
			
			break;

		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	//	Log.i("TAG","Character lenght" + mEditText.getText().length());

		if(mEditText.getText().length() > 0)
			mButton_set.setEnabled(true);
		else
			mButton_set.setEnabled(false);
	}

}
