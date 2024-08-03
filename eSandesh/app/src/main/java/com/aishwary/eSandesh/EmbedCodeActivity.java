package com.aishwary.eSandesh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import java.text.DecimalFormat;
import android.content.ClipData;
import android.content.ClipboardManager;

public class EmbedCodeActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private FloatingActionButton _fab;
	private double linecount = 0;
	private double n1 = 0;
	private String str = "";
	private double count = 0;
	private double n = 0;
	private String size = "";
	private String chatroom = "";
	private String chatcopy = "";
	private String chatNotifications = "";
	private double latitude = 0;
	private double longitude = 0;
	private double net_latitude = 0;
	private double net_longitude = 0;
	private double net_accuracy = 0;
	private String sender_key = "";
	private String recipient_key = "";
	private String notification_key = "";
	private HashMap<String, Object> map = new HashMap<>();
	private double accuracy = 0;
	private String username = "";
	
	private ArrayList<String> list1 = new ArrayList<>();
	
	private LinearLayout linear6;
	private LinearLayout linear10;
	private LinearLayout linear13;
	private ScrollView vscroll1;
	private LinearLayout linear7;
	private TextView textview3;
	private Spinner spinner1;
	private LinearLayout linear8;
	private ImageView imageview1;
	private EditText edittext3;
	private LinearLayout linear12;
	private ImageView imageview2;
	private LinearLayout linear14;
	private ImageView imageview3;
	private EditText edittext4;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private TextView textview1;
	private HorizontalScrollView hscroll1;
	private EditText edittext1;
	
	private AlertDialog.Builder dialog;
	private DatabaseReference chat1 = _firebase.getReference("" + chatroom + "");
	private ChildEventListener _chat1_child_listener;
	private DatabaseReference chat2 = _firebase.getReference("" + chatcopy + "");
	private ChildEventListener _chat2_child_listener;
	private DatabaseReference notify = _firebase.getReference("" + chatNotifications + "");
	private ChildEventListener _notify_child_listener;
	private Calendar cal = Calendar.getInstance();
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
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.embed_code);
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
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		textview3 = (TextView) findViewById(R.id.textview3);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		edittext4 = (EditText) findViewById(R.id.edittext4);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		textview1 = (TextView) findViewById(R.id.textview1);
		hscroll1 = (HorizontalScrollView) findViewById(R.id.hscroll1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		dialog = new AlertDialog.Builder(this);
		fauth = FirebaseAuth.getInstance();
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (list1.get((int)(_position)).equals("Custom")) {
					linear10.setVisibility(View.VISIBLE);
				}
				else {
					linear10.setVisibility(View.GONE);
					_setTextSize(edittext1, Double.parseDouble(list1.get((int)(_position))));
					_setTextSize(textview1, Double.parseDouble(list1.get((int)(_position))));
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_customText("Ready to send");
				cal = Calendar.getInstance();
				notification_key = chat1.push().getKey();
				sender_key = chat2.push().getKey();
				recipient_key = notify.push().getKey();
				map = new HashMap<>();
				map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("senderNickname", username);
				map.put("notification_key", notification_key);
				map.put("sender_key", sender_key);
				map.put("recipient_key", recipient_key);
				map.put("message", edittext4.getText().toString());
				map.put("source_code", edittext1.getText().toString());
				map.put("time", new SimpleDateFormat("dd MMMM yyyy - hh:mm:ss a").format(cal.getTime()));
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
				_customText("Sending Source code...");
				finish();
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext3.getText().toString().length() > 0) {
					if (4 > edittext3.getText().toString().length()) {
						list1.add(edittext3.getText().toString());
						_setTextSize(edittext1, Double.parseDouble(edittext3.getText().toString()));
						_setTextSize(textview1, Double.parseDouble(edittext3.getText().toString()));
						linear10.setVisibility(View.GONE);
					}
					else {
						_customText("Allowed Text Size Range is 1 to 999 only");
					}
				}
				else {
					_customText("Input the custom text size");
				}
			}
		});
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				str = _charSeq.trim();
				count = 0;
				if (str.length() > 0) {
					count++;
					n = 0;
					for(int _repeat34 = 0; _repeat34 < (int)(str.length()); _repeat34++) {
						if (str.substring((int)(n), (int)(n + 1)).equals("\n")) {
							count++;
						}
						n++;
					}
				}
				linecount = count;
				textview1.setText("");
				n1 = 1;
				for(int _repeat49 = 0; _repeat49 < (int)(linecount); _repeat49++) {
					textview1.setText(textview1.getText().toString().concat(String.valueOf((long)(n1)).concat("\n")));
					n1++;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext1.getText().toString().length() > 0) {
					((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", edittext1.getText().toString()));
					_customText("Source Code copied");
				}
				else {
					_customText("Nothing found to copy\nPlease type your source code first! ");
				}
			}
		});
		
		_chat1_child_listener = new ChildEventListener() {
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
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					username = _childValue.get("nickname").toString();
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
		_setHighlighter(edittext1);
		list1.add("10");
		list1.add("11");
		list1.add("12");
		list1.add("14");
		list1.add("16");
		list1.add("18");
		list1.add("20");
		list1.add("25");
		list1.add("30");
		list1.add("40");
		list1.add("50");
		list1.add("60");
		list1.add("70");
		list1.add("80");
		list1.add("90");
		list1.add("100");
		list1.add("Custom");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, list1));
		
		spinner1.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, list1) {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView1 = (TextView) super.getView(position, convertView, parent);
				textView1.setTextColor(Color.RED);
				textView1.setTextSize(16);
				return textView1; }
			
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				TextView textView1 = (TextView) super.getDropDownView(position, convertView, parent); textView1.setTextColor(Color.RED);
				textView1.setTextSize(16);
				return textView1; }
		});
		((ArrayAdapter)spinner1.getAdapter()).notifyDataSetChanged();
		spinner1.setSelection((int)(list1.indexOf("16")));
		linear10.setVisibility(View.GONE);
		if (getIntent().getStringExtra("codeStatus").equals("availiable")) {
			edittext1.setText(getIntent().getStringExtra("code"));
		}
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
		edittext1.setTextIsSelectable(true);
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
	
	
	private void _setHighlighter (final TextView _view) {
		final String secondaryColor = "#eeeeee"; final String primaryColor = "#86b55a"; final String numbersColor = "#f6c921"; final String quotesColor = "#ff1744"; final String commentsColor = "#757575"; final String charColor = "#ff5722"; final TextView regex1 = new TextView(this); final TextView regex2 = new TextView(this); final TextView regex3 = new TextView(this); final TextView regex4 = new TextView(this); final TextView regex5 = new TextView(this); final TextView regex6 = new TextView(this); final TextView regex7 = new TextView(this); final TextView regex8 = new TextView(this); final TextView regex9 = new TextView(this); final TextView regex10 = new TextView(this); final TextView regex11 = new TextView(this); regex1.setText("\\b(out|print|println|valueOf|toString|concat|equals|for|while|switch|getText"); regex2.setText("|println|printf|print|out|parseInt|round|sqrt|charAt|compareTo|compareToIgnoreCase|concat|contains|contentEquals|equals|length|toLowerCase|trim|toUpperCase|toString|valueOf|substring|startsWith|split|replace|replaceAll|lastIndexOf|size)\\b"); regex3.setText("\\b(public|private|protected|void|switch|case|class|import|package|extends|Activity|TextView|EditText|LinearLayout|CharSequence|String|int|onCreate|ArrayList|float|if|else|static|Intent|Button|SharedPreferences"); regex4.setText("|abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|interface|long|native|new|package|private|protected|"); regex5.setText("public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while|true|false|null)\\b"); regex6.setText("\\b([0-9]+)\\b"); regex7.setText("(\\w+)(\\()+"); regex8.setText("\\@\\s*(\\w+)"); regex9.setText("\"(.*?)\"|'(.*?)'"); regex10.setText("/\\*(?:.|[\\n\\r])*?\\*/|//.*"); regex11.setText("\\b(Uzuakoli|Amoji|Bright|Ndudirim|Ezinwanne|Lightworker|Isuochi|Abia|Ngodo)\\b"); _view.addTextChangedListener(new TextWatcher() { ColorScheme keywords1 = new ColorScheme(java.util.regex.Pattern.compile(regex1.getText().toString().concat(regex2.getText().toString())), Color.parseColor(secondaryColor)); ColorScheme keywords2 = new ColorScheme(java.util.regex.Pattern.compile(regex3.getText().toString().concat(regex4.getText().toString().concat(regex5.getText().toString()))), Color.parseColor(primaryColor)); ColorScheme keywords3 = new ColorScheme(java.util.regex.Pattern.compile(regex6.getText().toString()), Color.parseColor(numbersColor)); ColorScheme keywords4 = new ColorScheme(java.util.regex.Pattern.compile(regex7.getText().toString()), Color.parseColor(secondaryColor)); ColorScheme keywords5 = new ColorScheme(java.util.regex.Pattern.compile(regex9.getText().toString()), Color.parseColor(quotesColor)); ColorScheme keywords6 = new ColorScheme(java.util.regex.Pattern.compile(regex10.getText().toString()), Color.parseColor(commentsColor)); ColorScheme keywords7 = new ColorScheme(java.util.regex.Pattern.compile(regex8.getText().toString()), Color.parseColor(numbersColor)); ColorScheme keywords8 = new ColorScheme(java.util.regex.Pattern.compile(regex11.getText().toString()), Color.parseColor(charColor)); final ColorScheme[] schemes = {keywords1, keywords2, keywords3, keywords4, keywords5, keywords6, keywords7, keywords8}; @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { } @Override public void onTextChanged(CharSequence s, int start, int before, int count) { } @Override public void afterTextChanged(Editable s) { removeSpans(s, android.text.style.ForegroundColorSpan.class); for(ColorScheme scheme : schemes) { for(java.util.regex.Matcher m = scheme.pattern.matcher(s); m.find();) { if (scheme == keywords4) { s.setSpan(new android.text.style.ForegroundColorSpan(scheme.color), m.start(), m.end()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); } else { s.setSpan(new android.text.style.ForegroundColorSpan(scheme.color), m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); } } } } void removeSpans(Editable e, Class type) { android.text.style.CharacterStyle[] spans = e.getSpans(0, e.length(), type); for (android.text.style.CharacterStyle span : spans) { e.removeSpan(span); } } class ColorScheme { final java.util.regex.Pattern pattern; final int color; ColorScheme(java.util.regex.Pattern pattern, int color) { this.pattern = pattern; this.color = color; } } });
	}
	
	
	private void _setTextSize (final TextView _view, final double _size) {
		_view.setTextSize((float) _size);
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
