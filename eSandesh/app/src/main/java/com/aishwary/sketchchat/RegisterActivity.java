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
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.EditText;
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
import android.content.ClipData;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Continuation;
import java.io.File;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import com.bumptech.glide.Glide;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class RegisterActivity extends AppCompatActivity {
	
	public final int REQ_CD_FILE_PICKER = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
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
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear3;
	private LinearLayout linear2;
	private Button button1;
	private ImageView imageview2;
	private LinearLayout linear4;
	private ProgressBar progressbar1;
	private ImageView imageview1;
	private TextView textview1;
	private EditText name;
	private LinearLayout linear7;
	private LinearLayout linear5;
	private Button button3;
	private EditText edittext2;
	private EditText email;
	private EditText password;
	private EditText re_enter_password;
	private LinearLayout linear8;
	private LinearLayout linear10;
	private LinearLayout linear9;
	private TextView textview2;
	private ImageView imageview4;
	private TextView textview5;
	private TextView dob;
	private TextView textview3;
	private TextView age;
	private EditText country_code;
	private EditText phone;
	
	private FirebaseAuth Firebase_auth;
	private OnCompleteListener<AuthResult> _Firebase_auth_create_user_listener;
	private OnCompleteListener<AuthResult> _Firebase_auth_sign_in_listener;
	private OnCompleteListener<Void> _Firebase_auth_reset_password_listener;
	private DatabaseReference Firebase_Database = _firebase.getReference("User Details");
	private ChildEventListener _Firebase_Database_child_listener;
	private Intent intent = new Intent();
	private AlertDialog.Builder dialog;
	private Intent file_picker = new Intent(Intent.ACTION_GET_CONTENT);
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private StorageReference Firebase_storage = _firebase_storage.getReference("Profile Pictures");
	private OnCompleteListener<Uri> _Firebase_storage_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _Firebase_storage_download_success_listener;
	private OnSuccessListener _Firebase_storage_delete_success_listener;
	private OnProgressListener _Firebase_storage_upload_progress_listener;
	private OnProgressListener _Firebase_storage_download_progress_listener;
	private OnFailureListener _Firebase_storage_failure_listener;
	private SharedPreferences save_data_locally;
	private SharedPreferences mode;
	private SharedPreferences last_action;
	private SharedPreferences rrg;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.register);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		button1 = (Button) findViewById(R.id.button1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview1 = (TextView) findViewById(R.id.textview1);
		name = (EditText) findViewById(R.id.name);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		button3 = (Button) findViewById(R.id.button3);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		re_enter_password = (EditText) findViewById(R.id.re_enter_password);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		textview2 = (TextView) findViewById(R.id.textview2);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview5 = (TextView) findViewById(R.id.textview5);
		dob = (TextView) findViewById(R.id.dob);
		textview3 = (TextView) findViewById(R.id.textview3);
		age = (TextView) findViewById(R.id.age);
		country_code = (EditText) findViewById(R.id.country_code);
		phone = (EditText) findViewById(R.id.phone);
		Firebase_auth = FirebaseAuth.getInstance();
		dialog = new AlertDialog.Builder(this);
		file_picker.setType("image/*");
		file_picker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		save_data_locally = getSharedPreferences("profile_picture", Activity.MODE_PRIVATE);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		last_action = getSharedPreferences("lastAction", Activity.MODE_PRIVATE);
		rrg = getSharedPreferences("S", Activity.MODE_PRIVATE);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((name.getText().toString().equals("") || email.getText().toString().equals("")) || (password.getText().toString().equals("") || re_enter_password.getText().toString().equals(""))) {
					_customText("⚠️ All fields are required");
				}
				else {
					if (dp == 1) {
						if (peofilePicture == 1) {
							if (!(dob.getText().toString().equals("...") || age.getText().toString().equals("..."))) {
								if (name_error || (phone_error || error)) {
									if ((password.getText().toString().length() > 6) || (re_enter_password.getText().toString().length() > 6)) {
										if (!country_code.getText().toString().equals("")) {
											if (password.getText().toString().equals(re_enter_password.getText().toString())) {
												_verifyCode();
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
												Users_Detail = new HashMap<>();
												Users_Detail.put("1) Name", Name);
												Users_Detail.put("2) Date of Birth", dob.getText().toString());
												Users_Detail.put("3) Phone", Phone);
												Users_Detail.put("4) Email", Email);
												Users_Detail.put("5) Password", Password);
												Firebase_Database.child(code).updateChildren(Users_Detail);
												Firebase_auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(RegisterActivity.this, _Firebase_auth_create_user_listener);
												Users_Detail.clear();
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
						else {
							_customText("Wait till profile picture is uploaded");
						}
					}
					else {
						_customText("⚠️ Choose a profile picture");
					}
				}
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (name.getText().toString().equals("")) {
					_customText("First fill your name");
				}
				else {
					startActivityForResult(file_picker, REQ_CD_FILE_PICKER);
				}
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (name.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "First fill your name ");
				}
				else {
					startActivityForResult(file_picker, REQ_CD_FILE_PICKER);
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
				if (country_code.getText().toString().equals("")) {
					country_code.setError ("Country code required in proper format") ;
				}
				else {
					if (phone.getText().toString().trim().length() == 10) {
						_sendVerificationCode();
						_customText("OTP Sent");
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
		
		_Firebase_Database_child_listener = new ChildEventListener() {
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
		Firebase_Database.addChildEventListener(_Firebase_Database_child_listener);
		
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
		
		_Firebase_storage_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				progressbar1.setProgress((int)_progressValue);
			}
		};
		
		_Firebase_storage_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_Firebase_storage_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				Glide.with(getApplicationContext()).load(Uri.parse(_downloadUrl)).into(imageview2);
				peofilePicture = 1;
				downloadUrl = _downloadUrl;
				save_data_locally.edit().putString("profile_pic_download_url", _downloadUrl).commit();
				_customText("Profile Picture successfully uploaded");
			}
		};
		
		_Firebase_storage_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_Firebase_storage_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_Firebase_storage_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_Firebase_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					Users_Detail = new HashMap<>();
					Users_Detail.put("1) Name", Name);
					Users_Detail.put("2) Date of Birth", dob.getText().toString());
					Users_Detail.put("3) Phone", Phone);
					Users_Detail.put("4) Email", Email);
					Users_Detail.put("5) Password", Password);
					index = email.getText().toString().lastIndexOf(" @".trim());
					code = Name.concat("-".trim()).concat(Email.substring((int)(0), (int)(index)));
					Firebase_Database.child(code).updateChildren(Users_Detail);
					_customText("User details added to database");
					mode.edit().putString("mode", "register").commit();
					save_data_locally.edit().putString("dob", dob.getText().toString()).commit();
					Firebase_auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(RegisterActivity.this, _Firebase_auth_sign_in_listener);
					intent.setClass(getApplicationContext(), RegisteredActivity.class);
					startActivity(intent);
					Users_Detail.clear();
				}
				dialog.setTitle("Unable to register !");
				dialog.setMessage(_errorMessage);
				dialog.create().show();
				SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
			}
		};
		
		_Firebase_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					Users_Detail = new HashMap<>();
					Users_Detail.put("1) Name", Name);
					Users_Detail.put("2) Date of Birth", dob.getText().toString());
					Users_Detail.put("3) Phone", Phone);
					Users_Detail.put("4) Email", Email);
					Users_Detail.put("5) Password", Password);
					Users_Detail.put("6) profile_picture", downloadUrl);
					index = email.getText().toString().lastIndexOf(" @".trim());
					code = Name.concat("-".trim()).concat(Email.substring((int)(0), (int)(index)));
					code = code.replace(".", "dot");
					Users_Detail.put("User access code", code);
					Firebase_Database.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(Users_Detail);
					_customText("Profile Picture successfully uploaded");
					Users_Detail.clear();
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
		dp = 0;
		peofilePicture = 0;
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
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FILE_PICKER:
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
				image_path = _filePath.get((int)(0));
				image_name = Uri.parse(_filePath.get((int)(0))).getLastPathSegment();
				Firebase_storage.child(name.getText().toString()).putFile(Uri.fromFile(new File(image_path))).addOnFailureListener(_Firebase_storage_failure_listener).addOnProgressListener(_Firebase_storage_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
					@Override
					public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
						return Firebase_storage.child(name.getText().toString()).getDownloadUrl();
					}}).addOnCompleteListener(_Firebase_storage_upload_success_listener);
				dp = 1;
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	private void _sendVerificationCode () {
		phoneVerification = country_code.getText().toString().concat(phone.getText().toString());
		com.google.firebase.auth.PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneVerification, 60, java.util.concurrent.TimeUnit.SECONDS, this, mCallbacks);
	}
	com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
		@Override
		public void onVerificationCompleted(com.google.firebase.auth.PhoneAuthCredential phoneAuthCredential) {
			showMessage("Verification completed");
		}
		 @Override
		public void onVerificationFailed(com.google.firebase.FirebaseException e) {
			showMessage(e.toString());
		}
		 @Override
		public void onCodeSent(String s, com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken forceResendingToken) {
			super.onCodeSent(s, forceResendingToken);
			codeSent = s;
			showMessage("OTP Sent");
		}
	};
	{
	}
	
	
	private void _verifyCode () {
		otp = edittext2.getText().toString();
		com.google.firebase.auth.PhoneAuthCredential credential = com.google.firebase.auth.PhoneAuthProvider.getCredential(codeSent, otp);
		signInWithPhoneAuthCredential(credential);
	}
	private void signInWithPhoneAuthCredential(com.google.firebase.auth.PhoneAuthCredential credential) {
		Firebase_auth.signInWithCredential(credential) .addOnCompleteListener(this, _Firebase_auth_sign_in_listener);
	}
	{
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
