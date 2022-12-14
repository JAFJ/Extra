package com.example.myextra;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myextra.json.jsoncito;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    private Button btn4, selec;
    private EditText reusr,recontra, remail, retel, redate, repseud;
    private CheckBox box1, box2, box3, box4;
    private Spinner gyro;
    private RadioButton r1, r2;
    private int dia,mes,anual;
    private static final String TAG = "MainActivity";
    public static final String archivito = "archivito.json";
    String jason = null;
    public static String rusr, rcontra, rmail, rtel, rdate, rgenfav, rpseud;
    public static boolean booly= false;
    public static boolean boolpref;
    public static String[] box = new String [4];
    public static List<jsoncito> listita = new ArrayList<jsoncito>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regis);
        gyro = findViewById(R.id.gyro);
        String [] generos = {"Pop","Rock","Trap","Banda","Indie (pop/rock)","Rap","Regional","Pop/Rock","Cumbia","Salsa"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,generos);
        gyro.setAdapter(adapter);
        selec=findViewById(R.id.selec);
        btn4 = findViewById(R.id.buttonre);
        Button btn5 = findViewById(R.id.buttonini);
        reusr = findViewById(R.id.xrusr);
        recontra = findViewById(R.id.xrcontra);
        remail = findViewById(R.id.xrmail);
        retel = findViewById(R.id.xrtel);
        redate = findViewById(R.id.xrdate);
        repseud = findViewById(R.id.xrpseud);
        box1 = findViewById(R.id.checkBox);
        box2 = findViewById(R.id.checkBox2);
        box3 = findViewById(R.id.checkBox3);
        box4 = findViewById(R.id.checkBox4);
        r1 = findViewById(R.id.radioButton);
        r2 = findViewById(R.id.radioButton2);
        Switch switcher = findViewById(R.id.switcher);
        Leer();
        addListita(jason);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, Logincito.class);
                startActivity(intent);
            }
        });
        selec.setOnClickListener(this);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsoncito info = new jsoncito();
                rusr = String.valueOf(reusr.getText());
                rcontra = String.valueOf(recontra.getText());
                rmail = String.valueOf(remail.getText());
                rtel = String.valueOf(retel.getText());
                rgenfav = gyro.getSelectedItem().toString();
                rpseud = String.valueOf(repseud.getText());

                //cajitas
                if(box1.isChecked()==true){
                    box[0]="genero1";
                }else{
                    box[0]="no";
                }
                if(box2.isChecked()==true){
                    box[1]="genero2";
                }else{
                    box[1]="no";
                }
                if(box3.isChecked()==true){
                    box[2]="genero3";
                }else{
                    box[2]="no";
                }
                if(box4.isChecked()==true){
                    box[3]="genero4";
                }else{
                    box[3]="no";
                }
                //radios
                if(r1.isChecked()==true){
                    boolpref=true;
                }
                if(r2.isChecked()==true){
                    boolpref=true;
                }
                if(switcher.isChecked()){
                    booly=true;
                }
                //fin de radios
                if(rusr.equals("")||rcontra.equals("")||rmail.equals("")){
                    Log.d(TAG, "vacio");
                    Log.d(TAG, rusr);
                    Log.d(TAG, rcontra);
                    Log.d(TAG, rmail);
                    Toast.makeText(getApplicationContext(), "Campos vacios detectados, beep boop", Toast.LENGTH_LONG).show();
                }else{
                    if(Metoditos.validamail(rmail)){
                        if(listita.isEmpty()){
                            Log.d(TAG,"lleno");
                            Metoditos.llenainfo(info);
                            addJsoncito(info, listita);
                        }else{
                            if(Metoditos.musers(listita, rusr)){
                                Log.d(TAG, "Nombre de usuario previamente registrado.");
                                Toast.makeText(getApplicationContext(), "El nombre de usuario ya esta en uso.", Toast.LENGTH_LONG).show();
                            }else{
                                Metoditos.llenainfo(info);
                                addJsoncito(info, listita);

                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Correo inválido", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public void addJsoncito(jsoncito info, List<jsoncito> listita){
        Gson gson = null;
        String json = null;
        gson = new Gson();
        listita.add(info);
        json = gson.toJson(listita, ArrayList.class);
        if (json == null){
            Log.d(TAG, "Error jason, json igual a null");
        }else{
            Log.d(TAG, json);
            escribirArchi(json);
        }
        Toast.makeText(getApplicationContext(), "Todo correctoo", Toast.LENGTH_LONG).show();
    }
    private boolean escribirArchi (String text){
        File archivo = null;
        FileOutputStream fileOutputStream = null;
        try{
            archivo=getArchivito();
            fileOutputStream=new FileOutputStream(archivo);
            fileOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            Log.d(TAG, "tremendo");
            return true;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    private File getArchivito(){
        return new File(getDataDir(),archivito);
    }
    public boolean Leer(){
        if(!JasonIsReal()){
            return false;
        }
        File archivo = getArchivito();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)archivo.length()];
        try{
            fileInputStream = new FileInputStream(archivo);
            fileInputStream.read(bytes);
            jason= new String(bytes);
            Log.d(TAG,jason);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }
    private boolean JasonIsReal(){
        File archivo = getArchivito();
        if(archivo==null){
            return false;
        }
        return archivo.isFile()&&archivo.exists();
    }
    public void addListita(String json){
        Gson gson = null;
        String msj = null;
        if (json==null||json.length()==0){
            Toast.makeText(getApplicationContext(), "Jason con vacio existencial", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<jsoncito>>(){}.getType();
        listita = gson.fromJson(json, listType);
        if (listita == null||listita.size()==0){
            Toast.makeText(getApplicationContext(), "Jason con vacio existencial", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View view){
        if (view==selec){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anual = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    redate.setText(i2+"/"+(i1+1)+"/"+i);
                }
            },dia,mes,anual);
            datePickerDialog.show();
        }
    }
}
