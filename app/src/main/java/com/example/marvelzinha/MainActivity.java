package com.example.marvelzinha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_procurar,btn_salvar,btn_historico;
    EditText edit_name;
    String url;
    ListView list;
    ArrayAdapter adapter;

    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBase(this);

        btn_historico = findViewById(R.id.btn_hist);
        btn_procurar = findViewById(R.id.btn_procurar);
        btn_salvar = findViewById(R.id.btn_salvar);
        edit_name = findViewById(R.id.editTextWord);
        list = findViewById(R.id.list);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model cm = new Model();
                cm.setDescription(edit_name.getText().toString());
                db.addData(cm);

                Toast.makeText(getBaseContext(), "Resultado salvo com sucesso no Banco de Dados", Toast.LENGTH_SHORT).show();
            }
        });

        btn_historico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase dataBase = new DataBase(MainActivity.this);
                List<Model> everyone = dataBase.getEveryone();

                ArrayAdapter customerArrayAdapter = new ArrayAdapter<Model>(MainActivity.this, android.R.layout.simple_list_item_1, everyone);
                list.setAdapter(customerArrayAdapter);
            }
        });

    }
// on click botão procurar
    public void requestApiButtonClick(View v){
        url = MarvelEntries();
        MarvelRequest dictionaryRequest =  new MarvelRequest(this);
        dictionaryRequest.execute(url);

    }

    private String MarvelEntries() {
        final String publicKey = "3436c16c79249964b5a4d17edaee362e";
        final String privateKey = "0e482fac08f10af28f8d5a539e4a48276d511416";
        final String name = edit_name.getText().toString();
        Long tsLong = System.currentTimeMillis()/1000;
        String timestamp = tsLong.toString();
        return "https://gateway.marvel.com:443/v1/public/characters?name=" + name + "&apikey=" + publicKey + "&hash=" + generateHash(timestamp, publicKey, privateKey)  + "&ts=" + timestamp;


    }

    public String generateHash(String timestamp, String publicKey, String privateKey) {
        final String MD5 = "MD5";
        try {
            //Criação do MD5 e junção das variaveis para criar o hash

            String hash = timestamp + privateKey + publicKey;
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(hash.getBytes());
            byte messageDigest[] = digest.digest();

            // Criação da Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

