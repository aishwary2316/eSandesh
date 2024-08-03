package com.aishwary.sketchchat;

import androidx.appcompat.app.AppCompatActivity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class AppUsageActivity extends AppCompatActivity {
	
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private Button button1;
	private TextView textview1;
	private ImageView imageview2;
	private TextView textview2;
	private ImageView imageview3;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private ImageView imageview5;
	private ImageView imageview4;
	private TextView textview3;
	private ImageView imageview6;
	private TextView textview4;
	private ImageView imageview7;
	private TextView textview5;
	private ImageView imageview8;
	private TextView textview6;
	private ImageView imageview9;
	private TextView textview7;
	private ImageView imageview10;
	private TextView textview10;
	private ImageView imageview11;
	private TextView textview12;
	private ImageView imageview12;
	private TextView textview13;
	private ImageView imageview13;
	private TextView textview15;
	private ImageView imageview14;
	private TextView textview16;
	private ImageView imageview15;
	private TextView textview17;
	private ImageView imageview16;
	private TextView textview18;
	private ImageView imageview17;
	private TextView textview19;
	
	private SharedPreferences shared_preference;
	private Intent intent = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.app_usage);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		button1 = (Button) findViewById(R.id.button1);
		textview1 = (TextView) findViewById(R.id.textview1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		textview2 = (TextView) findViewById(R.id.textview2);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview3 = (TextView) findViewById(R.id.textview3);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		textview4 = (TextView) findViewById(R.id.textview4);
		imageview7 = (ImageView) findViewById(R.id.imageview7);
		textview5 = (TextView) findViewById(R.id.textview5);
		imageview8 = (ImageView) findViewById(R.id.imageview8);
		textview6 = (TextView) findViewById(R.id.textview6);
		imageview9 = (ImageView) findViewById(R.id.imageview9);
		textview7 = (TextView) findViewById(R.id.textview7);
		imageview10 = (ImageView) findViewById(R.id.imageview10);
		textview10 = (TextView) findViewById(R.id.textview10);
		imageview11 = (ImageView) findViewById(R.id.imageview11);
		textview12 = (TextView) findViewById(R.id.textview12);
		imageview12 = (ImageView) findViewById(R.id.imageview12);
		textview13 = (TextView) findViewById(R.id.textview13);
		imageview13 = (ImageView) findViewById(R.id.imageview13);
		textview15 = (TextView) findViewById(R.id.textview15);
		imageview14 = (ImageView) findViewById(R.id.imageview14);
		textview16 = (TextView) findViewById(R.id.textview16);
		imageview15 = (ImageView) findViewById(R.id.imageview15);
		textview17 = (TextView) findViewById(R.id.textview17);
		imageview16 = (ImageView) findViewById(R.id.imageview16);
		textview18 = (TextView) findViewById(R.id.textview18);
		imageview17 = (ImageView) findViewById(R.id.imageview17);
		textview19 = (TextView) findViewById(R.id.textview19);
		shared_preference = getSharedPreferences("visit", Activity.MODE_PRIVATE);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				shared_preference.edit().putString("visits", "complete").commit();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
	}
	private void initializeLogic() {
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
