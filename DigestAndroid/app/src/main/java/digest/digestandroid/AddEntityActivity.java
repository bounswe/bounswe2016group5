package digest.digestandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import digest.digestandroid.Models.Tagit;

public class AddEntityActivity extends AppCompatActivity {

    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entity);
    /* Initialize Radio Group and attach click handler */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupEntities);
        radioGroup.clearCheck();

        RadioButton rb = (RadioButton) findViewById(R.id.radioButtonEntity1);
        rb.setText(Cache.getInstance().getEntityList().get(0));
        rb = (RadioButton) findViewById(R.id.radioButtonEntity2);
        rb.setText(Cache.getInstance().getEntityList().get(1));
        rb = (RadioButton) findViewById(R.id.radioButtonEntity3);
        rb.setText(Cache.getInstance().getEntityList().get(2));
        rb = (RadioButton) findViewById(R.id.radioButtonEntity4);
        rb.setText(Cache.getInstance().getEntityList().get(3));
        rb = (RadioButton) findViewById(R.id.radioButtonEntity5);
        rb.setText(Cache.getInstance().getEntityList().get(4));


        /* Attach CheckedChangeListener to radio group */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if(null!=rb && checkedId > -1){
                    Toast.makeText(AddEntityActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void onClear(View v) {
        /* Clears all selected radio buttons to default */
        radioGroup.clearCheck();
        finish();
    }

    public void onSubmit(View v) {
        RadioButton rb = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        Toast.makeText(AddEntityActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
        Tagit tagit = new Tagit(rb.getText().toString(), Cache.getInstance().getTag(), true);
        Cache.getInstance().getChosenTags().add(tagit);
        finish();
    }
}
