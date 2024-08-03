package com.aishwary.sketchchat;

import androidx.appcompat.app.AppCompatActivity;
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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.view.View;

public class BackgroundFinalizerActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private double code = 0;
	private double width = 0;
	private String user1 = "";
	private String user2 = "";
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> chatroom1 = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private Button button1;
	private LinearLayout linear1;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private ImageView imageview2;
	private ImageView imageview1;
	private EditText edittext1;
	private ImageView imageview3;
	private ImageView imageview4;
	
	private SharedPreferences background;
	private Intent i = new Intent();
	private FirebaseAuth fauth;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private Calendar cal = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.background_finalizer);
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
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		button1 = (Button) findViewById(R.id.button1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		background = getSharedPreferences("background", Activity.MODE_PRIVATE);
		fauth = FirebaseAuth.getInstance();
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (getIntent().getStringExtra("selection").equals("20")) {
					background.edit().putString("mode", "Custom Background").commit();
					background.edit().putString("selection", getIntent().getStringExtra("path")).commit();
					if (getIntent().getStringExtra("previousScreen").equals("chat")) {
						i.setClass(getApplicationContext(), AllUsersActivity.class);
						startActivity(i);
					}
					else {
						i.setClass(getApplicationContext(), CommunityActivity.class);
						startActivity(i);
					}
					_customText("Save Complete.\nOpen Chat page to view and enjoy your new settings.");
				}
				else {
					background.edit().putString("mode", "Built in Background").commit();
					background.edit().putString("selection", getIntent().getStringExtra("selection")).commit();
					if (getIntent().getStringExtra("previousScreen").equals("chat")) {
						i.setClass(getApplicationContext(), AllUsersActivity.class);
						startActivity(i);
					}
					else {
						i.setClass(getApplicationContext(), CommunityActivity.class);
						startActivity(i);
					}
					_customText("Save Complete.\nOpen Chat page to view and enjoy your new settings.");
				}
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					user1 = _childValue.get("nickname").toString();
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		users.addChildEventListener(_users_child_listener);
		
		_fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	private void initializeLogic() {
		code = Double.parseDouble(getIntent().getStringExtra("selection"));
		if (code == 0) {
			linear1.setBackgroundResource(R.drawable.background_20200412180718);
		}
		if (code == 1) {
			linear1.setBackgroundResource(R.drawable.background_1);
		}
		if (code == 2) {
			linear1.setBackgroundResource(R.drawable.background_2);
		}
		if (code == 3) {
			linear1.setBackgroundResource(R.drawable.aurora_1440x2560_space_abstract_hd_20703);
		}
		if (code == 4) {
			linear1.setBackgroundResource(R.drawable.pattern);
		}
		if (code == 5) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_1);
		}
		if (code == 6) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_2);
		}
		if (code == 7) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_3);
		}
		if (code == 8) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_4);
		}
		if (code == 9) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_5);
		}
		if (code == 10) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_6);
		}
		if (code == 11) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_7);
		}
		if (code == 12) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_8);
		}
		if (code == 13) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_9);
		}
		if (code == 14) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_10);
		}
		if (code == 15) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_11);
		}
		if (code == 16) {
			linear1.setBackgroundResource(R.drawable.hd_backgrounds_12);
		}
		if (code == 17) {
			linear1.setBackgroundResource(R.drawable.sender);
		}
		if (code == 18) {
			linear1.setBackgroundResource(R.drawable.co2_20191019230424_20200410195639_20200611184008_20200714205450);
		}
		if (code == 19) {
			linear1.setBackgroundResource(R.drawable.image_search_1556276381468_20200714210446);
		}
		if (code == 20) {
			_setBackgroundOf(linear1, getIntent().getStringExtra("path"));
		}
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor("#ffffff"));
		gd.setCornerRadius(60);
		linear6.setBackground(gd);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
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
		gd.setStroke(2, Color.parseColor("#ff0000"));
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
