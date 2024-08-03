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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.HorizontalScrollView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.app.Activity;
import android.content.SharedPreferences;
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
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.view.View;
import com.bumptech.glide.Glide;

public class MenuActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private String nickname = "";
	private double index = 0;
	private HashMap<String, Object> userdata = new HashMap<>();
	private String visitstr = "";
	private double visit = 0;
	private String share = "";
	private HashMap<String, Object> share_app_url = new HashMap<>();
	private String share_url = "";
	private double progress = 0;
	private String messag = "";
	
	private LinearLayout linear2;
	private LinearLayout linear15;
	private LinearLayout linear17;
	private ScrollView vscroll1;
	private LinearLayout linear14;
	private LinearLayout linear4;
	private ImageView profile_picture;
	private TextView username_textview;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private TextView textview5;
	private HorizontalScrollView hscroll2;
	private TextView textview6;
	private TextView textview7;
	private HorizontalScrollView hscroll1;
	private TextView textview8;
	private ImageView imageview1;
	private TextView textview10;
	private LinearLayout linear18;
	private Button button1;
	private TextView textview3;
	private Button button5;
	private TextView textview2;
	private Button button4;
	private Button button3;
	private LinearLayout linear12;
	private Button button2;
	private ProgressBar progressbar1;
	private TextView textview9;
	private LinearLayout linear19;
	private ImageView imageview2;
	private TextView textview11;
	private ImageView imageview3;
	private HorizontalScrollView hscroll3;
	
	private SharedPreferences sp;
	private FirebaseAuth Firebase_auth;
	private OnCompleteListener<AuthResult> _Firebase_auth_create_user_listener;
	private OnCompleteListener<AuthResult> _Firebase_auth_sign_in_listener;
	private OnCompleteListener<Void> _Firebase_auth_reset_password_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private Intent intent = new Intent();
	private AlertDialog.Builder dialog;
	private SharedPreferences save_data_locally;
	private SharedPreferences mode;
	private Intent i = new Intent();
	private DatabaseReference share_app = _firebase.getReference("share_app");
	private ChildEventListener _share_app_child_listener;
	private TimerTask timer;
	private DatabaseReference message = _firebase.getReference("message");
	private ChildEventListener _message_child_listener;
	private SharedPreferences dev_mess;
	private ObjectAnimator animation = new ObjectAnimator();
	private TimerTask animator;
	private Calendar cal = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.menu);
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
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		linear17 = (LinearLayout) findViewById(R.id.linear17);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		profile_picture = (ImageView) findViewById(R.id.profile_picture);
		username_textview = (TextView) findViewById(R.id.username_textview);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		textview5 = (TextView) findViewById(R.id.textview5);
		hscroll2 = (HorizontalScrollView) findViewById(R.id.hscroll2);
		textview6 = (TextView) findViewById(R.id.textview6);
		textview7 = (TextView) findViewById(R.id.textview7);
		hscroll1 = (HorizontalScrollView) findViewById(R.id.hscroll1);
		textview8 = (TextView) findViewById(R.id.textview8);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview10 = (TextView) findViewById(R.id.textview10);
		linear18 = (LinearLayout) findViewById(R.id.linear18);
		button1 = (Button) findViewById(R.id.button1);
		textview3 = (TextView) findViewById(R.id.textview3);
		button5 = (Button) findViewById(R.id.button5);
		textview2 = (TextView) findViewById(R.id.textview2);
		button4 = (Button) findViewById(R.id.button4);
		button3 = (Button) findViewById(R.id.button3);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		button2 = (Button) findViewById(R.id.button2);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		textview9 = (TextView) findViewById(R.id.textview9);
		linear19 = (LinearLayout) findViewById(R.id.linear19);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		textview11 = (TextView) findViewById(R.id.textview11);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		hscroll3 = (HorizontalScrollView) findViewById(R.id.hscroll3);
		sp = getSharedPreferences("data", Activity.MODE_PRIVATE);
		Firebase_auth = FirebaseAuth.getInstance();
		dialog = new AlertDialog.Builder(this);
		save_data_locally = getSharedPreferences("profile_picture", Activity.MODE_PRIVATE);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		dev_mess = getSharedPreferences("mess", Activity.MODE_PRIVATE);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), NotificationActivity.class);
				startActivity(intent);
			}
		});
		
		textview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), AllUsersActivity.class);
				startActivity(intent);
			}
		});
		
		button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), CommunityActivity.class);
				i.putExtra("position", "NA");
				startActivity(i);
			}
		});
		
		textview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), ProfileActivity.class);
				startActivity(intent);
			}
		});
		
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dev_mess.edit().putString("message", messag).commit();
				intent.setClass(getApplicationContext(), DeveloperMessageActivity.class);
				startActivity(intent);
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linear12.setVisibility(View.VISIBLE);
				timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (share_url.equals("")) {
									if (progress > 100) {
										timer.cancel();
										progressbar1.setProgress((int)0);
										progress = 0;
										_customText("Unable to load link\nCheck your network connection or try again later");
									}
									else {
										progressbar1.setProgress((int)progress + 1);
									}
								}
								else {
									textview9.setText("Link received");
									progressbar1.setProgress((int)100);
									timer.cancel();
									share = share_url.replace("'''".trim(), "\n");
									Intent intentshare = new Intent(Intent.ACTION_SEND);intentshare.setType("text/plain"); intentshare.putExtra(Intent.EXTRA_TEXT,share); startActivity(Intent.createChooser(intentshare, "Share using"));
								}
							}
						});
					}
				};
				_timer.scheduleAtFixedRate(timer, (int)(0), (int)(100));
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), ApplockActivity.class);
				startActivity(intent);
			}
		});
		
		linear19.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_customText("Happy Birthday ".concat(nickname.concat(". I wish you a Very Very Happy Birthday. ")));
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("profile_picture").toString())).into(profile_picture);
					username_textview.setText(_childValue.get("nickname").toString());
					if (_childValue.containsKey("dob")) {
						cal = Calendar.getInstance();
						if (new SimpleDateFormat("d/M").format(cal.getTime()).equals(_childValue.get("dob").toString().substring((int)(0), (int)(_childValue.get("dob").toString().lastIndexOf("/"))).trim())) {
							hscroll3.setVisibility(View.VISIBLE);
						}
						else {
							hscroll3.setVisibility(View.GONE);
						}
					}
					else {
						hscroll3.setVisibility(View.GONE);
					}
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
		
		_share_app_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals("Share_app_url")) {
					share_url = _childValue.get("share_app").toString();
				}
				else {
					
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
		share_app.addChildEventListener(_share_app_child_listener);
		
		_message_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals("message")) {
					messag = _childValue.get("message").toString();
					if (dev_mess.getString("message", "").equals("")) {
						dev_mess.edit().putString("message", _childValue.get("message").toString()).commit();
						animation.setTarget(button4);
						animation.setPropertyName("alpha");
						animation.setFloatValues((float)(0.1d), (float)(1));
						animation.setDuration((int)(2000));
						animator = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										animation.cancel();
										animation.start();
									}
								});
							}
						};
						_timer.scheduleAtFixedRate(animator, (int)(0), (int)(2000));
					}
					else {
						if (dev_mess.getString("message", "").equals(_childValue.get("message").toString())) {
							
						}
						else {
							dev_mess.edit().putString("message", _childValue.get("message").toString()).commit();
							animation.setTarget(button4);
							animation.setPropertyName("alpha");
							animation.setFloatValues((float)(10), (float)(100));
							animation.setDuration((int)(2000));
							animator = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											animation.cancel();
											animation.start();
										}
									});
								}
							};
							_timer.scheduleAtFixedRate(animator, (int)(0), (int)(2000));
						}
					}
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
		message.addChildEventListener(_message_child_listener);
		
		_Firebase_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_Firebase_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_Firebase_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	private void initializeLogic() {
		setTitle("Menu");
		sp.edit().putString("mode", "login").commit();
		if (sp.getString("visit", "").equals("")) {
			if (mode.getString("mode", "").equals("register")) {
				nickname = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring((int)(0), (int)(FirebaseAuth.getInstance().getCurrentUser().getEmail().lastIndexOf(" @".trim()))).concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(10000), (int)(99999)))));
				userdata = new HashMap<>();
				userdata.put("nickname", nickname);
				userdata.put("profile_picture", save_data_locally.getString("profile_pic_download_url", ""));
				userdata.put("dob", save_data_locally.getString("dob", ""));
				users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(userdata);
				userdata.clear();
				sp.edit().putString("visit", "0").commit();
				mode.edit().putString("mode", "login").commit();
			}
			else {
				sp.edit().putString("visit", "0").commit();
			}
		}
		else {
			visitstr = sp.getString("visit", "");
			visit = Double.parseDouble(visitstr);
			visit++;
			sp.edit().putString("visit", String.valueOf((long)(visit))).commit();
		}
		textview6.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
		textview8.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
		linear12.setVisibility(View.GONE);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		finishAffinity () ;
	}
	
	@Override
	public void onStop() {
		super.onStop();
		if (animation.isRunning()) {
			animation.cancel();
			animator.cancel();
		}
		else {
			
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
