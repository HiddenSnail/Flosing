package com.our.flosing.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.our.flosing.R;

/**
 * Created by RunNishino on 2017/1/4.
 */

public class QRCodeActivity extends AppCompatActivity {
    private ImageView QRImageView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_activity);

        Intent intent = getIntent();

        QRImageView = (ImageView) findViewById(R.id.image_barcode);

        String cardID = intent.getStringExtra("cardID");
        try {
            Bitmap QRCodeBitmap = EncodingHandler.createQRCode(cardID, 800);
            QRImageView.setImageBitmap(QRCodeBitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }
}
