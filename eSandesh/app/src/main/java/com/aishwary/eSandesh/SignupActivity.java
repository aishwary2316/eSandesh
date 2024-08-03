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
import java.util.ArrayList;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
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
import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Continuation;
import java.io.File;
import android.content.ClipData;
import android.view.View;
import android.widget.CompoundButton;
import com.bumptech.glide.Glide;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class SignupActivity extends AppCompatActivity {
	
	public final int REQ_CD_FPIC = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private Toolbar _toolbar;
	private double profile_picture = 0;
	private double picture_uploading = 0;
	private String image_path = "";
	private String image_name = "";
	private String dp_download_url = "";
	private HashMap<String, Object> profile_map = new HashMap<>();
	private String defaultDpURL = "";
	private String name = "";
	private double nameNum = 0;
	
	private ArrayList<String> list = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear10;
	private LinearLayout linear12;
	private LinearLayout linear3;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private ImageView profile_pic;
	private LinearLayout linear4;
	private ProgressBar progressbar1;
	private ImageView imageview2;
	private TextView textview4;
	private LinearLayout linear11;
	private ProgressBar progressbar2;
	private TextView textview5;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private ImageView imageview1;
	private TextView textview6;
	private LinearLayout linear16;
	private LinearLayout linear17;
	private LinearLayout linear18;
	private CheckBox checkbox1;
	private Button button2;
	private ImageView imageview5;
	private LinearLayout linear6;
	private EditText edittext1;
	private ImageView imageview4;
	private LinearLayout linear7;
	private EditText edittext2;
	private ImageView imageview3;
	private LinearLayout linear19;
	private LinearLayout linear20;
	private EditText edittext3;
	private ScrollView vscroll2;
	private LinearLayout linear21;
	private TextView textview1;
	private LinearLayout linear5;
	private TextView textview2;
	private TextView textview3;
	
	private Intent i = new Intent();
	private FirebaseAuth fauth;
	private OnCompleteListener<Void> fauth_updateEmailListener;
	private OnCompleteListener<Void> fauth_updatePasswordListener;
	private OnCompleteListener<Void> fauth_emailVerificationSentListener;
	private OnCompleteListener<Void> fauth_deleteUserListener;
	private OnCompleteListener<Void> fauth_updateProfileListener;
	private OnCompleteListener<AuthResult> fauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private SharedPreferences last_action;
	private AlertDialog.Builder dialogue;
	private DatabaseReference default_profile_pic = _firebase.getReference("Defaults");
	private ChildEventListener _default_profile_pic_child_listener;
	private SharedPreferences save_data_locallay;
	private StorageReference storeProfile_pics = _firebase_storage.getReference("Profile Pictures");
	private OnCompleteListener<Uri> _storeProfile_pics_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _storeProfile_pics_download_success_listener;
	private OnSuccessListener _storeProfile_pics_delete_success_listener;
	private OnProgressListener _storeProfile_pics_upload_progress_listener;
	private OnProgressListener _storeProfile_pics_download_progress_listener;
	private OnFailureListener _storeProfile_pics_failure_listener;
	private Intent fpic = new Intent(Intent.ACTION_GET_CONTENT);
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.signup);
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
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		profile_pic = (ImageView) findViewById(R.id.profile_pic);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		textview4 = (TextView) findViewById(R.id.textview4);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		progressbar2 = (ProgressBar) findViewById(R.id.progressbar2);
		textview5 = (TextView) findViewById(R.id.textview5);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview6 = (TextView) findViewById(R.id.textview6);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		linear17 = (LinearLayout) findViewById(R.id.linear17);
		linear18 = (LinearLayout) findViewById(R.id.linear18);
		checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
		button2 = (Button) findViewById(R.id.button2);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		linear19 = (LinearLayout) findViewById(R.id.linear19);
		linear20 = (LinearLayout) findViewById(R.id.linear20);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		vscroll2 = (ScrollView) findViewById(R.id.vscroll2);
		linear21 = (LinearLayout) findViewById(R.id.linear21);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		fauth = FirebaseAuth.getInstance();
		last_action = getSharedPreferences("last_action", Activity.MODE_PRIVATE);
		dialogue = new AlertDialog.Builder(this);
		save_data_locallay = getSharedPreferences("profile_picture", Activity.MODE_PRIVATE);
		fpic.setType("image/*");
		fpic.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fpic, REQ_CD_FPIC);
			}
		});
		
		checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					edittext3.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
				}
				else {
					edittext3.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
				}
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				fauth.signInWithEmailAndPassword(edittext2.getText().toString(), edittext3.getText().toString()).addOnCompleteListener(SignupActivity.this, _fauth_sign_in_listener);
			}
		});
		
		textview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (picture_uploading == 0) {
					profile_map = new HashMap<>();
					profile_map.put("profile_picture", defaultDpURL);
					profile_map.put("dob", getIntent().getStringExtra("dob"));
					profile_map.put("nickname", getIntent().getStringExtra("name"));
					users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(profile_map);
					SketchwareUtil.getAllKeysFromMap(profile_map, list);
					_customText("Default Profile Picture have been successfully set for your eSandesh account.");
					i.setClass(getApplicationContext(), RegisteredActivity.class);
					startActivity(i);
				}
				else {
					i.setClass(getApplicationContext(), RegisteredActivity.class);
					startActivity(i);
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
		
		_default_profile_pic_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals("Profile Picture")) {
					defaultDpURL = _childValue.get("Profile Picture").toString();
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
		
		_storeProfile_pics_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				progressbar1.setProgress((int)_progressValue);
			}
		};
		
		_storeProfile_pics_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_storeProfile_pics_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				picture_uploading = 1;
				dp_download_url = _downloadUrl;
				profile_map = new HashMap<>();
				profile_map.put("profile_picture", _downloadUrl);
				profile_map.put("dob", getIntent().getStringExtra("dob"));
				profile_map.put("nickname", getIntent().getStringExtra("nickname"));
				Glide.with(getApplicationContext()).load(Uri.parse(_downloadUrl)).into(profile_pic);
				users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(profile_map);
				save_data_locallay.edit().putString("profile_pic_download_url", _downloadUrl).commit();
				profile_map.clear();
				_customText("Profile picture successfully added to your eSandesh account.");
				textview3.setText("Done");
			}
		};
		
		_storeProfile_pics_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_storeProfile_pics_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_storeProfile_pics_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				dialogue.setTitle("Failure to upload profile picture");
				dialogue.setMessage("Your request to add a profile picture to your eSandesh profile could not be fulfilled.\nError Message : ".concat(_message));
				dialogue.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						storeProfile_pics.child(last_action.getString("username", "").concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(10000000), (int)(99999999)))).concat("-")).concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(10000000), (int)(99999999)))).concat("-").concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(10000000), (int)(99999999)))).concat("-")).concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(10000000), (int)(99999999)))))))).putFile(Uri.fromFile(new File(image_path))).addOnFailureListener(_storeProfile_pics_failure_listener).addOnProgressListener(_storeProfile_pics_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
							@Override
							public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
								return storeProfile_pics.child(last_action.getString("username", "").concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(10000000), (int)(99999999)))).concat("-")).concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(10000000), (int)(99999999)))).concat("-").concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(10000000), (int)(99999999)))).concat("-")).concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(10000000), (int)(99999999)))))))).getDownloadUrl();
							}}).addOnCompleteListener(_storeProfile_pics_upload_success_listener);
					}
				});
				dialogue.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						profile_picture = 0;
						picture_uploading = 0;
					}
				});
				dialogue.create().show();
			}
		};
		
		fauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		fauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
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
				if (_success) {
					linear2.setVisibility(View.VISIBLE);
					linear3.setVisibility(View.VISIBLE);
					linear10.setVisibility(View.GONE);
				}
				else {
					_customText("Unable to sign in");
					linear12.setVisibility(View.VISIBLE);
				}
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
		profile_picture = 0;
		picture_uploading = 0;
		fauth.signInWithEmailAndPassword(getIntent().getStringExtra("email"), getIntent().getStringExtra("password")).addOnCompleteListener(SignupActivity.this, _fauth_sign_in_listener);
		linear2.setVisibility(View.GONE);
		linear3.setVisibility(View.GONE);
		linear12.setVisibility(View.GONE);
		name = getIntent().getStringExtra("name");
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FPIC:
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
				storeProfile_pics.child(name.concat("-").concat(Uri.parse(_filePath.get((int)(0))).getLastPathSegment())).putFile(Uri.fromFile(new File(_filePath.get((int)(0))))).addOnFailureListener(_storeProfile_pics_failure_listener).addOnProgressListener(_storeProfile_pics_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
					@Override
					public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
						return storeProfile_pics.child(name.concat("-").concat(Uri.parse(_filePath.get((int)(0))).getLastPathSegment())).getDownloadUrl();
					}}).addOnCompleteListener(_storeProfile_pics_upload_success_listener);
				profile_picture = 1;
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
