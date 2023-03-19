package com.pobopovola.gymanager_app.activity;

import android.content.Context;
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
import com.pobopovola.gymanager_app.model.UserToken;
import com.pobopovola.gymanager_app.model.AuthCredits;
import com.pobopovola.gymanager_app.tasks.BaseNetAsyncTask;
import com.pobopovola.gymanager_app.utils.RestTemplateBuilder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

public class AuthViewActivity extends AppCompatActivity {
    private final String LOGGER_TAG = AuthViewActivity.class.getSimpleName();
    private final RestTemplate restTemplate = RestTemplateBuilder.buildDefault();
    private Context context;

    private EditText loginField;
    private EditText passwordField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_view);

        context = getApplicationContext();

        final Button button = findViewById(R.id.auth_button);
        loginField = findViewById(R.id.user_login);
        passwordField = findViewById(R.id.user_password);

        if (CreditsHolder.getCredits() != null) {
            AuthCredits credits = CreditsHolder.getCredits();
            loginField.setText(credits.getLogin());
            passwordField.setText(credits.getPassword());
        }

        button.setOnClickListener(view -> {
            String login = loginField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
                Toast.makeText(context, "Fields can't be empty!", Toast.LENGTH_LONG).show();
                return;
            }
            CreditsHolder.setCredits(new AuthCredits(login, password));

            new AuthTask(
                    restTemplate,
                    r -> {
                        CreditsHolder.setToken(r);
                        Toast.makeText(context, "Welcome!", Toast.LENGTH_SHORT).show();
                        finish();
                    },
                    f -> {
                        Toast.makeText(context, "Failed to auth!", Toast.LENGTH_SHORT).show();
                    }
            ).execute();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CreditsHolder.saveToPreferences(context);
    }

    class AuthTask extends BaseNetAsyncTask<UserToken> {
        private final String LOGGER_TAG = AuthTask.class.getSimpleName();

        public AuthTask(
                RestTemplate restTemplate,
                Consumer<UserToken> processSuccess,
                Consumer<HttpStatus> processFailure) {
            super(restTemplate, processSuccess, processFailure);
        }

        @Override
        protected ResponseEntity<UserToken> makeRequest() {
            AuthCredits authCredits = CreditsHolder.getCredits();
            if (authCredits == null) {
                Log.e(LOGGER_TAG, "Credits are empty!");
                return null;
            }

            return getRestTemplate().postForEntity(
                    API.LOGIN_URL,
                    authCredits,
                    UserToken.class
            );
        }
    }
}
