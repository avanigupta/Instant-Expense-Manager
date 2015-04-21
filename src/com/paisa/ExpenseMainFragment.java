//package com.paisa;
//
//import java.util.LinkedList;
//import java.util.Queue;
//
//import com.actionbarsherlock.app.SherlockFragment;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.Point;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.RelativeLayout;
//
//public class ExpenseMainFragment extends SherlockFragment implements OnTouchListener {
//
//	private RelativeLayout drawingLayout;
//	Context mContext;
//	Activity mActivity;
//	private MyView myView;
//	Button red, blue, yellow;
//	Paint paint;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		
//		mActivity = getActivity();
//		mContext = getActivity().getApplicationContext();
//
//		super.onCreate(savedInstanceState);
//	}
//
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//
//		View view = inflater.inflate(R.layout.expense_on_home, container, false);
//
//		myView = new MyView(mContext);
//		drawingLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);
//		drawingLayout.addView(myView);
//
//		red = (GridView) view.findViewById(R.id.expenseGrid);
//		blue = (Button) view.findViewById(R.id.btn_blue);
//		yellow = (Button) view.findViewById(R.id.btn_yellow);
//
//		red.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				paint.setColor(Color.RED);
//			}
//		});
//
//		yellow.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				paint.setColor(Color.YELLOW);
//			}
//		});
//		blue.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				paint.setColor(Color.BLUE);
//			}
//		});
//
//
//		return super.onCreateView(inflater, container, savedInstanceState);
//
//
//	}
//
//
//	public class MyView extends View {
//
//		private Path path;
//		Bitmap mBitmap;
//		ProgressDialog pd;
//		final Point p1 = new Point();
//		Canvas canvas;
//
//		// Bitmap mutableBitmap ;
//		public MyView(Context context) {
//			super(context);
//
//			paint = new Paint();
//			paint.setAntiAlias(true);
//			pd = new ProgressDialog(context);
//			paint.setStyle(Paint.Style.STROKE);
//			paint.setStrokeJoin(Paint.Join.ROUND);
//			paint.setStrokeWidth(5f);
//			mBitmap = BitmapFactory.decodeResource(getResources(),
//					R.drawable.cartoon).copy(Bitmap.Config.ARGB_8888, true);
//
//			this.path = new Path();
//		}
//
//		@Override
//		protected void onDraw(Canvas canvas) {
//			this.canvas = canvas;
//			paint.setColor(Color.GREEN);
//			canvas.drawBitmap(mBitmap, 0, 0, paint);
//
//		}
//
//		@Override
//		public boolean onTouchEvent(MotionEvent event) {
//			float x = event.getX();
//			float y = event.getY();
//			switch (event.getAction()) {
//			case MotionEvent.ACTION_DOWN:
//
//				p1.x = (int) x;
//				p1.y = (int) y;
//				final int sourceColor = mBitmap.getPixel((int) x, (int) y);
//				final int targetColor = paint.getColor();
//				new TheTask(mBitmap, p1, sourceColor, targetColor).execute();
//				invalidate();
//			}
//			return true;
//		}
//
//		public void clear() {
//			path.reset();
//			invalidate();
//		}
//
//		public int getCurrentPaintColor() {
//			return paint.getColor();
//		}
//
//		class TheTask extends AsyncTask<Void, Integer, Void> {
//
//			Bitmap bmp;
//			Point pt;
//			int replacementColor, targetColor;
//
//			public TheTask(Bitmap bm, Point p, int sc, int tc) {
//				this.bmp = bm;
//				this.pt = p;
//				this.replacementColor = tc;
//				this.targetColor = sc;
//				pd.setMessage("Filling....");
//				pd.show();
//			}
//
//			@Override
//			protected void onPreExecute() {
//				pd.show();
//
//			}
//
//			@Override
//			protected void onProgressUpdate(Integer... values) {
//
//			}
//
//			@Override
//			protected Void doInBackground(Void... params) {
//				FloodFill f = new FloodFill();
//				f.floodFill(bmp, pt, targetColor, replacementColor);
//				return null;
//			}
//
//			@Override
//			protected void onPostExecute(Void result) {
//				pd.dismiss();
//				invalidate();
//			}
//		}
//	}
//
//	// flood fill
//
//	public class FloodFill {
//		public void floodFill(Bitmap image, Point node, int targetColor,
//				int replacementColor) {
//			int width = image.getWidth();
//			int height = image.getHeight();
//			int target = targetColor;
//			int replacement = replacementColor;
//			if (target != replacement) {
//				Queue<Point> queue = new LinkedList<Point>();
//				do {
//
//					int x = node.x;
//					int y = node.y;
//					while (x > 0 && image.getPixel(x - 1, y) == target) {
//						x--;
//
//					}
//					boolean spanUp = false;
//					boolean spanDown = false;
//					while (x < width && image.getPixel(x, y) == target) {
//						image.setPixel(x, y, replacement);
//						if (!spanUp && y > 0
//								&& image.getPixel(x, y - 1) == target) {
//							queue.add(new Point(x, y - 1));
//							spanUp = true;
//						} else if (spanUp && y > 0
//								&& image.getPixel(x, y - 1) != target) {
//							spanUp = false;
//						}
//						if (!spanDown && y < height - 1
//								&& image.getPixel(x, y + 1) == target) {
//							queue.add(new Point(x, y + 1));
//							spanDown = true;
//						} else if (spanDown && y < height - 1
//								&& image.getPixel(x, y + 1) != target) {
//							spanDown = false;
//						}
//						x++;
//					}
//				} while ((node = queue.poll()) != null);
//			}
//		}
//	}
//
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//
//
