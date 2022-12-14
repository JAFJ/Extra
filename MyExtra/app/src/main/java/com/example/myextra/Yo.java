package com.example.myextra;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

public class Yo extends AppCompatActivity {
    public static List<jsoncito> list;
    public static String jason = null;
    public static String TAG = "msj";
    public static String cadenita = null;
    TextView textover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yo);
        textover = findViewById(R.id.textover);
        Leer();
        addListita(jason);
        harvest();
    }
    private File getArchivito(){
        return new File (getDataDir(), Registro.archivito);
    }
    private boolean JasonIsReal(){
        File archivo = getArchivito();
        if(archivo==null)
        {
            return false;
        }
        return archivo.isFile()&&archivo.exists();
    }
    public void addListita (String json){
        Gson gson = null;
        String msj = null;
        if (json == null || json.length()==0){
            Toast.makeText(getApplicationContext(), "Jason con vacio existencial (Error)", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<jsoncito>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list==null||list.size()==0){
            Toast.makeText(getApplicationContext(), "Lista vacia", Toast.LENGTH_LONG).show();
        }
    }
    public boolean Leer(){
        if (!JasonIsReal()){
            return false;
        }
        File archivo = getArchivito();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)archivo.length()];
        try{
            fileInputStream = new FileInputStream(archivo);
            fileInputStream.read(bytes);
            jason=new String(bytes);
            Log.d(TAG,jason);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public void harvest(){
        for(jsoncito info: list){
            if(Logincito.lusr.equals(info.getJusr())){
                textover.setText("Podemos afirmar dos cosas, el usuario esta correcto, y la contraseña esta olvidada.");
            }else{
                textover.setText("Usted no sabe ni el usuario ni la contraseña, no invente.");
            }
        }
    }
}
