package com.our.flosing.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.google.zxing.client.android.CaptureActivity;
import com.our.flosing.R;
import com.our.flosing.qrcode.FindLostHandler;

/**
 * Created by huangrui on 2016/12/29.
 */

public class MainActivity extends AppCompatActivity implements IBaseView {
    LostCardFragment lostCardFragment;
    FoundCardFragment foundCardFragment;

    private Button lost_list;
    private Button find_list;
    private ImageButton search;
    private ImageButton scan;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        lost_list = (Button) findViewById(R.id.lost_list);
        find_list = (Button) findViewById(R.id.find_list);
        search = (ImageButton) findViewById(R.id.search);
        scan = (ImageButton) findViewById(R.id.scan);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

//        默认fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        lostCardFragment = new LostCardFragment();
        fragmentTransaction.replace(R.id.content,lostCardFragment);
        fragmentTransaction.commit();

        fragmentTransaction = fragmentManager.beginTransaction();


        lost_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lostCardFragment == null) {
                    lostCardFragment = new LostCardFragment();
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content,lostCardFragment);
                fragmentTransaction.commit();

                lost_list.setTextColor(0xff2EB872);
                find_list.setTextColor(0xffffffff);
                lost_list.setBackgroundResource(R.drawable.shape_corner_left_white);
                find_list.setBackgroundResource(R.drawable.shape_corner_right_green);
            }
        });

        find_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foundCardFragment == null) {
                    foundCardFragment = new FoundCardFragment();
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, foundCardFragment);
                fragmentTransaction.commit();

                lost_list.setTextColor(0xffffffff);
                find_list.setTextColor(0xff2EB872);
                lost_list.setBackgroundResource(R.drawable.shape_corner_left_green);
                find_list.setBackgroundResource(R.drawable.shape_corner_right_white);
            }
        });

//        lostCardFragment = (LostCardFragment) getSupportFragmentManager().findFragmentById(R.id.content);
//        listView = lostCardFragment.getPullToRefreshListView();
//
//        listView = (PullToRefreshListView) findViewById(R.id.listview_lostcards);

        ImageButton person = (ImageButton) findViewById(R.id.person);
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // AVUser.logOut();
                startActivity(new Intent(MainActivity.this, PersonPageActivity.class));
            }
        });

        //Lost:寻物启事
        final ImageButton lostPublish = (ImageButton) findViewById(R.id.lost_publish);
        lostPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LostPublishActivity.class));
            }
        });

        //Found:失物招领
        final ImageButton foundPublish = (ImageButton) findViewById(R.id.found_publish);
        foundPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoundPublishActivity.class));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            FindLostHandler handler = new FindLostHandler(MainActivity.this);
            handler.handleCode(scanResult);
        }
    }

    public void onSuccess(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void onFailure(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}