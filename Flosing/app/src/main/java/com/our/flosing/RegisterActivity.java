package com.our.flosing;

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


/**
 * Created by RunNishino on 2016/12/26.
 */

public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView usernameView;
    private AutoCompleteTextView emailView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //注册逻辑代码
        usernameView = (AutoCompleteTextView) findViewById(R.id.username);
        emailView = (AutoCompleteTextView) findViewById(R.id.email);
        passwordView = (EditText) findViewById(R.id.password);

        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register || id == EditorInfo.IME_NULL){
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button usernameSignButton = (Button) findViewById(R.id.username_register_button);
        usernameSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }

    //向LeanCloud注册用户
    private void attemptRegister(){
        usernameView.setError(null);
        emailView.setError(null);
        passwordView.setError(null);

        String username = usernameView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //判断填写信息是否合法
        if(!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError("密码必须大于4位");
            focusView = passwordView;
            cancel = true;
        }
        if(!TextUtils.isEmpty(email) && !isEmailValid(email)){
            emailView.setError("请输入正确的邮箱");
            focusView = emailView;
            cancel = true;
        }
        if(TextUtils.isEmpty(username)){
            usernameView.setError("用户名必须填写");
            focusView = usernameView;
            cancel = true;
        }

        if (cancel){
            focusView.requestFocus();
        }else{
            AVUser user = new AVUser();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e==null){
                        //注册成功，并且currentUser设为注册的用户
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        RegisterActivity.this.finish();
                    }else{
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    private boolean isPasswordValid(String password){
        return password.length() > 4;
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
