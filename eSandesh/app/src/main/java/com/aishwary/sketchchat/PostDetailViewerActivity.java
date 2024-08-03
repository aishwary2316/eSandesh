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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.app.Activity;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Continuation;
import java.io.File;
import android.view.View;
import com.bumptech.glide.Glide;
import java.text.DecimalFormat;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class PostDetailViewerActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private Toolbar _toolbar;
	private double like_num = 0;
	private String uid = "";
	private String key = "";
	private String like_key = "";
	private boolean liked = false;
	private double comment_num = 0;
	private String image_url = "";
	private String file_url = "";
	private String downloadPath = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String nickname = "";
	private String profile_picture = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear11;
	private LinearLayout linear10;
	private ImageView imageview1;
	private LinearLayout linear3;
	private TextView textview2;
	private TextView textview1;
	private TextView textview3;
	private ImageView imageview2;
	private ImageView imageview3;
	private ProgressBar progressbar1;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private ImageView imageview4;
	private TextView textview4;
	private TextView textview5;
	private ImageView imageview5;
	private TextView textview6;
	private TextView textview7;
	private LinearLayout linear12;
	private LinearLayout linear14;
	private LinearLayout linear13;
	private ImageView imageview7;
	private TextView textview9;
	private ImageView imageview8;
	private TextView textview10;
	private ImageView imageview6;
	private TextView textview8;
	
	private SharedPreferences file;
	private SharedPreferences names;
	private DatabaseReference post = _firebase.getReference("posts");
	private ChildEventListener _post_child_listener;
	private DatabaseReference likes = _firebase.getReference("likes");
	private ChildEventListener _likes_child_listener;
	private DatabaseReference comments = _firebase.getReference("comments");
	private ChildEventListener _comments_child_listener;
	private Intent i = new Intent();
	private AlertDialog.Builder d;
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private StorageReference files = _firebase_storage.getReference("community_attachment");
	private OnCompleteListener<Uri> _files_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _files_download_success_listener;
	private OnSuccessListener _files_delete_success_listener;
	private OnProgressListener _files_upload_progress_listener;
	private OnProgressListener _files_download_progress_listener;
	private OnFailureListener _files_failure_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.post_detail_viewer);
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
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview3 = (TextView) findViewById(R.id.textview3);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview5 = (TextView) findViewById(R.id.textview5);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		textview6 = (TextView) findViewById(R.id.textview6);
		textview7 = (TextView) findViewById(R.id.textview7);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		imageview7 = (ImageView) findViewById(R.id.imageview7);
		textview9 = (TextView) findViewById(R.id.textview9);
		imageview8 = (ImageView) findViewById(R.id.imageview8);
		textview10 = (TextView) findViewById(R.id.textview10);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		textview8 = (TextView) findViewById(R.id.textview8);
		file = getSharedPreferences("file", Activity.MODE_PRIVATE);
		names = getSharedPreferences("names", Activity.MODE_PRIVATE);
		d = new AlertDialog.Builder(this);
		auth = FirebaseAuth.getInstance();
		
		linear10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("Delete post ?");
				d.setMessage("Are you sure you want to delete this post ?");
				d.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(uid)) {
							post.child(key).removeValue();
							finish();
						}
					}
				});
				d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), DpViewerActivity.class);
				i.putExtra("profilePicDownloadUrl", "profile_picture");
				startActivity(i);
			}
		});
		
		textview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("Open private chat");
				d.setMessage("Do you want to chat privately with ".concat(nickname));
				d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						i.putExtra("firstuser", FirebaseAuth.getInstance().getCurrentUser().getUid());
						i.putExtra("seconduser", getIntent().getStringExtra("uid"));
						i.setClass(getApplicationContext(), ChatActivity.class);
						startActivity(i);
						_customText("Starting private chat with ".concat(nickname));
					}
				});
				
				d.create().show();
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("Post contains an image");
				d.setMessage("Image file name : ".concat(Uri.parse(Uri.parse(image_url).getLastPathSegment()).getLastPathSegment()));
				d.setPositiveButton("Download Image", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (FileUtil.isExistFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/"))) {
							_firebase_storage.getReferenceFromUrl(image_url).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/Sender_Post_".concat(Uri.parse(Uri.parse(image_url).getLastPathSegment()).getLastPathSegment())))).addOnSuccessListener(_files_download_success_listener).addOnFailureListener(_files_failure_listener).addOnProgressListener(_files_download_progress_listener);
							downloadPath = FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/Sender_Post_".concat(Uri.parse(Uri.parse(image_url).getLastPathSegment()).getLastPathSegment()));
						}
						else {
							FileUtil.makeDir(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/"));
							_firebase_storage.getReferenceFromUrl(image_url).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/Sender_Post_".concat(Uri.parse(Uri.parse(image_url).getLastPathSegment()).getLastPathSegment())))).addOnSuccessListener(_files_download_success_listener).addOnFailureListener(_files_failure_listener).addOnProgressListener(_files_download_progress_listener);
							downloadPath = FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/Sender_Post_".concat(Uri.parse(Uri.parse(image_url).getLastPathSegment()).getLastPathSegment()));
						}
					}
				});
				d.setNeutralButton("View Image", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						i.setClass(getApplicationContext(), ImageViewerActivity.class);
						i.putExtra("imageDownloadUrl", image_url);
						startActivity(i);
					}
				});
				d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("Post contains an attachment");
				d.setMessage("Attachment contains a file.\nFile name : ".concat("".concat("\nDo you want to download this file ?")));
				d.setPositiveButton("Download", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (FileUtil.isExistFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/"))) {
							_firebase_storage.getReferenceFromUrl(file_url).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/ Sender By Aishwary Raj Community post file attachment - ".concat(Uri.parse(Uri.parse(file_url).getLastPathSegment()).getLastPathSegment())))).addOnSuccessListener(_files_download_success_listener).addOnFailureListener(_files_failure_listener).addOnProgressListener(_files_download_progress_listener);
							downloadPath = FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/ Sender By Aishwary Raj Community post file attachment - ".concat(Uri.parse(Uri.parse(file_url).getLastPathSegment()).getLastPathSegment()));
						}
						else {
							FileUtil.makeDir(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/"));
							_firebase_storage.getReferenceFromUrl(file_url).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/ Sender By Aishwary Raj Community post file attachment - ".concat(Uri.parse(Uri.parse(file_url).getLastPathSegment()).getLastPathSegment())))).addOnSuccessListener(_files_download_success_listener).addOnFailureListener(_files_failure_listener).addOnProgressListener(_files_download_progress_listener);
							downloadPath = FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Sender Community Posts/ Sender By Aishwary Raj Community post file attachment - ".concat(Uri.parse(Uri.parse(file_url).getLastPathSegment()).getLastPathSegment()));
						}
					}
				});
				d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
		});
		
		linear7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		linear9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		linear12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (liked) {
					map = new HashMap<>();
					map.put("value", "false");
					map.put("key", key);
					map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
					likes.child(like_key).updateChildren(map);
				}
				else {
					map = new HashMap<>();
					map.put("value", "true");
					map.put("key", key);
					map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
					likes.child(like_key).updateChildren(map);
				}
			}
		});
		
		linear13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), CommentsActivity.class);
				i.putExtra("key", key);
				startActivity(i);
			}
		});
		
		_post_child_listener = new ChildEventListener() {
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
		post.addChildEventListener(_post_child_listener);
		
		_likes_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((_childValue.containsKey("key") && _childValue.containsKey("value")) && _childValue.containsKey("uid")) {
					if (key.equals(_childValue.get("key").toString()) && _childValue.get("value").toString().equals("true")) {
						like_num++;
						textview5.setText(String.valueOf((long)(like_num)));
					}
					if (key.equals(_childValue.get("key").toString()) && _childValue.get("uid").toString().equals(file.getString("uid", ""))) {
						if (_childValue.get("value").toString().equals("true")) {
							imageview4.setImageResource(R.drawable.ic_favorite_grey);
							liked = true;
						}
						else {
							imageview4.setImageResource(R.drawable.ic_favorite_outline_grey);
							liked = false;
						}
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("key").toString().equals(key) && _childValue.get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_childValue.get("value").toString().equals("true")) {
						imageview7.setImageResource(R.drawable.ic_favorite_black);
						textview9.setText("Liked Post");
						liked = true;
					}
					else {
						imageview7.setImageResource(R.drawable.ic_favorite_outline_black);
						textview9.setText("Like Post");
						liked = false;
					}
				}
				if (_childValue.get("key").toString().equals(key)) {
					if (_childValue.get("value").toString().equals("true")) {
						like_num++;
						textview5.setText(String.valueOf((long)(like_num)));
					}
					else {
						like_num--;
						textview5.setText(String.valueOf((long)(like_num)));
					}
				}
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
		likes.addChildEventListener(_likes_child_listener);
		
		_comments_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.containsKey("post_key")) {
					if (key.equals(_childValue.get("post_key").toString())) {
						comment_num++;
						textview7.setText(String.valueOf((long)(comment_num)));
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.containsKey("post_key")) {
					if (key.equals(_childValue.get("post_key").toString())) {
						comment_num++;
						textview7.setText(String.valueOf((long)(comment_num)));
					}
				}
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
		comments.addChildEventListener(_comments_child_listener);
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (uid.equals(_childKey)) {
					textview2.setText(_childValue.get("nickname").toString());
					nickname = _childValue.get("nickname").toString();
					Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("profile_picture").toString())).into(imageview1);
					profile_picture = _childValue.get("profile_picture").toString();
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
		
		_files_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_files_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				progressbar1.setVisibility(View.VISIBLE);
				progressbar1.setProgress((int)_progressValue);
				_customText(String.valueOf(Math.round(_progressValue)).concat(" % File downloaded"));
			}
		};
		
		_files_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				
			}
		};
		
		_files_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				_customText("File Successfully downloaded");
				progressbar1.setVisibility(View.GONE);
				_customText("File saved at :   ".concat(""));
			}
		};
		
		_files_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_files_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	private void initializeLogic() {
		key = getIntent().getStringExtra("key");
		uid = getIntent().getStringExtra("uid");
		textview1.setText(getIntent().getStringExtra("time"));
		if (getIntent().getStringExtra("file").equals("NA")) {
			imageview3.setVisibility(View.GONE);
		}
		else {
			file_url = getIntent().getStringExtra("file");
		}
		if (getIntent().getStringExtra("image").equals("NA")) {
			imageview2.setVisibility(View.GONE);
		}
		else {
			Glide.with(getApplicationContext()).load(Uri.parse(getIntent().getStringExtra("image"))).into(imageview2);
			image_url = getIntent().getStringExtra("image");
		}
		if (getIntent().getStringExtra("message").equals("NA")) {
			textview3.setVisibility(View.GONE);
		}
		else {
			textview3.setText(getIntent().getStringExtra("message"));
		}
		like_num = 0;
		comment_num = 0;
		liked = false;
		like_key = key.concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
		progressbar1.setVisibility(View.GONE);
		_Linkify(textview3, "#e81586");
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
