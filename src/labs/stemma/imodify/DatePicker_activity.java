package labs.stemma.imodify;

import java.lang.reflect.Method;
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
import android.widget.DatePicker;

public class DatePicker_activity extends Activity implements
		OnCheckedChangeListener, OnClickListener {

	private CheckBox mCheckBox_today_date;
	private DatePicker mDatePicker;
	private Button mButton_cancel;
	private Button mButton_set;
	private int date;
	private int month;
	private int year;
	private boolean todayDate = false;
	private Button mButton_reset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_picker);
		setTitle("Choose date");

		mCheckBox_today_date = (CheckBox) findViewById(R.id.checkBox1);
		mDatePicker = (DatePicker) findViewById(R.id.datePicker1);
		mButton_cancel = (Button) findViewById(R.id.cancel);
		mButton_set = (Button) findViewById(R.id.set);
		mButton_reset = (Button) findViewById(R.id.button_reset);
		if (getResources().getBoolean(R.bool.isPhone)) {
			int currentapiVersion = android.os.Build.VERSION.SDK_INT;
			if (currentapiVersion >= 11) {
				try {
					Method m = mDatePicker.getClass().getMethod(
							"setCalendarViewShown", boolean.class);
					m.invoke(mDatePicker, false);
				} catch (Exception e) {
				} 
			}
		}

		mCheckBox_today_date.setOnCheckedChangeListener(this);
		mButton_cancel.setOnClickListener(this);
		mButton_set.setOnClickListener(this);
		mButton_reset.setOnClickListener(this);

	}

	@Override
	protected void onResume() {

		Calendar calendar = Calendar.getInstance();
		date = calendar.get(Calendar.DAY_OF_MONTH);
		month = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);

		super.onResume();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			mDatePicker.setVisibility(View.GONE);
			mButton_reset.setVisibility(View.INVISIBLE);

			todayDate = true;
		}
		if (!isChecked) {
			mDatePicker.setVisibility(View.VISIBLE);
			mButton_reset.setVisibility(View.VISIBLE);

			todayDate = false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			finish();
		case R.id.set:
			date = mDatePicker.getDayOfMonth();
			month = mDatePicker.getMonth();
			year = mDatePicker.getYear();

			if (todayDate) {
				Calendar calendar = Calendar.getInstance();
				date = calendar.get(Calendar.DAY_OF_MONTH);
				month = calendar.get(Calendar.MONTH);
				year = calendar.get(Calendar.YEAR);
			}

			Intent i = this.getIntent();
			i.putExtra("date", date);
			i.putExtra("month", month);
			i.putExtra("year", year);
			setResult(RESULT_OK, i);
			finish();
		case R.id.button_reset:
			mDatePicker.updateDate(year, month, date);

		}
	}

}
