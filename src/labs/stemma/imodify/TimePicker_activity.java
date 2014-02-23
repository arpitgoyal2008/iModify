package labs.stemma.imodify;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class TimePicker_activity extends Activity implements
		OnCheckedChangeListener, OnClickListener, OnTimeChangedListener {

	private CheckBox mCheckBox_today_time;
	private TimePicker mTimePicker;
	private Button mButton_cancel;
	private Button mButton_set;
	private int minute;
	private boolean currentTime = false;
	private Button mButton_reset;
	private int hour_of_day;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_picker);
		setTitle("Choose Time");

		mCheckBox_today_time = (CheckBox) findViewById(R.id.checkBox1);
		mTimePicker = (TimePicker) findViewById(R.id.timePicker1);
		mButton_cancel = (Button) findViewById(R.id.cancel);
		mButton_set = (Button) findViewById(R.id.set);
		mButton_reset = (Button) findViewById(R.id.button_reset);

		mCheckBox_today_time.setOnCheckedChangeListener(this);
		mButton_cancel.setOnClickListener(this);
		mButton_set.setOnClickListener(this);
		mButton_reset.setOnClickListener(this);

	}

	@Override
	protected void onResume() {

		Calendar calendar = Calendar.getInstance();
		minute = calendar.get(Calendar.MINUTE);
		hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
		mTimePicker.setOnTimeChangedListener(this);
		// mTimePicker.seton
		super.onResume();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			mTimePicker.setVisibility(View.GONE);
			mButton_reset.setVisibility(View.INVISIBLE);

			currentTime = true;
		}
		if (!isChecked) {
			mTimePicker.setVisibility(View.VISIBLE);
			mButton_reset.setVisibility(View.VISIBLE);

			currentTime = false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			finish();
		case R.id.set:

			if (currentTime) {
				Calendar calendar = Calendar.getInstance();
				minute = calendar.get(Calendar.MINUTE);
				hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
			}

			Intent i = this.getIntent();
			i.putExtra("hour", hour_of_day);
			i.putExtra("minute", minute);
			setResult(RESULT_OK, i);
			finish();
		case R.id.button_reset:
			Calendar calendar = Calendar.getInstance();
			minute = calendar.get(Calendar.MINUTE);
			hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
			mTimePicker.setCurrentHour(hour_of_day);
			mTimePicker.setCurrentMinute(minute);
			break;

		}
	}

	@Override
	public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
		switch (arg0.getId()) {
		case R.id.timePicker1:
			hour_of_day = arg1;
			minute = arg2;
			break;
		}
	}

}
