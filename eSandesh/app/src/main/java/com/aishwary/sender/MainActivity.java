package com.aishwary.sender;

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
import android.widget.LinearLayout;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.TextView;
import android.widget.ProgressBar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Context;
import android.os.Vibrator;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CompoundButton;
import android.content.ClipData;
import android.content.ClipboardManager;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private double progress_value = 0;
	private double network_status = 0;
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private CheckBox checkbox1;
	private LinearLayout linear4;
	private ImageView imageview4;
	private WebView html_animation;
	private TextView textview2;
	private ImageView imageview1;
	private ProgressBar progressbar1;
	private ImageView imageview5;
	private ImageView imageview3;
	
	private FirebaseAuth Firebase_auth;
	private OnCompleteListener<AuthResult> _Firebase_auth_create_user_listener;
	private OnCompleteListener<AuthResult> _Firebase_auth_sign_in_listener;
	private OnCompleteListener<Void> _Firebase_auth_reset_password_listener;
	private Vibrator vibrator;
	private Intent intent = new Intent();
	private TimerTask timer;
	private ObjectAnimator animator = new ObjectAnimator();
	private TimerTask animation_timer;
	private TimerTask progress;
	private RequestNetwork request_internet;
	private RequestNetwork.RequestListener _request_internet_request_listener;
	private TimerTask network_timer;
	private SharedPreferences shared_prefence;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		html_animation = (WebView) findViewById(R.id.html_animation);
		html_animation.getSettings().setJavaScriptEnabled(true);
		html_animation.getSettings().setSupportZoom(true);
		textview2 = (TextView) findViewById(R.id.textview2);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		Firebase_auth = FirebaseAuth.getInstance();
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		request_internet = new RequestNetwork(this);
		shared_prefence = getSharedPreferences("visit", Activity.MODE_PRIVATE);
		
		checkbox1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				progress.cancel();
				progressbar1.setIndeterminate(true);
			}
		});
		
		checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					timer.cancel();
				}
				else {
					if (shared_prefence.getString("visits", "").equals("")) {
						intent.setClass(getApplicationContext(), TutorialActivity.class);
						startActivity(intent);
					}
					else {
						intent.setClass(getApplicationContext(), LoginActivity.class);
						startActivity(intent);
					}
				}
			}
		});
		
		linear4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", "aishwary2316@outlook.com"));
				SketchwareUtil.showMessage(getApplicationContext(), "Developer Aishwary Raj's personal email copied to clipboard");
			}
		});
		
		html_animation.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
		
		textview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", "aishwary2316@outlook.com"));
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", "Developer Email copied to clipboard"));
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", "aishwary2316@outlook.com"));
				SketchwareUtil.showMessage(getApplicationContext(), "Developer Email copied to clipboard");
			}
		});
		
		_request_internet_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _response = _param2;
				if (network_status == 0) {
					network_timer.cancel();
					progressbar1.setIndeterminate(false);
					progress_value = 0;
					progress = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									progressbar1.setProgress((int)progress_value);
									progress_value = progress_value + 100;
								}
							});
						}
					};
					_timer.scheduleAtFixedRate(progress, (int)(0), (int)(100));
					timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									progress.cancel();
									animation_timer.cancel();
									animator.cancel();
									if (shared_prefence.getString("visits", "").equals("")) {
										intent.setClass(getApplicationContext(), TutorialActivity.class);
										startActivity(intent);
									}
									else {
										intent.setClass(getApplicationContext(), LoginActivity.class);
										startActivity(intent);
									}
								}
							});
						}
					};
					_timer.schedule(timer, (int)(5000));
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				SketchwareUtil.showMessage(getApplicationContext(), "No internet connection.\nMake sure you have an active mobile data or wifi connection");
				timer.cancel();
				progressbar1.setIndeterminate(true);
				network_status = 0;
			}
		};
		
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
		request_internet.startRequestNetwork(RequestNetworkController.GET, "https://www.google.com/?gws_rd=ssl#spf=1587216170687", "A", _request_internet_request_listener);
		network_status = 1;
		html_animation.loadUrl("https://www.script-tutorials.com/demos/234/ex6.html");
		progressbar1.setIndeterminate(false);
		progress = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						progressbar1.setProgress((int)progress_value);
						progress_value = progress_value + 100;
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(progress, (int)(0), (int)(100));
		animator.setTarget(imageview1);
		animator.setPropertyName("alpha");
		animator.setFloatValues((float)(0), (float)(1));
		animator.setDuration((int)(2000));
		animator.start();
		animation_timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						animator.cancel();
						animator.start();
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(animation_timer, (int)(2000), (int)(2000));
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						progress.cancel();
						animation_timer.cancel();
						animator.cancel();
						if (shared_prefence.getString("visits", "").equals("")) {
							intent.setClass(getApplicationContext(), TutorialActivity.class);
							startActivity(intent);
						}
						else {
							intent.setClass(getApplicationContext(), LoginActivity.class);
							startActivity(intent);
						}
					}
				});
			}
		};
		_timer.schedule(timer, (int)(10000));
		network_timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						request_internet.startRequestNetwork(RequestNetworkController.GET, "https://www.google.com/?gws_rd=ssl#spf=1587216170687", "A", _request_internet_request_listener);
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(network_timer, (int)(2000), (int)(2000));
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
		finishAffinity() ;
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
