package com.aishwary.eSandesh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
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
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.content.ClipData;
import android.content.ClipboardManager;

public class EmbeddedCodeActivity extends AppCompatActivity {
	
	
	private double linecount = 0;
	private String size = "";
	private double n = 0;
	private double n1 = 0;
	private String str = "";
	private double count = 0;
	
	private ArrayList<String> list1 = new ArrayList<>();
	
	private LinearLayout linear4;
	private LinearLayout linear10;
	private ScrollView vscroll3;
	private LinearLayout linear5;
	private TextView textview5;
	private Spinner spinner1;
	private LinearLayout linear15;
	private TextView textview4;
	private ImageView imageview1;
	private EditText edittext1;
	private LinearLayout linear14;
	private ImageView imageview2;
	private LinearLayout linear9;
	private LinearLayout linear7;
	private LinearLayout linear12;
	private TextView textview1;
	private HorizontalScrollView hscroll4;
	private LinearLayout linear13;
	private TextView textview2;
	
	private Intent i = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.embedded_code);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		vscroll3 = (ScrollView) findViewById(R.id.vscroll3);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		textview5 = (TextView) findViewById(R.id.textview5);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		textview4 = (TextView) findViewById(R.id.textview4);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		textview1 = (TextView) findViewById(R.id.textview1);
		hscroll4 = (HorizontalScrollView) findViewById(R.id.hscroll4);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		textview2 = (TextView) findViewById(R.id.textview2);
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (list1.get((int)(_position)).equals("Custom")) {
					linear10.setVisibility(View.VISIBLE);
				}
				else {
					linear10.setVisibility(View.GONE);
					_setTextSize(textview1, Double.parseDouble(list1.get((int)(_position))));
					_setTextSize(textview2, Double.parseDouble(list1.get((int)(_position))));
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		textview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), EmbedCodeActivity.class);
				i.putExtra("codeStatus", "available");
				i.putExtra("code", textview2.getText().toString());
				i.putExtra("firstuser", getIntent().getStringExtra("firstuser"));
				i.putExtra("seconduser", getIntent().getStringExtra("seconduser"));
				startActivity(i);
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", textview2.getText().toString()));
				_customText("Source Code copied to clip board");
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext1.getText().toString().length() > 0) {
					if (4 > edittext1.getText().toString().length()) {
						list1.add(edittext1.getText().toString());
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
	}
	private void initializeLogic() {
		setTitle("Source Code Viewer");
		textview2.setText(getIntent().getStringExtra("code"));
		_setHighlighter(textview2);
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
		textview2.setTextIsSelectable(true);
		str = textview2.getText().toString().trim();
		count = 0;
		if (str.length() > 0) {
			count++;
			n = 0;
			for(int _repeat21 = 0; _repeat21 < (int)(str.length()); _repeat21++) {
				if (str.substring((int)(n), (int)(n + 1)).equals("\n")) {
					count++;
				}
				n++;
			}
		}
		linecount = count;
		textview1.setText("");
		n1 = 1;
		for(int _repeat37 = 0; _repeat37 < (int)(linecount); _repeat37++) {
			textview1.setText(textview1.getText().toString().concat(String.valueOf((long)(n1)).concat("\n")));
			n1++;
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
