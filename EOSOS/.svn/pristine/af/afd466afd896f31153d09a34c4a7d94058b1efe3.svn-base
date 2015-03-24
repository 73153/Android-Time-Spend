package com.eosos.components.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.ViewFlipper;

import com.eosos.common.classes.IjoomerHomeMaster;
import com.eosos.common.classes.IjoomerUtilities;
import com.eosos.common.classes.IjoomerWebviewClient;
import com.eosos.common.configuration.IjoomerGlobalConfiguration;
import com.eosos.common.flipanimation.AnimationFactory;
import com.eosos.customviews.IjoomerTextView;
import com.eosos.src.R;
import com.smart.framework.CustomAlertMagnatic;
import com.smart.framework.CustomAlertNeutral;

public class EososMenuActivity extends IjoomerHomeMaster {

	private static Bitmap imageOriginal;
	private static Matrix matrix;

	private double rotatedDegree = 0.0;

	@SuppressWarnings("unused")
	private boolean isScreenOn = true;

	private ImageView dialer;
	private int dialerHeight, dialerWidth;

	private GestureDetector detector;

	private boolean[] quadrantTouched;

	private boolean allowRotating, isTouchedEnable;

	private ImageView viewCenter;

	public int finalTangent;
	private LinearLayout lnrBrifcase;
	private ViewPager viewPager;
	private ImageView imgLeft, imgRight;
	private MyPagerAdapter adapter;
	private ViewFlipper viewFlipper;
	private ImageView imgHome;
	private IjoomerTextView txtHeader;

	private SeekBar seekRate, seekFaq, seekFeedback, seekTerms;
	private int progress;
	private String faqContent;

