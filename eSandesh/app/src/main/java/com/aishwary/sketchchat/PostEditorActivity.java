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
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Button;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Build;
import androidx.core.content.FileProvider;
import java.io.File;
import android.content.ClipData;
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
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import java.text.DecimalFormat;
import com.bumptech.glide.Glide;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class PostEditorActivity extends AppCompatActivity {
	
	public final int REQ_CD_CAM = 101;
	public final int REQ_CD_FPICK = 102;
	public final int REQ_CD_FILE = 103;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private Toolbar _toolbar;
	private HashMap<String, Object> m = new HashMap<>();
	private double file_num = 0;
	private double image_num = 0;
	private String image_path = "";
	private String file_path = "";
	private String file_size = "";
	private String file_name = "";
	private HashMap<String, Object> map = new HashMap<>();
	private double gps_lat = 0;
	private double gps_lng = 0;
	private double gps_acc = 0;
	private double net_lat = 0;
	private double net_lng = 0;
	private double net_acc = 0;
	private String sender_nickname = "";
	private String profile_url = "";
	private String likePostId = "";
	private String postId = "";
	
	private ArrayList<String> str = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private ImageView profile_picture;
	private TextView textview1;
	private EditText message;
	private LinearLayout linear6;
	private LinearLayout linear8;
	private LinearLayout linear7;
	private ImageView insert_image;
	private ProgressBar image_progressbar;
	private ImageView capture_photo;
	private ProgressBar camera_progressbar;
	private ImageView attach_file;
	private ProgressBar file_progressbar;
	private Button post;
	
	private DatabaseReference posts = _firebase.getReference("posts");
	private ChildEventListener _posts_child_listener;
	private FirebaseAuth fauth;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_cam;
	private Intent fpick = new Intent(Intent.ACTION_GET_CONTENT);
	private Intent file = new Intent(Intent.ACTION_GET_CONTENT);
	private AlertDialog.Builder dialogue;
	private StorageReference file_store = _firebase_storage.getReference("community_attachment");
	private OnCompleteListener<Uri> _file_store_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _file_store_download_success_listener;
	private OnSuccessListener _file_store_delete_success_listener;
	private OnProgressListener _file_store_upload_progress_listener;
	private OnProgressListener _file_store_download_progress_listener;
	private OnFailureListener _file_store_failure_listener;
	private StorageReference image_store = _firebase_storage.getReference("community_attachment");
	private OnCompleteListener<Uri> _image_store_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _image_store_download_success_listener;
	private OnSuccessListener _image_store_delete_success_listener;
	private OnProgressListener _image_store_upload_progress_listener;
	private OnProgressListener _image_store_download_progress_listener;
	private OnFailureListener _image_store_failure_listener;
	private Calendar cal = Calendar.getInstance();
	private LocationManager gps_location;
	private LocationListener _gps_location_location_listener;
	private LocationManager net_location;
	private LocationListener _net_location_location_listener;
	private SharedPreferences file_url;
	private SharedPreferences image_url;
	private SharedPreferences click;
	private DatabaseReference like = _firebase.getReference("" + likePostId + "");
	private ChildEventListener _like_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.post_editor);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
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
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		profile_picture = (ImageView) findViewById(R.id.profile_picture);
		textview1 = (TextView) findViewById(R.id.textview1);
		message = (EditText) findViewById(R.id.message);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		insert_image = (ImageView) findViewById(R.id.insert_image);
		image_progressbar = (ProgressBar) findViewById(R.id.image_progressbar);
		capture_photo = (ImageView) findViewById(R.id.capture_photo);
		camera_progressbar = (ProgressBar) findViewById(R.id.camera_progressbar);
		attach_file = (ImageView) findViewById(R.id.attach_file);
		file_progressbar = (ProgressBar) findViewById(R.id.file_progressbar);
		post = (Button) findViewById(R.id.post);
		fauth = FirebaseAuth.getInstance();
		_file_cam = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_cam = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_cam= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_cam);
		}
		else {
			_uri_cam = Uri.fromFile(_file_cam);
		}
		cam.putExtra(MediaStore.EXTRA_OUTPUT, _uri_cam);
		cam.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		fpick.setType("image/*");
		fpick.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		file.setType("*/*");
		file.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		dialogue = new AlertDialog.Builder(this);
		gps_location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		net_location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		file_url = getSharedPreferences("file_url", Activity.MODE_PRIVATE);
		image_url = getSharedPreferences("imagr_url", Activity.MODE_PRIVATE);
		click = getSharedPreferences("click", Activity.MODE_PRIVATE);
		
		message.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				_Linkify(message, "#e81586");
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		insert_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fpick, REQ_CD_FPICK);
				click.edit().putString("click_button", "image").commit();
			}
		});
		
		capture_photo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(cam, REQ_CD_CAM);
				click.edit().putString("click_button", "camera").commit();
			}
		});
		
		attach_file.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(file, REQ_CD_FILE);
			}
		});
		
		post.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (message.getText().toString().trim().equals("")) {
					if ((image_num == 0) && (file_num == 0)) {
						_customText("Invalid Post\nYou must fill the message or a picture or file to post it. ");
					}
					else {
						if (image_num == 1) {
							image_store.child(Uri.parse(image_path).getLastPathSegment()).putFile(Uri.fromFile(new File(image_path))).addOnFailureListener(_image_store_failure_listener).addOnProgressListener(_image_store_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
								@Override
								public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
									return image_store.child(Uri.parse(image_path).getLastPathSegment()).getDownloadUrl();
								}}).addOnCompleteListener(_image_store_upload_success_listener);
						}
						if (file_num == 1) {
							file_store.child(Uri.parse(file_path).getLastPathSegment()).putFile(Uri.fromFile(new File(file_path))).addOnFailureListener(_file_store_failure_listener).addOnProgressListener(_file_store_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
								@Override
								public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
									return file_store.child(Uri.parse(file_path).getLastPathSegment()).getDownloadUrl();
								}}).addOnCompleteListener(_file_store_upload_success_listener);
						}
					}
				}
				else {
					if ((image_num == 0) && (file_num == 0)) {
						cal = Calendar.getInstance();
						postId = String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000)))).concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000)))))).concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000))))).concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000)))))));
						map = new HashMap<>();
						map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
						map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
						map.put("senderNickname", sender_nickname);
						map.put("profile_picture", profile_url);
						map.put("key", postId);
						map.put("message", message.getText().toString().trim());
						map.put("time", new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss a").format(cal.getTime()));
						map.put("gps_latitude", String.valueOf(gps_lat));
						map.put("gps_longitude", String.valueOf(gps_lng));
						map.put("gps_accuracy", String.valueOf(gps_acc));
						map.put("net_latitude", String.valueOf(net_lat));
						map.put("net_longitude", String.valueOf(net_lng));
						map.put("net_accuracy", String.valueOf(net_acc));
						posts.child(postId).updateChildren(map);
						map.clear();
						message.setText("");
					}
					else {
						if (image_num == 1) {
							image_store.child(Uri.parse(image_path).getLastPathSegment()).putFile(Uri.fromFile(new File(image_path))).addOnFailureListener(_image_store_failure_listener).addOnProgressListener(_image_store_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
								@Override
								public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
									return image_store.child(Uri.parse(image_path).getLastPathSegment()).getDownloadUrl();
								}}).addOnCompleteListener(_image_store_upload_success_listener);
						}
						if (file_num == 1) {
							file_store.child(Uri.parse(file_path).getLastPathSegment()).putFile(Uri.fromFile(new File(file_path))).addOnFailureListener(_file_store_failure_listener).addOnProgressListener(_file_store_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
								@Override
								public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
									return file_store.child(Uri.parse(file_path).getLastPathSegment()).getDownloadUrl();
								}}).addOnCompleteListener(_file_store_upload_success_listener);
						}
					}
					android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
					_customText("Sending Message... ");
				}
			}
		});
		
		_posts_child_listener = new ChildEventListener() {
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
				dialogue.setTitle("Message post unsuccessful");
				dialogue.setMessage("Your message was not posted\n".concat("Error code :  ").concat(String.valueOf((long)(_errorCode)).concat("\n\nError message : ".concat(_errorMessage))));
				dialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						_customText("Your message was not posted");
					}
				});
			}
		};
		posts.addChildEventListener(_posts_child_listener);
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					textview1.setText(_childValue.get("nickname").toString());
					Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("profile_picture").toString())).into(profile_picture);
					sender_nickname = _childValue.get("nickname").toString();
					profile_url = _childValue.get("profile_picture").toString();
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
		
		_file_store_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				file_progressbar.setVisibility(View.VISIBLE);
				file_progressbar.setIndeterminate(false);
				file_progressbar.setProgress((int)_progressValue);
				_customText("File uploaded : ".concat(String.valueOf(Math.round(_progressValue)).concat(" %")));
			}
		};
		
		_file_store_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_file_store_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				cal = Calendar.getInstance();
				postId = String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000)))).concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000)))))).concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000))))).concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000)))))));
				map = new HashMap<>();
				map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("senderNickname", sender_nickname);
				map.put("profile_picture", profile_url);
				map.put("key", postId);
				map.put("message", message.getText().toString().trim().concat("  "));
				map.put("file", _downloadUrl);
				map.put("time", new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss a").format(cal.getTime()));
				map.put("gps_latitude", String.valueOf(gps_lat));
				map.put("gps_longitude", String.valueOf(gps_lng));
				map.put("gps_accuracy", String.valueOf(gps_acc));
				map.put("net_latitude", String.valueOf(net_lat));
				map.put("net_longitude", String.valueOf(net_lng));
				map.put("net_accuracy", String.valueOf(net_acc));
				posts.child(postId).updateChildren(map);
				map.clear();
				file_num = 0;
				message.setText("");
				_customText("Attachment uploaded and posted");
				file_progressbar.setVisibility(View.GONE);
				attach_file.setImageResource(R.drawable.ib_attach_20200412180718);
				file_url.edit().putString("fileDownloadUrl", _downloadUrl).commit();
			}
		};
		
		_file_store_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_file_store_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_file_store_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				dialogue.setTitle("File upload unsuccessful ! ");
				dialogue.setMessage(_message);
				dialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						_customText("Message post unsuccessful ");
					}
				});
			}
		};
		
		_image_store_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				if (click.getString("click_button", "").equals("camera")) {
					camera_progressbar.setVisibility(View.VISIBLE);
					camera_progressbar.setIndeterminate(false);
					camera_progressbar.setProgress((int)_progressValue);
				}
				else {
					image_progressbar.setVisibility(View.VISIBLE);
					image_progressbar.setIndeterminate(false);
					image_progressbar.setProgress((int)_progressValue);
				}
				_customText("Image uploaded : ".concat(String.valueOf(Math.round(_progressValue)).concat(" %")));
			}
		};
		
		_image_store_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_image_store_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				cal = Calendar.getInstance();
				postId = String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000)))).concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000)))))).concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000))))).concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(100000), (int)(1000000)))))));
				map = new HashMap<>();
				map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("senderNickname", sender_nickname);
				map.put("profile_picture", profile_url);
				map.put("key", postId);
				map.put("message", message.getText().toString().trim().concat("  "));
				map.put("image", _downloadUrl);
				map.put("time", new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss a").format(cal.getTime()));
				map.put("gps_latitude", String.valueOf(gps_lat));
				map.put("gps_longitude", String.valueOf(gps_lng));
				map.put("gps_accuracy", String.valueOf(gps_acc));
				map.put("net_latitude", String.valueOf(net_lat));
				map.put("net_longitude", String.valueOf(net_lng));
				map.put("net_accuracy", String.valueOf(net_acc));
				posts.child(postId).updateChildren(map);
				map.clear();
				image_num = 0;
				message.setText("");
				image_url.edit().putString("imageDownloadUrl", _downloadUrl).commit();
				_customText("Image uploaded and sent");
				insert_image.setImageResource(R.drawable.ic_crop_original_grey);
				capture_photo.setImageResource(R.drawable.ic_camera_alt_grey_20200412180718);
				image_progressbar.setVisibility(View.GONE);
				camera_progressbar.setVisibility(View.GONE);
			}
		};
		
		_image_store_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_image_store_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_image_store_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				dialogue.setTitle("Image upload unsuccessful");
				dialogue.setMessage(_message);
				dialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						_customText("Message post unsuccessful ");
					}
				});
			}
		};
		
		_gps_location_location_listener = new LocationListener() {
			@Override
			public void onLocationChanged(Location _param1) {
				final double _lat = _param1.getLatitude();
				final double _lng = _param1.getLongitude();
				final double _acc = _param1.getAccuracy();
				gps_lat = _lat;
				gps_lng = _lng;
				gps_acc = _acc;
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
			@Override
			public void onProviderEnabled(String provider) {}
			@Override
			public void onProviderDisabled(String provider) {}
		};
		
		_net_location_location_listener = new LocationListener() {
			@Override
			public void onLocationChanged(Location _param1) {
				final double _lat = _param1.getLatitude();
				final double _lng = _param1.getLongitude();
				final double _acc = _param1.getAccuracy();
				net_lat = _lat;
				net_lng = _lng;
				net_acc = _acc;
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
			@Override
			public void onProviderEnabled(String provider) {}
			@Override
			public void onProviderDisabled(String provider) {}
		};
		
		_like_child_listener = new ChildEventListener() {
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
		like.addChildEventListener(_like_child_listener);
		
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
		image_num = 0;
		file_num = 0;
		image_progressbar.setVisibility(View.GONE);
		camera_progressbar.setVisibility(View.GONE);
		file_progressbar.setVisibility(View.GONE);
		image_progressbar.setIndeterminate(true);
		camera_progressbar.setIndeterminate(true);
		file_progressbar.setIndeterminate(true);
		android.graphics.drawable.GradientDrawable gd2 = new android.graphics.drawable.GradientDrawable();
		gd2.setColor(Color.parseColor("#ffffff"));
		
		gd2.setStroke(2, Color.parseColor("#616161"));
		
		linear2.setBackground(gd2);
		android.graphics.drawable.GradientDrawable gd3 = new android.graphics.drawable.GradientDrawable();
		gd3.setColor(Color.parseColor("#ffffff"));
		
		gd3.setStroke(2, Color.parseColor("#616161"));
		
		linear6.setBackground(gd3);
		android.graphics.drawable.GradientDrawable gd4 = new android.graphics.drawable.GradientDrawable();
		gd4.setColor(Color.parseColor("#ffffff"));
		
		gd4.setStroke(2, Color.parseColor("#616161"));
		
		linear7.setBackground(gd4);
		android.graphics.drawable.GradientDrawable gd5 = new android.graphics.drawable.GradientDrawable();
		gd5.setColor(Color.parseColor("#ffffff"));
		
		gd5.setStroke(2, Color.parseColor("#616161"));
		
		linear8.setBackground(gd5);
		android.graphics.drawable.GradientDrawable gd6 = new android.graphics.drawable.GradientDrawable();
		gd6.setColor(Color.parseColor("#ffffff"));
		
		gd6.setStroke(2, Color.parseColor("#616161"));
		
		linear4.setBackground(gd6);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_CAM:
			if (_resultCode == Activity.RESULT_OK) {
				 String _filePath = _file_cam.getAbsolutePath();
				
				image_path = _filePath;
				capture_photo.setImageResource(R.drawable.ic_camera_alt_black);
				insert_image.setImageResource(R.drawable.ic_crop_original_grey);
				image_progressbar.setVisibility(View.GONE);
				camera_progressbar.setVisibility(View.VISIBLE);
				image_progressbar.setIndeterminate(false);
				camera_progressbar.setIndeterminate(false);
				image_num = 1;
			}
			else {
				
			}
			break;
			
			case REQ_CD_FPICK:
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
				insert_image.setImageResource(R.drawable.ic_crop_original_black);
				capture_photo.setImageResource(R.drawable.ic_camera_alt_grey_20200412180718);
				image_progressbar.setVisibility(View.VISIBLE);
				camera_progressbar.setVisibility(View.GONE);
				image_num = 1;
			}
			else {
				
			}
			break;
			
			case REQ_CD_FILE:
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
				java.io.File file = new java.io.File(file_path);
				file_size = String.valueOf(file.length()/1024);
				if (Double.parseDouble(file_size) < 25001) {
					file_name = Uri.parse(_filePath.get((int)(0))).getLastPathSegment();
					file_path = _filePath.get((int)(0));
					file_num = 1;
					attach_file.setImageResource(R.drawable.attach_clip_icon);
					file_progressbar.setVisibility(View.VISIBLE);
				}
				else {
					dialogue.setTitle("File too large! ");
					dialogue.setMessage("The selected file is too large to be sent.\nYou can share a file as large as 25 MB only.\nThe size of the file selected was ".concat(String.valueOf(Double.parseDouble(file_size) / 1000).concat(" MB")));
					dialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialogue.create().show();
				}
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
	
	
	private void _Linkify (final TextView _text, final String _color) {
		_text.setClickable(true);
		
		android.text.util.Linkify.addLinks(_text, android.text.util.Linkify.ALL);
		
		_text.setLinkTextColor(Color.parseColor("#" + _color.replace("#", "")));
		
		_text.setLinksClickable(true);
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
