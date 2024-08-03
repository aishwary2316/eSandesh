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
import java.util.HashMap;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;

public class RegisterActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private String Email = "";
	private String Name = "";
	private String Phone = "";
	private String Password = "";
	private String code = "";
	private double index = 0;
	private HashMap<String, Object> Users_Detail = new HashMap<>();
	private String image_path = "";
	private String image_name = "";
	private String storage = "";
	private double dp = 0;
	private double peofilePicture = 0;
	private String downloadUrl = "";
	private boolean error = false;
	private boolean name_error = false;
	private boolean phone_error = false;
	private String otp = "";
	private String phoneVerification = "";
	private String codeSent = "";
	private String phoneNumber = "";
	private double time = 0;
	private double otpresult = 0;
	private double otpClick = 0;
	private String mVerificationId = "";
	private HashMap<String, Object> country_id = new HashMap<>();
	private String flags = "";
	private double pClick = 0;
	private double rpClick = 0;
	private String default_dp = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private Button button1;
	private TextView textview1;
	private LinearLayout linear13;
	private EditText name;
	private LinearLayout linear7;
	private LinearLayout linear12;
	private LinearLayout linear5;
	private Button button3;
	private EditText edittext2;
	private EditText email;
	private LinearLayout linear15;
	private LinearLayout linear19;
	private LinearLayout linear8;
	private LinearLayout linear10;
	private LinearLayout linear9;
	private TextView textview2;
	private ImageView imageview4;
	private TextView textview5;
	private TextView dob;
	private TextView textview3;
	private TextView age;
	private TextView textview6;
	private TextView country_name;
	private ImageView country_flag;
	private EditText country_code;
	private EditText phone;
	private ImageView imageview8;
	private LinearLayout linear28;
	private LinearLayout linear29;
	private ImageView imageview9;
	private EditText password;
	private ImageView imageview3;
	private LinearLayout linear21;
	private LinearLayout linear22;
	private ImageView imageview2;
	private EditText re_enter_password;
	
	private FirebaseAuth Firebase_auth;
	private OnCompleteListener<Void> Firebase_auth_updateEmailListener;
	private OnCompleteListener<Void> Firebase_auth_updatePasswordListener;
	private OnCompleteListener<Void> Firebase_auth_emailVerificationSentListener;
	private OnCompleteListener<Void> Firebase_auth_deleteUserListener;
	private OnCompleteListener<Void> Firebase_auth_updateProfileListener;
	private OnCompleteListener<AuthResult> Firebase_auth_phoneAuthListener;
	private OnCompleteListener<AuthResult> _Firebase_auth_create_user_listener;
	private OnCompleteListener<AuthResult> _Firebase_auth_sign_in_listener;
	private OnCompleteListener<Void> _Firebase_auth_reset_password_listener;
	private Intent intent = new Intent();
	private AlertDialog.Builder dialog;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private SharedPreferences save_data_locally;
	private SharedPreferences mode;
	private SharedPreferences last_action;
	private SharedPreferences rrg;
	private TimerTask t;
	private RequestNetwork flags_name;
	private RequestNetwork.RequestListener _flags_name_request_listener;
	private RequestNetwork country_calling_code;
	private RequestNetwork.RequestListener _country_calling_code_request_listener;
	private DatabaseReference default_profile_pic = _firebase.getReference("Defaults");
	private ChildEventListener _default_profile_pic_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.register);
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
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		button1 = (Button) findViewById(R.id.button1);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		name = (EditText) findViewById(R.id.name);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		button3 = (Button) findViewById(R.id.button3);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		email = (EditText) findViewById(R.id.email);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		linear19 = (LinearLayout) findViewById(R.id.linear19);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		textview2 = (TextView) findViewById(R.id.textview2);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview5 = (TextView) findViewById(R.id.textview5);
		dob = (TextView) findViewById(R.id.dob);
		textview3 = (TextView) findViewById(R.id.textview3);
		age = (TextView) findViewById(R.id.age);
		textview6 = (TextView) findViewById(R.id.textview6);
		country_name = (TextView) findViewById(R.id.country_name);
		country_flag = (ImageView) findViewById(R.id.country_flag);
		country_code = (EditText) findViewById(R.id.country_code);
		phone = (EditText) findViewById(R.id.phone);
		imageview8 = (ImageView) findViewById(R.id.imageview8);
		linear28 = (LinearLayout) findViewById(R.id.linear28);
		linear29 = (LinearLayout) findViewById(R.id.linear29);
		imageview9 = (ImageView) findViewById(R.id.imageview9);
		password = (EditText) findViewById(R.id.password);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		linear21 = (LinearLayout) findViewById(R.id.linear21);
		linear22 = (LinearLayout) findViewById(R.id.linear22);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		re_enter_password = (EditText) findViewById(R.id.re_enter_password);
		Firebase_auth = FirebaseAuth.getInstance();
		dialog = new AlertDialog.Builder(this);
		save_data_locally = getSharedPreferences("profile_picture", Activity.MODE_PRIVATE);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		last_action = getSharedPreferences("lastAction", Activity.MODE_PRIVATE);
		rrg = getSharedPreferences("S", Activity.MODE_PRIVATE);
		flags_name = new RequestNetwork(this);
		country_calling_code = new RequestNetwork(this);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((name.getText().toString().equals("") || email.getText().toString().equals("")) || (password.getText().toString().equals("") || re_enter_password.getText().toString().equals(""))) {
					_customText("⚠️ All fields are required");
				}
				else {
					if (!(dob.getText().toString().equals("...") || age.getText().toString().equals("..."))) {
						if (name_error || (phone_error || error)) {
							if ((password.getText().toString().length() > 6) || (re_enter_password.getText().toString().length() > 6)) {
								if (!country_code.getText().toString().equals("")) {
									if (password.getText().toString().equals(re_enter_password.getText().toString())) {
										otp = edittext2.getText().toString();
										_Verify("mVerificationId", otp);
										name.setText(name.getText().toString().trim());
										email.setText(email.getText().toString().trim());
										password.setText(password.getText().toString().trim());
										Name = name.getText().toString();
										Phone = phone.getText().toString();
										Email = email.getText().toString();
										Password = password.getText().toString();
										index = email.getText().toString().lastIndexOf(" @".trim());
										code = Name.concat("-".trim()).concat(Email.substring((int)(0), (int)(index)));
										code = code.replace(".", "dot");
										Firebase_auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(RegisterActivity.this, _Firebase_auth_create_user_listener);
										_customText("Account Creation under progress");
									}
									else {
										_customText("⚠️ The Password first entered did not match the password entered second time ");
									}
								}
								else {
									country_code.setError("Input Country code in proper format (eg. +91 for INDIA)");
								}
							}
							else {
								dialog.setTitle("Invalid Password !");
								dialog.setMessage("The Password you entered is too short.\nCreate a strong password with atleast 7 characters and retry.\nCreating a strong password increases security.\n\nSuggestion : Use a combination of numbers and, alphabets. ");
								dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface _dialog, int _which) {
										password.setText("");
										re_enter_password.setText("");
									}
								});
								dialog.create().show();
							}
						}
						else {
							_customText("⚠️ Invalid credentials");
						}
					}
					else {
						_customText("⚠️ Please submit your Date of Birth\n      You have not submitted your Date of Birth");
					}
				}
			}
		});
		
		name.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() < 3) {
					name_error = true;
					_customText("⚠️ Name should contain atleast 3 characters");
				}
				else {
					if (_charSeq.length() < 41) {
						name_error = true;
						_customText("⚠️ Name should not contain more than 40 characters");
					}
					else {
						name_error = false;
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				otpClick++;
				if (country_code.getText().toString().equals("")) {
					country_code.setError ("Country code required in proper format") ;
				}
				else {
					if (phone.getText().toString().trim().length() == 10) {
						phoneNumber = country_code.getText().toString().concat(phone.getText().toString());
						_customText("OTP Sent");
						t = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										if (!(time == 0)) {
											button3.setText("Resend OTP in ".concat(String.valueOf((long)(time))).concat(" Seconds"));
											time--;
											button3.setEnabled(false);
										}
										else {
											button3.setEnabled(true);
											_Resend(phoneNumber);
											time = 60;
											button3.setText("Resend OTP");
											t.cancel();
										}
									}
								});
							}
						};
						_timer.scheduleAtFixedRate(t, (int)(1000), (int)(1000));
						if (otpClick == 1) {
							_Login(phoneNumber);
						}
					}
					else {
						phone.setError ("Input ten digit phone number") ;
					}
				}
			}
		});
		
		email.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (!(" @".trim().indexOf(_charSeq) == -1)) {
					if (" @".trim().indexOf(_charSeq) == " @".trim().lastIndexOf(_charSeq)) {
						error = false;
					}
					else {
						error = true;
						_customText("⚠️ Enter a valid email address");
					}
				}
				else {
					error = true;
					_customText("⚠️ Enter a valid email address");
				}
				if (!(".".trim().indexOf(_charSeq) == -1)) {
					error = true;
					_customText("⚠️ Enter a valid email address");
				}
				else {
					error = false;
				}
				if ("domain".trim().indexOf(_charSeq) == -1) {
					error = true;
					_customText("⚠️ Enter a valid email address");
				}
				else {
					error = true;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		textview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				showDatePickerDialog(imageview4);
			}
		});
		
		phone.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() == 10) {
					phone_error = false;
				}
				else {
					phone_error = true;
					_customText("⚠️ Phone number should contain only ten characters excluding +91");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		imageview9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				pClick++;
				if ((pClick % 2) == 1) {
					password.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
					imageview9.setImageResource(R.drawable.ic_remove_red_eye_white);
				}
				else {
					password.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
					imageview9.setImageResource(R.drawable.ic_visibility_off_white);
				}
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				rpClick++;
				if ((rpClick % 2) == 1) {
					re_enter_password.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
					imageview2.setImageResource(R.drawable.ic_remove_red_eye_white);
				}
				else {
					re_enter_password.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
					imageview2.setImageResource(R.drawable.ic_visibility_off_white);
				}
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		
		_flags_name_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _response = _param2;
				country_id = new HashMap<>();
				country_id = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
				country_name.setText(country_id.get("country").toString());
				flags = "https://www.countryflags.io/".concat(country_id.get("countryCode").toString().concat("/flat/64.png"));
				Glide.with(getApplicationContext()).load(Uri.parse(flags)).into(country_flag);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				_customText("Unable to load Country name and country flag hence showing default.\nError Message : ".concat(_message));
			}
		};
		
		_country_calling_code_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _response = _param2;
				if (_response.length() < 5) {
					country_code.setText(_response);
				}
				else {
					country_code.setText("+91");
					_customText("Unable to load Country code, Hence showing default country code\nYou may change it if you wish ");
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				_customText("Unable to load Country Code\nError message : ".concat(_message));
			}
		};
		
		_default_profile_pic_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals("Profile Picture")) {
					default_dp = _childValue.get("Profile Picture").toString();
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
		default_profile_pic.addChildEventListener(_default_profile_pic_child_listener);
		
		Firebase_auth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Firebase_auth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Firebase_auth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Firebase_auth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		Firebase_auth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		Firebase_auth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_Firebase_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					index = email.getText().toString().lastIndexOf(" @".trim());
					code = Name.concat("-".trim()).concat(Email.substring((int)(0), (int)(index)));
					code = code.replace(".", "dot");
					mode.edit().putString("mode", "register").commit();
					save_data_locally.edit().putString("dob", dob.getText().toString()).commit();
					Firebase_auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(RegisterActivity.this, _Firebase_auth_sign_in_listener);
					_customText("Account Created\nNow signing you in");
				}
				else {
					dialog.setTitle("Unable to register !");
					dialog.setMessage(_errorMessage);
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
			}
		};
		
		_Firebase_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					_customText("Sign in Success...");
					intent.setClass(getApplicationContext(), MenuActivity.class);
					startActivity(intent);
				}
				else {
					dialog.setTitle("Unable to sign in");
					dialog.setMessage(_errorMessage);
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
				}
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
		linear1.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		otpClick = 0;
		dp = 0;
		peofilePicture = 0;
		time = 60;
		pClick = 0;
		rpClick = 0;
		_setSingleLine(name, true);
		_setSingleLine(edittext2, true);
		_setSingleLine(email, true);
		_setSingleLine(country_code, true);
		_setSingleLine(phone, true);
		_setSingleLine(password, true);
		_setSingleLine(re_enter_password, true);
		dialog.setTitle("Security Notice! ");
		dialog.setMessage("Since user data security is our top priority so we want to tell you that your very little data will be made public.\nYour name, email, passwords, and phone numbers will be kept private so you should enter only original details.\nYou will be allowed to set a nickname for your account later which will be made public\nYour profile picture can be seen by others but they can not save it in anyform in their device.\nSo enter correct details only.");
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		dialog.create().show();
		country_code.setText("+91");
		_extra();
		_Callback();
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
	public void onStart() {
		super.onStart();
		flags_name.startRequestNetwork(RequestNetworkController.GET, "http://ip-api.com/json/", "flags_name", _flags_name_request_listener);
		country_calling_code.startRequestNetwork(RequestNetworkController.GET, "https://ipapi.co/country_calling_code/", "dial", _country_calling_code_request_listener);
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
	
	
	private void _extra () {
	}
	
	// Define showDatePickerDialog(View).
	public void showDatePickerDialog(View v) {
		// Create and show a new DatePickerFragment.
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}
	
	// Define a DialogFragment class DatePickerFragment.
	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		// Define a new Calendar for present date
		Calendar now = Calendar.getInstance();
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Create DatePickerFragment (a DialogFragment) with a new DatePickerDialog,
			// Present day of month, month, and year are set as the day, month, and year of this DatePickerDialog.
			int y = now.get(Calendar.YEAR);
			int m = now.get(Calendar.MONTH);
			int d = now.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, y, m, d);
		}
		
		// When Date if birth is selected
		public void onDateSet(DatePicker view, int year, int month, int day) {
			int mon = month +1;
			// Define a new Calendar for birth date.
			Calendar birthDay = Calendar.getInstance();
			String date = day + "/" + mon + "/" + year;
			// Define the two EditText again using their IDs in main.xml.
			TextView edittext21 = getActivity().findViewById(R.id.dob);
			TextView textview22 = getActivity().findViewById(R.id.age);
			edittext21.setText(date);
			// Set the selected year, month, and day as the year, month, and day of Calendar birthDay.
			birthDay.set(Calendar.YEAR, year);
			birthDay.set(Calendar.MONTH, month);
			birthDay.set(Calendar.DAY_OF_MONTH, day);
			// find difference between present date and selected date in milliseconds.
			double diff = (long)(now.getTimeInMillis() - birthDay.getTimeInMillis());
			// If difference is less than 0, show message that selected date is in future.
			if (diff < 0) {
				Toast.makeText(getContext(), "Selected date is in future.", Toast.LENGTH_SHORT).show();
				textview22.setText("");
			} else {
				// Get difference between years
				int years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
				int currMonth = now.get(Calendar.MONTH) + 1;
				int birthMonth = birthDay.get(Calendar.MONTH) + 1;
				// Get difference between months
				int months = currMonth - birthMonth;
				// If month difference is negative then reduce years by one
				// and calculate the number of months.
				if (months < 0){
					years--;
					months = 12 - birthMonth + currMonth;
					if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
					months--;
				} else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)){
					years--;
					months = 11;
				}
				// Calculate the days
				int days = 0;
				if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
				days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
				else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
				{
					int today = now.get(Calendar.DAY_OF_MONTH);
					now.add(Calendar.MONTH, -1);
					days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
				} else {
					days = 0;
					if (months == 12){
						years++;
						months = 0;
					}
				}
				// Display the age in years, months and days
				textview22.setText(years + " years, " + months + " months, " + days + " days");
			}
		}
	}
	
	
	private void _Callback () {
		mAuth = com.google.firebase.auth.FirebaseAuth.getInstance(); 
		mCallbacks = new com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
			 @Override
			public void onVerificationCompleted(com.google.firebase.auth.PhoneAuthCredential credential) {
				signInWithPhoneAuthCredential(credential);
			}
			 @Override
			public void onVerificationFailed(com.google.firebase.FirebaseException e) {
				if (e instanceof com.google.firebase.auth.FirebaseAuthInvalidCredentialsException) {
					_Snackbar(linear1, "Invalid phone number.");
				} else if (e instanceof com.google.firebase.FirebaseTooManyRequestsException) {
					_Snackbar(linear1, "Quota exceeded.");
				} }
			 @Override
			public void onCodeSent(String verificationId, com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken token) {
				mVerificationId = verificationId;
				mResendToken = token;
			} };
	}
	
	
	private void _Snackbar (final View _v, final String _text) {
		com.google.android.material.snackbar.Snackbar.make(_v,_text,com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
	}
	
	
	private void _signinWithFirebase () {
	}
	private com.google.firebase.auth.FirebaseAuth mAuth;
	private com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken mResendToken;
	private com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
	private void signInWithPhoneAuthCredential(com.google.firebase.auth.PhoneAuthCredential credential) {
		mAuth.signInWithCredential(credential).addOnCompleteListener(this, new com.google.android.gms.tasks.OnCompleteListener<com.google.firebase.auth.AuthResult>() {
			@Override
			public void onComplete(@androidx.annotation.NonNull com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> task) {
				if (task.isSuccessful()) {
					com.google.firebase.auth.FirebaseUser user = task.getResult().getUser();
					_loginSuccess();
				} else {
					if (task.getException() instanceof com.google.firebase.auth.FirebaseAuthInvalidCredentialsException) {
						_Snackbar(linear1, "Invalid code.");
					} } } });
	}
	
	
	private void _Login (final String _number) {
		com.google.firebase.auth.PhoneAuthProvider.getInstance().verifyPhoneNumber(
		_number,
		60,
		java.util.concurrent.TimeUnit.SECONDS,
		this,
		mCallbacks);
	}
	
	
	private void _Verify (final String _id, final String _code) {
		com.google.firebase.auth.PhoneAuthCredential credential = com.google.firebase.auth.PhoneAuthProvider.getCredential(_id, _code); signInWithPhoneAuthCredential(credential);
	}
	
	
	private void _Resend (final String _number) {
		resendVerificationCode(_number,mResendToken);
	}
	private void resendVerificationCode(String phoneNumber, com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken token) {
		com.google.firebase.auth.PhoneAuthProvider.getInstance().verifyPhoneNumber(
		phoneNumber,
		60,
		java.util.concurrent.TimeUnit.SECONDS,
		this,
		mCallbacks,
		token);
	}
	
	
	private void _loginSuccess () {
		_customText("OTP Successfully Verified");
	}
	
	
	private void _setSingleLine (final TextView _view, final boolean _logicalValue) {
		_view.setSingleLine(_logicalValue);
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
