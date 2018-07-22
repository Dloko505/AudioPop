package com.adulgr.audio.audiopop;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.adulgr.audio.audiopop.db.AudioPopDb;
import com.adulgr.audio.audiopop.fragments.result.ResultSetupFragment;
import com.adulgr.audio.audiopop.fragments.test.TestFragment;
import com.adulgr.audio.audiopop.fragments.setup.SetupFragment;
import com.adulgr.audio.audiopop.fragments.user.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  public static AudioPopDb audioDb;

  private static final int REQUEST_ALL_PERMISSION = 1000;
  private String[] permissions = {
      Manifest.permission.RECORD_AUDIO,
      Manifest.permission.INTERNET
  };

  private void checkPermissions() {
    boolean needPermission = false;
    boolean needRationale = false;
    for (String permission : permissions) {
      needPermission |= ContextCompat.checkSelfPermission(
          this, permission) != PackageManager.PERMISSION_GRANTED;
      needRationale |= ActivityCompat.shouldShowRequestPermissionRationale(
          this, permission);
    }
    if (needPermission) {
      if (needRationale) {
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
      } else {
        // No explanation needed; request the permission
        ActivityCompat.requestPermissions(this, permissions, REQUEST_ALL_PERMISSION);
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_ALL_PERMISSION) {
      if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
        finish();
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    displaySelectedScreen(R.id.nav_setup);

    audioDb = Room.databaseBuilder(getApplicationContext(),
        AudioPopDb.class, "audio_pop_db").build();

//    new AsyncTask<Void, Void, Void>() {
//
//      @Override
//      protected Void doInBackground(Void... voids) {
//        AudioPopDb.getInstance(MainActivity.this).getSetupDao().select();
//        return null;
//      }
//    }.execute();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
//    if (id == R.id.action_logout) {
//      signOut();
//      return true;
//    }

    return super.onOptionsItemSelected(item);
  }

  private void signOut() {
    getSignInApplication().getSignInClient().signOut()
        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            getSignInApplication().setSignInAccount(null);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
          }
        });
  }

  private AudioApplication getSignInApplication() {
    return (AudioApplication) getApplication();
  }

  private void displaySelectedScreen(int itemId) {
    // Create fragment object
    Fragment fragment = null;

    // Initialize the fragment object which is selected
    switch (itemId) {
      case R.id.nav_user:
        signOut();
        break;
      case R.id.nav_setup:
        fragment = new SetupFragment();
        break;
      case R.id.nav_test:
        fragment = new TestFragment();
        break;
      case R.id.nav_results:
        fragment = new ResultSetupFragment();

    }

    // Replace the fragment
    if (fragment != null) {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.main_container, fragment);
      ft.commit();
    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Call the method displaySelectedScreen and pass the id of selected window.
    displaySelectedScreen(item.getItemId());
    //make this method blank
    return true;
  }

}
