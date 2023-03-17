package com.example.trabalho1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.trabalho1.R;
import com.example.trabalho1.databinding.ActivityOperationBinding;
import com.example.trabalho1.model.Registro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperationActivity extends AppCompatActivity {
    List<Registro> registros = new ArrayList<>();
    private ActivityOperationBinding binding;
    String spinnerSelected ;
    String genero;
    int idade;
    long contacto;
    String nome;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("contacto", Integer.parseInt(binding.editContacto.getText().toString()));
        outState.putString("usarname", binding.editNome.getText().toString());
        outState.putInt("idade", Integer.parseInt(binding.editIdade.getText().toString()));
        outState.putString("sexo", binding.radioButtonMasculino.isChecked()? "masculino" : "femenino");
        outState.putInt("horario",binding.spinner.getSelectedItemPosition());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        binding.editNome.setText(savedInstanceState.getString("username"));
        binding.editContacto.setText(savedInstanceState.getString("contacto"));
        binding.editIdade.setText(savedInstanceState.getInt("idade"));
        binding.spinner.setSelection(savedInstanceState.getInt("horario"));

        if(savedInstanceState.getString("sexo") == "masculino"){
            binding.radioButtonMasculino.setChecked(true);
        }else{
            binding.radioButtonFemenino.setChecked(true);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        SQLiteDatabase database = null;


        final String[] operacoes = {"08 : 00  10 : 00", "10:00 - 12 : 00", "14 : 00 - 16 : 00"};

     //   database = openOrCreateDatabase("app", MODE_PRIVATE, null);

        // config binding
        binding = ActivityOperationBinding .inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, operacoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);


        try{
            database.execSQL("CREATE TABLE IF NOT EXISTS registo(nome VACHAR(20), idade INT(2), sexo VARCHAR(10), contacto INT(11), horario VARCHAR(30))");

        }catch (Exception e){
            e.printStackTrace();
            Log.i("som", e.getMessage());
        }

        //radio Button selected
        binding.radioGenero.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioButtonMasculino){
                    genero = "Masculino";
                }else if(checkedId == R.id.radioButtonFemenino){
                    genero = "Femenino";
                }
            }
        });

        // spinner selected
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerSelected = operacoes[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

       SQLiteDatabase finalDatabase = database;
        binding.buttonGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG",  "spinnerSelected");
                nome = binding.editNome.getText().toString();
                idade = Integer.parseInt(binding.editIdade.getText().toString());
                contacto = Integer.parseInt(binding.editContacto.getText().toString());

                if(validation(nome, idade, genero, contacto, spinnerSelected)){
                    Registro registro = new Registro(nome, idade, genero, contacto, spinnerSelected);
                    registros.add(registro);
                    finalDatabase.execSQL("INSERT INTO registo(nome, idade, sexo, contacto, horario) VALUES('"+nome+"', "+idade+", '"+genero+"',"+contacto+", '"+spinnerSelected+"')");

                    binding.editNome.setText("");
                    binding.editIdade.setText("");
                    binding.editContacto.setText("");
                }
            }
        });


        binding.buttonListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    for(Registro registro: registros){
                        Log.i("Nome", registro.getNome());
                            Log.i("idade", String.valueOf(registro.getIdade()));
                            Log.i("sexo", registro.getSexo());
                            Log.i("contacto", String.valueOf(registro.getContacto()));
                            Log.i("Horario", registro.getHorario());
                    }

                    Cursor cursor = finalDatabase.rawQuery("SELECT nome, idade, sexo, contacto, horario FROM  registo", null);

                    if(cursor != null && cursor.moveToFirst()){
                        do{
                            Log.i("Nome", cursor.getString(0));
                            Log.i("idade", String.valueOf(cursor.getInt(1)));
                            Log.i("sexo", cursor.getString(2));
                            Log.i("contacto", String.valueOf(cursor.getInt(3)));
                            Log.i("Horario", cursor.getString(4));
                        }while (cursor.moveToNext());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Log.i("tag", e.getMessage());
                }
            }
        });

    }

    public boolean validation(String nome,  int idade, String sexo, long contacto, String horario){
        if(nome.equals("") || nome.equals(null) || idade == 0 || sexo.equals(null) || sexo.equals("") || contacto == 0
         || horario.equals("") || horario.equals(null)) return false;

        return  true;
    }


}