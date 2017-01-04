package com.our.flosing.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.our.flosing.R;

/**
 * Created by RunNishino on 2017/1/3.
 */

public class PersonPageActivity extends AppCompatActivity {
    PersonLostFragment personLostFragment;

    Button logout;
    Button lost_list;
    Button found_list;

    TextView usernameView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personpage);

        usernameView = (TextView) findViewById(R.id.username_personPage);
        usernameView.setText("你好," + AVUser.getCurrentUser().getUsername());


        lost_list = (Button) findViewById(R.id.lost_person);
        //退出按钮
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser.getCurrentUser().logOut();
                startActivity(new Intent(PersonPageActivity.this,LoginActivity.class));
                PersonPageActivity.this.finish();
            }
        });

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        personLostFragment = new PersonLostFragment();
        fragmentTransaction.replace(R.id.personPage_content,personLostFragment);
        fragmentTransaction.commit();

        fragmentTransaction = fragmentManager.beginTransaction();

        lost_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (personLostFragment == null){
                    personLostFragment = new PersonLostFragment();
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.personPage_content,personLostFragment);
                fragmentTransaction.commit();
            }
        });

    }
}
