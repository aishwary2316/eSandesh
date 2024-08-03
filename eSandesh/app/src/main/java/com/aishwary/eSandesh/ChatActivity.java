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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
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
import com.google.firebase.auth.FirebaseUser;
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
import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.text.Editable;
import android.text.TextWatcher;
import java.text.DecimalFormat;
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
	private String share = "";
	private double net_latitude = 0;
	private double net_longitude = 0;
	private double net_accuracy = 0;
	private String file_path = "";
	private String file_size = "";
	private String chatNotifications = "";
	private double width = 0;
	private String notification_key = "";
	private String sender_key = "";
	private String recipient_key = "";
	private double code = 0;
	
	private ArrayList<HashMap<String, Object>> chatroom1 = new ArrayList<>();
	private ArrayList<String> list = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear7;
	private ListView listview1;
	private ProgressBar progressbar1;
	private LinearLayout linear8;
	private ProgressBar progressbar2;
	private LinearLayout linear6;
	private ImageView imageview2;
	private ImageView imageview5;
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
	private OnCompleteListener<Void> fauth_updateEmailListener;
	private OnCompleteListener<Void> fauth_updatePasswordListener;
	private OnCompleteListener<Void> fauth_emailVerificationSentListener;
	private OnCompleteListener<Void> fauth_deleteUserListener;
	private OnCompleteListener<Void> fauth_updateProfileListener;
	private OnCompleteListener<AuthResult> fauth_phoneAuthListener;
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
	private MediaPlayer media_player;
	private LocationManager location_net;
	private LocationListener _location_net_location_listener;
	private DatabaseReference notify = _firebase.getReference("" + chatNotifications + "");
	private ChildEventListener _notify_child_listener;
	private SharedPreferences background;
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
		listview1 = (ListView) findViewById(R.id.listview1);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		progressbar2 = (ProgressBar) findViewById(R.id.progressbar2);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
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
		location_net = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		background = getSharedPreferences("background", Activity.MODE_PRIVATE);
		
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
					dialog.setNeutralButton("Share message", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							share = chatroom1.get((int)_position).get("message").toString();
							Intent intentshare = new Intent(Intent.ACTION_SEND);intentshare.setType("text/plain"); intentshare.putExtra(Intent.EXTRA_TEXT,share); startActivity(Intent.createChooser(intentshare, "Share message via"));
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
					dialog.setNeutralButton("Share message", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							share = chatroom1.get((int)_position).get("message").toString();
							Intent intentshare = new Intent(Intent.ACTION_SEND);intentshare.setType("text/plain"); intentshare.putExtra(Intent.EXTRA_TEXT,share); startActivity(Intent.createChooser(intentshare, "Share message via"));
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
				dialog.setTitle("Delete Message ?");
				dialog.setMessage("Choose message delete mode");
				if (chatroom1.get((int)_position).get("sender").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					dialog.setPositiveButton("DELETE FOR EVERYONE", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							chat1.child(chatroom1.get((int)_position).get("sender_key").toString()).removeValue();
							chat2.child(chatroom1.get((int)_position).get("recipient_key").toString()).removeValue();
							notify.child(chatroom1.get((int)_position).get("notification_key").toString()).removeValue();
						}
					});
				}
				dialog.setNeutralButton("DELETE FOR ME", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (chatroom1.get((int)_position).get("sender").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
							chat1.child(chatroom1.get((int)_position).get("sender_key").toString()).removeValue();
						}
						else {
							chat2.child(chatroom1.get((int)_position).get("recipient_key").toString()).removeValue();
							notify.child(chatroom1.get((int)_position).get("notification_key").toString()).removeValue();
						}
					}
				});
				dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dialog.create().show();
				return true;
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext2.getText().toString().equals("")) {
					if ((image == 0) && (file_num == 0)) {
						
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
				}
				else {
					if ((image == 0) && (file_num == 0)) {
						calender = Calendar.getInstance();
						notification_key = chat1.push().getKey();
						sender_key = chat2.push().getKey();
						recipient_key = notify.push().getKey();
						map = new HashMap<>();
						map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
						map.put("senderNickname", user1);
						map.put("notification_key", notification_key);
						map.put("sender_key", sender_key);
						map.put("recipient_key", recipient_key);
						map.put("message", edittext2.getText().toString());
						map.put("time", new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss a").format(calender.getTime()));
						map.put("gps_latitude", String.valueOf(latitude));
						map.put("gps_longitude", String.valueOf(longitude));
						map.put("gps_accuracy", String.valueOf(accuracy));
						map.put("net_latitude", String.valueOf(net_latitude));
						map.put("net_longitude", String.valueOf(net_longitude));
						map.put("net_accuracy", String.valueOf(net_accuracy));
						chat1.child(sender_key).updateChildren(map);
						chat2.child(recipient_key).updateChildren(map);
						notify.child(notification_key).updateChildren(map);
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
							_customText("Sending Message... ");
						}
						if (file_num == 1) {
							file_storage.child(fileName).putFile(Uri.fromFile(new File(filePath))).addOnFailureListener(_file_storage_failure_listener).addOnProgressListener(_file_storage_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
								@Override
								public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
									return file_storage.child(fileName).getDownloadUrl();
								}}).addOnCompleteListener(_file_storage_upload_success_listener);
							_customText("Sending Message... ");
						}
					}
					android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
					_customText("Sending Message... ");
					imageview4.setVisibility(View.VISIBLE);
				}
			}
		});
		
		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), EmbedCodeActivity.class);
				intent.putExtra("codeStatus", "NULL");
				intent.putExtra("code", "</>");
				intent.putExtra("firstuser", getIntent().getStringExtra("firstuser"));
				intent.putExtra("seconduser", getIntent().getStringExtra("seconduser"));
				startActivity(intent);
			}
		});
		
		imgnum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (image == 0) {
					startActivityForResult(fpick, REQ_CD_FPICK);
				}
				else {
					_customText("Only one image allowed at a time");
				}
			}
		});
		
		edittext2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext2.getText().toString().equals("")) {
					
				}
				else {
					
				}
			}
		});
		
		edittext2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.equals("")) {
					imageview4.setVisibility(View.VISIBLE);
				}
				else {
					imageview4.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
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
					_customText("Only one image allowed at a time");
				}
			}
		});
		
		_chat1_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				linear8.setVisibility(View.VISIBLE);
				progressbar2.setVisibility(View.VISIBLE);
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
						linear8.setVisibility(View.GONE);
						progressbar2.setVisibility(View.GONE);
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
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				_customText(_errorMessage);
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
					setTitle(_childValue.get("nickname").toString());
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
				_customText("Uploaded : ".concat(String.valueOf(_progressValue).concat("%")));
			}
		};
		
		_fstore_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				progressbar1.setVisibility(View.VISIBLE);
				progressbar1.setProgress((int)_progressValue);
				_customText("Downloaded : ".concat(String.valueOf(_progressValue).concat("%")));
			}
		};
		
		_fstore_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				calender = Calendar.getInstance();
				notification_key = chat1.push().getKey();
				sender_key = chat2.push().getKey();
				recipient_key = notify.push().getKey();
				map = new HashMap<>();
				map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("notification_key", notification_key);
				map.put("sender_key", sender_key);
				map.put("recipient_key", recipient_key);
				map.put("message", edittext2.getText().toString().concat("  "));
				map.put("image", _downloadUrl);
				map.put("time", new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss a").format(calender.getTime()));
				map.put("gps_latitude", String.valueOf(latitude));
				map.put("gps_longitude", String.valueOf(longitude));
				map.put("gps_accuracy", String.valueOf(accuracy));
				map.put("net_latitude", String.valueOf(net_latitude));
				map.put("net_longitude", String.valueOf(net_longitude));
				map.put("net_accuracy", String.valueOf(net_accuracy));
				chat1.child(sender_key).updateChildren(map);
				chat2.child(recipient_key).updateChildren(map);
				notify.child(notification_key).updateChildren(map);
				map.clear();
				image = 0;
				imgnum.setBackgroundResource(R.drawable.ic_crop_original_grey);
				edittext2.setText("");
				imageurl.edit().putString("imageDownloadUrl", _downloadUrl).commit();
				_customText("Image uploaded and sent");
				progressbar1.setVisibility(View.GONE);
			}
		};
		
		_fstore_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				_customText("File download successful\nFile Size : ".concat(String.valueOf(_totalByteCount).concat(" Bytes")));
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
				_customText(_message);
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
				_customText("Downloaded : ".concat(String.valueOf(_progressValue).concat("%")));
			}
		};
		
		_file_storage_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				calender = Calendar.getInstance();
				notification_key = chat1.push().getKey();
				sender_key = chat2.push().getKey();
				recipient_key = notify.push().getKey();
				map = new HashMap<>();
				map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("notification_key", notification_key);
				map.put("sender_key", sender_key);
				map.put("recipient_key", recipient_key);
				map.put("message", edittext2.getText().toString().concat("  "));
				map.put("file", _downloadUrl);
				map.put("time", new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss a").format(calender.getTime()));
				map.put("gps_latitude", String.valueOf(latitude));
				map.put("gps_longitude", String.valueOf(longitude));
				map.put("gps_accuracy", String.valueOf(accuracy));
				map.put("net_latitude", String.valueOf(net_latitude));
				map.put("net_longitude", String.valueOf(net_longitude));
				map.put("net_accuracy", String.valueOf(net_accuracy));
				chat1.child(sender_key).updateChildren(map);
				chat2.child(recipient_key).updateChildren(map);
				notify.child(notification_key).updateChildren(map);
				map.clear();
				image = 0;
				imgnum.setBackgroundResource(R.drawable.ic_crop_original_grey);
				edittext2.setText("");
				fileUrl.edit().putString("fileDownloadUrl", _downloadUrl).commit();
				_customText("Attachment uploaded and sent");
				progressbar1.setVisibility(View.GONE);
			}
		};
		
		_file_storage_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				_customText("File download successful\nDownloaded file size : ".concat(String.valueOf(_totalByteCount).concat(" Bytes")));
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
				_customText(_message);
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
		
		_location_net_location_listener = new LocationListener() {
			@Override
			public void onLocationChanged(Location _param1) {
				final double _lat = _param1.getLatitude();
				final double _lng = _param1.getLongitude();
				final double _acc = _param1.getAccuracy();
				net_latitude = _lat;
				net_longitude = _lng;
				net_accuracy = _acc;
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
			@Override
			public void onProviderEnabled(String provider) {}
			@Override
			public void onProviderDisabled(String provider) {}
		};
		
		_notify_child_listener = new ChildEventListener() {
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
		notify.addChildEventListener(_notify_child_listener);
		
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
		notify.removeEventListener(_notify_child_listener);
		chatroom = "chat/".concat(getIntent().getStringExtra("firstuser")).concat("/".concat(getIntent().getStringExtra("seconduser")));
		chatcopy = "chat/".concat(getIntent().getStringExtra("seconduser")).concat("/".concat(getIntent().getStringExtra("firstuser")));
		chatNotifications = "users/".concat(getIntent().getStringExtra("seconduser")).concat("/".concat("notifications"));
		chat1 = _firebase.getReference(chatroom);
		chat2 = _firebase.getReference (chatcopy) ;
		notify = _firebase.getReference(chatNotifications);
		chat1.addChildEventListener(_chat1_child_listener);
		chat2.addChildEventListener(_chat2_child_listener);
		notify.addChildEventListener(_notify_child_listener);
		listview1.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		listview1.setStackFromBottom(true);
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor("#ffffff"));
		gd.setCornerRadius(60);
		linear6.setBackground(gd);
		listview1.setDivider(null);
		listview1.setDividerHeight(0);
		
		image = 0;
		imgnum.setBackgroundResource(R.drawable.ic_crop_original_grey);
		imageview4.setImageResource(R.drawable.ic_camera_alt_grey_20200412180718);
		progressbar1.setVisibility(View.GONE);
		progressbar2.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this).colors(new int[]{0xFFC93437, 0xFF375BF1, 0xFFF7D23E, 0xFF34A350}).build());
		linear8.setVisibility(View.GONE);
		if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 100, _location_location_listener);
		}
		if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			location_net.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 100, _location_net_location_listener);
		}
		if (background.getString("mode", "").equals("")) {
			background.edit().putString("mode", "Default Background").commit();
			background.edit().putString("selection", "0").commit();
			linear1.setBackgroundResource(R.drawable.background_20200412180718);
		}
		else {
			if (background.getString("mode", "").equals("Default Background")) {
				linear1.setBackgroundResource(R.drawable.background_20200412180718);
			}
			if (background.getString("mode", "").equals("Built in Background")) {
				code = Double.parseDouble(background.getString("selection", ""));
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
			}
			if (background.getString("mode", "").equals("Custom Background")) {
				if (FileUtil.isExistFile(background.getString("selection", ""))) {
					_setBackgroundOf(linear1, background.getString("selection", ""));
				}
				else {
					background.edit().putString("mode", "Default Background").commit();
					background.edit().putString("selection", "0").commit();
					linear1.setBackgroundResource(R.drawable.background_20200412180718);
					_customText("Image file :".concat(Uri.parse(background.getString("selection", "")).getLastPathSegment()).concat("\nNot found at path :".concat(background.getString("selection", "").concat("\nThe image may have been moved or deleted.\nBackground set to default"))));
					dialog.setTitle("Invalid background image");
					dialog.setMessage("Image file :".concat(Uri.parse(background.getString("selection", "")).getLastPathSegment()).concat("\nNot found at path :".concat(background.getString("selection", "").concat("\nThe image may have been moved or deleted.\nBackground set to default"))));
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
				}
			}
			else {
				
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add("Refresh").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		menu.add("Change Background").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);menu.add("User Account").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		
		return true;
	}
	@Override 
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getTitle().toString()) {
			case "Refresh":_refresh_clicked() ;
			showMessage("refresh clicked");
			return true;
			case "Change Background":_change_background() ;
			showMessage("Change Background Clicked");
			return true;
			case "User Account":_user_account() ;
			showMessage("User Account Clicked");
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
				file_path = _filePath.get((int)(0));
				java.io.File file = new java.io.File(file_path);
				file_size = String.valueOf(file.length()/1024);
				if (Double.parseDouble(file_size) < 25001) {
					fileName = Uri.parse(_filePath.get((int)(0))).getLastPathSegment();
					filePath = _filePath.get((int)(0));
					file_num = 1;
					clip.setImageResource(R.drawable.attach_clip_icon);
				}
				else {
					dialog.setTitle("File too large! ");
					dialog.setMessage("The selected file is too large to be sent.\nYou can share a file as large as 25 MB only.\nThe size of the file selected was ".concat(String.valueOf(Double.parseDouble(file_size) / 1000).concat(" MB")));
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
				}
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
		listview1.smoothScrollToPosition((int)(chatroom1.size()));
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
	
	
	private void _Library () {
	}
	public static class GoogleProgressBar extends ProgressBar {
			public static int _type = 0;
			public static int[] _color = new int[]{0xFFC93437, 0xFF375BF1, 0xFFF7D23E, 0xFF34A350}; //Red, blue, yellow, green
		    private enum ProgressType {
			        FOLDING_CIRCLES,
			        GOOGLE_MUSIC_DICES,
			        NEXUS_ROTATION_CROSS,
			        CHROME_FLOATING_CIRCLES
			    }
		    public GoogleProgressBar(Context context) {
			        this(context, null);
			    }
		    public GoogleProgressBar(Context context, AttributeSet attrs) {
			        this(context, attrs,android.R.attr.progressBarStyle);
			    }
		    public GoogleProgressBar(Context context, AttributeSet attrs, int defStyle) {
			        super(context, attrs, defStyle);
			        if (isInEditMode())
			            return;
			        final int typeIndex = _type;
					final int[] colorsId = _color;
			        android.graphics.drawable.Drawable drawable = buildDrawable(context,typeIndex,colorsId);
			        if(drawable!=null)
			        setIndeterminateDrawable(drawable);
			    }
		    private android.graphics.drawable.Drawable buildDrawable(Context context, int typeIndex,int[] colorsId) {
			        android.graphics.drawable.Drawable drawable = null;
			        ProgressType type = ProgressType.values()[typeIndex];
			        switch (type){
				            case FOLDING_CIRCLES:
				                drawable = new FoldingCirclesDrawable.Builder(context).colors(colorsId).build();
				                break;
				            case GOOGLE_MUSIC_DICES:
				                drawable = new GoogleMusicDicesDrawable.Builder().build();
				                break;
				            case NEXUS_ROTATION_CROSS:
				                drawable = new NexusRotationCrossDrawable.Builder(context).colors(colorsId).build();
				                break;
				            case CHROME_FLOATING_CIRCLES:
				                drawable = new ChromeFloatingCirclesDrawable.Builder(context).colors(colorsId).build();
				                break;
				        }
			        return drawable;
			    }
		    public static void setType(int types) {
			    	_type = types;
			    }
		    public static void setColor(int[] colors) {
			    	_color = colors;
			    }
	}
	
	
	public static class NexusRotationCrossDrawable extends android.graphics.drawable.Drawable implements android.graphics.drawable.Drawable.Callback {
		    private static int ANIMATION_DURATION = 150;
		    private static int ANIMATION_START_DELAY = 300;
		    private static final android.view.animation.Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
		    private int mCenter;
		    private Point[] mArrowPoints;
		    private Path mPath;
		    private Paint mPaint1;
		    private Paint mPaint2;
		    private Paint mPaint3;
		    private Paint mPaint4;
		    private int mRotationAngle;
		    public NexusRotationCrossDrawable(int[] colors) {
			        mArrowPoints = new Point[5];
			        mPath = new Path();
			        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaint1.setColor(colors[0]);
			        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaint2.setColor(colors[1]);
			        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaint3.setColor(colors[2]);
			        mPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaint4.setColor(colors[3]);
			        initObjectAnimator();
			    }
		    private void initObjectAnimator() {
			        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "rotationAngle", 0, 180);
			        objectAnimator.setInterpolator(LINEAR_INTERPOLATOR);
			        objectAnimator.setDuration(ANIMATION_DURATION);
			        objectAnimator.addListener(new AnimatorListenerAdapter() {
				            @Override
				            public void onAnimationEnd(Animator animation) {
					                if (mRotationAngle == 180) {
						                    objectAnimator.setIntValues(180, 360);
						                    objectAnimator.setStartDelay(ANIMATION_START_DELAY * 2);
						                } else {
						                    objectAnimator.setIntValues(0, 180);
						                    objectAnimator.setStartDelay(ANIMATION_START_DELAY);
						                    mRotationAngle = 0;
						                }
					                objectAnimator.start();
					            }
				        });
			        objectAnimator.start();
			    }
		    @Override
		    public void draw(Canvas canvas) {
			        drawArrows(canvas);
			    }
		    private void drawArrows(Canvas canvas) {
			        canvas.rotate(mRotationAngle, mCenter, mCenter);
			        mPath.reset();
			        mPath.moveTo(mArrowPoints[0].x, mArrowPoints[0].y);
			        for (int i = 1; i < mArrowPoints.length; i++) {
				            mPath.lineTo(mArrowPoints[i].x, mArrowPoints[i].y);
				        }
			        mPath.lineTo(mArrowPoints[0].x, mArrowPoints[0].y);
			        canvas.save();
			        canvas.drawPath(mPath, mPaint1);
			        canvas.restore();
			        canvas.save();
			        canvas.rotate(90, mCenter, mCenter);
			        canvas.drawPath(mPath, mPaint2);
			        canvas.restore();
			        canvas.save();
			        canvas.rotate(180, mCenter, mCenter);
			        canvas.drawPath(mPath, mPaint3);
			        canvas.restore();
			        canvas.save();
			        canvas.rotate(270, mCenter, mCenter);
			        canvas.drawPath(mPath, mPaint4);
			        canvas.restore();
			    }
		    @Override
		    public void invalidateDrawable(android.graphics.drawable.Drawable who) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.invalidateDrawable(this);
				        }
			    }
		    @Override
		    public void scheduleDrawable(android.graphics.drawable.Drawable who, Runnable what, long when) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.scheduleDrawable(this, what, when);
				        }
			    }
		    @Override
		    public void unscheduleDrawable(android.graphics.drawable.Drawable who, Runnable what) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.unscheduleDrawable(this, what);
				        }
			    }
		    @Override
		    public void setAlpha(int alpha) {
			        mPaint1.setAlpha(alpha);
			        mPaint2.setAlpha(alpha);
			        mPaint3.setAlpha(alpha);
			        mPaint4.setAlpha(alpha);
			    }
		    @Override
		    public void setColorFilter(ColorFilter cf) {
			        mPaint1.setColorFilter(cf);
			        mPaint2.setColorFilter(cf);
			        mPaint3.setColorFilter(cf);
			        mPaint4.setColorFilter(cf);
			    }
		    @Override
		    protected void onBoundsChange(Rect bounds) {
			        super.onBoundsChange(bounds);
			        measureDrawable(bounds);
			    }
		    private void measureDrawable(Rect bounds) {
			        mCenter = bounds.centerX();
			        int arrowMargin = bounds.width() / 50;
			        int arrowWidth = bounds.width() / 15;
			        int padding = mCenter - (int) (mCenter /  Math.sqrt(2));
			        mArrowPoints[0] = new Point(mCenter - arrowMargin, mCenter - arrowMargin);
			        mArrowPoints[1] = new Point(mArrowPoints[0].x, mArrowPoints[0].y - arrowWidth);
			        mArrowPoints[2] = new Point(padding + arrowWidth, padding);
			        mArrowPoints[3] = new Point(padding, padding + arrowWidth);
			        mArrowPoints[4] = new Point(mArrowPoints[0].x - arrowWidth, mArrowPoints[0].y);
			    }
		    @Override
		    public int getOpacity() {
			        return PixelFormat.TRANSLUCENT;
			    }
		    void setRotationAngle(int angle) {
			        mRotationAngle = angle;
			    }
		    int getRotationAngle() {
			        return mRotationAngle;
			    }
		    public static class Builder {
			        private int[] mColors;
			        public Builder(Context context) {
				            initDefaults(context);
				        }
			        
			        private void initDefaults(Context context) {
				            mColors = new int[]{0xFFC93437, 0xFF375BF1, 0xFFF7D23E, 0xFF34A350}; //Red, blue, yellow, green
				        }
			        
			        public Builder colors(int[] colors) {
				            if (colors == null || colors.length != 4) {
					                throw new IllegalArgumentException("Your color array must contains 4 values");
					            }
				            mColors = colors;
				            return this;
				        }
			        public android.graphics.drawable.Drawable build() {
				            return new NexusRotationCrossDrawable(mColors);
				        }
			    }
	}
	
	
	public static class GoogleMusicDicesDrawable extends android.graphics.drawable.Drawable implements android.graphics.drawable.Drawable.Callback {
		    private static final int DICE_SIDE_COLOR = Color.parseColor("#FFDBDBDB");
		    private static final int DICE_SIDE_SHADOW_COLOR = Color.parseColor("#FFB8B8B9");
		    private static final int ANIMATION_DURATION = 350;
		    private static final int ANIMATION_START_DELAY = 150;
		    private static final android.view.animation.Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
		    private Paint mPaint;
		    private Paint mPaintShadow;
		    private Paint mPaintCircle;
		    private int mSize;
		    private float mScale;
		    private DiceRotation mDiceRotation;
		    private DiceState[] mDiceStates;
		    private int mDiceState;
		    private enum DiceSide {
			        ONE,
			        TWO,
			        THREE,
			        FOUR,
			        FIVE,
			        SIX
			    }
		    private enum DiceRotation {
			        LEFT,
			        DOWN;
			
			        DiceRotation invert() {
				            return this == LEFT ? DOWN : LEFT;
				        }
			    }
		    private class DiceState {
			        private DiceSide side1;
			        private DiceSide side2;
			        DiceState(DiceSide side1, DiceSide side2) {
				            this.side1 = side1;
				            this.side2 = side2;
				        }
			    }
		    public GoogleMusicDicesDrawable() {
			        init();
			    }
		    private void init() {
			        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaint.setColor(DICE_SIDE_COLOR);
			        mPaintShadow = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaintShadow.setColor(DICE_SIDE_SHADOW_COLOR);
			        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaintCircle.setColor(Color.WHITE);
			        mDiceStates = new DiceState[] {
				                new DiceState(DiceSide.ONE, DiceSide.THREE),
				                new DiceState(DiceSide.TWO, DiceSide.THREE),
				                new DiceState(DiceSide.TWO, DiceSide.SIX),
				                new DiceState(DiceSide.FOUR, DiceSide.SIX),
				                new DiceState(DiceSide.FOUR, DiceSide.FIVE),
				                new DiceState(DiceSide.ONE, DiceSide.FIVE)
				        };
			        mDiceRotation = DiceRotation.LEFT;
			        initObjectAnimator();
			    }
		    private void initObjectAnimator() {
			        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "scale", 0, 1);
			        objectAnimator.setInterpolator(ACCELERATE_INTERPOLATOR);
			        objectAnimator.setDuration(ANIMATION_DURATION);
			        objectAnimator.setStartDelay(ANIMATION_START_DELAY);
			        objectAnimator.addListener(new AnimatorListenerAdapter() {
				            @Override
				            public void onAnimationEnd(Animator animation) {
					                mScale = 0;
					                mDiceState++;
					                if (mDiceState == mDiceStates.length) {
						                    mDiceState = 0;
						                }
					                mDiceRotation = mDiceRotation.invert();
					                objectAnimator.start();
					            }
				        });
			        objectAnimator.start();
			    }
		    @Override
		    public void draw(Canvas canvas) {
			        if (mDiceRotation != null) {
				            switch (mDiceRotation) {
					                case DOWN:
					                    drawScaleY(canvas);
					                    break;
					                case LEFT:
					                    drawScaleX(canvas);
					                    break;
					            }
				        }
			    }
		    @Override
		    public void setAlpha(int alpha) {
			        mPaint.setAlpha(alpha);
			        mPaintShadow.setAlpha(alpha);
			        mPaintCircle.setAlpha(alpha);
			    }
		    @Override
		    public void setColorFilter(ColorFilter cf) {
			        mPaint.setColorFilter(cf);
			        mPaintShadow.setColorFilter(cf);
			        mPaintCircle.setColorFilter(cf);
			    }
		    @Override
		    public int getOpacity() {
			        return PixelFormat.TRANSLUCENT;
			    }
		    @Override
		    protected void onBoundsChange(Rect bounds) {
			        super.onBoundsChange(bounds);
			        mSize = bounds.width();
			    }
		    @Override
		    public void invalidateDrawable(android.graphics.drawable.Drawable who) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.invalidateDrawable(this);
				        }
			    }
		    @Override
		    public void scheduleDrawable(android.graphics.drawable.Drawable who, Runnable what, long when) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.scheduleDrawable(this, what, when);
				        }
			    }
		    @Override
		    public void unscheduleDrawable(android.graphics.drawable.Drawable who, Runnable what) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.unscheduleDrawable(this, what);
				        }
			    }
		    private void drawScaleX(Canvas canvas) {
			        canvas.save();
			        Matrix matrix = new Matrix();
			        matrix.preScale(1 - mScale, 1, 0, mSize / 2);
			        canvas.concat(matrix);
			        drawDiceSide(canvas, mDiceStates[mDiceState].side1, mScale > 0.1f);
			        canvas.restore();
			        canvas.save();
			        matrix = new Matrix();
			        matrix.preScale(mScale, 1, mSize, mSize / 2);
			        canvas.concat(matrix);
			        drawDiceSide(canvas, mDiceStates[mDiceState].side2, false);
			        canvas.restore();
			    }
		    private void drawScaleY(Canvas canvas) {
			        canvas.save();
			        Matrix matrix = new Matrix();
			        matrix.preScale(1, mScale, mSize / 2, 0);
			        canvas.concat(matrix);
			        drawDiceSide(canvas, mDiceStates[mDiceState].side1, false);
			        canvas.restore();
			        canvas.save();
			        matrix = new Matrix();
			        matrix.preScale(1, 1 - mScale, mSize / 2, mSize);
			        canvas.concat(matrix);
			        drawDiceSide(canvas, mDiceStates[mDiceState].side2, mScale > 0.1f);
			        canvas.restore();
			    }
		    private void drawDiceSide(Canvas canvas, DiceSide side, boolean shadow) {
			        int circleRadius = mSize / 10;
			        canvas.drawRect(0, 0, mSize, mSize, shadow ? mPaintShadow : mPaint);
			        switch (side) {
				            case ONE:
				                canvas.drawCircle(mSize / 2, mSize / 2, circleRadius, mPaintCircle);
				                break;
				            case TWO:
				                canvas.drawCircle(mSize / 4, mSize - mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize - mSize / 4, mSize / 4, circleRadius, mPaintCircle);
				                break;
				            case THREE:
				                canvas.drawCircle(mSize / 2, mSize / 2, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize / 4, mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize - mSize / 4, mSize - mSize / 4, mSize / 10, mPaintCircle);
				                break;
				            case FOUR:
				                canvas.drawCircle(mSize / 4, mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize / 4, mSize - mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize - mSize / 4, mSize - mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize - mSize / 4, mSize / 4, circleRadius, mPaintCircle);
				                break;
				            case FIVE:
				                canvas.drawCircle(mSize / 2, mSize / 2, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize / 4, mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize / 4, mSize - mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize - mSize / 4, mSize - mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize - mSize / 4, mSize / 4, circleRadius, mPaintCircle);
				                break;
				            case SIX:
				                canvas.drawCircle(mSize / 4, mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize / 4, mSize / 2, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize / 4, mSize - mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize - mSize / 4, mSize / 4, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize - mSize / 4, mSize / 2, circleRadius, mPaintCircle);
				                canvas.drawCircle(mSize - mSize / 4, mSize - mSize / 4, circleRadius, mPaintCircle);
				                break;
				        }
			    }
		    float getScale() {
			        return mScale;
			    }
		    void setScale(float scale) {
			        this.mScale = scale;
			    }
		    public static class Builder {
			        public android.graphics.drawable.Drawable build() {
				            return new GoogleMusicDicesDrawable();
				        }
			    }
	}
	
	
	public static class FoldingCirclesDrawable extends android.graphics.drawable.Drawable implements android.graphics.drawable.Drawable.Callback {
		    private static final float MAX_LEVEL = 10000;
		    private static final float CIRCLE_COUNT = ProgressStates.values().length;
		    private static final float MAX_LEVEL_PER_CIRCLE = MAX_LEVEL / CIRCLE_COUNT;
		    private static final int ALPHA_OPAQUE = 255;
		    private static final int ALPHA_ABOVE_DEFAULT = 235;
		    private Paint mFstHalfPaint;
		    private Paint mScndHalfPaint;
		    private Paint mAbovePaint;
		    private RectF mOval = new RectF();
		    private int mDiameter;
		    private Path mPath;
		    private int mHalf;
		    private ProgressStates mCurrentState;
		    private int mControlPointMinimum;
		    private int mControlPointMaximum;
		    private int mAxisValue;
		    private int mAlpha = ALPHA_OPAQUE;
		    private ColorFilter mColorFilter;
		    private static int mColor1;
		    private static int mColor2;
		    private static int mColor3;
		    private static int mColor4;
		    private int fstColor, scndColor;
		    private boolean goesBackward;
		    private enum ProgressStates {
			        FOLDING_DOWN,
			        FOLDING_LEFT,
			        FOLDING_UP,
			        FOLDING_RIGHT
			    }
		    public FoldingCirclesDrawable(int[] colors) {
			        initCirclesProgress(colors);
			    }
		    private void initCirclesProgress(int[] colors) {
			        initColors(colors);
			        mPath = new Path();
			        Paint basePaint = new Paint();
			        basePaint.setAntiAlias(true);
			        mFstHalfPaint = new Paint(basePaint);
			        mScndHalfPaint = new Paint(basePaint);
			        mAbovePaint = new Paint(basePaint);
			        setAlpha(mAlpha);
			        setColorFilter(mColorFilter);
			    }
		    private void initColors(int[] colors) {
			        mColor1=colors[0];
			        mColor2=colors[1];
			        mColor3=colors[2];
			        mColor4=colors[3];
			    }
		    @Override
		    protected void onBoundsChange(Rect bounds) {
			        super.onBoundsChange(bounds);
			        measureCircleProgress(bounds.width(), bounds.height());
			    }
		    @Override
		    protected boolean onLevelChange(int level) {
			        int animationLevel = level == MAX_LEVEL ? 0 : level;
			        int stateForLevel = (int) (animationLevel / MAX_LEVEL_PER_CIRCLE);
			        mCurrentState = ProgressStates.values()[stateForLevel];
			        resetColor(mCurrentState);
			        int levelForCircle = (int) (animationLevel % MAX_LEVEL_PER_CIRCLE);
			        boolean halfPassed;
			        if (!goesBackward) {
				            halfPassed = levelForCircle != (int) (animationLevel % (MAX_LEVEL_PER_CIRCLE / 2));
				        } else {
				            halfPassed = levelForCircle == (int) (animationLevel % (MAX_LEVEL_PER_CIRCLE / 2));
				            levelForCircle = (int) (MAX_LEVEL_PER_CIRCLE - levelForCircle);
				        }
			        mFstHalfPaint.setColor(fstColor);
			        mScndHalfPaint.setColor(scndColor);
			        if (!halfPassed) {
				            mAbovePaint.setColor(mScndHalfPaint.getColor());
				        } else {
				            mAbovePaint.setColor(mFstHalfPaint.getColor());
				        }
			        setAlpha(mAlpha);
			        mAxisValue = (int) (mControlPointMinimum + (mControlPointMaximum - mControlPointMinimum) * (levelForCircle / MAX_LEVEL_PER_CIRCLE));
			        return true;
			    }
		    private void resetColor(ProgressStates currentState) {
			        switch (currentState){
				            case FOLDING_DOWN:
				                fstColor= mColor1;
				                scndColor=mColor2;
				                goesBackward=false;
				            break;
				            case FOLDING_LEFT:
				                fstColor= mColor1;
				                scndColor=mColor3;
				                goesBackward=true;
				                break;
				            case FOLDING_UP:
				                fstColor= mColor3;
				                scndColor=mColor4;
				                goesBackward=true;
				                break;
				            case FOLDING_RIGHT:
				                fstColor=mColor2;
				                scndColor=mColor4;
				                goesBackward=false;
				                break;
				        }
			    }
		    @Override
		    public void draw(Canvas canvas) {
			        if (mCurrentState != null) {
				            makeCirclesProgress(canvas);
				        }
			    }
		    private void measureCircleProgress(int width, int height) {
			        mDiameter = Math.min(width, height);
			        mHalf = mDiameter / 2;
			        mOval.set(0, 0, mDiameter, mDiameter);
			        mControlPointMinimum = -mDiameter / 6;
			        mControlPointMaximum = mDiameter + mDiameter / 6;
			    }
		    private void makeCirclesProgress(Canvas canvas) {
			        switch (mCurrentState) {
				            case FOLDING_DOWN:
				            case FOLDING_UP:
				                drawYMotion(canvas);
				                break;
				            case FOLDING_RIGHT:
				            case FOLDING_LEFT:
				                drawXMotion(canvas);
				                break;
				        }
			        canvas.drawPath(mPath, mAbovePaint);
			    }
		    private void drawXMotion(Canvas canvas) {
			        canvas.drawArc(mOval, 90, 180, true, mFstHalfPaint);
			        canvas.drawArc(mOval, -270, -180, true, mScndHalfPaint);
			        mPath.reset();
			        mPath.moveTo(mHalf, 0);
			        mPath.cubicTo(mAxisValue, 0, mAxisValue, mDiameter, mHalf, mDiameter);
			        mPath.moveTo(mHalf+1, 0);
			        mPath.cubicTo(mAxisValue, 0, mAxisValue, mDiameter, mHalf+1, mDiameter);
			    }
		    private void drawYMotion(Canvas canvas) {
			        canvas.drawArc(mOval, 0, -180, true, mFstHalfPaint);
			        canvas.drawArc(mOval, -180, -180, true, mScndHalfPaint);
			        mPath.reset();
			        mPath.moveTo(0, mHalf);
			        mPath.cubicTo(0, mAxisValue, mDiameter, mAxisValue, mDiameter, mHalf);
			        mPath.moveTo(0, mHalf+1);
			        mPath.cubicTo(0, mAxisValue, mDiameter, mAxisValue, mDiameter, mHalf+1);
			    }
		    @Override
		    public void setAlpha(int alpha) {
			        this.mAlpha = alpha;
			        mFstHalfPaint.setAlpha(alpha);
			        mScndHalfPaint.setAlpha(alpha);
			        int targetAboveAlpha = (ALPHA_ABOVE_DEFAULT * alpha) / ALPHA_OPAQUE;
			        mAbovePaint.setAlpha(targetAboveAlpha);
			    }
		    @Override
		    public void setColorFilter(ColorFilter cf) {
			        this.mColorFilter = cf;
			        mFstHalfPaint.setColorFilter(cf);
			        mScndHalfPaint.setColorFilter(cf);
			        mAbovePaint.setColorFilter(cf);
			    }
		    @Override
		    public int getOpacity() {
			        return PixelFormat.TRANSLUCENT;
			    }
		    @Override
		    public void invalidateDrawable(android.graphics.drawable.Drawable who) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.invalidateDrawable(this);
				        }
			    }
		    @Override
		    public void scheduleDrawable(android.graphics.drawable.Drawable who, Runnable what, long when) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.scheduleDrawable(this, what, when);
				        }
			    }
		    @Override
		    public void unscheduleDrawable(android.graphics.drawable.Drawable who, Runnable what) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.unscheduleDrawable(this, what);
				        }
			    }
		    public static class Builder {
			        private int[] mColors;
			        public Builder(Context context){
				            initDefaults(context);
				        }
			        
			        private void initDefaults(Context context) {
				            //Default values
				            mColors = new int[]{0xFFC93437, 0xFF375BF1, 0xFFF7D23E, 0xFF34A350}; //Red, blue, yellow, green
				        }
			        
			        public Builder colors(int[] colors) {
				            if (colors == null || colors.length == 0) {
					                throw new IllegalArgumentException("Your color array must contains at least 4 values");
					            }
				            mColors = colors;
				            return this;
				        }
			        public android.graphics.drawable.Drawable build() {
				            return new FoldingCirclesDrawable(mColors);
				        }
			    }
	}
	
	
	public static class ChromeFloatingCirclesDrawable extends android.graphics.drawable.Drawable implements android.graphics.drawable.Drawable.Callback {
		    private static final int MAX_LEVEL = 10000;
		    private static final int CENT_LEVEL = MAX_LEVEL / 2;
		    private static final int MID_LEVEL = CENT_LEVEL / 2;
		    private static final int ALPHA_OPAQUE = 255;
		    private static final int ACCELERATION_LEVEL = 2;
		    private int mAlpha = ALPHA_OPAQUE;
		    private ColorFilter mColorFilter;
		    private Point[] mArrowPoints;
		    private Paint mPaint1;
		    private Paint mPaint2;
		    private Paint mPaint3;
		    private Paint mPaint4;
		    private double unit;
		    private int width, x_beg, y_beg, x_end, y_end, offset;
		    private int acceleration = ACCELERATION_LEVEL;
		    private double distance = 0.5 * ACCELERATION_LEVEL * MID_LEVEL * MID_LEVEL;
		    private double max_speed; // set in setAcceleration(...);
		    private double offsetPercentage;
		    private int colorSign;
		    private ProgressStates currentProgressStates = ProgressStates.GREEN_TOP;
		    private enum ProgressStates {
			        GREEN_TOP,
			        YELLOW_TOP,
			        RED_TOP,
			        BLUE_TOP
			    }
		    public ChromeFloatingCirclesDrawable(int[] colors) {
			        initCirclesProgress(colors);
			    }
		    private void initCirclesProgress(int[] colors) {
			        initColors(colors);
			        setAlpha(mAlpha);
			        setColorFilter(mColorFilter);
			        setAcceleration(ACCELERATION_LEVEL);
			        offsetPercentage = 0;
			        colorSign = 1; // |= 1, |= 2, |= 4, |= 8 --> 0xF
			    }
		
		    private void initColors(int[] colors) {
			        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaint1.setColor(colors[0]);
			        mPaint1.setAntiAlias(true);
			        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaint2.setColor(colors[1]);
			        mPaint2.setAntiAlias(true);
			        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaint3.setColor(colors[2]);
			        mPaint3.setAntiAlias(true);
			        mPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
			        mPaint4.setColor(colors[3]);
			        mPaint4.setAntiAlias(true);
			    }
		    @Override
		    protected void onBoundsChange(Rect bounds) {
			        super.onBoundsChange(bounds);
			        measureCircleProgress(bounds.width(), bounds.height());
			    }
		    @Override
		    protected boolean onLevelChange(int level) {
			        level %= MAX_LEVEL / acceleration;
			        final int temp_level = level % (MID_LEVEL / acceleration);
			        final int ef_width = (int)(unit * 3.0); // effective width
			        if(level < CENT_LEVEL / acceleration) { // go
				            if(level < MID_LEVEL / acceleration) {
					                if(colorSign == 0xF) {
						                    changeTopColor();
						                    colorSign = 1;
						                }
					                offsetPercentage = 0.5 * acceleration * temp_level * temp_level / distance;
					                offset = (int)(offsetPercentage * ef_width / 2); // x and y direction offset
					            }
				            else {
					                colorSign |= 2;
					                // from mid to end
					                offsetPercentage = (max_speed * temp_level
					                        - 0.5 * acceleration * temp_level * temp_level) / distance
					                        + 1.0;
					                offset = (int)(offsetPercentage * ef_width / 2); // x and y direction offset
					            }
				        }
			        else { // back
				            if(level < (CENT_LEVEL + MID_LEVEL) / acceleration) {
					                // set colorSign
					                if(colorSign == 0x3) {
						                    changeTopColor();
						                    colorSign |= 4;
						                }
					                // from end to mid
					                offsetPercentage = 0.5 * acceleration * temp_level * temp_level  / distance;
					                offset = (int)(ef_width - offsetPercentage * ef_width / 2); // x and y direction offset
					            }
				            else {
					                // set colorSign
					                colorSign |= 8;
					                // from mid to beg
					                offsetPercentage = (max_speed * temp_level
					                        - 0.5 * acceleration * temp_level * temp_level) / distance
					                        + 1.0;
					                offsetPercentage = offsetPercentage == 1.0 ? 2.0 : offsetPercentage;
					                offset = (int)(ef_width - offsetPercentage * ef_width / 2); // x and y direction offset
					            }
				        }
			
			        mArrowPoints[0].set((int)unit+x_beg+offset, (int)unit+y_beg+offset); // mPaint1, left up
			        mArrowPoints[1].set((int)(unit*4.0)+x_beg-offset, (int)(unit*4.0)+y_beg-offset); // mPaint2, right down
			        mArrowPoints[2].set((int)unit+x_beg+offset, (int)(unit*4.0)+y_beg-offset); // mPaint3, left down
			        mArrowPoints[3].set((int)(unit*4.0)+x_beg-offset, (int)unit+y_beg+offset); // mPaint4, right up
			
			        return true;
			    }
		
		    private void changeTopColor() {
			        switch(currentProgressStates){
				            case GREEN_TOP:
				                currentProgressStates = ProgressStates.YELLOW_TOP;
				                break;
				            case YELLOW_TOP:
				                currentProgressStates = ProgressStates.RED_TOP;
				                break;
				            case RED_TOP:
				                currentProgressStates = ProgressStates.BLUE_TOP;
				                break;
				            case BLUE_TOP:
				                currentProgressStates = ProgressStates.GREEN_TOP;
				                break;
				        }
			    }
		
		    @Override
		    public void draw(Canvas canvas) {
			        // draw circles
			        if(currentProgressStates != ProgressStates.RED_TOP)
			            canvas.drawCircle(mArrowPoints[0].x, mArrowPoints[0].y, (float)unit, mPaint1);
			        if(currentProgressStates != ProgressStates.BLUE_TOP)
			            canvas.drawCircle(mArrowPoints[1].x, mArrowPoints[1].y, (float)unit, mPaint2);
			        if(currentProgressStates != ProgressStates.YELLOW_TOP)
			            canvas.drawCircle(mArrowPoints[2].x, mArrowPoints[2].y, (float)unit, mPaint3);
			        if(currentProgressStates != ProgressStates.GREEN_TOP)
			            canvas.drawCircle(mArrowPoints[3].x, mArrowPoints[3].y, (float)unit, mPaint4);
			
			        // draw the top one
			        switch(currentProgressStates){
				            case GREEN_TOP:
				                canvas.drawCircle(mArrowPoints[3].x, mArrowPoints[3].y, (float)unit, mPaint4);
				                break;
				            case YELLOW_TOP:
				                canvas.drawCircle(mArrowPoints[2].x, mArrowPoints[2].y, (float)unit, mPaint3);
				                break;
				            case RED_TOP:
				                canvas.drawCircle(mArrowPoints[0].x, mArrowPoints[0].y, (float)unit, mPaint1);
				                break;
				            case BLUE_TOP:
				                canvas.drawCircle(mArrowPoints[1].x, mArrowPoints[1].y, (float)unit, mPaint2);
				                break;
				        }
			    }
		
		    private void measureCircleProgress(int width, int height) {
			        // get min edge as width
			        if(width > height) {
				            // use height
				            this.width = height - 1; // minus 1 to avoid "3/2=1"
				            x_beg = (width - height) / 2 + 1;
				            y_beg = 1;
				            x_end = x_beg + this.width;
				            y_end = this.width;
				        }
			        else {
				            //use width
				            this.width = width - 1;
				            x_beg = 1;
				            y_beg = (height - width) / 2 + 1;
				            x_end = this.width;
				            y_end = y_beg + this.width;
				        }
			        unit = (double)this.width / 5.0;
			
			        // init the original position, and then set position by offsets
			        mArrowPoints = new Point[4];
			        mArrowPoints[0] = new Point((int)unit+x_beg, (int)unit+y_beg); // mPaint1, left up
			        mArrowPoints[1] = new Point((int)(unit*4.0)+x_beg, (int)(unit*4.0)+y_beg); // mPaint2, right down
			        mArrowPoints[2] = new Point((int)unit+x_beg, (int)(unit*4.0)+y_beg); // mPaint3, left down
			        mArrowPoints[3] = new Point((int)(unit*4.0)+x_beg, (int)unit+y_beg); // mPaint4, right up
			    }
		
		    public void setAcceleration(int acceleration) {
			        this.acceleration = acceleration;
			        distance = 0.5 * acceleration * (MID_LEVEL / acceleration) * (MID_LEVEL / acceleration);
			        max_speed = acceleration * (MID_LEVEL / acceleration);
			    }
		
		    @Override
		    public void setAlpha(int alpha) {
			        mPaint1.setAlpha(alpha);
			        mPaint2.setAlpha(alpha);
			        mPaint3.setAlpha(alpha);
			        mPaint4.setAlpha(alpha);
			    }
		
		    @Override
		    public void setColorFilter(ColorFilter cf) {
			        mColorFilter = cf;
			        mPaint1.setColorFilter(cf);
			        mPaint2.setColorFilter(cf);
			        mPaint3.setColorFilter(cf);
			        mPaint4.setColorFilter(cf);
			    }
		
		    @Override
		    public int getOpacity() {
			        return PixelFormat.TRANSLUCENT;
			    }
		
		    @Override
		    public void invalidateDrawable(android.graphics.drawable.Drawable who) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.invalidateDrawable(this);
				        }
			    }
		
		    @Override
		    public void scheduleDrawable(android.graphics.drawable.Drawable who, Runnable what, long when) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.scheduleDrawable(this, what, when);
				        }
			    }
		
		    @Override
		    public void unscheduleDrawable(android.graphics.drawable.Drawable who, Runnable what) {
			        final Callback callback = getCallback();
			        if (callback != null) {
				            callback.unscheduleDrawable(this, what);
				        }
			    }
		
		    public static class Builder {
			        private int[] mColors;
			
			        public Builder(Context context){
				            initDefaults(context);
				            return;
				        }
					
			        private void initDefaults(Context context) {
				            //Default values
				            mColors = new int[]{0xFFC93437, 0xFF375BF1, 0xFFF7D23E, 0xFF34A350}; //Red, blue, yellow, green
				            return;
				        }
					
			        public Builder colors(int[] colors) {
				            if (colors == null || colors.length == 0) {
					                throw new IllegalArgumentException("Your color array must contains at least 4 values");
					            }
				
				            mColors = colors;
				            return this;
				        }
			
			        public android.graphics.drawable.Drawable build() {
				            return new ChromeFloatingCirclesDrawable(mColors);
				        }
			    }
	}
	
	
	{
	}
	
	
	private void _change_background () {
		intent.setClass(getApplicationContext(), ChangeBackgroundActivity.class);
		intent.putExtra("previousScreen", "chat");
		startActivity(intent);
	}
	
	
	private void _user_account () {
		intent.setClass(getApplicationContext(), ProfileActivity.class);
		startActivity(intent);
	}
	
	
	private void _Linkify (final TextView _text, final String _color) {
		_text.setClickable(true);
		
		android.text.util.Linkify.addLinks(_text, android.text.util.Linkify.ALL);
		
		_text.setLinkTextColor(Color.parseColor("#" + _color.replace("#", "")));
		
		_text.setLinksClickable(true);
	}
	
	
	private void _setBackgroundOf (final View _view, final String _path) {
		_view.setBackground(new android.graphics.drawable.BitmapDrawable(getResources(), FileUtil.decodeSampleBitmapFromPath(_path, 1024, 1024)));
	}
	
	
	private void _setHighlighter (final TextView _view) {
		final String secondaryColor = "#678cb1"; final String primaryColor = "#86b55a"; final String numbersColor = "#f6c921"; final String quotesColor = "#ff1744"; final String commentsColor = "#9e9e9e"; final String charColor = "#ff5722"; final TextView regex1 = new TextView(this); final TextView regex2 = new TextView(this); final TextView regex3 = new TextView(this); final TextView regex4 = new TextView(this); final TextView regex5 = new TextView(this); final TextView regex6 = new TextView(this); final TextView regex7 = new TextView(this); final TextView regex8 = new TextView(this); final TextView regex9 = new TextView(this); final TextView regex10 = new TextView(this); final TextView regex11 = new TextView(this); regex1.setText("\\b(out|print|println|valueOf|toString|concat|equals|for|while|switch|getText"); regex2.setText("|println|printf|print|out|parseInt|round|sqrt|charAt|compareTo|compareToIgnoreCase|concat|contains|contentEquals|equals|length|toLowerCase|trim|toUpperCase|toString|valueOf|substring|startsWith|split|replace|replaceAll|lastIndexOf|size)\\b"); regex3.setText("\\b(public|private|protected|void|switch|case|class|import|package|extends|Activity|TextView|EditText|LinearLayout|CharSequence|String|int|onCreate|ArrayList|float|if|else|static|Intent|Button|SharedPreferences"); regex4.setText("|abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|interface|long|native|new|package|private|protected|"); regex5.setText("public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while|true|false|null)\\b"); regex6.setText("\\b([0-9]+)\\b"); regex7.setText("(\\w+)(\\()+"); regex8.setText("\\@\\s*(\\w+)"); regex9.setText("\"(.*?)\"|'(.*?)'"); regex10.setText("/\\*(?:.|[\\n\\r])*?\\*/|//.*"); regex11.setText("\\b(Uzuakoli|Amoji|Bright|Ndudirim|Ezinwanne|Lightworker|Isuochi|Abia|Ngodo)\\b"); _view.addTextChangedListener(new TextWatcher() { ColorScheme keywords1 = new ColorScheme(java.util.regex.Pattern.compile(regex1.getText().toString().concat(regex2.getText().toString())), Color.parseColor(secondaryColor)); ColorScheme keywords2 = new ColorScheme(java.util.regex.Pattern.compile(regex3.getText().toString().concat(regex4.getText().toString().concat(regex5.getText().toString()))), Color.parseColor(primaryColor)); ColorScheme keywords3 = new ColorScheme(java.util.regex.Pattern.compile(regex6.getText().toString()), Color.parseColor(numbersColor)); ColorScheme keywords4 = new ColorScheme(java.util.regex.Pattern.compile(regex7.getText().toString()), Color.parseColor(secondaryColor)); ColorScheme keywords5 = new ColorScheme(java.util.regex.Pattern.compile(regex9.getText().toString()), Color.parseColor(quotesColor)); ColorScheme keywords6 = new ColorScheme(java.util.regex.Pattern.compile(regex10.getText().toString()), Color.parseColor(commentsColor)); ColorScheme keywords7 = new ColorScheme(java.util.regex.Pattern.compile(regex8.getText().toString()), Color.parseColor(numbersColor)); ColorScheme keywords8 = new ColorScheme(java.util.regex.Pattern.compile(regex11.getText().toString()), Color.parseColor(charColor)); final ColorScheme[] schemes = {keywords1, keywords2, keywords3, keywords4, keywords5, keywords6, keywords7, keywords8}; @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { } @Override public void onTextChanged(CharSequence s, int start, int before, int count) { } @Override public void afterTextChanged(Editable s) { removeSpans(s, android.text.style.ForegroundColorSpan.class); for(ColorScheme scheme : schemes) { for(java.util.regex.Matcher m = scheme.pattern.matcher(s); m.find();) { if (scheme == keywords4) { s.setSpan(new android.text.style.ForegroundColorSpan(scheme.color), m.start(), m.end()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); } else { s.setSpan(new android.text.style.ForegroundColorSpan(scheme.color), m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); } } } } void removeSpans(Editable e, Class type) { android.text.style.CharacterStyle[] spans = e.getSpans(0, e.length(), type); for (android.text.style.CharacterStyle span : spans) { e.removeSpan(span); } } class ColorScheme { final java.util.regex.Pattern pattern; final int color; ColorScheme(java.util.regex.Pattern pattern, int color) { this.pattern = pattern; this.color = color; } } });
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
			final LinearLayout linear3 = (LinearLayout) _v.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _v.findViewById(R.id.linear4);
			final LinearLayout code_linear = (LinearLayout) _v.findViewById(R.id.code_linear);
			final TextView textview1 = (TextView) _v.findViewById(R.id.textview1);
			final TextView time = (TextView) _v.findViewById(R.id.time);
			final TextView textview2 = (TextView) _v.findViewById(R.id.textview2);
			final ImageView img = (ImageView) _v.findViewById(R.id.img);
			final ImageView fileviewer = (ImageView) _v.findViewById(R.id.fileviewer);
			final TextView code_textview = (TextView) _v.findViewById(R.id.code_textview);
			
			width = Math.round(SketchwareUtil.getDisplayWidthPixels(getApplicationContext()) * 0.75d);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)width, LinearLayout.LayoutParams.WRAP_CONTENT);
			linear2.setLayoutParams(lp);
			if (chatroom1.get((int)_position).containsKey("message")) {
				textview1.setText(chatroom1.get((int)_position).get("message").toString());
				_Linkify(textview1, "#e81586");
			}
			if (chatroom1.get((int)_position).containsKey("sender")) {
				
			}
			if (chatroom1.get((int)_position).get("sender").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
				linear1.setGravity(Gravity.RIGHT);
				textview2.setText("By - ".concat(user1));
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(Color.parseColor("#CCFF90"));
				gd.setCornerRadius(40);
				linear2.setBackground(gd);
				Animation animation; animation = AnimationUtils.loadAnimation( getApplicationContext(), android.R.anim.slide_in_left ); animation.setDuration(700); linear1.startAnimation(animation); animation = null;
			}
			else {
				linear1.setGravity(Gravity.LEFT);
				textview2.setText("By - ".concat(user2));
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(Color.parseColor("#ffffff"));
				gd.setCornerRadius(40);
				linear2.setBackground(gd);
				Animation animation; animation = AnimationUtils.loadAnimation( getApplicationContext(), android.R.anim.slide_in_left ); animation.setDuration(700); linear1.startAnimation(animation); animation = null;
			}
			if (chatroom1.get((int)_position).containsKey("image")) {
				img.setVisibility(View.VISIBLE);
				Glide.with(getApplicationContext()).load(Uri.parse(chatroom1.get((int)_position).get("image").toString())).into(img);
				img.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						dialog.setTitle("Message contains Image");
						dialog.setMessage("Image file name : ".concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("image").toString()).getLastPathSegment()).getLastPathSegment()).concat("Do you want to view image or download the image ?"));
						dialog.setPositiveButton("View Image", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								intent.setAction(Intent.ACTION_VIEW);
								intent.putExtra("imageDownloadUrl", chatroom1.get((int)_position).get("image").toString());
								intent.setClass(getApplicationContext(), ImageViewerActivity.class);
								startActivity(intent);
							}
						});
						dialog.setNeutralButton("Download", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								if (FileUtil.isExistFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Files/Private Chat Files/"))) {
									_firebase_storage.getReferenceFromUrl(chatroom1.get((int)_position).get("image").toString()).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Files/Private Chat Files/ Sender By Aishwary Raj Image File - ").concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("image").toString()).getLastPathSegment()).getLastPathSegment()))).addOnSuccessListener(_fstore_download_success_listener).addOnFailureListener(_fstore_failure_listener).addOnProgressListener(_fstore_download_progress_listener);
									_customText("Starting image download");
								}
								else {
									FileUtil.makeDir("/Sender Files/Private Chat Files/");
									_firebase_storage.getReferenceFromUrl(chatroom1.get((int)_position).get("image").toString()).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Files/Private Chat Files/Sender By Aishwary Raj Image Files - ").concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("image").toString()).getLastPathSegment()).getLastPathSegment()))).addOnSuccessListener(_fstore_download_success_listener).addOnFailureListener(_fstore_failure_listener).addOnProgressListener(_fstore_download_progress_listener);
									_customText("Starting image download ");
								}
							}
						});
						dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						dialog.create().show();
					}
				});
			}
			else {
				img.setVisibility(View.GONE);
			}
			if (chatroom1.get((int)_position).containsKey("file")) {
				fileviewer.setVisibility(View.VISIBLE);
				fileviewer.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						dialog.setTitle("Message contains a file");
						dialog.setMessage(" File name : ".concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("image").toString()).getLastPathSegment()).getLastPathSegment()).concat("Do you want to download this file ?"));
						dialog.setPositiveButton("Download File", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								if (FileUtil.isExistFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Files/Private Chat Files/"))) {
									_firebase_storage.getReferenceFromUrl(chatroom1.get((int)_position).get("image").toString()).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Files/Private Chat Files/Sender By Aishwary Raj File - ").concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("image").toString()).getLastPathSegment()).getLastPathSegment()))).addOnSuccessListener(_fstore_download_success_listener).addOnFailureListener(_fstore_failure_listener).addOnProgressListener(_fstore_download_progress_listener);
									_customText("Starting image download");
								}
								else {
									FileUtil.makeDir("/Sender Files/Private Chat Files/");
									_firebase_storage.getReferenceFromUrl(chatroom1.get((int)_position).get("image").toString()).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Files/Private Chat Files/Sender By Aishwary Raj File - ").concat(Uri.parse(Uri.parse(chatroom1.get((int)_position).get("image").toString()).getLastPathSegment()).getLastPathSegment()))).addOnSuccessListener(_fstore_download_success_listener).addOnFailureListener(_fstore_failure_listener).addOnProgressListener(_fstore_download_progress_listener);
									_customText("Starting image download ");
								}
							}
						});
						dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						dialog.create().show();
					}
				});
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
			if (chatroom1.get((int)_position).containsKey("source_code")) {
				code_linear.setVisibility(View.VISIBLE);
				_setHighlighter(code_textview);
				code_textview.setText(chatroom1.get((int)_position).get("source_code").toString());
				code_linear.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						intent.setClass(getApplicationContext(), EmbeddedCodeActivity.class);
						intent.putExtra("code", chatroom1.get((int)_position).get("source_code").toString());
						intent.putExtra("firstuser", getIntent().getStringExtra("firstuser"));
						intent.putExtra("seconduser", getIntent().getStringExtra("seconduser"));
						startActivity(intent);
					}
				});
			}
			else {
				code_linear.setVisibility(View.GONE);
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
