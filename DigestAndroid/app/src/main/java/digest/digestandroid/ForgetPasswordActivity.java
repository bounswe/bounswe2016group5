package digest.digestandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Sahin on 11/1/2016.
 */

public class ForgetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgetPasswordActivity";

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.btn_resetpassword) Button _resetpasswordButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    public void resetpassword(){

        Log.d(TAG, "Reset password");

        if (!validate()) {
     
	     return;
        }

     
    }

    public boolean validate(){
        boolean valid = true;

        String email = _emailText.getText().toString();


        return valid;
    }

}












