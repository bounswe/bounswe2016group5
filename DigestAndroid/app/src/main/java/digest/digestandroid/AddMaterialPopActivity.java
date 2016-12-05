package digest.digestandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import digest.digestandroid.Models.Comment;

public class AddMaterialPopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material_pop);
    }

    public void addMaterial(View view) {
        String material;

        material = String.valueOf(((EditText)findViewById(R.id.MaterialBox)).getText());

        Cache.getInstance().setMaterial(material);

        finish();

    }
}
