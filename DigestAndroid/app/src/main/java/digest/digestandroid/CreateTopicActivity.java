package digest.digestandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateTopicActivity extends AppCompatActivity {

    private Toolbar toolbar2;
    private EditText edit_text_title;
    private EditText edit_text_description;
    private EditText edit_text_tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);

        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_text_title = (EditText) findViewById(R.id.edit_text_title);
        edit_text_description = (EditText) findViewById(R.id.edit_text_description);
        edit_text_tags = (EditText) findViewById(R.id.edit_text_tags);

        Button next = (Button) findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTopicActivity.this, AddMaterialActivity.class);
                startActivity(intent);
            }
        });

    }
}
