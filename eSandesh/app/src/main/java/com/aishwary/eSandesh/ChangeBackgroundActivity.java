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
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.content.ClipData;
import android.widget.AdapterView;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class ChangeBackgroundActivity extends AppCompatActivity {
	
	public final int REQ_CD_PICKIMAGE = 101;
	
	private Toolbar _toolbar;
	private String previousScreen = "";
	private String modeValue = "";
	private double selection = 0;
	private String filePath = "";
	
	private ArrayList<String> list1 = new ArrayList<>();
	
	private LinearLayout linear24;
	private ScrollView vscroll1;
	private LinearLayout linear3;
	private TextView textview1;
	private Spinner spinner1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear36;
	private LinearLayout linear4;
	private Button button1;
	private Button button2;
	private LinearLayout linear37;
	private LinearLayout linear5;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear11;
	private LinearLayout linear12;
	private LinearLayout linear13;
	private LinearLayout linear23;
	private LinearLayout linear25;
	private LinearLayout linear26;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private ImageView imageview1;
	private ImageView imageview2;
	private LinearLayout linear15;
	private LinearLayout linear16;
	private ImageView imageview3;
	private ImageView imageview4;
	private LinearLayout linear17;
	private LinearLayout linear18;
	private ImageView imageview5;
	private ImageView imageview6;
	private LinearLayout linear19;
	private LinearLayout linear20;
	private ImageView imageview7;
	private ImageView imageview8;
	private LinearLayout linear21;
	private LinearLayout linear22;
	private ImageView imageview9;
	private ImageView imageview10;
	private LinearLayout linear27;
	private LinearLayout linear28;
	private ImageView imageview11;
	private ImageView imageview12;
	private LinearLayout linear29;
	private LinearLayout linear30;
	private ImageView imageview13;
	private ImageView imageview14;
	private LinearLayout linear31;
	private LinearLayout linear32;
	private ImageView imageview15;
	private ImageView imageview16;
	private LinearLayout linear33;
	private LinearLayout linear34;
	private ImageView imageview17;
	private ImageView imageview18;
	private LinearLayout linear35;
	private ImageView imageview19;
	
	private SharedPreferences background;
	private Intent i = new Intent();
	private Intent pickImage = new Intent(Intent.ACTION_GET_CONTENT);
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.change_background);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
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
		linear24 = (LinearLayout) findViewById(R.id.linear24);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		textview1 = (TextView) findViewById(R.id.textview1);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear36 = (LinearLayout) findViewById(R.id.linear36);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		linear37 = (LinearLayout) findViewById(R.id.linear37);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linear23 = (LinearLayout) findViewById(R.id.linear23);
		linear25 = (LinearLayout) findViewById(R.id.linear25);
		linear26 = (LinearLayout) findViewById(R.id.linear26);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		linear17 = (LinearLayout) findViewById(R.id.linear17);
		linear18 = (LinearLayout) findViewById(R.id.linear18);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		linear19 = (LinearLayout) findViewById(R.id.linear19);
		linear20 = (LinearLayout) findViewById(R.id.linear20);
		imageview7 = (ImageView) findViewById(R.id.imageview7);
		imageview8 = (ImageView) findViewById(R.id.imageview8);
		linear21 = (LinearLayout) findViewById(R.id.linear21);
		linear22 = (LinearLayout) findViewById(R.id.linear22);
		imageview9 = (ImageView) findViewById(R.id.imageview9);
		imageview10 = (ImageView) findViewById(R.id.imageview10);
		linear27 = (LinearLayout) findViewById(R.id.linear27);
		linear28 = (LinearLayout) findViewById(R.id.linear28);
		imageview11 = (ImageView) findViewById(R.id.imageview11);
		imageview12 = (ImageView) findViewById(R.id.imageview12);
		linear29 = (LinearLayout) findViewById(R.id.linear29);
		linear30 = (LinearLayout) findViewById(R.id.linear30);
		imageview13 = (ImageView) findViewById(R.id.imageview13);
		imageview14 = (ImageView) findViewById(R.id.imageview14);
		linear31 = (LinearLayout) findViewById(R.id.linear31);
		linear32 = (LinearLayout) findViewById(R.id.linear32);
		imageview15 = (ImageView) findViewById(R.id.imageview15);
		imageview16 = (ImageView) findViewById(R.id.imageview16);
		linear33 = (LinearLayout) findViewById(R.id.linear33);
		linear34 = (LinearLayout) findViewById(R.id.linear34);
		imageview17 = (ImageView) findViewById(R.id.imageview17);
		imageview18 = (ImageView) findViewById(R.id.imageview18);
		linear35 = (LinearLayout) findViewById(R.id.linear35);
		imageview19 = (ImageView) findViewById(R.id.imageview19);
		background = getSharedPreferences("background", Activity.MODE_PRIVATE);
		pickImage.setType("image/*");
		pickImage.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				modeValue = list1.get((int)(_position));
				if (list1.get((int)(_position)).equals("Built in Background")) {
					linear4.setVisibility(View.VISIBLE);
				}
				else {
					linear4.setVisibility(View.GONE);
				}
				if (list1.get((int)(_position)).equals("Default Background")) {
					button1.setVisibility(View.VISIBLE);
				}
				else {
					button1.setVisibility(View.GONE);
				}
				if (list1.get((int)(_position)).equals("Custom Background")) {
					button2.setVisibility(View.VISIBLE);
				}
				else {
					button2.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				background.edit().putString("mode", "Default Background").commit();
				background.edit().putString("selection", "0").commit();
				i.setClass(getApplicationContext(), AllUsersActivity.class);
				startActivity(i);
				_customText("Setting default Chat screen background complete");
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(pickImage, REQ_CD_PICKIMAGE);
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "1");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "2");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "3");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "4");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "5");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "6");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "7");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "8");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "9");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "10");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "11");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "12");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "13");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "14");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview15.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "15");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview16.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "16");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview17.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "17");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview18.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "18");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
		
		imageview19.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "19");
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
		});
	}
	private void initializeLogic() {
		list1.add("Default Background");
		list1.add("Built in Background");
		list1.add("Custom Background");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, list1));
		
		spinner1.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, list1) {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView1 = (TextView) super.getView(position, convertView, parent);
				textView1.setTextColor(Color.RED);
				textView1.setTextSize(16);
				return textView1; }
			
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				TextView textView1 = (TextView) super.getDropDownView(position, convertView, parent); textView1.setTextColor(Color.RED);
				textView1.setTextSize(16);
				return textView1; }
		});
		if (background.getString("mode", "").equals("")) {
			background.edit().putString("mode", "Default Background").commit();
			background.edit().putString("selection", "0").commit();
			spinner1.setSelection((int)(list1.indexOf("Default Background")));
			selection = 0;
			linear36.setVisibility(View.GONE);
		}
		else {
			if (background.getString("mode", "").equals("Default Background")) {
				background.edit().putString("selection", "0").commit();
				spinner1.setSelection((int)(list1.indexOf("Default Background")));
				selection = 0;
				linear36.setVisibility(View.GONE);
			}
			else {
				
			}
			if (background.getString("mode", "").equals("Built in Background")) {
				spinner1.setSelection((int)(list1.indexOf("Built in Background")));
				linear36.setVisibility(View.GONE);
				selection = Double.parseDouble(background.getString("selection", ""));
				if (selection == 1) {
					linear6.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 2) {
					linear7.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 3) {
					linear15.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 4) {
					linear16.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 5) {
					linear17.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 6) {
					linear18.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 7) {
					linear19.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 8) {
					linear20.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 9) {
					linear21.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 10) {
					linear22.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 11) {
					linear27.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 12) {
					linear28.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 13) {
					linear29.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 14) {
					linear30.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 15) {
					linear31.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 16) {
					linear32.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 17) {
					linear33.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 18) {
					linear34.setBackgroundColor(0xFFFFFFFF);
				}
				if (selection == 19) {
					linear35.setBackgroundColor(0xFFFFFFFF);
				}
			}
			else {
				
			}
			if (background.getString("mode", "").equals("Custom Background")) {
				if (FileUtil.isExistFile(background.getString("selection", ""))) {
					spinner1.setSelection((int)(list1.indexOf("Custom Background")));
					linear36.setVisibility(View.VISIBLE);
					linear36.setBackgroundColor(0xFFFFFFFF);
					_setBackgroundOf(linear37, background.getString("selection", ""));
				}
				else {
					background.edit().putString("mode", "Default Background").commit();
					background.edit().putString("selection", "0").commit();
					_customText("Image file :".concat(Uri.parse(background.getString("selection", "")).getLastPathSegment()).concat("\nNot found at path :".concat(background.getString("selection", "").concat("\nThe image may have been moved or deleted.\nBackground set to default"))));
				}
			}
			else {
				
			}
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_PICKIMAGE:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				filePath = _filePath.get((int)(0));
				_customText("Selected Image file : ".concat(Uri.parse(_filePath.get((int)(0))).getLastPathSegment()).concat("\nLocated at : ".concat(_filePath.get((int)(0)))));
				i.setClass(getApplicationContext(), BackgroundFinalizerActivity.class);
				i.putExtra("selection", "20");
				i.putExtra("path", _filePath.get((int)(0)));
				i.putExtra("previousScreen", getIntent().getStringExtra("previousScreen"));
				startActivity(i);
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	private void _customText (final String _text) {
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
	
	
	private void _setBackgroundOf (final View _view, final String _path) {
		_view.setBackground(new android.graphics.drawable.BitmapDrawable(getResources(), FileUtil.decodeSampleBitmapFromPath(_path, 1024, 1024)));
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
