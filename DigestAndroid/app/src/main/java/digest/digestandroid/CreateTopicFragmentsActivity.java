package digest.digestandroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import digest.digestandroid.fragments.TopicAddDescriptionFragment;
import digest.digestandroid.fragments.TopicAddMaterialFragment;
import digest.digestandroid.fragments.TopicAddQuizFragment;
import digest.digestandroid.fragments.TopicMaterialFragment;

public class CreateTopicFragmentsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private MyViewPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic_fragments);

        toolbar = (Toolbar) findViewById(R.id.toolbar_create_topic);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager_create_topic);

        myPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);

    }

    public void jumptoAddMaterial(View view) {
        viewPager.setCurrentItem(1);
    }

    public void jumptoAddQuiz(View view) {
        viewPager.setCurrentItem(2);
    }


    public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return new TopicAddDescriptionFragment();
                case 1:
                    return new TopicAddMaterialFragment();
                case 2:
                    return new TopicAddQuizFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
