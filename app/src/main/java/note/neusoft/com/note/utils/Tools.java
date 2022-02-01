package note.neusoft.com.note.utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.View;

import note.neusoft.com.note.R;

public class Tools {



	/**
	 * get the compressed picture at the specified size
	 *
	 * @param mcContext
	 *            context
	 * @param image
	 *            Original picture
	 * @return Compressed picture
	 */
	public static Bitmap getThumbnails(Context mcContext, Bitmap image) {
		/*
		 * getResources().getDimensionPixelSize Take out the value in dimens
		 */
		Bitmap bitmap;
		int width = mcContext.getResources().getDimensionPixelSize(R.dimen.headimage_width);
		int height = mcContext.getResources().getDimensionPixelSize(R.dimen.headimage_height);
		/*
		 * ThumbnailUtils.extractThumbnail Create a thumbnail of the specified size
		 *
		 * @param source Source file (Bitmap type)
		 *
		 * @param width Compressed width
		 *
		 * @param height Compressed height
		 */
		int h = image.getHeight();
		int w = image.getWidth();
		if (h > width || w > height) {
			bitmap = ThumbnailUtils.extractThumbnail(image, width, height);
		} else {
			bitmap = image;
		}
		return bitmap;
	}

	/**
	 * Get the height or width of the control isHeight=true to measure the height of the control, isHeight=false to measure the width of the control
	 * 
	 * @param view
	 * @param isHeight
	 * @return
	 */
	public static int getViewHeight(View view, boolean isHeight) {
		int result;
		if (view == null)
			return 0;
		if (isHeight) {
			int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			view.measure(h, 0);
			result = view.getMeasuredHeight();
		} else {
			int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			view.measure(0, w);
			result = view.getMeasuredWidth();
		}
		return result;
	}


	/**
	 * Get the compressed picture at the specified size.
	 * 
	 * @param mcContext
	 *            context
	 * @param image
	 *            Original picture
	 * @return Compressed picture
	 */
	public static Bitmap getThumbnails(Context mcContext, Bitmap image, int widths, int heights) {
		/*
		 * getResources().getDimensionPixelSize Take out the value in dimens
		 */
		Bitmap bitmap;
		int width = mcContext.getResources().getDimensionPixelSize(widths);
		int height = mcContext.getResources().getDimensionPixelSize(heights);
		/*
		 * ThumbnailUtils.extractThumbnail Create a thumbnail of the specified size
		 * 
		 * @param source Source file (Bitmap type)
		 * 
		 * @param width Compressed width
		 * 
		 * @param height Compressed height
		 */
		int h = image.getHeight();
		int w = image.getWidth();
		if (h > width || w > height) {
			bitmap = ThumbnailUtils.extractThumbnail(image, width, height);
		} else {
			bitmap = image;
		}
		return bitmap;
	}



	/**
	 * Convert Bitmap to byte array
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * Convert byte array to Bitmap
	 * 
	 * @param temp
	 * @return
	 */
	public static Bitmap getBitmapFromByte(byte[] temp) {
		if (temp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		} else {
			return null;
		}
	}

	/**
	 * Convert a Drawable to Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Convert Bitmap to Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable Bitmap_Drawable(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}


}
