package kr.saintdev.pdiary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import android.widget.Toast;
import kr.saintdev.pdiary.views.fragments.main.CalenderFragment;
import kr.saintdev.pdiary.views.fragments.main.DiaryFragment;
import kr.saintdev.pdiary.views.fragments.main.MemoFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.main_nav_diary:
                            replaceFragment(0);
                            return true;
                        case R.id.main_nav_memo:
                            replaceFragment(1);
                            return true;
                        case R.id.main_nav_calender:
                            replaceFragment(2);
                            return true;
                    }

                    return false;
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.navigation);
        nav.setOnNavigationItemSelectedListener(this.listener);
        replaceFragment(0);
    }

    private long lastPressTime = 0;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastPressTime < 3000) {
            finish();
        } else {
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            lastPressTime = System.currentTimeMillis();
        }
    }

    /**
     * Fragment mapping.
     */
    private Fragment[] fragmentArray = new Fragment[]{
            new DiaryFragment(), new MemoFragment(), new CalenderFragment() };

    private void replaceFragment(int idx) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.main_container, fragmentArray[idx]);
        trans.addToBackStack(null);
        trans.commit();
    }
}
