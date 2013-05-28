package com.build.android.photobyintent;

import java.util.List;
import android.graphics.Color;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class PhotoIntentActivity extends Activity {

	private static final int ACTION_TAKE_PHOTO_S = 2;

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String TEXT_STORAGE_KEY = "viewtext";
    private static final String COLOR_STORAGE_KEY = "";
    private static final String COLOR_CODE_KEY = "";

	private Bitmap mImageBitmap;
    private TextView mTextView;
    private String mColorText;
    private String mColor = "";
    private String mColorCode;

	private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, actionCode);
	}
    private String getColor(){
        int colorNum = (int)(Math.random() * (10 + 1));
        String color = "white";
        mColorCode= "#FFFFFF";
        if(colorNum==1){color="black";mColorCode= "#000000";}
        if(colorNum==2){color="red";mColorCode= "#FF0000";}
        if(colorNum==3){color="green";mColorCode= "#00FF00";}
        if(colorNum==4){color="blue";mColorCode= "#0000FF";}
        if(colorNum==5){color="yellow";mColorCode= "#F2FF00";}
        if(colorNum==6){color="purple";mColorCode= "#F200FF";}
        if(colorNum==7){color="cyan";mColorCode= "#00DDFF";}
        if(colorNum==8){color="orange";mColorCode= "#FA932D";}
        if(colorNum==9){color="pink";mColorCode= "#FA93B2";}
        if(colorNum==10){color="dark green";mColorCode= "#077510";}
        if(color.equals(mColor)){
            color = getColor();
        }
        mTextView.setTextColor(Color.parseColor(mColorCode));
        return color;
    }

	private void handleSmallCameraPhoto(Intent intent) {
		Bundle extras = intent.getExtras();
		mImageBitmap = (Bitmap) extras.get("data");

        mColorText = pixelCheck(mImageBitmap);
        mTextView.setText(mColorText);

	}

    private String pixelCheck(Bitmap mImageBitmap){
        int h = mImageBitmap.getHeight();
        int w = mImageBitmap.getWidth();
        int[] pixels = new int[h*w];
        String colorResult = "";
        mImageBitmap.getPixels(pixels,0,w,0,0,w,h);
        for(int i=0;i<pixels.length;i++){
            int r = Color.red(pixels[i]);
            int g = Color.green(pixels[i]);
            int b = Color.blue(pixels[i]);
            colorResult = colorCheck(r,g,b);
            if(colorResult.equals("Found!!")){
                mColor = getColor();
                return colorResult + " Now find " + mColor;
            }
        }
        return colorResult;
    }

    private String colorCheck(int r, int g, int b){
        if(mColor.equals("white")){
            if(g>180){if(b>180){if(r>180){return "Found!!";}}}
            return "No white, try again";
        }
        if(mColor.equals("black")){
            if(g<25){if(b<25){if(r<25){return "Found!!";}}}
            return "No black, try again";
        }
        if(mColor.equals("red")){
            if(g<100){if(b<100){if(r>150){return "Found!!";}}}
            return "No red, try again";
        }
        if(mColor.equals("green")){
            if(g>130){if(b<100){if(r<100){return "Found!!";}}}
            return "No green, try again";
        }
        if(mColor.equals("blue")){
            if(g<100){if(b>150){if(r<100){return "Found!!";}}}
            return "No blue, try again";
        }
        if(mColor.equals("yellow")){
            if(g>140){if(b<80){if(r>140){return "Found!!";}}}
            return "No yellow, try again";
        }
        if(mColor.equals("purple")){
            if(g<100){if(b>140){if(r>140){return "Found!!";}}}
            return "No purple, try again";
        }
        if(mColor.equals("orange")){
            if(g>100){if(b<100){if(r>160){return "Found!!";}}}
            return "No orange, try again";
        }
        if(mColor.equals("cyan")){
            if(g>150){if(b>150){if(r<120){return "Found!!";}}}
            return "No cyan, try again";
        }
        if(mColor.equals("dark green")){
            if(g>80){if(b<50){if(r<50){return "Found!!";}}}
            return "No dark green, try again";
        }
        if(mColor.equals("pink")){
            if(g<140){if(b<140){if(r>180){return "Found!!";}}}
            return "No pink, try again";
        }
        return "Oops, please tap next";
    }

    private void popup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("How to Play");
        builder.setMessage("Take a picture of the color given by the app. The app will tell you if it finds the color in the picture. It seems simple, but the game can prove challenging in certain places. Try playing in interesting places, like car rides, while hiking, in the mall... Tips: play in good light or outside, and try adjusting your camera settings.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                    //do things
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    Button.OnClickListener mTakePicSOnClickListener=
        new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
             dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);
         }
        };


	Button.OnClickListener  mHowTo =
		new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			popup();
		}
	};

    Button.OnClickListener  mNext =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mColor = getColor();
                    finish();
                    startActivity(getIntent());
                }
            };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


        mTextView = (TextView) findViewById(R.id.colorText);
		mImageBitmap = null;
        mColor = getColor();
        mColorText = "Find " + mColor;
        mTextView.setText(mColorText);

        Button howToBtn = (Button) findViewById(R.id.howToBtn);
        howToBtn.setOnClickListener(mHowTo);

		Button picSBtn = (Button) findViewById(R.id.btnIntendS);
		picSBtn.setOnClickListener(mTakePicSOnClickListener);

        Button nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(mNext);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == RESULT_OK) {
				handleSmallCameraPhoto(data);
			}
	}

	// Some lifecycle callbacks so that the image can survive orientation change
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
        outState.putString(TEXT_STORAGE_KEY, mColorText);
        outState.putString(COLOR_STORAGE_KEY, mColor);
        outState.putString(COLOR_CODE_KEY, mColorCode);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);

        mColorText = savedInstanceState.getString(TEXT_STORAGE_KEY);
        mTextView.setText(mColorText);
        mColor = savedInstanceState.getString(COLOR_STORAGE_KEY);
        mColorCode = savedInstanceState.getString(COLOR_CODE_KEY);
        mTextView.setTextColor(Color.parseColor(mColorCode));

	}

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 *
	 * @param context The application's environment.
	 * @param action The Intent action to check for availability.
	 *
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list =
			packageManager.queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

}