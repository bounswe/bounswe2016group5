package digest.digestandroid;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;

import digest.digestandroid.fragments.TopicAddDescriptionFragment;
import digest.digestandroid.fragments.TopicAddMaterialFragment;
import digest.digestandroid.fragments.TopicAddQuizFragment;
import digest.digestandroid.fragments.TopicMaterialFragment;

public class CreateTopicFragmentsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private MyViewPagerAdapter myPagerAdapter;

    private EditText edit_text_title;
    private EditText edit_text_body;
    private EditText edit_text_tags;
    private EditText edit_text_imageurl;
    private Button button_image_upload;
    private ImageView image_view_topic;

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

        edit_text_title = (EditText) findViewById(R.id.topic_create_title);
        edit_text_body = (EditText) findViewById(R.id.topic_create_body);
        edit_text_tags = (EditText) findViewById(R.id.topic_create_tags);
        edit_text_imageurl = (EditText) findViewById(R.id.topic_create_imageurl);
        image_view_topic = (ImageView) findViewById(R.id.topic_create_image);

        button_image_upload = (Button) findViewById(R.id.button_image_upload);
        button_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_text_imageurl.getText().toString().length()>0) {
                    URL url = null;
                    try {
                        url = new URL(edit_text_imageurl.getText().toString());
                        //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        //.setImageBitmap(bmp);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


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
