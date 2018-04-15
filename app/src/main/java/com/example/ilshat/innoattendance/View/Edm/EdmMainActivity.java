package com.example.ilshat.innoattendance.View.Edm;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ilshat.innoattendance.Presenter.Edm.EdmMainPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.View.Common.StatisticsManagementFragment;
import com.example.ilshat.innoattendance.View.Edm.EducationManagement.EducationManagementFragment;
import com.example.ilshat.innoattendance.View.Edm.EducationManagement.SubjectManagementFragment;
import com.example.ilshat.innoattendance.View.Edm.UserManagement.UserManagementFragment;
import com.example.ilshat.innoattendance.View.OnBackPressedListener;

public class EdmMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EdmMainPresenter presenter;
    ActionBarDrawerToggle toggle;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        navigationView.inflateMenu(R.menu.edm_activity_main_drawer);
        headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(0).setChecked(true));

        presenter = new EdmMainPresenter(this);

    }

    public View getHeaderView() {
        return headerView;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            int num = fragmentManager.getBackStackEntryCount() - 1;
            String fragmentTag = fragmentManager
                    .getBackStackEntryAt(num).getName();
            Fragment last = fragmentManager.findFragmentByTag(fragmentTag);
            if (last instanceof OnBackPressedListener &&
                    ((OnBackPressedListener) last).onBackPressed()) {
                return;
            }
            fragmentManager.popBackStack();
            if (num == 0) {
                reverseToolbar();
            }
        }
    }


    public void setupToolbarNavigation() {
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void reverseToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.setToolbarNavigationClickListener(null);
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
        if (id == R.id.log_out) {
            presenter.logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearFragmentStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
        reverseToolbar();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Class fragmentClass = null;
        Fragment fragment = null;
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_user_management:
                fragmentClass = UserManagementFragment.class;
                break;
            case R.id.nav_education_management:
                fragmentClass = EducationManagementFragment.class;
                break;
            case R.id.nav_statistics_management:
                fragmentClass = StatisticsManagementFragment.class;
                break;
            default:
                presenter.logOut();
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            Bundle args = new Bundle();
            args.putString("title", item.getTitle().toString());
            fragment.setArguments(args);
            clearFragmentStack();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        } catch (Exception e) {
            Log.v(EdmMainActivity.class.getName(), e.getMessage());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
