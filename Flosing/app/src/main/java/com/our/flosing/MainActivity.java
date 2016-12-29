package com.our.flosing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.avos.avoscloud.AVUser;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.LostCardAdapter;
import com.our.flosing.view.LoginActivity;
import com.our.flosing.view.LostPublishActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private List<LostCard> lostCards = new ArrayList<LostCard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LostCardAdapter lostCardAdapter = new LostCardAdapter(MainActivity.this, R.layout.lostcard_item, lostCards);
        ListView listView = (ListView) findViewById(R.id.listview_lostcards);
        listView.setAdapter(lostCardAdapter);

        initLostCards();

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser.getCurrentUser().logOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                MainActivity.this.finish();
            }
        });

        final Button lostPublish = (Button) findViewById(R.id.lost_publish);
        lostPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LostPublishActivity.class));
            }
        });
    }

    private void initLostCards() {
        LostCard lostCard1 = new LostCard();
        lostCard1.setTitle("title");
        lostCard1.setName("wallet");
        lostCard1.setType("钱包");
        lostCard1.setSDate(new Date());
        lostCard1.setEDate(new Date());
        lostCards.add(lostCard1);
        LostCard lostCard2 = new LostCard();
        lostCards.add(lostCard2);
        LostCard lostCard3 = new LostCard();
        lostCards.add(lostCard3);
        LostCard lostCard4 = new LostCard();
        lostCards.add(lostCard4);
        LostCard lostCard5 = new LostCard();
        lostCards.add(lostCard5);
        LostCard lostCard6 = new LostCard();
        lostCards.add(lostCard6);
        LostCard lostCard7 = new LostCard();
        lostCards.add(lostCard7);
        LostCard lostCard8 = new LostCard();
        lostCards.add(lostCard8);
        LostCard lostCard9 = new LostCard();
        lostCards.add(lostCard9);
        LostCard lostCard10 = new LostCard();
        lostCards.add(lostCard10);
        LostCard lostCard11 = new LostCard();
        lostCards.add(lostCard11);
    }
}
