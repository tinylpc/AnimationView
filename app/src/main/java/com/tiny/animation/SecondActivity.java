package com.tiny.animation;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

/**
 * SecondActivity.java
 * 类的描述信息
 *
 * @author tiny
 * @version 2016/11/23 10:23
 *          CopyRight www.eku001.com
 */
public class SecondActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_layout);
		AnimationView animationView = (AnimationView) findViewById(R.id.animation_view);
		animationView.startAnimation(this);

		TextView tv_test = (TextView) animationView.findViewById(R.id.tv_test);
		TextView tv_test2 = (TextView) findViewById(R.id.tv_test2);
		tv_test2.setOnClickListener(v -> animationView.startAnimation(this));
		tv_test.setOnClickListener(v -> {
			Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
			animationView.destroy();
		});
	}
}
