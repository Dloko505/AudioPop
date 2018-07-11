package com.adulgr.audio.audiopop.fragments.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.adulgr.audio.audiopop.AudioApplication;
import com.adulgr.audio.audiopop.MainActivity;
import com.adulgr.audio.audiopop.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

  private static final int REQUEST_CODE = 1000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    SignInButton signIn = findViewById(R.id.sign_in);
    signIn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        signIn();
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    if (account != null) {
      getAudioApplication().setSignInAccount(account);
      switchToSetup();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE) {

      try {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        GoogleSignInAccount account = task.getResult(ApiException.class);
        getAudioApplication().setSignInAccount(account);
        switchToSetup();
      } catch (ApiException e) {
        throw new RuntimeException(); //FIXME Recover more gracefully.
      }
    }
  }

  private void signIn() {
    Intent intent = getAudioApplication().getSignInClient().getSignInIntent();
    startActivityForResult(intent, REQUEST_CODE);
  }

  private void switchToSetup() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  private AudioApplication getAudioApplication() {
    return (AudioApplication) getApplication();
  }

}
