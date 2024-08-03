package com.aishwary.eSandesh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.HorizontalScrollView;
import android.widget.EditText;
import android.widget.Button;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;

public class ApplockActivity extends AppCompatActivity {
	
	
	private Toolbar _toolbar;
	private boolean applock_active = false;
	
	private LinearLayout linear1;
	private LinearLayout linear6;
	private ImageView imageview2;
	private LinearLayout linear3;
	private LinearLayout linear2;
	private LinearLayout linear5;
	private ImageView imageview1;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear4;
	private TextView textview3;
	private HorizontalScrollView hscroll1;
	private EditText edittext2;
	private Button button1;
	
	private SharedPreferences shared_preference;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.applock);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		textview3 = (TextView) findViewById(R.id.textview3);
		hscroll1 = (HorizontalScrollView) findViewById(R.id.hscroll1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		button1 = (Button) findViewById(R.id.button1);
		shared_preference = getSharedPreferences("app_lock_status", Activity.MODE_PRIVATE);
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				applock_active = !applock_active;
				if (applock_active) {
					imageview2.setImageResource(R.drawable.buttons_5);
					linear2.setVisibility(View.VISIBLE);
				}
				else {
					imageview2.setImageResource(R.drawable.buttons_4);
					linear2.setVisibility(View.GONE);
				}
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (applock_active) {
					if (edittext2.getText().toString().equals("")) {
						_customToast("Password required");
					}
					else {
						shared_preference.edit().putString("status", "true").commit();
						shared_preference.edit().putString("password", edittext2.getText().toString()).commit();
						textview2.setText("Active");
						_customToast("App lock successfully activated");
					}
				}
				else {
					shared_preference.edit().putString("status", "false").commit();
					shared_preference.edit().putString("password", "").commit();
					textview2.setText("Inactive");
					_customToast("App lock successfully deactivated");
				}
			}
		});
	}
	private void initializeLogic() {
		if (shared_preference.getString("status", "").equals("")) {
			applock_active = false;
			shared_preference.edit().putString("status", "false").commit();
			textview2.setText("Disabled");
			imageview2.setImageResource(R.drawable.buttons_4);
			linear2.setVisibility(View.GONE);
		}
		else {
			if (shared_preference.getString("status", "").equals("true")) {
				applock_active = true;
			}
			else {
				applock_active = false;
			}
			if (applock_active) {
				textview2.setText("Active");
				edittext2.setText(shared_preference.getString("password", ""));
				imageview2.setImageResource(R.drawable.buttons_5);
				linear2.setVisibility(View.VISIBLE);
			}
			else {
				textview2.setText("Disabled");
				imageview2.setImageResource(R.drawable.buttons_4);
				linear2.setVisibility(View.GONE);
			}
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	private void _customToast (final String _text) {
		LayoutInflater inflater = getLayoutInflater(); View toastLayout = inflater.inflate(R.layout.custom1, null);
		
		TextView textview1 = (TextView) toastLayout.findViewById(R.id.textview1);
		textview1.setText(_text);
		LinearLayout linear1 = (LinearLayout) toastLayout.findViewById(R.id.linear1);
		
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor("#00449a"));
		gd.setCornerRadius(60);
		gd.setStroke(2, Color.parseColor("#000000"));
		linear1.setBackground(gd);
		
		Toast toast = new Toast(getApplicationContext()); toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastLayout);
		toast.show();
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
