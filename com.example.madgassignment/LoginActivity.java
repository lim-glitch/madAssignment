package com.example.madgassignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button cancelButton;
    private ImageButton visibilityButton;
    private Button signUpButton;
    private Button forgotPasswordButton;
    private boolean isPasswordVisible = false;

    private UserDao userDao;
    private AppDatabase appDatabase;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        appDatabase = AppDatabase.getDatabase(getApplicationContext());
        userDao = appDatabase.userDao();

        usernameEditText = findViewById(R.id.PTLoginUsername);
        passwordEditText = findViewById(R.id.PTLoginPassword);
        loginButton = findViewById(R.id.BtnLoginLogin);
        cancelButton = findViewById(R.id.BtnLoginCancel);
        visibilityButton = findViewById(R.id.IBVisibilityOff);
        signUpButton = findViewById(R.id.BtnLoginSignUp);
        forgotPasswordButton = findViewById(R.id.BtnLoginForgotPassword);

        // Toggle password visibility
        visibilityButton.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                visibilityButton.setImageResource(R.drawable.visibility_on);
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                visibilityButton.setImageResource(R.drawable.visibility_off);
            }
            passwordEditText.setSelection(passwordEditText.getText().length());
        });

        // Login button
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            authenticateUser(username, password);
        });

        // Cancel button
        cancelButton.setOnClickListener(v -> finishAffinity());

        // Sign up button
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Forgot password button
        forgotPasswordButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    // Authenticate the user
    private void authenticateUser(String username, String password) {
        executorService.execute(() -> {
            User user = userDao.authenticateUser(username, password);

            runOnUiThread(() -> {
                if (user != null) {
                    // User authenticated successfully, navigate to home page
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish(); // Close the login activity
                } else {
                    // Invalid credentials, show an error toast
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    usernameEditText.getText().clear();
                    passwordEditText.getText().clear();
                }
            });
        });
    }
}
