package savindev.myuniversity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import savindev.myuniversity.db.DBHelper;
import savindev.myuniversity.schedule.DailyScheduleFragment;
import savindev.myuniversity.settings.SettingsFragment;
import savindev.myuniversity.welcomescreen.FirstStartActivity;

public class MainActivity extends AppCompatActivity {

    static Toolbar toolbar;
    String username;
    String email;
    WelcomeFragment welcomeFragment;
    NewsFragment newsFragment;
    SettingsFragment settingsFragment;
    DailyScheduleFragment dailyScheduleFragment;

   static PrimaryDrawerItem itemSchedule ;
   static PrimaryDrawerItem itemNavigation;
   static PrimaryDrawerItem itemNotes ;
   static PrimaryDrawerItem itemEducation ;
   static PrimaryDrawerItem itemNews ;
   static SecondaryDrawerItem itemSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        if(settings.getBoolean("isFirstStart",true)==true) {

            Intent intent = new Intent(getApplicationContext(), FirstStartActivity.class);
            startActivity(intent);
            finish();
        }



        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        welcomeFragment = new WelcomeFragment();
        newsFragment = new NewsFragment();
        settingsFragment = new SettingsFragment();
        dailyScheduleFragment = new DailyScheduleFragment();

        getUserSettings();
        initDrawer();

        DBHelper dbHelper = new DBHelper(this);
        Log.d("Departments_request",dbHelper.getDepartments(this).toString()) ;
        Log.d("Groups_request",dbHelper.getGroups(this).toString()) ;
        Log.d("Faculties_request",dbHelper.getFaculties(this).toString()) ;
        Log.d("Teachers_request",dbHelper.getTeachers(this).toString()) ;

    }


   private void getUserSettings(){
       SharedPreferences settings = getSharedPreferences("UserInfo", 0);

       username = settings.getString("UserName","no user");
       email = settings.getString("Email","no email");
    }

    void initDrawer(){

         itemSchedule = new PrimaryDrawerItem().withName(R.string.drawer_schedule).withIcon(R.drawable.ic_schedule).withSelectedIcon(R.drawable.ic_schedule_select);
         itemNavigation = new PrimaryDrawerItem().withName(R.string.drawer_navigator).withIcon(R.drawable.ic_navigation).withSelectedIcon(R.drawable.ic_navigation_select);
         itemNotes = new PrimaryDrawerItem().withName(R.string.drawer_notes).withIcon(R.drawable.ic_notes).withSelectedIcon(R.drawable.ic_notes_select);
         itemNews = new PrimaryDrawerItem().withName(R.string.drawer_news).withIcon(R.drawable.ic_news).withBadge("12").withSelectedIcon(R.drawable.ic_news_select);
         itemEducation = new PrimaryDrawerItem().withName(R.string.drawer_education).withIcon(R.drawable.ic_school).withSelectedIcon(R.drawable.ic_school_select);
         itemSettings = new SecondaryDrawerItem().withName(R.string.drawer_settings).withIcon(R.drawable.ic_settings).withSelectedIcon(R.drawable.ic_settings_select);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_header)
                .addProfiles(
                        new ProfileDrawerItem().withName(username).withEmail(email).withIcon(getResources().getDrawable(R.drawable.ic_account_circle_white_48dp))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Вы хотите сменить пользователя?")
                                .setMessage("Смена аккаунта")
                                .setIcon(R.drawable.drawer_header)
                                .setCancelable(false)
                                .setPositiveButton("Да",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent i = new Intent(MainActivity.this,FirstStartActivity.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }).setNegativeButton("Нет",
                                         new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                             }
                                                 });
                        AlertDialog alert = builder.create();
                        alert.show();

                        return false;
                    }
                })
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        itemSchedule,

                        itemNavigation,

                        itemNotes,

                        itemNews,

                        itemEducation,

                        itemSettings,
                        new SecondaryDrawerItem()


                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction;

                        switch (position) {
                            case 1:
                                addfragment(R.string.drawer_schedule,dailyScheduleFragment);
                                break;
                            case 2:
                                addfragment(R.string.drawer_navigator,welcomeFragment);
                                break;
                            case 3:
                                addfragment(R.string.drawer_notes,welcomeFragment);
                                break;
                            case 4:
                                addfragment(R.string.drawer_news,newsFragment);
                                break;
                            case 5:
                                addfragment(R.string.drawer_education,welcomeFragment);
                                break;
                            case 6:
                                addfragment(R.string.drawer_settings,settingsFragment);
                                break;

                        }
                        return false;
                    }
                })
                .build();
    }

    void addfragment(int title,Fragment fragment){

        toolbar.setTitle(title);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction;
        fragmentTransaction=fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.content_main,fragment);
        fragmentTransaction.commit();
        Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();

    }

    //Проверка на подключение к интернету
    public static boolean isNetworkConnected(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }
}
