package com.example.trabalho1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.trabalho1.R;
import com.example.trabalho1.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        Intent intent =  new Intent(getApplication(), OperationActivity.class);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginValidation()){
                    binding.editTextUserName.setText("");
                    binding.editTextPassword.setText("");
                   startActivity(intent);
                }
            }
        });
    }


    public boolean loginValidation(){
        boolean validation = false;
        try{
            String name = binding.editTextUserName.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            if(name.equals("lecc31") && password.equals("8765A")){
                validation = true;
            }else if(name.equals(null) || name.equals("") || password.equals(null) || password.equals("")){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Aviso");
                builder.setMessage("Preencha todos os campos");

                builder.setPositiveButton("OK", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }else {
                Toast.makeText(getApplication(),
                        "Usuario ou senha errados",
                        Toast.LENGTH_LONG).show();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return validation;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("usarname", binding.editTextUserName.getText().toString());
        outState.putString("password", binding.editTextPassword.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        binding.editTextUserName.setText(savedInstanceState.getString("usarname"));
        binding.editTextPassword.setText(savedInstanceState.getString("password"));

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}