package digest.digestandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import digest.digestandroid.Models.User;
import digest.digestandroid.api.APIHandler;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_FORGETPASSWORD = 0;
    //Context context;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;
    @InjectView(R.id.link_forgetpassword) TextView _forgetpasswordLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        //context = this;

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
        _forgetpasswordLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the ForgetPassword activity
                Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivityForResult(intent, REQUEST_FORGETPASSWORD);
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        /*
        if (!validate()) {
            onLoginFailed();
            return;
        }*/

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        User user = new User(username, password);
        //User user = new User("atakanguney", "1234");
        //User user = new User("AILover", "12345678");
        user.setId(-10);

        Response.Listener<User> response = new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                if (response.getEmail() != null) {
                    progressDialog.dismiss();
                    Log.d("Login", "Login success " + response.getEmail());

                    // Writing data to SharedPreferences
                    Cache.getInstance().setUser(response);

                    _loginButton.setEnabled(true);
                    Intent intent = new Intent(getApplicationContext(), ViewRegisteredHomeActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    progressDialog.dismiss();
                    Log.d("Login", "Error: ");
                    Log.d("Wrong credentials:", "Not valid username and password");

                    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                    _loginButton.setEnabled(true);
                    _emailText.setText("");
                    _passwordText.setText("");

                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("Failed", "Login Failed");
                Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                _loginButton.setEnabled(true);
                _emailText.setText("");
                _passwordText.setText("");

            }
        };

        // TODO: Implement your own authentication logic here.
        APIHandler.getInstance().login("LOGIN", user, response, errorListener);


        /*
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        // onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
                */

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(), CreateTopicFragmentsActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
