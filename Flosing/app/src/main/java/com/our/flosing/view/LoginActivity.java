package com.our.flosing.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.our.flosing.R;
import com.our.flosing.presenter.LoginPresenter;

/**
 * Created by RunNishino on 2016/12/25.
 */

public class LoginActivity extends AppCompatActivity implements ILoginView {
    static private LoginPresenter loginPresenter;
    private Context context;
    private AutoCompleteTextView usernameView;
    private EditText passwordView;
    private ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        if (loginPresenter == null) loginPresenter = new LoginPresenter(this);

        usernameView = (AutoCompleteTextView) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);

        loginDialog = new ProgressDialog(context);
        loginDialog.setMessage("正在登录...");

        findViewById(R.id.username_login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login(usernameView.getText().toString(), passwordView.getText().toString());
            }
        });

        findViewById(R.id.username_register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (loginDialog != null) loginDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public void updateView() {

    }

    @Override
    public void showProgressDialog() {
        loginDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        loginDialog.hide();
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

//    private void attemptLogin(){
//        usernameView.setError(null);
//        passwordView.setError(null);
//
//        final String username = usernameView.getText().toString();
//        final String password = passwordView.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        //判断密码是否合法（大于4位）
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)){
//            passwordView.setError("密码需大于4位");
//            focusView = passwordView;
//            cancel = true;
//        }
//        //判断用户名是否合法
//        if (TextUtils.isEmpty(username)){
//             usernameView.setError("用户名必须填写");
//            focusView = usernameView;
//            cancel = true;
//        }
//
//        //判断是否能够登录 不能登录则在第一处错误的地方重置光标
//        if (cancel){
//            focusView.requestFocus();
//        }else{
//            //LeanCloud登录逻辑
//            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
//                @Override
//                public void done(AVUser avUser, AVException e) {
//                    if (e==null){
//                        LoginActivity.this.finish();
//                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                    }else{
//                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
//
    //        //判断是否已经登录，如果已经登录则进入主活动
//        if (AVUser.getCurrentUser() != null){
//            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//            LoginActivity.this.finish();
//        }

//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("登录");
//        usernameView = (AutoCompleteTextView) findViewById(R.id.username);
//        passwordView = (EditText) findViewById(R.id.password);

//        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL){
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });
//        Button usernameLoginButton = (Button) findViewById(R.id.username_login_button);
//        Button usernameRegisterButton = (Button) findViewById(R.id.username_register_button);
//
//        usernameLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptLogin();
//            }
//        });
//        usernameRegisterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
//                LoginActivity.this.finish();
//            }
//        });
}
