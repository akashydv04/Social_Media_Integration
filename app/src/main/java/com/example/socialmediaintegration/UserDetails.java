package com.example.socialmediaintegration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetails extends AppCompatActivity {
    private CircleImageView imageView;
    private TextView txtName, txtEmail;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        imageView = findViewById(R.id.circleImageView);
        txtName = findViewById(R.id.textView);
        txtEmail = findViewById(R.id.textView2);
        logoutbtn = findViewById(R.id.button2);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null){
            Picasso.with(this).load(user.getPhotoUrl()).into(imageView);
            txtName.setText(user.getDisplayName());
            txtEmail.setText(user.getEmail());
        }

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDetails.this)
                        .setTitle("Logout")
                        .setMessage("Sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SessionManager<TwitterSession> sessionManager = TwitterCore.getInstance().getSessionManager();
                                if (sessionManager.getActiveSession() != null) {
                                    sessionManager.clearActiveSession();
                                    FirebaseAuth.getInstance().signOut();
                                }
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();

                                FirebaseUser users = mAuth.getCurrentUser();
                                updateUI(users);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });


    }

    private void updateUI(FirebaseUser user){
        if (user == null){
            startActivity(new Intent(UserDetails.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(user);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}