	/**
	 * Override Methods
	 */
	@Override
	public void initComponents() {
		dialer = (ImageView) findViewById(R.id.imageView_ring);
		viewCenter = (ImageView) findViewById(R.id.viewCenter);
		lnrBrifcase = (LinearLayout) findViewById(R.id.lnrBrifcase);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		imgLeft = (ImageView) findViewById(R.id.imgLeft);
		imgRight = (ImageView) findViewById(R.id.imgRight);
		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

		imgHome = (ImageView) findViewById(R.id.imgHome);
		txtHeader = (IjoomerTextView) findViewById(R.id.txtHeader);
		seekRate = (SeekBar) findViewById(R.id.seekRate);
		seekFaq = (SeekBar) findViewById(R.id.seekFaq);
		seekFeedback = (SeekBar) findViewById(R.id.seekFeedback);
		seekTerms = (SeekBar) findViewById(R.id.seekTerms);
		faqContent = "<h3>What does EOSOS Elite stand for?</h3>EOSOS Elite specializes in nightlife and entertainment and hence ‘eosos’ (latin) is referring to the dawn after a good night out. The last part, elite, is a reference to the high profile venues we are working with.<br><br><h3>What can I use the application for?</h3>EOSOS Elite created ‘Elite Clubs’ with the vision to provide a global directory of the leading nightclubs across the globe. In today's busy life, we need information quickly and easily. The Elite Clubs provides not only essential information about the world’s leading nightclubs in the most vital cities, but also includes tools to help plan and enjoy your night.<br><br><h3>In which countries can I use the app?</h3>We have venues listed on 5 continents, more than 60 countries and 130 cities globally.<br><br><h3>Can I access the information of this app on any other devices?</h3>Yes, the content of this application can also be downloaded to your iPad and any device that works with Android applications.<br><br><h3>What types of clubs are in the apps’ directory?</h3>We refrain from using the word ‘best’ because everyone’s definition of ‘best’ is different; however, we would venture to say that we have included the world’s most prestigious clubs. We are constantly looking for feedback to maintain an accurate list, as some clubs improve, while others slowly fade away.<br><br><h3>How can I update the application?</h3>As with any other app, styling and functionality updates will be available through the app store. However, content updates are instantaneously drawn from our servers, hence, you never need to update the content – it is always up-to-date.<br><br><h3>Does the app work in offline modus?</h3>This app will work best online; however, main information such as the directory will be ‘cached’ on your phone. Note, the content will not update when you are offline.<br><br><h3>How does the geotagging function work?</h3>You need to allow your phone/tablet to detect your current location. The application will show your point on a map.  Nightclubs from our directory within that region will be shown.<br><br><h3>How can I add a club to my favorites list?</h3>A club can simply be added to the favorite list by clicking the black diamond on the right top of the description page of the club. Once a club has been favored its diamond will appear red and it will appear in the favorite list.<br><br><h3>How does the Route feature work?</h3>The route feature enables you to plan your route to a chosen nightclub. Simply select the start of the journey and the club. You will be able to choose between walking and car.<br><br><h3>How does the Planner feature work?</h3>The planner enables you to look up clubs in cities on particular nights. So, if you are planning on going to Paris on a Wednesday, you can look up which clubs will actually be open that night.<br><br><h3>A club is not yet in the list, where can I recommend it?</h3>We are constantly looking for feedback to have the most comprehensive list possible. You can recommend a club on the settings page of the app. We will review your submission an add it if it meets our criteria.<br><br><h3>How can a club be removed or information be adjusted?</h3>Just as you can suggest adding a club, you can suggest information change or removal. As global nightlife is fast shifting, local feedback is the best way to make sure our list stays current.<br><br><h3>Can I rate a club?</h3>Yes, you can rate a club from one to five ‘globes’ and leave a comment if you like. The globe rating will help identify the clubs, which you should visit in any particular city. The ratings will be reset every now and so often in order to have an up-to-date rating of all clubs.<br><br><h3>How will my rating and feedback be processed?</h3>Feedback directly to us (via settings) will be processed within 48h. Ratings and comments will be instantly posted to the app and can be seen by you and other users straight away.";

		adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);

	}

	@Override
	public void prepareViews() {
		txtHeader.setText(getString(R.string.eosos_elite_clubs_header));
		if (imageOriginal == null) {
			imageOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.homecircle);
		}

		// initialize the matrix only once
		if (matrix == null) {
			matrix = new Matrix();
		} else {
			// not needed, you can also post the matrix immediately to restore
			// the old state
			matrix.reset();
		}

		detector = new GestureDetector(this, new MyGestureDetector());
		// there is no 0th quadrant, to keep it simple the first value gets
		// ignored
		quadrantTouched = new boolean[] { false, false, false, false, false };

		allowRotating = true;
		isTouchedEnable = true;

	}

	@Override
	public void setActionListeners() {
		seekTerms.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(final SeekBar seekBar) {

				setProgreeHandler(seekBar);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress == 100) {
					try {
						try {
							loadNew(IjoomerWebviewClient.class, EososMenuActivity.this, false, "url", "file:///android_asset/terms.html", "IN_FROM",
									getString(R.string.eosos_terms_n_conditions_header));
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
		seekFeedback.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				setProgreeHandler(seekBar);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress == 100) {
					loadNew(EososFeedbackActivity.class, EososMenuActivity.this, false);
				}
			}
		});

		seekFaq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				setProgreeHandler(seekBar);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress == 100) {
					try {
						loadNew(IjoomerWebviewClient.class, EososMenuActivity.this, false, "IN_CONTENT", faqContent, "IN_FROM", getString(R.string.eosos_f_a_q_header));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		seekRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				setProgreeHandler(seekBar);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress == 100) {
					IjoomerUtilities.getCustomConfirmRatingAndFacebookShareDialog(getString(R.string.app_text), getString(R.string.app_rating_text), new CustomAlertMagnatic() {
						@Override
						public void PositiveMethod() {
							final String appPackageName = getPackageName();
							try {
								startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
							} catch (android.content.ActivityNotFoundException anfe) {
								startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
							}
						}

						@Override
						public void NegativeMethod() {
							seekRate.setProgress(0);
						}
					});

				}
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

				if (arg0 == (IjoomerGlobalConfiguration.getSliderList().size() - 1)) {
					imgRight.setVisibility(View.VISIBLE);
					imgLeft.setVisibility(View.INVISIBLE);
				} else if (arg0 == 0) {
					imgLeft.setVisibility(View.VISIBLE);
					imgRight.setVisibility(View.INVISIBLE);
				} else {
					imgRight.setVisibility(View.VISIBLE);
					imgLeft.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		lnrBrifcase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
			}
		});
		imgHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
			}
		});

		viewCenter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					viewCenter.setImageResource(R.drawable.middle_circle_active);
					loadNew(EososNearByEntryListActivity.class, EososMenuActivity.this, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		dialer.setOnTouchListener(new MyOnTouchListener());
		dialer.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// method called more than once, but the values only need to be
				// initialized one time
				if (dialerHeight == 0 || dialerWidth == 0) {
					dialerHeight = dialer.getHeight();
					dialerWidth = dialer.getWidth();
					float translateX = dialerWidth / 2 - imageOriginal.getWidth() / 2;
					float translateY = dialerHeight / 2 - imageOriginal.getHeight() / 2;
					matrix.postTranslate(translateX, translateY);
					// dialer.setImageBitmap(imageScaled);
					dialer.setImageBitmap(imageOriginal); // remove this if you
					dialer.setImageMatrix(matrix);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		dialer.setImageResource(R.drawable.homecircle);
		viewCenter.setImageResource(R.drawable.middle_circle);
		seekRate.setProgress(0);
		seekFaq.setProgress(0);
		seekFeedback.setProgress(0);
		seekTerms.setProgress(0);
		super.onResume();
	}

	public void setProgreeHandler(final SeekBar seekBar) {

		progress = seekBar.getProgress();
		if (progress > 50) {

			new Thread(new Runnable() {

				@Override
				public void run() {

					while (progress < 100) {
						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								progress += 5;
								seekBar.setProgress(progress);
							}
						});
					}

				}
			}).start();

		} else {
			seekBar.setProgress(0);
		}

	}

	/**
	 * Rotate the dialer.
	 * 
	 * @param degrees
	 *            The degrees, the dialer should get rotated.
	 */
	private void rotateDialer(float degrees) {

		matrix.postRotate(degrees, dialerHeight / 2, dialerHeight / 2);
		rotatedDegree += degrees;
		// matrix.postRotate(degrees);
		// if (degrees < 300)
		// rotatedDegree += degrees;
		//
		// if (rotatedDegree > 180) {
		// Log.e("log", rotatedDegree + "");
		// } else if (rotatedDegree > 90) {
		// Log.e("log", rotatedDegree + "");
		// }
		if (rotatedDegree > 359.99)
			rotatedDegree -= 360.00;
		else if (rotatedDegree < -360)
			rotatedDegree += 360.00;

		dialer.setImageMatrix(matrix);

	}

	private void rotateDialerThread(final float degrees, final SmartRotationListener local) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// Log.e("Rotating In thread", "" + degrees);
				if (degrees >= 0) {
					for (int i = 0; i < degrees; i++) {
						// Thread.sleep(2);
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								rotateDialer(1.0f);
							}
						});
					}
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							local.onRotationComplete();
						}
					});
				} else {
					for (int i = Math.abs(Math.round(degrees)); i >= 0; i--) {
						// Thread.sleep(2);
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								rotateDialer(-1.0f);
							}
						});
					}
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							local.onRotationComplete();
						}
					});
				}
				isTouchedEnable = true;
			}
		}).start();
	}

	private void rotateDialerThreadNew(final int speed, final float degrees, final SmartRotationListener local) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// Log.e("Rotating In thread", "" + degrees);
				if (degrees >= 0) {
					for (int i = 0; i < degrees; i++) {
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								rotateDialer(1.0f);
							}
						});
					}
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							local.onRotationComplete();
						}
					});
				} else {
					for (int i = Math.abs(Math.round(degrees)); i >= 0; i--) {
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								rotateDialer(-1.0f);
							}
						});
					}
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							local.onRotationComplete();
						}
					});
				}
				isTouchedEnable = true;
			}
		}).start();
	}

	public double getangleofselectedquadrent(double x, double y) {
		double newangle = Math.toDegrees(Math.atan2(y, x));

		return newangle;

	}

	private static double D2R(double degree) {
		return (degree / 180.0) * Math.PI;
	}

	/**
	 * @return The selected quadrant.
	 */
	private static int getQuadrant(double x, double y) {

		if (x >= 0) {
			return y >= 0 ? 1 : 4;
		} else {
			return y >= 0 ? 2 : 3;
		}
	}

	private float getTranslatedX(float x) {
		return x - (dialerWidth / 2);
	}

	private float getTranslatedY(float y) {
		return y - (dialerHeight / 2);
	}

	private float tX, tY;

	private double convertTangentToDegree(double tangent) {
		if (tangent > 0)
			return tangent;
		else {
			tangent *= -1;
			return (180 - tangent) + 180;
		}
	}

	/**
	 * Simple implementation of an {@link OnTouchListener} for registering the
	 * dialer's touch events.
	 */
	private class MyOnTouchListener implements OnTouchListener {

		private double startAngle;
		private float xStart, yStart;
		private float xEnd, yEnd;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// try {
			// int pixel = 0;
			// int tmpx = (int) event.getX();
			// int tmpy = (int) event.getY();
			//
			// if (tmpx > 0 && dialerHeight > tmpy && dialerWidth > tmpx && tmpy
			// > 0) {
			//
			// pixel = imageOriginal.getPixel(tmpx, tmpy);
			//
			// if (pixel == Color.TRANSPARENT) {
			// return true;
			// }
			// }
			//
			// } catch (Throwable e) {
			// return true;
			// }

			tX = Math.round(getTranslatedX(event.getX()));
			tY = Math.round(getTranslatedY(event.getY()));
			finalTangent = (int) convertTangentToDegree(getangleofselectedquadrent(tX, tY));

			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:

				xStart = event.getX();
				yStart = event.getY();

				// reset the touched quadrants
				for (int i = 0; i < quadrantTouched.length; i++) {
					quadrantTouched[i] = false;
				}

				allowRotating = false;

				// startAngle = getAngle(event.getX(), event.getY());

				startAngle = getangleofselectedquadrent(tX, tY);

				break;

			case MotionEvent.ACTION_MOVE:

				int tmpx = (int) event.getX();
				int tmpy = (int) event.getY();
				// try {
				// if (tmpx <= 0 || tmpx > dialerWidth || tmpy <= 0 || tmpy >
				// dialerHeight) {
				// return true;
				// }
				// } catch (Exception e) {
				//
				// }
				// double currentAngle = getAngle(event.getX(), event.getY());
				double currentAngle = getangleofselectedquadrent(tX, tY);
				// System.out.println("StartAngle : "+startAngle+", currentAngle : "+currentAngle);
				rotateDialer((float) (currentAngle - startAngle));
				startAngle = currentAngle;
				break;

			case MotionEvent.ACTION_UP:

				xEnd = event.getX();
				yEnd = event.getY();

				allowRotating = true;
				break;
			}

			// set the touched quadrant to true
			quadrantTouched[getQuadrant(event.getX() - (dialerWidth / 2), dialerHeight - event.getY() - (dialerHeight / 2))] = true;

			// float meanDistance = (xEnd - xStart) + (yEnd - yStart);
			// meanDistance = (meanDistance > 0) ? meanDistance : (meanDistance
			// * -1);

			// if (meanDistance < 1.1)
			// {
			//
			// }
			detector.onTouchEvent(event);

			return true;
		}
	}

	/**
	 * Simple implementation of a {@link SimpleOnGestureListener} for detecting
	 * a fling event.
	 */
	private class MyGestureDetector extends SimpleOnGestureListener {

		@Override
		public boolean onSingleTapConfirmed(MotionEvent event) {
			executeOnSingleTapConfirmed(event);
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

			// int q1 = getQuadrant(e1.getX() - (dialerWidth / 2), dialerHeight
			// - e1.getY() - (dialerHeight / 2));
			// int q2 = getQuadrant(e2.getX() - (dialerWidth / 2), dialerHeight
			// - e2.getY() - (dialerHeight / 2));
			//
			// if ((q1 == 2 && q2 == 2 && Math.abs(velocityX) <
			// Math.abs(velocityY)) || (q1 == 3 && q2 == 3) || (q1 == 1 && q2 ==
			// 3)
			// || (q1 == 4 && q2 == 4 && Math.abs(velocityX) >
			// Math.abs(velocityY)) || ((q1 == 2 && q2 == 3) || (q1 == 3 && q2
			// == 2))
			// || ((q1 == 3 && q2 == 4) || (q1 == 4 && q2 == 3)) || (q1 == 2 &&
			// q2 == 4 && quadrantTouched[3]) || (q1 == 4 && q2 == 2 &&
			// quadrantTouched[3])) {
			//
			// dialer.post(new FlingRunnable(-1 * (velocityX + velocityY)));
			// Log.e("wrong way", (-1 * (velocityX + velocityY)) + "");
			// } else {
			// dialer.post(new FlingRunnable(velocityX + velocityY));
			// }
			float scrollTheta = vectorToScalarScroll(velocityX, velocityY, e2.getX() - (dialerWidth / 2), e2.getY() - (dialerHeight / 2));
			if (scrollTheta > 3500)
				scrollTheta = 3500;
			else if (scrollTheta < 2500 && scrollTheta > 0)
				scrollTheta = 2500;
			else if (scrollTheta < -3500)
				scrollTheta = -3500;
			else if (scrollTheta > -2500 && scrollTheta < 0)
				scrollTheta = -2500;
			Log.e("scrollTheata", scrollTheta + "");
			dialer.post(new FlingRunnable(scrollTheta));
			return true;
		}

	}

	private static float vectorToScalarScroll(float dx, float dy, float x, float y) {
		// get the length of the vector
		float l = (float) Math.sqrt(dx * dx + dy * dy);

		// decide if the scalar should be negative or positive by finding
		// the dot product of the vector perpendicular to (x,y).
		float crossX = -y;
		float crossY = x;

		float dot = (crossX * dx + crossY * dy);
		float sign = Math.signum(dot);
		Log.e("scalarscroll", (l * sign) + "");
		return l * sign;
	}

	private boolean executeOnSingleTapConfirmed(MotionEvent e1) {

		finalTangent = finalTangent + (int) (rotatedDegree * -1);

		if (finalTangent > 359) {
			finalTangent -= 360;
		}

		if (finalTangent < 0)
			finalTangent = finalTangent + 360;

		System.out.println("Final : " + finalTangent);

		if (finalTangent > 180 && finalTangent <= 270) {
			dialer.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fav));
			Float float1 = new Float(-135.0 + (rotatedDegree * -1.0));
			if (float1 < -180)
				float1 = float1 + 360;
			else if (float1 > 180)
				float1 = float1 - 360;
			Log.e("float", float1 + "");
			// rotateDialerThread(float1, new SmartRotationListener() {
			//
			// @Override
			// public void onRotationComplete() {
			// loadNew(EososFavouritesActivity.class, EososMenuActivity.this,
			// false);
			// }
			//
			// });
			rotateDialerThreadNew(2, float1, new SmartRotationListener() {

				@Override
				public void onRotationComplete() {
					loadNew(EososFavouritesActivity.class, EososMenuActivity.this, false);
				}

			});

		} else if (finalTangent > 270 && finalTangent <= 360) {
			dialer.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.route));
			Float float1 = new Float(135.0 + (rotatedDegree * -1.0));
			if (float1 < -180)
				float1 = float1 + 360;
			else if (float1 > 180)
				float1 = float1 - 360;
			Log.e("float", float1 + "");
			// rotateDialerThread(float1, new SmartRotationListener() {
			//
			// @Override
			// public void onRotationComplete() {
			// loadNew(EososRouteActivity.class, EososMenuActivity.this, false);
			// }
			//
			// });
			rotateDialerThreadNew(2, float1, new SmartRotationListener() {

				@Override
				public void onRotationComplete() {
					loadNew(EososRouteActivity.class, EososMenuActivity.this, false);
				}

			});

		} else if (finalTangent >= 0 && finalTangent <= 90) {
			dialer.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.planner));
			Float float1 = new Float(45.0 + (rotatedDegree * -1.0));
			if (float1 < -180)
				float1 = float1 + 360;
			else if (float1 > 180)
				float1 = float1 - 360;
			Log.e("float", float1 + "");
			// rotateDialerThread(float1, new SmartRotationListener() {
			//
			// @Override
			// public void onRotationComplete() {
			// loadNew(EososPlannerActivity.class, EososMenuActivity.this,
			// false);
			// }
			// });
			rotateDialerThreadNew(2, float1, new SmartRotationListener() {

				@Override
				public void onRotationComplete() {
					loadNew(EososPlannerActivity.class, EososMenuActivity.this, false);
				}

			});

		} else if (finalTangent > 90 && finalTangent <= 180) {
			dialer.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.directory));
			Float float1 = new Float(-45.0 + (rotatedDegree * -1.0));
			if (float1 < -180)
				float1 = float1 + 360;
			else if (float1 > 180)
				float1 = float1 - 360;
			Log.e("float", float1 + "");
			// rotateDialerThread(float1, new SmartRotationListener() {
			//
			// @Override
			// public void onRotationComplete() {
			//
			// loadNew(EososEntryListActivity.class, EososMenuActivity.this,
			// false);
			// }
			//
			// });
			rotateDialerThreadNew(2, float1, new SmartRotationListener() {

				@Override
				public void onRotationComplete() {
					loadNew(EososEntryListActivity.class, EososMenuActivity.this, false);
				}

			});

		}

		return true;
	}

	private void showDialog() {
		IjoomerUtilities.getCustomOkDialog("Information", "Under Construction", getString(R.string.ok), R.layout.ijoomer_ok_dialog, new CustomAlertNeutral() {
			@Override
			public void NeutralMethod() {
			}
		});
	}

	/**
	 * A {@link Runnable} for animating the the dialer's fling.
	 */
	private class FlingRunnable implements Runnable {

		private float velocity;

		public FlingRunnable(float velocity) {
			this.velocity = velocity;
		}

		@Override
		public void run() {
			if (allowRotating && (velocity > 500 || velocity < -500)) {
				// if (velocity > 4000)
				// velocity = 4000;
				// else if (velocity < -4000)
				// velocity = -4000;
				// if (velocity > 2000 || velocity < -2000)
				velocity /= 1.0666F;
				// else
				// velocity /= 1.0444F;

				rotateDialer(velocity / 80);
				// post this instance again
				dialer.post(this);
			}
		}
	}

	@Override
	protected void onDestroy() {
		isScreenOn = false;
		super.onDestroy();
	}

	@Override
	public void loadHeaderComponents() {

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

	}

	@Override
	public int setLayoutId() {
		return R.layout.menu_screen;
	}

	@Override
	public View setLayoutView() {
		return null;
	}

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return IjoomerGlobalConfiguration.getSliderList().size();
		}

		@Override
		public Object instantiateItem(View collection, int position) {
			LayoutInflater inflater = LayoutInflater.from(EososMenuActivity.this);
			View headerView = inflater.inflate(R.layout.eosos_text_slider, null, false);
			IjoomerTextView txtNote = (IjoomerTextView) headerView.findViewById(R.id.txtNote);
			txtNote.setMovementMethod(new ScrollingMovementMethod());
			txtNote.setText(IjoomerGlobalConfiguration.getSliderList().get(position));
			((ViewPager) collection).addView(headerView, 0);
			return headerView;
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			((ViewPager) collection).removeView((View) view);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((View) object);
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

	}
}