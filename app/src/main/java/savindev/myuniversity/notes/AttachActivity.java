package savindev.myuniversity.notes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import savindev.myuniversity.MainActivity;
import savindev.myuniversity.R;
import savindev.myuniversity.db.DBHelper;

/**
 * Активити для добавления задания
 */


//CR public? non-modifier? PRIVATE
public class AttachActivity extends AppCompatActivity {


    public static EditText noteName;
    String typeAttach;
    Toolbar toolbar;
    public static int scheduleId;
    public static String date;
    public static String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        noteName = (EditText) findViewById(R.id.noteTitle);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            typeAttach = extras.getString("TypeAttach");//тип добавляемого материала TODO переделать в  NoteType
            scheduleId = extras.getInt("scheduleId", 0);
            date = extras.getString("date", "");
            time = extras.getString("time", "");

        }
        final DBHelper dbHelper = new DBHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper.getNotesHelper().setPairNote(AttachNoteFragment.saveNote());
                Intent reloadActivity = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                MainActivity.mainActivity.finish();
                startActivity(reloadActivity);

            }
        });

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle("");


        switch (typeAttach) {
            // Исходя из полученных данных с Bundle добавляем соответствующий фрагмент
            case "Note":
                addfragment(R.string.title_attach_note, new AttachNoteFragment());
                break;

        }
    }


    void addfragment(int title, Fragment fragment) {
        toolbar.setTitle(title);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.attach_fragment, fragment);
        fragmentTransaction.commit();
    }

}
