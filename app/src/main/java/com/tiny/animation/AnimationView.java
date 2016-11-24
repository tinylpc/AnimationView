package com.tiny.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * AnimationView.java
 * 类的描述信息
 *
 * @author tiny
 * @version 2016/11/23 10:26
 */
public class AnimationView extends RelativeLayout {

	private View bacggroundView;
	private View contentView;
	private int background;
	private int duration;
	private int contentId;

	/**
	 * 是否正在进行进入动画
	 */
	private boolean isShowingAnimation = false;

	/**
	 * 是否正在进行退出动画
	 */
	private boolean isDismissingAnimation = false;

	/**
	 * 是否正在展示
	 */
	private boolean isShowing = false;

	public AnimationView(Context context) {

		this(context, null);
	}

	public AnimationView(Context context, AttributeSet attrs) {

		this(context, attrs, 0);
	}

	public AnimationView(Context context, AttributeSet attrs, int defStyleAttr) {

		super(context, attrs, defStyleAttr);

		final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.animatinview);
		background = typedArray.getColor(R.styleable.animatinview_background_color, getResources()
				.getColor(R.color.hard_grey));
		contentId = typedArray.getResourceId(R.styleable.animatinview_content, R.layout.test_layout);
		duration = typedArray.getInt(R.styleable.animatinview_duration, 1000);
		typedArray.recycle();
	}

	/**
	 * 开始内容显示动画
	 * @param context 上下文
	 */
	public void startAnimation(Context context) {

		if (!isShowing && !isShowingAnimation) {
			isShowingAnimation = true;
			setVisibility(View.VISIBLE);
			if (bacggroundView == null) {
				bacggroundView = new View(context);
				bacggroundView.setBackgroundColor(background);
				bacggroundView.setOnClickListener(v -> destroy());
			}
			addView(bacggroundView);
			ObjectAnimator animator = ObjectAnimator.ofFloat(bacggroundView, "alpha", 0f, 1f).setDuration(duration);
			animator.addListener(new Animator.AnimatorListener() {

				@Override
				public void onAnimationStart(Animator animation) {

				}

				@Override
				public void onAnimationEnd(Animator animation) {

					isShowing = true;
					isShowingAnimation = false;
				}

				@Override
				public void onAnimationCancel(Animator animation) {

				}

				@Override
				public void onAnimationRepeat(Animator animation) {

				}
			});
			animator.start();

			if (contentView == null) {
				contentView = LayoutInflater.from(context).inflate(contentId, this, false);
			}
			LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
			lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			addView(contentView, lp);

			// 屏蔽内容区域点击也会移除视图
			contentView.setOnClickListener(v -> {
			});

			contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {

					ObjectAnimator.ofFloat(contentView, "translationY", contentView.getHeight(), 0)
							.setDuration(duration).start();
					getViewTreeObserver().removeGlobalOnLayoutListener(this);
				}
			});
		}
	}

	public void destroy() {

		if (isShowing && !isDismissingAnimation) {
			isDismissingAnimation = true;
			ObjectAnimator.ofFloat(bacggroundView, "alpha", 1f, 0f).setDuration(duration).start();
			ObjectAnimator animator = ObjectAnimator.ofFloat(contentView, "translationY", 0, contentView.getHeight())
					.setDuration(duration);
			animator.addListener(new Animator.AnimatorListener() {

				@Override
				public void onAnimationStart(Animator animation) {

				}

				@Override
				public void onAnimationEnd(Animator animation) {

					isShowing = false;
					isDismissingAnimation = false;
					removeAllViews();
					setVisibility(View.GONE);
				}

				@Override
				public void onAnimationCancel(Animator animation) {

				}

				@Override
				public void onAnimationRepeat(Animator animation) {

				}
			});
			animator.start();
		}
	}
}
