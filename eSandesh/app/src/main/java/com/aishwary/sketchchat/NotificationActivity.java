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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.AdapterView;

public class NotificationActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private String chatNotifications = "";
	
	private ArrayList<HashMap<String, Object>> maplist_notifier = new ArrayList<>();
	
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear2;
	private ProgressBar progressbar1;
	private ListView listview1;
	
	private DatabaseReference notify = _firebase.getReference("" + chatNotifications + "");
	private ChildEventListener _notify_child_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private Intent intent = new Intent();
	private FirebaseAuth fauth;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private AlertDialog.Builder dialog;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.notification);
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
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		listview1 = (ListView) findViewById(R.id.listview1);
		fauth = FirebaseAuth.getInstance();
		dialog = new AlertDialog.Builder(this);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				intent.setClass(getApplicationContext(), ChatActivity.class);
				intent.putExtra("firstuser", FirebaseAuth.getInstance().getCurrentUser().getUid());
				intent.putExtra("seconduser", maplist_notifier.get((int)_position).get("sender").toString());
				startActivity(intent);
			}
		});
		
		_notify_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				progressbar1.setVisibility(View.VISIBLE);
				notify.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						maplist_notifier = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								maplist_notifier.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(maplist_notifier));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
						progressbar1.setVisibility(View.GONE);
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
				notify.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						maplist_notifier = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								maplist_notifier.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(maplist_notifier));
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
				_customText("Selected Notification Cleared");
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				_customText(_errorMessage);
			}
		};
		notify.addChildEventListener(_notify_child_listener);
		
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
		setTitle("Notifications");
		notify.removeEventListener(_notify_child_listener);
		chatNotifications = "users/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()).concat("/".concat("notifications"));
		notify = _firebase.getReference(chatNotifications);
		notify.addChildEventListener(_notify_child_listener);
		progressbar1.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this).colors(new int[]{0xFFC93437, 0xFF375BF1, 0xFFF7D23E, 0xFF34A350}).build());
		linear5.setVisibility(View.VISIBLE);
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
				_v = _inflater.inflate(R.layout.notifier, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final TextView username = (TextView) _v.findViewById(R.id.username);
			final LinearLayout linear5 = (LinearLayout) _v.findViewById(R.id.linear5);
			final LinearLayout linear2 = (LinearLayout) _v.findViewById(R.id.linear2);
			final TextView sent_you_a_message = (TextView) _v.findViewById(R.id.sent_you_a_message);
			final LinearLayout linear6 = (LinearLayout) _v.findViewById(R.id.linear6);
			final ImageView delete_button = (ImageView) _v.findViewById(R.id.delete_button);
			final TextView at = (TextView) _v.findViewById(R.id.at);
			final TextView time = (TextView) _v.findViewById(R.id.time);
			
			if (maplist_notifier.get((int)_position).containsKey("senderNickname")) {
				username.setText(maplist_notifier.get((int)_position).get("senderNickname").toString());
			}
			if (maplist_notifier.get((int)_position).containsKey("time")) {
				time.setText(maplist_notifier.get((int)_position).get("time").toString());
			}
			else {
				linear2.setVisibility(View.GONE);
			}
			if (maplist_notifier.get((int)_position).containsKey("notification_key")) {
				delete_button.setVisibility(View.VISIBLE);
				delete_button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						notify.child(maplist_notifier.get((int)_position).get("notification_key").toString()).removeValue();
					}
				});
			}
			else {
				delete_button.setVisibility(View.GONE);
			}
			Animation animation; animation = AnimationUtils.loadAnimation( getApplicationContext(), android.R.anim.slide_in_left ); animation.setDuration(700); linear1.startAnimation(animation); animation = null;
			
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
