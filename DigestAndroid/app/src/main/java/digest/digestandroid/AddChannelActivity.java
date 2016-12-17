package digest.digestandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import digest.digestandroid.Models.Comment;
import digest.digestandroid.api.APIHandler;

/**
 * Created by sahin on 17.12.2016.
 */

public class AddChannelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO width valıe of the layout is fixed , it should be automatic
        setContentView(R.layout.activity_add_channel);

        Button addButton = ((Button)findViewById(R.id.ChannelAddButton));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String channelName =  String.valueOf(((EditText)findViewById(R.id.ChannelBox)).getText());

                APIHandler.getInstance().addChannel(Cache.getInstance().getUser(), channelName, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("200")){

                            Toast.makeText(getApplicationContext(), "Channel Add is successful", Toast.LENGTH_LONG).show();
                        }else {
                            Log.d("Failed", "Comment Failed ");
                            Toast.makeText(getApplicationContext(), "Channel Add Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                Log.d("aa",channelName);

                finish();
            }
        });

    }

}
