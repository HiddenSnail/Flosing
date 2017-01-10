package com.our.flosing.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.our.flosing.R;
import com.our.flosing.bean.LostCard;
import com.our.flosing.model.LostCardModel;
import com.our.flosing.presenter.LostPublishPresenter;

import java.util.Arrays;

/**
 * Created by huangrui on 2017/1/10.
 */

public class TestActivity extends AppCompatActivity {

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.test);

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.scan);

        ImageView imageView = (ImageView) findViewById(R.id.bitmap);
        imageView.setImageBitmap(bitmap);

        LostCard lostCard = new LostCard();
        lostCard.setPics(Arrays.asList(bitmap));

        LostCardModel lostCardModel = new LostCardModel();
        lostCardModel.publishLost(lostCard).subscribe();
    }
}
