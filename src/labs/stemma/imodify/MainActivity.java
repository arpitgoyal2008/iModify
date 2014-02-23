package labs.stemma.imodify;

import java.util.Date;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnCheckedChangeListener,
		OnClickListener {

	private static final int GET_NAME = 4;
	private static final int GET_TIME = 3;
	private static final int GET_DATE = 2;
	private static final int REQUEST_CODE_IMAGE = 1;
	private CheckBox mCheckBox_name;
	private CheckBox mCheckBox_date;
	private CheckBox mCheckBox_time;
	private TextView mTextView_name;
	private TextView mTextView_time;
	private TextView mTextView_date;
	private Button mButton;
	private boolean dateChecked = false;
	private boolean timeChecked = false;
	private boolean nameChecked = false;
	private TextView mTextView_check_date;
	private TextView mTextView_check_name;
	private TextView mTextView_check_time;

	private boolean wantsName = false;
	private boolean wantsDate = false;
	private boolean wantsTime = false;

	private String mName = null;
	private int mDate = 0;
	private int mMonth = -1;
	private int mYear = 0;

	private int mHour = 0;
	private int mMinute = 0;

	java.text.DateFormat[] formats;
	private Button mButton_reset;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCheckBox_name = (CheckBox) findViewById(R.id.checkBox_name);
		mCheckBox_date = (CheckBox) findViewById(R.id.checkBox_date);
		mCheckBox_time = (CheckBox) findViewById(R.id.checkBox_time);

		mTextView_name = (TextView) findViewById(R.id.textView_name);
		mTextView_date = (TextView) findViewById(R.id.textView_date);
		mTextView_time = (TextView) findViewById(R.id.textView_time);

		mButton = (Button) findViewById(R.id.button);
		mButton_reset = (Button) findViewById(R.id.button_reset);

		mTextView_check_name = (TextView) findViewById(R.id.textBox_check_name);
		mTextView_check_date = (TextView) findViewById(R.id.textBox_check_date);
		mTextView_check_time = (TextView) findViewById(R.id.textBox_check_time);

		formats = new java.text.DateFormat[] {
				java.text.DateFormat.getDateInstance(),
				java.text.DateFormat.getTimeInstance() };

	}

	/*
	 * mDialog = new Dialog(this);
	 * mDialog.setContentView(R.layout.activity_date_picker);
	 */

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {

		mTextView_check_date.setOnClickListener(this);
		mCheckBox_date.setOnCheckedChangeListener(this);

		mTextView_check_time.setOnClickListener(this);
		mCheckBox_time.setOnCheckedChangeListener(this);

		mTextView_check_name.setOnClickListener(this);
		mCheckBox_name.setOnCheckedChangeListener(this);

		mButton.setOnClickListener(this);
		mButton_reset.setOnClickListener(this);

		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {

		case R.id.checkBox_name:
			if (isChecked && !nameChecked) {
				startActivityForResult(new Intent(this,
						NamePicker_activity.class), GET_NAME);
				wantsName = true;
			}
			if (!isChecked)
				wantsName = false;
			break;

		case R.id.checkBox_date:
			if (isChecked && !dateChecked) {
				startActivityForResult(new Intent(this,
						DatePicker_activity.class), GET_DATE);
				wantsDate = true;
			}
			if (!isChecked)
				wantsDate = false;
			break;

		case R.id.checkBox_time:
			if (isChecked && !timeChecked) {
				startActivityForResult(new Intent(this,
						TimePicker_activity.class), GET_TIME);
				wantsTime = true;
			}
			if (!isChecked)
				wantsTime = false;
			break;

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.button:

			Intent i = new Intent(Intent.ACTION_GET_CONTENT);
			i.setType("image/*");
			if (i.resolveActivity(getPackageManager()) != null) {
				startActivityForResult(Intent.createChooser(i, "Pick image"),
						REQUEST_CODE_IMAGE);
			}
			break;

		case R.id.textBox_check_date:
			startActivityForResult(new Intent(this, DatePicker_activity.class),
					GET_DATE);
			break;
		case R.id.textBox_check_time:
			startActivityForResult(new Intent(this, TimePicker_activity.class),
					GET_TIME);
			break;
		case R.id.textBox_check_name:
			startActivityForResult(new Intent(this, NamePicker_activity.class),
					GET_NAME);
			break;
		case R.id.button_reset: 
			mName = null;
			mMonth = 0;
			mDate = 0;
			mYear = 0;
			mHour = 0;
			mMinute = 0;
			wantsDate = false;
			wantsName = false;
			wantsTime = false;
			mCheckBox_name.setChecked(false);
			mCheckBox_time.setChecked(false);
			mCheckBox_date.setChecked(false);
			mTextView_date.setVisibility(View.GONE);
			mTextView_name.setVisibility(View.GONE);
			mTextView_time.setVisibility(View.GONE);
			dateChecked = false;
			timeChecked = false;
			nameChecked = false;
			

			break;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {

			Uri uri = data.getData();

			Intent i = new Intent(this, ShowImage.class);
			i.setData(uri);
			Log.i("tag", String.valueOf(wantsName));

			i.putExtra("b1", wantsName);
			i.putExtra("b2", wantsDate);
			i.putExtra("b3", wantsTime);

			if (wantsName)
				i.putExtra("name", mName);
			if (wantsDate) {
				i.putExtra("date", mDate);
				i.putExtra("month", mMonth);
				i.putExtra("year", mYear);
			}
			if (wantsTime) {
				i.putExtra("hour", mHour);
				i.putExtra("minute", mMinute);
			}
			startActivity(i);

		}

		if (requestCode == GET_DATE && resultCode == RESULT_OK) {
			dateChecked = true;
			mTextView_date.setVisibility(View.VISIBLE);
			mTextView_date.setText("Your Selection: "+ formats[0].format(new Date(data.getIntExtra(
					"year", 0) - 1900, data.getIntExtra("month", 13), data
					.getIntExtra("date", 0))));
			mDate = data.getIntExtra("date", 0);
			mMonth = data.getIntExtra("month", 0);
			mYear = data.getIntExtra("year", 0) - 1900;

			mCheckBox_date.setChecked(true);
			wantsDate = true;
		}

		if (requestCode == GET_TIME && resultCode == RESULT_OK) {
			timeChecked = true;
			mTextView_time.setVisibility(View.VISIBLE);
			mTextView_time.setText("Your Selection: "+formats[1].format(new Date(0, 0, 0, data
					.getIntExtra("hour", 0), data.getIntExtra("minute", 0))));
			mCheckBox_time.setChecked(true);
			wantsTime = true;
			mHour = data.getIntExtra("hour", 0);
			mMinute = data.getIntExtra("minute", 0);

		}

		if (requestCode == GET_NAME && resultCode == RESULT_OK) {
			nameChecked = true;
			mTextView_name.setVisibility(View.VISIBLE);
			mTextView_name.setText("Your Selection: "+ data.getStringExtra("text"));
			mCheckBox_name.setChecked(true);
			wantsName = true;
			mName = data.getStringExtra("text");

		}
		
		if(resultCode != RESULT_OK){
			if(requestCode==GET_NAME)
				mCheckBox_name.setChecked(false);
			if(requestCode==GET_DATE)
				mCheckBox_date.setChecked(false);
			if(requestCode==GET_TIME)
				mCheckBox_time.setChecked(false);
			
				
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("savedName", mName);
		outState.putInt("date", mDate);
		outState.putInt("month", mMonth);
		outState.putInt("year", mYear);
		outState.putInt("Hour", mHour);
		outState.putInt("Minute", mMinute);

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mName = savedInstanceState.getString("savedName");
		mDate = savedInstanceState.getInt("date");
		mMonth = savedInstanceState.getInt("month", -1);
		mYear = savedInstanceState.getInt("year");
		mHour = savedInstanceState.getInt("Hour");
		mMinute = savedInstanceState.getInt("Minute");

		if (mName != null)
			mTextView_name.setText(mName);
		if (!(mDate == 1 || mMonth != -1 || mYear == 1))
			mTextView_date.setText(formats[0].format(new Date(mYear, mMonth,
					mDate)));
		if (mHour != 0 && mMinute != 0)
			mTextView_date.setText(formats[1].format(new Date(0, 0, 0, mHour,
					mMinute)));
		;

	}
}
