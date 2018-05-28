package com.olssonjonas.grocerylistapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.olssonjonas.grocerylistapp.model.AuthenticationToken;
import com.olssonjonas.grocerylistapp.model.LoginCredentials;
import com.olssonjonas.grocerylistapp.services.UserCredentialsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView userView;
    private EditText passwordView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (mAuthTask != null){
            continueToMainActivity();
        }
        // Set up the login form.
        userView = (AutoCompleteTextView) findViewById(R.id.username);

        passwordView = (EditText) findViewById(R.id.password);
        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signInButton = (Button) findViewById(R.id.sign_in_btn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        userView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String username = userView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }
    private void continueToMainActivity() {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private static final String TAG = "UserLoginTask";

        private boolean responseSuccess;
        private final String username;
        private final String password;

        UserLoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            LoginCredentials login = new LoginCredentials();
            login.setUsername(this.username);
            login.setPassword(this.password);

            Call<AuthenticationToken> userCredentials = ServiceGenerator.
                    createService(UserCredentialsService.class).authenticate(login);

            try{
                userCredentials.enqueue(new Callback<AuthenticationToken>() {
                    @Override
                    public void onResponse(Call<AuthenticationToken> call,
                                           Response<AuthenticationToken> response) {
                        if (response.isSuccessful()){
                            responseSuccess = true;
                            Log.d(TAG, "onResponse:  =" + response.body().toString());

                            String responseHeaders = response.headers().get("Authorization");
                            AuthenticationToken token = new AuthenticationToken();
                            token.setTokenId(responseHeaders);

                            Log.d(TAG, "onResponse: ResponserHeders Authorization = "
                                    + responseHeaders.toString() );
                            TokenHolder.getInstance().setToken(token);
                            continueToMainActivity();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticationToken> call, Throwable t) {
                        Log.d(TAG, "onFailure: FAILIURE TO AUTHENTICATE");
                        responseSuccess = false;
                    }
                });
            }catch (Exception e) {
                Log.d(TAG, "authenticateUser: Cauth "+ e.getMessage());

            }
            return responseSuccess;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                continueToMainActivity();
            } else {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

