package com.example.myextra;

import androidx.core.util.PatternsCompat;

import com.example.myextra.json.jsoncito;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Metoditos {
    public static final String TAG = "angel";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static byte[] sha1inador(String text){
        MessageDigest messageDigest = null;
        byte[] bytes = null;
        byte[] bytesResult = null;
        try{
            messageDigest = MessageDigest.getInstance("SHA-1");
            bytes = text.getBytes("iso-8859-1");
            messageDigest.update(bytes, 0, bytes.length);
            bytesResult = messageDigest.digest();
            return bytesResult;
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }
    public static String bytesToHex(byte[] bytes){
        char[] hexChars = new char[bytes.length*2];
        for (int j=0; j<bytes.length; j++){
            int v = bytes[j]& 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1]=HEX_ARRAY[v & 0xFF];
        }
        return new String(hexChars);
    }
    public static boolean validamail(String mail){
        boolean booleador;
        if(mail.isEmpty()){
            booleador=false;
        }else{
            if(PatternsCompat.EMAIL_ADDRESS.matcher(mail).matches()){
                booleador=true;
            }else{
                booleador=false;
            }
        }
        return booleador;
    }
    public static boolean musers(List<jsoncito> list, String musr){
        boolean booleador = false;
        for(jsoncito info: list){
            if(info.getJusr().equals(musr)){
                booleador=true;
            }
        }
        return booleador;
    }
    public static void llenainfo(jsoncito info){
        info.setJusr(Registro.rusr);
        String pasale = Registro.rcontra + Registro.rusr;
        info.setJcontra(bytesToHex(sha1inador(pasale)));
        info.setJtel(Registro.rtel);
        info.setJdate(Registro.rdate);
        info.setJplat(Registro.box);
        info.setJmail(Registro.rmail);
        info.setJgenfav(Registro.rgenfav);
        info.setJpref(Registro.boolpref);
        info.setBooleador(Registro.booly);
        info.setJpseud(Registro.rpseud);
    }
    public static void Jasonvaciopordentro(String json){
        json = null;
    }
    public static void harvest (String cadenita){
        for(jsoncito info: Yo.list){
            if(Logincito.lusr.equals(info.getJusr())){
                cadenita = "Hay usuario, no hay contraseña, que feo";
            }else{
                cadenita = "No hay tal usuario, al menos no en este universo";
            }
        }
    }
}
