package com.kpu.kpuindoormap;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.kpu.kpuindoormap.bookitems.IconTextItem;
import com.kpu.kpuindoormap.bookitems.IconTextListAdapter;


public class InfoActivity extends Activity {

    //ImageView image_1;

    ListView listView;
    IconTextListAdapter adapter;
    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //image_1 = (ImageView) findViewById(R.id.image_1);
        // 리스트뷰 객체 참조
        listView = (ListView) findViewById(R.id.listView);
        // 어댑터 객체 생성
        adapter = new IconTextListAdapter(this);

        close = (Button) findViewById(R.id.close);

        //Resources res = getResources();// 아이템 데이터 만들기

        // 서점 책 목록,

        int listName = getIntent().getIntExtra("LIST_NAME", 0);
        switch (listName){    // 첫번째 컴퓨터 공학부.
            case R.id.btn_1:
                Resources res = getResources();
                adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.book_c), "c programing", "hanbit", "22000"));
                adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.book_data), "data structure", "orange", "27000"));
                adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.book_structure), "principle of computer structure 2.0", "hanbit", "25000"));
                adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.book_jsp), "JSP & Servlet", "hanbit", "27000"));

                //image_1.setImageResource(R.drawable.ic_launcher);
                break;
            case R.id.btn_2:
                //image_1.setImageResource(R.drawable.ic_launcher);
                break;

            case R.id.btn_3:
                //image_1.setImageResource(R.drawable.ic_launcher);
                break;

            case R.id.btn_4:
                //image_1.setImageResource(R.drawable.ic_launcher);
                break;

            default:
                break;

        }

        // 리스트뷰에 어댑터 설정
        listView.setAdapter(adapter);

        // 새로 정의한 리스너로 객체를 만들어 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IconTextItem curItem = (IconTextItem) adapter.getItem(position);
                String[] curData = curItem.getData();

                Toast.makeText(getApplicationContext(), "Selected : " + curData[0], Toast.LENGTH_LONG).show();

            }

        });

    }

    public void InfoClicked (View v) {

        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
