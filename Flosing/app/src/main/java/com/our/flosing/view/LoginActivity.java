package com.our.flosing.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.our.flosing.MainActivity;
import com.our.flosing.R;
import com.our.flosing.presenter.LoginPresenter;

/**
 * Created by RunNishino on 2016/12/25.
 */

public class LoginActivity extends AppCompatActivity implements ILoginView {
    static private LoginPresenter loginPresenter;
    private AutoCompleteTextView usernameView;
    private EditText passwordView;
    private ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);

        if (loginPresenter == null) loginPresenter = new LoginPresenter(this);
        loginPresenter.takeView(this);

        usernameView = (AutoCompleteTextView) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);

        loginDialog = new ProgressDialog(LoginActivity.this);
        loginDialog.setMessage("正在登录...");

        loginPresenter.isLogin();

        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if(id == R.id.login || id == EditorInfo.IME_NULL){
                    loginPresenter.login(usernameView.getText().toString(),passwordView.getText().toString());
                    return true;
                }
                return false;
            }
        });

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
    public void updateView() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
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
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        if (loginDialog != null) loginDialog.dismiss();
        super.onDestroy();
        if (isFinishing()) loginPresenter = null;
    }

}
