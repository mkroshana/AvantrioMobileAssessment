package com.avantrio.mobileassessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avantrio.mobileassessment.Models.AuthenticationModel;
import com.avantrio.mobileassessment.Models.UserToken;
import com.avantrio.mobileassessment.Models.api.AbstractAPIListener;

public class LoginActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        EditText txtInEmail = findViewById(R.id.txtEmail);
        EditText txtInPassword = findViewById(R.id.txtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view ->
        {
            String strEmail = txtInEmail.getText().toString();
            String strPassword = txtInPassword.getText().toString();

            if (strEmail.matches("") || strPassword.matches(""))
            {
                Toast.makeText(LoginActivity.this,
                        "Email or Password is empty, check again!" + strPassword,
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                AuthenticationModel authenticationModel = AuthenticationModel.getInstance(LoginActivity.this.getApplication());
                authenticationModel.login(strEmail,strPassword, new AbstractAPIListener()
                {
                    @Override
                    public void onLogin (UserToken userToken)
                    {
                        authenticationModel.setUserToken(userToken);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}