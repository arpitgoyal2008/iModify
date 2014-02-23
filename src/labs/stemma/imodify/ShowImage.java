package labs.stemma.imodify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class ShowImage extends Activity implements OnClickListener {

	private Button mButton_share;
	private Button mButton_save;
	private Intent mIntent;
	private Bitmap mBitmap;
	private Bitmap mBitmap_new;

	private String timeStamp = null;
	private String name = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_image);

		/*
		 * mImageView_original = (ImageView)
		 * findViewById(R.id.imageView_original);
		 */

		mButton_share = (Button) findViewById(R.id.share);
		mButton_save = (Button) findViewById(R.id.save);

		mIntent = getIntent();

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {

		((ImageView) findViewById(R.id.imageView_modified))
				.setOnClickListener(this);

		Uri uri = mIntent.getData();
		try {
			InputStream is = getContentResolver().openInputStream(uri);
			/*
			 * Log.i("tag","arpit"); Options options1 = new Options();
			 * options1.inJustDecodeBounds = true;
			 * BitmapFactory.decodeStream(is, null, options1); int w =
			 * options1.outWidth; int h = options1.outHeight; is.close();
			 * 
			 * Options options = new BitmapFactory.Options();
			 * options.inJustDecodeBounds = false;
			 * 
			 * int displayWidth = getResources().getDisplayMetrics().widthPixels
			 * / 2 - 32; int displayHeight =
			 * getResources().getDisplayMetrics().heightPixels;
			 * Log.i("tag","DisplayWidth : " + displayWidth);
			 * Log.i("tag","DisplayHeight: "+ displayHeight);
			 * Log.i("tag","ImageWidth : " + w); Log.i("tag","ImageHeight: "+
			 * h);
			 * 
			 * int sample = 1; while (w > sample * displayWidth || h > sample *
			 * displayHeight) { sample = sample * 2; } Log.i("tag","Sample: " +
			 * sample); sample = 1; options.inSampleSize = sample; is =
			 * getContentResolver().openInputStream(uri);
			 */
			mBitmap = BitmapFactory.decodeStream(is/* , null, options */);
			is.close();

			/* mImageView_original.setImageBitmap(mBitmap); */

			java.text.DateFormat[] formats = new java.text.DateFormat[] {
					java.text.DateFormat.getDateInstance(),
					java.text.DateFormat.getTimeInstance() };

			mBitmap_new = Bitmap.createBitmap(mBitmap.getWidth(),
					mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(mBitmap_new);

			timeStamp = ""
					+ (mIntent.getBooleanExtra("b2", false) ? (formats[0]
							.format(new Date(mIntent.getIntExtra("year", 0),
									mIntent.getIntExtra("month", 0), mIntent
											.getIntExtra("date", 0)))) : "")
					+ " "
					+ (mIntent.getBooleanExtra("b3", false) ? (formats[1]
							.format(new Date(0, 0, 0, mIntent.getIntExtra(
									"hour", 0), mIntent
									.getIntExtra("minute", 0)))) : "");

			name = ""
					+ (mIntent.getBooleanExtra("b1", false) ? mIntent
							.getStringExtra("name") : "");
			Log.i("tag", timeStamp);
			Log.i("tag", name);
			c.drawBitmap(mBitmap, 0, 0, null);
			TextPaint tp = new TextPaint();
			tp.setTextSize(42);
			tp.setColor(0x800000FF);
			c.drawText(timeStamp, 0, 42, tp);
			c.drawText(name, 0, mBitmap_new.getHeight()/2, tp);
			/*
			 * mBitmap.recycle(); Log.i("tag", "in ShowImage" +
			 * String.valueOf(mIntent.getBooleanExtra("nameBoolean", true)
			 * mIntent.getBooleanExtra("b1", false));
			 */

			((ImageView) findViewById(R.id.imageView_modified))
					.setImageBitmap(mBitmap_new);
		} catch (Exception e) {
			Log.e("TAG", "Error: " + e);

		}

		mButton_save.setOnClickListener(this);
		mButton_share.setOnClickListener(this);

		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_image, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.imageView_modified) {
			Dialog dialog = new Dialog(this);
			DynamicImageView imageView = new DynamicImageView(this,null);
			imageView.setImageBitmap(mBitmap_new);
			imageView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imageView.setScaleType(ScaleType.CENTER_CROP);
			//imageView.setAdjustViewBounds(true);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

			dialog.addContentView(imageView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			


			dialog.show();
		} else {

			File path = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES
							+ File.separator + "Watermarked Images");
			path.mkdirs();
			String fileName = System.currentTimeMillis() + ".jpg";
			File file = new File(path, fileName);
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
				mBitmap_new.compress(CompressFormat.JPEG, 100, fos);
				
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Uri uri = Uri.fromFile(file);

			switch (v.getId()) {

			case R.id.save:
				Toast.makeText(this, "Picture Saved", Toast.LENGTH_SHORT)
				.show();

				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
						.setData(uri));

				break;
			case R.id.share:
				Log.i("tag", "Entered in share case");
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("image/jpeg");
				intent.putExtra(Intent.EXTRA_STREAM, uri);
				startActivity(Intent.createChooser(intent, "Share using"));
				break;

			}
		}
	}

}