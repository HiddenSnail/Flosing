package com.our.flosing.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.our.flosing.MainActivity;
import com.our.flosing.R;
import com.our.flosing.presenter.RegisterPresenter;


/**
 * Created by RunNishino on 2016/12/26.
 */

public class RegisterActivity extends AppCompatActivity implements IRegisterView {
    static private RegisterPresenter registerPresenter;
    private Context context;
    private AutoCompleteTextView usernameView;
    private AutoCompleteTextView emailView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);

        context = this;
        if (registerPresenter == null) registerPresenter = new RegisterPresenter(this);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        usernameView = (AutoCompleteTextView) findViewById(R.id.username);
        emailView = (AutoCompleteTextView) findViewById(R.id.email);
        passwordView = (EditText) findViewById(R.id.password);

        final Button usernameSignButton = (Button) findViewById(R.id.username_register_button);
        usernameSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameView.getText().toString();
                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();
                registerPresenter.register(username, email, password);
            }
        });
    }

    @Override
    public void updateView() {
        Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }
}
