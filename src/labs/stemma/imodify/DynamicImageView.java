package labs.stemma.imodify;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DynamicImageView extends ImageView {

	public DynamicImageView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		final Drawable d = this.getDrawable();

		if (d != null) {
			
			final int width = MeasureSpec.getSize(widthMeasureSpec);
			final int height = (int) Math.floor(width
					* (float) d.getIntrinsicHeight() / d.getIntrinsicWidth());
			this.setMeasuredDimension(width, height);
			

		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
