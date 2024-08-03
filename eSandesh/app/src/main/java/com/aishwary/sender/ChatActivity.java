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
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;
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
import android.provider.MediaStore;
import android.os.Build;
import androidx.core.content.FileProvider;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.view.View;
import android.widget.AdapterView;
import java.text.DecimalFormat;
import android.content.ClipboardManager;
import com.bumptech.glide.Glide;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class ChatActivity extends AppCompatActivity {
	
	public final int REQ_CD_FPICK = 101;
	public final int REQ_CD_CAMERA = 102;
	public final int REQ_CD_FILE = 103;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private Toolbar _toolbar;
	private String chatroom = "";
	private String chatcopy = "";
	private String user1 = "";
	private String user2 = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String Sender = "";
	private String Recipient = "";
	private double image = 0;
	private String imageName = "";
	private String imagePath = "";
	private double file_num = 0;
	private String fileName = "";
	private String filePath = "";
	private double latitude = 0;
	private double longitude = 0;
	private double accuracy = 0;
	private String message = "";
	
	private ArrayList<HashMap<String, Object>> chatroom1 = new ArrayList<>();
	private ArrayList<String> list = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear7;
	private ProgressBar progressbar1;
	private ListView listview1;
	private LinearLayout linear6;
	private ImageView imageview2;
	private TextView imgnum;
	private EditText edittext2;
	private ImageView clip;
	private ImageView imageview4;
	
	private Intent intent = new Intent();
	private DatabaseReference chat1 = _firebase.getReference("" + chatroom + "");
	private ChildEventListener _chat1_child_listener;
	private DatabaseReference chat2 = _firebase.getReference("" + chatcopy + "");
	private ChildEventListener _chat2_child_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private FirebaseAuth fauth;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private AlertDialog.Builder dialog;
	private Intent fpick = new Intent(Intent.ACTION_GET_CONTENT);
	private StorageReference fstore = _firebase_storage.getReference("Sender Files");
	private OnCompleteListener<Uri> _fstore_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fstore_download_success_listener;
	private OnSuccessListener _fstore_delete_success_listener;
	private OnProgressListener _fstore_upload_progress_listener;
	private OnProgressListener _fstore_download_progress_listener;
	private OnFailureListener _fstore_failure_listener;
	private SharedPreferences imageurl;
	private Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_camera;
	private SharedPreferences camera_image_path;
	private Intent file = new Intent(Intent.ACTION_GET_CONTENT);
	private StorageReference file_storage = _firebase_storage.getReference("Attachment");
	private OnCompleteListener<Uri> _file_storage_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _file_storage_download_success_listener;
	private OnSuccessListener _file_storage_delete_success_listener;
	private OnProgressListener _file_storage_upload_progress_listener;
	private OnProgressListener _file_storage_download_progress_listener;
	private OnFailureListener _file_storage_failure_listener;
	private SharedPreferences fileUrl;
	private Calendar calender = Calendar.getInstance();
	private LocationManager location;
	private LocationListener _location_location_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.chat);
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
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		listview1 = (ListView) findViewById(R.id.listview1);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		imgnum = (TextView) findViewById(R.id.imgnum);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		clip = (ImageView) findViewById(R.id.clip);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		fauth = FirebaseAuth.getInstance();
		dialog = new AlertDialog.Builder(this);
		fpick.setType("image/*");
		fpick.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		imageurl = getSharedPreferences("imageurl", Activity.MODE_PRIVATE);
		_file_camera = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_camera = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_camera= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_camera);
		}
		else {
			_uri_camera = Uri.fromFile(_file_camera);
		}
		camera.putExtra(MediaStore.EXTRA_OUTPUT, _uri_camera);
		camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		camera_image_path = getSharedPreferences("camera", Activity.MODE_PRIVATE);
		file.setType("*/*");
		file.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		fileUrl = getSharedPreferences("fileUrl", Activity.MODE_PRIVATE);
		location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (chatroom1.get((int)_position).get("sender").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					Sender = user1;
					Recipient = user2;
				}
				else {
					Recipient = user1;
					Sender = user2;
				}
				if (chatroom1.get((int)_position).containsKey("image")) {
					dialog.setTitle("Message Details");
					dialog.setMessage("Message : ".concat(chatroom1.get((int)_position).get("message").toString()).concat("\n").concat("Sent by : ".concat(Sender).concat("\n").concat("Sent to : ".concat(Recipient))));
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.setNegativeButton("View Image", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							intent.setAction(Intent.ACTION_VIEW);
							intent.putExtra("imageDownloadUrl", chatroom1.get((int)_position).get("image").toString());
							intent.setClass(getApplicationContext(), ImageViewerActivity.class);
							startActivity(intent);
						}
					});
					dialog.create().show();
				}
				else {
					dialog.setTitle("Message Details");
					dialog.setMessage("Message : ".concat(chatroom1.get((int)_position).get("message").toString()).concat("\n").concat("Sent by : ".concat(Sender).concat("\n").concat("Sent to : ".concat(Recipient))));
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.setNeutralButton("Copy message", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", chatroom1.get((int)_position).get("message").toString()));
							SketchwareUtil.showMessage(getApplicationContext(), "URL copied to clipboard ");
						}
					});
					dialog.create().show();
				}
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (chatroom1.get((int)_position).containsKey("file")) {
					dialog.setTitle("Message contains an attachment");
					dialog.setMessage("Message contains a file named : ".concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("file").toString()).getLastPathSegment()).getLastPathSegment()));
					dialog.setPositiveButton("Download file", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							_firebase_storage.getReferenceFromUrl(chatroom1.get((int)_position).get("file").toString()).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/sender_By-Aishwary_Raj-file- ".concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("file").toString()).getLastPathSegment()).getLastPathSegment())))).addOnSuccessListener(_file_storage_download_success_listener).addOnFailureListener(_file_storage_failure_listener).addOnProgressListener(_file_storage_download_progress_listener);
							SketchwareUtil.showMessage(getApplicationContext(), "Attachment will be downloaded and saved at : ".concat(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/sender_By-Aishwary_Raj-file-".concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("file").toString()).getLastPathSegment()).getLastPathSegment()))));
						}
					});
					dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.setNeutralButton("Copy file download URL", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", chatroom1.get((int)_position).get("file").toString()));
							SketchwareUtil.showMessage(getApplicationContext(), "URL copied to clipboard ");
						}
					});
					dialog.create().show();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "No attachment found");
				}
				if (chatroom1.get((int)_position).containsKey("image")) {
					dialog.setTitle("Message contains an image");
					dialog.setMessage("Message contains an image file named : ".concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("image").toString()).getLastPathSegment()).getLastPathSegment()));
					dialog.setPositiveButton("Download file", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							_firebase_storage.getReferenceFromUrl(chatroom1.get((int)_position).get("image").toString()).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/sender_By-Aishwary_Raj-image-".concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("image").toString()).getLastPathSegment()).getLastPathSegment())))).addOnSuccessListener(_fstore_download_success_listener).addOnFailureListener(_fstore_failure_listener).addOnProgressListener(_fstore_download_progress_listener);
							SketchwareUtil.showMessage(getApplicationContext(), "File will be downloaded and saved at ".concat(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/sender_By-Aishwary_Raj-image-".concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("image").toString()).getLastPathSegment()).getLastPathSegment()))));
						}
					});
					dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.setNeutralButton("Copy image download URL", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", chatroom1.get((int)_position).get("image").toString()));
							SketchwareUtil.showMessage(getApplicationContext(), "URL copied to clipboard ");
						}
					});
					dialog.create().show();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "No attachment found");
				}
				return true;
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((image == 0) && (file_num == 0)) {
					calender = Calendar.getInstance();
					map = new HashMap<>();
					map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
					map.put("message", edittext2.getText().toString());
					map.put("time", new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss a").format(calender.getTime()));
					map.put("latitude", String.valueOf(latitude));
					map.put("longitude", String.valueOf(longitude));
					map.put("accuracy", String.valueOf(accuracy));
					chat1.push().updateChildren(map);
					chat2.push().updateChildren(map);
					map.clear();
					edittext2.setText("");
				}
				else {
					if (image == 1) {
						fstore.child(imageName).putFile(Uri.fromFile(new File(imagePath))).addOnFailureListener(_fstore_failure_listener).addOnProgressListener(_fstore_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
							@Override
							public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
								return fstore.child(imageName).getDownloadUrl();
							}}).addOnCompleteListener(_fstore_upload_success_listener);
					}
					if (file_num == 1) {
						file_storage.child(fileName).putFile(Uri.fromFile(new File(filePath))).addOnFailureListener(_file_storage_failure_listener).addOnProgressListener(_file_storage_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
							@Override
							public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
								return file_storage.child(fileName).getDownloadUrl();
							}}).addOnCompleteListener(_file_storage_upload_success_listener);
					}
				}
				android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
				SketchwareUtil.showMessage(getApplicationContext(), "Sending Message... ");
			}
		});
		
		imgnum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (image == 0) {
					startActivityForResult(fpick, REQ_CD_FPICK);
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Only one image allowed at a time");
				}
			}
		});
		
		edittext2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext2.getText().toString().equals("")) {
					
				}
				else {
					imageview4.setVisibility(View.GONE);
				}
			}
		});
		
		clip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(file, REQ_CD_FILE);
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (image == 0) {
					startActivityForResult(camera, REQ_CD_CAMERA);
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "only one image allowed at a time");
				}
			}
		});
		
		_chat1_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				chat1.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						chatroom1 = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								chatroom1.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(chatroom1));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				chat1.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						chatroom1 = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								chatroom1.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(chatroom1));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
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
		chat1.addChildEventListener(_chat1_child_listener);
		
		_chat2_child_listener = new ChildEventListener() {
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
		chat2.addChildEventListener(_chat2_child_listener);
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					user1 = _childValue.get("nickname").toString();
				}
				if (_childKey.equals(getIntent().getStringExtra("seconduser"))) {
					user2 = _childValue.get("nickname").toString();
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
		
		_fstore_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				progressbar1.setVisibility(View.VISIBLE);
				progressbar1.setProgress((int)_progressValue);
				SketchwareUtil.showMessage(getApplicationContext(), "Uploaded : ".concat(String.valueOf(_progressValue).concat("%")));
			}
		};
		
		_fstore_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				progressbar1.setVisibility(View.VISIBLE);
				progressbar1.setProgress((int)_progressValue);
				SketchwareUtil.showMessage(getApplicationContext(), "Downloaded : ".concat(String.valueOf(_progressValue).concat("%")));
			}
		};
		
		_fstore_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				calender = Calendar.getInstance();
				map = new HashMap<>();
				map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("message", edittext2.getText().toString().concat("  "));
				map.put("image", _downloadUrl);
				map.put("time", new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss a").format(calender.getTime()));
				map.put("latitude", String.valueOf(latitude));
				map.put("longitude", String.valueOf(longitude));
				map.put("accuracy", String.valueOf(accuracy));
				chat1.push().updateChildren(map);
				chat2.push().updateChildren(map);
				map.clear();
				image = 0;
				imgnum.setBackgroundResource(R.drawable.ic_crop_original_grey);
				edittext2.setText("");
				imageurl.edit().putString("imageDownloadUrl", _downloadUrl).commit();
				SketchwareUtil.showMessage(getApplicationContext(), "Image uploaded and sent");
				progressbar1.setVisibility(View.GONE);
			}
		};
		
		_fstore_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				SketchwareUtil.showMessage(getApplicationContext(), "File download successful\nFile Size : ".concat(String.valueOf(_totalByteCount)));
				progressbar1.setVisibility(View.GONE);
			}
		};
		
		_fstore_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_fstore_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_file_storage_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				progressbar1.setVisibility(View.VISIBLE);
				progressbar1.setProgress((int)_progressValue);
				SketchwareUtil.showMessage(getApplicationContext(), "Uploaded : ".concat(String.valueOf(_progressValue).concat("%")));
			}
		};
		
		_file_storage_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				progressbar1.setVisibility(View.VISIBLE);
				progressbar1.setProgress((int)_progressValue);
				SketchwareUtil.showMessage(getApplicationContext(), "Downloaded : ".concat(String.valueOf(_progressValue).concat("%")));
			}
		};
		
		_file_storage_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				calender = Calendar.getInstance();
				map = new HashMap<>();
				map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("message", edittext2.getText().toString().concat("  "));
				map.put("file", _downloadUrl);
				map.put("time", new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss a").format(calender.getTime()));
				map.put("latitude", String.valueOf(latitude));
				map.put("longitude", String.valueOf(longitude));
				map.put("accuracy", String.valueOf(accuracy));
				chat1.push().updateChildren(map);
				chat2.push().updateChildren(map);
				map.clear();
				image = 0;
				imgnum.setBackgroundResource(R.drawable.ic_crop_original_grey);
				edittext2.setText("");
				fileUrl.edit().putString("fileDownloadUrl", _downloadUrl).commit();
				SketchwareUtil.showMessage(getApplicationContext(), "Attachment uploaded and sent");
				progressbar1.setVisibility(View.GONE);
			}
		};
		
		_file_storage_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				SketchwareUtil.showMessage(getApplicationContext(), "File download successful\nDownloaded file size : ".concat(String.valueOf(_totalByteCount)));
				progressbar1.setVisibility(View.GONE);
			}
		};
		
		_file_storage_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_file_storage_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_location_location_listener = new LocationListener() {
			@Override
			public void onLocationChanged(Location _param1) {
				final double _lat = _param1.getLatitude();
				final double _lng = _param1.getLongitude();
				final double _acc = _param1.getAccuracy();
				latitude = _lat;
				longitude = _lng;
				accuracy = _acc;
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
			@Override
			public void onProviderEnabled(String provider) {}
			@Override
			public void onProviderDisabled(String provider) {}
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
		chat1.removeEventListener(_chat1_child_listener);
		chat2.removeEventListener(_chat2_child_listener);
		chatroom = "chat/".concat(getIntent().getStringExtra("firstuser")).concat("/".concat(getIntent().getStringExtra("seconduser")));
		chatcopy = "chat/".concat(getIntent().getStringExtra("seconduser")).concat("/".concat(getIntent().getStringExtra("firstuser")));
		chat1 = _firebase.getReference(chatroom);
		chat2 = _firebase.getReference (chatcopy) ;
		chat1.addChildEventListener(_chat1_child_listener);
		chat2.addChildEventListener(_chat2_child_listener);
		listview1.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		listview1.setStackFromBottom(true);
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor("#000000"));
		gd.setCornerRadius(60);
		linear1.setBackground(gd);
		image = 0;
		imgnum.setBackgroundResource(R.drawable.ic_crop_original_grey);
		imageview4.setImageResource(R.drawable.ic_camera_alt_grey_20200412180718);
		progressbar1.setVisibility(View.GONE);
		if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 100, _location_location_listener);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add("Refresh").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		
		return true;
	}
	@Override 
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getTitle().toString()) {
			case "Refresh":_refresh_clicked() ;
			showMessage("refresh clicked");
			return true;
			
			
			default:
			return super.onOptionsItemSelected(item);
		} 
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
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
				image = 1;
				imgnum.setBackgroundResource(R.drawable.ic_crop_original_black);
				imagePath = _filePath.get((int)(0));
				imageName = Uri.parse(_filePath.get((int)(0))).getLastPathSegment();
			}
			else {
				
			}
			break;
			
			case REQ_CD_CAMERA:
			if (_resultCode == Activity.RESULT_OK) {
				 String _filePath = _file_camera.getAbsolutePath();
				
				camera_image_path.edit().putString("camera_image_path", _filePath).commit();
				image = 1;
				imgnum.setBackgroundResource(R.drawable.ic_crop_original_black);
				imageview4.setImageResource(R.drawable.ic_camera_alt_black);
				imagePath = _filePath;
				imageName = Uri.parse(_filePath).getLastPathSegment();
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
				fileName = Uri.parse(_filePath.get((int)(0))).getLastPathSegment();
				filePath = _filePath.get((int)(0));
				file_num = 1;
				clip.setImageResource(R.drawable.attach_clip_icon);
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		intent.setClass(getApplicationContext(), AllUsersActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		location.removeUpdates(_location_location_listener);
	}
	private void _refresh_clicked () {
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		listview1.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		listview1.setStackFromBottom(true);
	}
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.quotes, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final LinearLayout linear2 = (LinearLayout) _v.findViewById(R.id.linear2);
			final TextView textview1 = (TextView) _v.findViewById(R.id.textview1);
			final TextView time = (TextView) _v.findViewById(R.id.time);
			final TextView textview2 = (TextView) _v.findViewById(R.id.textview2);
			final ImageView img = (ImageView) _v.findViewById(R.id.img);
			final ImageView fileviewer = (ImageView) _v.findViewById(R.id.fileviewer);
			
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			
			gd.setCornerRadius(60);
			linear1.setBackground(gd);
			if (chatroom1.get((int)_position).containsKey("message")) {
				textview1.setText(chatroom1.get((int)_position).get("message").toString());
			}
			if (chatroom1.get((int)_position).containsKey("sender")) {
				
			}
			if (chatroom1.get((int)_position).get("sender").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
				linear1.setGravity(Gravity.RIGHT);
				textview2.setText("By - ".concat(user1));
			}
			else {
				linear1.setGravity(Gravity.LEFT);
				textview2.setText("By - ".concat(user2));
			}
			if (chatroom1.get((int)_position).containsKey("image")) {
				img.setVisibility(View.VISIBLE);
				Glide.with(getApplicationContext()).load(Uri.parse(chatroom1.get((int)_position).get("image").toString())).into(img);
			}
			else {
				img.setVisibility(View.GONE);
			}
			if (chatroom1.get((int)_position).containsKey("file")) {
				fileviewer.setVisibility(View.VISIBLE);
			}
			else {
				fileviewer.setVisibility(View.GONE);
			}
			if (chatroom1.get((int)_position).containsKey("time")) {
				time.setVisibility(View.VISIBLE);
				time.setText(chatroom1.get((int)_position).get("time").toString());
			}
			else {
				time.setVisibility(View.GONE);
			}
			
			return _v;
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
