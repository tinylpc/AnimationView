package com.tiny.animation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn_btt = (Button) findViewById(R.id.btn_btt);
		btn_btt.setOnClickListener(v -> startActivity(new Intent(this, SecondActivity.class)));
	}
}
