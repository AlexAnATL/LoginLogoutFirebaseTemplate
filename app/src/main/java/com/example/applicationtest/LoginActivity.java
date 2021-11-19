package com.example.applicationtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = database.getReference().child("");
private void initButtons() {
    TextView signupTextView = findViewById(R.id.textViewSignup);
    signupTextView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
});
    Button buttonLogin = findViewById(R.id.buttonLogin);
    EditText editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
    EditText editTextPasswordLogin = findViewById(R.id.editTextPasswordLogin);
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    buttonLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = editTextEmailLogin.getText().toString().trim();
            String password = editTextPasswordLogin.getText().toString().trim();
            if(TextUtils.isEmpty(email)) {
                editTextEmailLogin.setError("email is required");
                return;
            }
            if(TextUtils.isEmpty(password)) {
                editTextPasswordLogin.setError("password is required");
                return;
            }
            if(password.length() < 6) {
                editTextPasswordLogin.setError("password must be longer than five characters");
            }
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }
            });
        }
    });
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initButtons();
    }
}