package com.kpu.kpuindoormap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;


public class MainActivity extends Activity
{


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//menu 버튼 시작.
		//mhome = findViewById(R.id.homepage);

		//메뉴버튼 끝.
		Button b1 = (Button)findViewById(R.id.b1); // 길찾기 서비스
		Button b2 = (Button)findViewById(R.id.b2); // 뉴스 / 이벤트
		Button b3 = (Button)findViewById(R.id.b3); // 문화센터
		Button b4 = (Button)findViewById(R.id.b4); // MyPage
		Button b5 = (Button)findViewById(R.id.b5); // 관리자 권한

		b1.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){ //인텐트 화면넘어가기.
				Intent intent = new Intent(MainActivity.this, NaviActivity.class);
				startActivity(intent);
			}
		});

		b2.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){ //인텐트 화면넘어가기.
				Intent intent = new Intent(MainActivity.this, EventActivity.class);
				startActivity(intent);
			}
		});

		b3.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){ //인텐트 화면넘어가기.
				Intent intent = new Intent(MainActivity.this, CultureActivity.class);
				startActivity(intent);
			}
		});

	}


	//메뉴 버튼 활성화 및 비활성화.
	/*Button.OnClickListener mClickListener = new Button.OnClickListener(){
		public void onClick(View v){
			mhome.setVisibility(View.VISIBLE);  // 홈화면을 바로 뜨게 하기.


		}
	};
	*/
	// 메뉴버튼

}
