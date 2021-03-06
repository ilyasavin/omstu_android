package savindev.myuniversity.welcomescreen;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import savindev.myuniversity.MainActivity;
import savindev.myuniversity.R;
import savindev.myuniversity.serverTasks.GetUniversityInfoTask;

public class FirstStartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignin;
    private Button btnSkip;
    AuthorizationFragment authorizationFragment;
    LinearLayout buttons;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new GetUniversityInfoTask(getBaseContext(), null).execute();

        setContentView(R.layout.activity_first_start);
        buttons = (LinearLayout) findViewById(R.id.buttonsLayout);
        icon = (ImageView) findViewById(R.id.icon);
        buttons.animate();

        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnSkip = (Button) findViewById(R.id.btnSkip);

        btnSignin.setOnClickListener(this);
        btnSkip.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignin:
                //SlideToDown();
                btnSignin.setVisibility(View.GONE);
                btnSkip.setWidth(100);
                ResizeIcon();
                authorizationFragment = new AuthorizationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
                transaction.replace(R.id.login_fragment, authorizationFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.btnSkip:
                SharedPreferences settings = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                settings.edit().putBoolean("isFirstStart", false);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("isFirstStart", false);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                this.finish();
                startActivity(intent);
                break;
        }
    }


    public void ResizeIcon() {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(icon,
                PropertyValuesHolder.ofFloat("scaleX", 0.8f),
                PropertyValuesHolder.ofFloat("scaleY", 0.8f));
        scaleDown.setDuration(500);
        scaleDown.start();
    }

    public void SlideToDown() {

        Animation slide = null;
        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 5.2f);

        slide.setDuration(500);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        buttons.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                buttons.setVisibility(View.GONE);

            }

        });
    }
}