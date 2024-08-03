package com.aishwary.sender;

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
	private EditText phone;
	private EditText email;
	private EditText password;
	private EditText re_enter_password;
	
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
		phone = (EditText) findViewById(R.id.phone);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		re_enter_password = (EditText) findViewById(R.id.re_enter_password);
		Firebase_auth = FirebaseAuth.getInstance();
		dialog = new AlertDialog.Builder(this);
		file_picker.setType("image/*");
		file_picker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		save_data_locally = getSharedPreferences("profile_picture", Activity.MODE_PRIVATE);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((name.getText().toString().equals("") || email.getText().toString().equals("")) || (password.getText().toString().equals("") || re_enter_password.getText().toString().equals(""))) {
					SketchwareUtil.showMessage(getApplicationContext(), "⚠️ All fields are required");
				}
				else {
					if (dp == 1) {
						if (peofilePicture == 1) {
							if (name_error || (phone_error || error)) {
								if ((password.getText().toString().length() > 6) || (re_enter_password.getText().toString().length() > 6)) {
									if (password.getText().toString().equals(re_enter_password.getText().toString())) {
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
										Users_Detail.put("2) Phone", Phone);
										Users_Detail.put("3) Email", Email);
										Users_Detail.put("4) Password", Password);
										Firebase_Database.child(code).updateChildren(Users_Detail);
										Firebase_auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(RegisterActivity.this, _Firebase_auth_create_user_listener);
										Users_Detail.clear();
									}
									else {
										SketchwareUtil.showMessage(getApplicationContext(), "⚠️ The Password first entered did not match the password entered second time ");
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
								SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Invalid credentials");
							}
						}
						else {
							SketchwareUtil.showMessage(getApplicationContext(), "Wait till profile picture is uploaded ");
						}
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Choose a profile picture");
					}
				}
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (name.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "First fill your name");
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
					SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Name should contain atleast 3 characters");
					name_error = true;
				}
				else {
					if (_charSeq.length() < 41) {
						SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Name should not contain more than 40 characters");
						name_error = true;
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
		
		phone.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if ("+91".trim().indexOf(_charSeq) == -1) {
					if (_charSeq.length() == 10) {
						phone_error = false;
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Phone number should contain only ten characters excluding +91 (if present)");
						phone_error = true;
					}
				}
				else {
					if (_charSeq.length() == 13) {
						phone_error = false;
					}
					else {
						phone_error = true;
						SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Phone number should contain only ten characters excluding +91 (if present)");
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
		
		email.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (!(" @".trim().indexOf(_charSeq) == -1)) {
					if (" @".trim().indexOf(_charSeq) == " @".trim().lastIndexOf(_charSeq)) {
						error = false;
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Enter a valid email address");
						error = true;
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Enter a valid email address");
					error = true;
				}
				if (!(".".trim().indexOf(_charSeq) == -1)) {
					SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Enter a valid email address");
					error = true;
				}
				else {
					error = false;
				}
				if ("domain".trim().indexOf(_charSeq) == -1) {
					SketchwareUtil.showMessage(getApplicationContext(), "⚠️ Enter a valid email address");
					error = true;
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
				SketchwareUtil.showMessage(getApplicationContext(), "Profile Picture successfully uploaded");
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
					mode.edit().putString("mode", "register").commit();
					Firebase_auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(RegisterActivity.this, _Firebase_auth_sign_in_listener);
					intent.setClass(getApplicationContext(), RegisteredActivity.class);
					startActivity(intent);
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
					Users_Detail.put("Name", Name);
					Users_Detail.put("Phone", Phone);
					Users_Detail.put("Email", Email);
					Users_Detail.put("Password", Password);
					Users_Detail.put("profile_picture", downloadUrl);
					index = email.getText().toString().lastIndexOf(" @".trim());
					code = Name.concat("-".trim()).concat(Email.substring((int)(0), (int)(index)));
					code = code.replace(".", "dot");
					Users_Detail.put("User access code", code);
					users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(Users_Detail);
					SketchwareUtil.showMessage(getApplicationContext(), "User details added to database");
					Users_Detail.clear();
				}
				else {
					dialog.setTitle("Unable to sign in");
					dialog.setMessage(_errorMessage);
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							dialog.setTitle("Profile picture lost");
							dialog.setMessage("Due to interruptions while signing in  for the first time the profile picture you uploaded has lost please upload the profile picture once more");
							dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									intent.setClass(getApplicationContext(), ProfileActivity.class);
									startActivity(intent);
								}
							});
							dialog.create().show();
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
