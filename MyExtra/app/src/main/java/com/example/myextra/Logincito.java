package com.example.myextra;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myextra.json.jsoncito;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Logincito extends AppCompatActivity {
    public static List<jsoncito> listita;
    public static String TAG = "msj";
    public static String jason = null;
    public static String lusr, lcontra;
    public Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn1= findViewById(R.id.button);
        btn2= findViewById(R.id.button2);
        btn3= findViewById(R.id.button3);
        EditText eusr = findViewById(R.id.xusr);
        EditText econtra = findViewById(R.id.xcontra);
        Leer();
        addListita(jason);
        if (jason == null || jason.length() == 0);{
            btn1.setEnabled(false);
            btn3.setEnabled(false);
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lusr = String.valueOf(eusr.getText());
                lcontra = String.valueOf(econtra.getText())+lusr;
                lcontra = Metoditos.bytesToHex(Metoditos.sha1inador(lcontra));
                orale(lusr, lcontra);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Logincito.this, Registro.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lusr =  String.valueOf(eusr.getText());
                lcontra = Metoditos.bytesToHex(Metoditos.sha1inador(String.valueOf(econtra.getText())));
                if(lusr.equals("")||lcontra.equals("")){
                    Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent (Logincito.this, Yo.class);
                    startActivity(intent);
                }
            }
        });
    }
    public boolean Leer(){
        if(!JsonIsReal()){
            return false;
        }
        File archivito = getArchivito();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)archivito.length()];
        try{
            fileInputStream = new FileInputStream(archivito);
            fileInputStream.read(bytes);
            jason=new String(bytes);
            Log.d(TAG,jason);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public void addListita (String json)
    {
        Gson gson = null;
        String msj = null;
        if (json == null||json.length()==0){
            Toast.makeText(getApplicationContext(), "Error, jason con vacio existencial", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<jsoncito>>(){}.getType();
        listita = gson.fromJson(json, listType);
        if (listita==null||listita.size()==0){
            Toast.makeText(getApplicationContext(), "Error, jason con vacio existencial", Toast.LENGTH_LONG).show();
        }
    }
    private File getArchivito(){
        return new File (getDataDir(), Registro.archivito);
    }
    private boolean JsonIsReal(){
        File archivito = getArchivito();
        if (archivito==null){
            return false;
        }
        return archivito.isFile() && archivito.exists();
    }
    public void orale(String lusr, String lcontra){
        int i=0;
        if(lusr.equals("")||lcontra.equals("")){
            Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_LONG).show();
        }else{
            for(jsoncito jsoncito:listita){
                if(jsoncito.getJusr().equals(lusr)&&jsoncito.getJcontra().equals(lcontra)){
                    Intent intent = new Intent (Logincito.this, Menucito.class);
                    startActivity(intent);
                    i=1;
                }
            }
        if (i==0){
            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
        }
    }
    public void yomero (String lusr, String lcontra){
        if(lusr.equals("")||lcontra.equals("")){
            Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent ( Logincito.this, Yo.class);
            startActivity(intent);
        }
    }
}
