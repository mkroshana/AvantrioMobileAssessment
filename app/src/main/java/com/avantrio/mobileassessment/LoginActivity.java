package com.avantrio.mobileassessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avantrio.mobileassessment.models.Model;
import com.avantrio.mobileassessment.models.UserToken;
import com.avantrio.mobileassessment.models.api.AbstractAPIListener;

public class LoginActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        //startActivity(intent);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        EditText txtInEmail = findViewById(R.id.txtEmail);
        EditText txtInPassword = findViewById(R.id.txtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String strEmail = txtInEmail.getText().toString();
                String strPassword = txtInPassword.getText().toString();

                if (strEmail.matches("") || strPassword.matches(""))
                {
                    Toast.makeText(LoginActivity.this, "Email or Password is empty, check again!" + strPassword, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Model model = Model.getInstance(LoginActivity.this.getApplication());
                    model.login(strEmail,strPassword, new AbstractAPIListener()
                    {
                        @Override
                        public void onLogin (UserToken userToken)
                        {
                            model.setUserToken(userToken);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            //Toast.makeText(LoginActivity.this, model.getUserToken().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}