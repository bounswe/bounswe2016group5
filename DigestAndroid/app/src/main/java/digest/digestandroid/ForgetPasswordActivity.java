package digest.digestandroid;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Sahin on 11/1/2016.
 */

public class ForgetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgetPasswordActivity";

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.btn_resetpassword) Button _resetpasswordButton;
    @InjectView(R.id.link_forgetpassword) TextView _forgetpasswordLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.inject(this);

        _resetpasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resetpassword();
            }
        });

        _forgetpasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the resetting screen and return to the Login activity
                finish();
            }
        });
    }

    public void resetpassword(){

        Log.d(TAG, "Reset password");

        if (!validate()) {
            onResetPasswordFailed();
            return;
        }

        _resetpasswordButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(ForgetPasswordActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Resetting password...");
        progressDialog.show();

        String email = _emailText.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onResetPasswordFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    public void onResetPasswordSuccess(){
        _resetpasswordButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onResetPasswordFailed(){
        Toast.makeText(getBaseContext(), "Resetting password failed", Toast.LENGTH_LONG).show();
        _resetpasswordButton.setEnabled(true);
    }

    public boolean validate(){
        boolean valid = true;

        String email = _emailText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }


        return valid;
    }

}












