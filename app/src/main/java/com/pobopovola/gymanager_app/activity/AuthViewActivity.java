package com.pobopovola.gymanager_app.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pobopovola.gymanager_app.API;
import com.pobopovola.gymanager_app.CreditsHolder;
import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.model.AuthCredits;
import com.pobopovola.gymanager_app.model.UserToken;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class AuthViewActivity extends AppCompatActivity {
    private final String LOGGER_TAG = AuthViewActivity.class.getSimpleName();
    private final RestTemplate restTemplate = new RestTemplate();
    private Context context;

    private final static String TEST_LOGIN = "test_admin";
    private final static String TEST_PASSWORD = "qwerty";

    private EditText loginField;
    private EditText passwordField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        setContentView(R.layout.auth_view);

        final Button button = findViewById(R.id.auth_button);
        loginField = findViewById(R.id.user_login);
        passwordField = findViewById(R.id.user_password);

        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        button.setOnClickListener(view -> {
            new AuthTask().execute();
        });
    }

    class AuthTask extends AsyncTask<Void, Void, ResponseEntity<UserToken>> {

        @Override
        protected ResponseEntity<UserToken> doInBackground(Void... voids) {
            String login = loginField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
                Toast.makeText(context, "Fields can't be empty!", Toast.LENGTH_LONG).show();
                return null;
            }

            try {
                return restTemplate.postForEntity(
                        API.LOGIN_URL,
                        new AuthCredits(login, password),
                        UserToken.class
                );
            } catch (Exception ex) {
                Log.i(LOGGER_TAG, "Failed to auth");
                Log.e(AuthViewActivity.class.getSimpleName(), ex.getMessage());
                Toast.makeText(context, "Failed to auth", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResponseEntity<UserToken> result) {
            super.onPostExecute(result);
            if (result == null || result.getStatusCode().value() != 200 || result.getBody() == null) {
                return;
            }

            CreditsHolder.setToken(result.getBody());
            Log.i(LOGGER_TAG, result.getBody().toString());
            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
        }
    }
}
