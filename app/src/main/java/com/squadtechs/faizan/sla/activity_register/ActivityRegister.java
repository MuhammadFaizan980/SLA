package com.squadtechs.faizan.sla.activity_register;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squadtechs.faizan.sla.R;

import java.util.HashMap;

public class ActivityRegister extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtRegistrationNumber;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        registerUser();
    }

    private void registerUser() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edtEmail.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                final String regNumber = edtRegistrationNumber.getText().toString().trim();
                if (!email.equals("") && !password.equals("") && !regNumber.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                HashMap map = new HashMap<String, String>();
                                map.put("registration_number", regNumber);
                                map.put("email", email);
                                map.put("password", password);
                                databaseReference.child(firebaseAuth.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> mTask) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        if (mTask.isSuccessful()) {
                                            Toast.makeText(ActivityRegister.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ActivityRegister.this, mTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(ActivityRegister.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityRegister.this, "Fil properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtRegistrationNumber = findViewById(R.id.edt_registration);
        btnRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progress_register);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
