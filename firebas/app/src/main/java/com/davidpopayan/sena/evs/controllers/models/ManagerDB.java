package com.davidpopayan.sena.evs.controllers.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManagerDB {
    Context context;
    SQLiteDatabase db;
    GestorDB gestorDB;

    public ManagerDB(Context context) {
        this.context = context;
    }

    public void openWriteDB(){
        gestorDB = new GestorDB(context);
        db = gestorDB.getWritableDatabase();
    }

    public void openReadDB(){
        gestorDB = new GestorDB(context);
        db = gestorDB.getReadableDatabase();
    }

    public void inputData(Datos datos){
        ContentValues values = new ContentValues();
        openWriteDB();
        values.put("FECTAMITAJE",datos.getFecTamitaje());
        values.put("NOMBREC",datos.getNombreCompleto());
        values.put("TIPOID",datos.getTipoID());
        values.put("NUMEROID",datos.getNumeroId());
        values.put("EPS",datos.getNombreEPS());
        values.put("IPS",datos.getiPS());
        values.put("TELEFONO",datos.getTelefono());
        values.put("DIRECCION",datos.getDireccion());
        values.put("FECNAC",datos.getFecNac());
        values.put("EDAD",datos.getEdad());
        values.put("EDADCATEGORIZADA",datos.getEdadCategorizada());
        values.put("GENERO",datos.getGenero());
        values.put("TALLA",datos.getTalla());
        values.put("PESO",datos.getPeso());
        values.put("PERIMETROA",datos.getPerimetroAbdominal());
        values.put("REALIZAAF",datos.getRealizarActividadFisicaD());
        values.put("FRECUENCIAVF",datos.getFrecuenciaVerdurasFrutas());
        values.put("MEDICAMENTOSH",datos.getMedicamentosHipertension());
        values.put("GLUCOSAALTA",datos.getGlucosaAlta());
        values.put("DIABETESFAM",datos.getDiabetesFamiliares());
        values.put("IMC",datos.getImc());
        values.put("CLASIFICACIONIMC",datos.getClasificacionIMC());
        values.put("RIESGODIABETES",datos.getRiesgoDeDiabetes());
        values.put("PRESIONAS",datos.getPresionAS());
        values.put("PRESIONDISATIOLICA",datos.getPresionDiastolica());
        values.put("PRESIONARTERIAL",datos.getPresionArterial());
        values.put("DIABETES",datos.getDiabetes());
        values.put("FUMA",datos.getFuma());
        values.put("PORCENTAJERIESGO",datos.getPorcentajeRiesgo());
        values.put("RIESGOCARDIO",datos.getRiesgoCardio());
        values.put("PACIENTEPRESENTARIESGO",datos.getPacientePresentaR());
        values.put("DETALLEDP",datos.getDetalleRiesgoPaciente());
        values.put("REALIZA",datos.getRealiza());
        values.put("LATITUD",datos.getLatitud());
        values.put("LONGITUD",datos.getLongitud());
        values.put("GLUCOMETRIA",datos.getGlucometria());
        db.insert("DATOS1",null,values);
        closeDB();
    }


    public void updateData(Datos datos){
        ContentValues values = new ContentValues();
        openWriteDB();
        values.put("FECTAMITAJE",datos.getFecTamitaje());
        values.put("NOMBREC",datos.getNombreCompleto());
        values.put("TIPOID",datos.getTipoID());
        values.put("NUMEROID",datos.getNumeroId());
        values.put("EPS",datos.getNombreEPS());
        values.put("IPS",datos.getiPS());
        values.put("TELEFONO",datos.getTelefono());
        values.put("DIRECCION",datos.getDireccion());
        values.put("FECNAC",datos.getFecNac());
        values.put("EDAD",datos.getEdad());
        values.put("EDADCATEGORIZADA",datos.getEdadCategorizada());
        values.put("GENERO","Masculino");
        values.put("GENERO","Femenino");
        values.put("TALLA",datos.getTalla());
        values.put("PESO",datos.getPeso());
        values.put("PERIMETROA",datos.getPerimetroAbdominal());
        values.put("REALIZAAF",datos.getRealizarActividadFisicaD());
        values.put("FRECUENCIAVF",datos.getFrecuenciaVerdurasFrutas());
        values.put("MEDICAMENTOSH",datos.getMedicamentosHipertension());
        values.put("GLUCOSAALTA",datos.getGlucosaAlta());
        values.put("DIABETESFAM",datos.getDiabetesFamiliares());
        values.put("IMC",datos.getImc());
        values.put("CLASIFICACIONIMC",datos.getClasificacionIMC());
        values.put("RIESGODIABETES",datos.getRiesgoDeDiabetes());
        values.put("PRESIONAS",datos.getPresionAS());
        values.put("PRESIONDISATIOLICA",datos.getPresionDiastolica());
        values.put("PRESIONARTERIAL",datos.getPresionArterial());
        values.put("DIABETES",datos.getDiabetes());
        values.put("FUMA",datos.getFuma());
        values.put("REALIZA",datos.getRealiza());
        values.put("PORCENTAJERIESGO",datos.getPorcentajeRiesgo());
        values.put("RIESGOCARDIO",datos.getRiesgoCardio());
        values.put("PACIENTEPRESENTARIESGO",datos.getPacientePresentaR());
        values.put("DETALLEDP",datos.getDetalleRiesgoPaciente());
        values.put("LATITUD",datos.getLatitud());
        values.put("LONGITUD",datos.getLongitud());
        values.put("GLUCOMETRIA",datos.getGlucometria());
        db.update("DATOS1",values,"NUMERO="+datos.getNumero(),null);
        closeDB();
    }

    public List<Datos> listaDatos(){
        List<Datos> results  = new ArrayList<>();
        openReadDB();
        Cursor cursor = db.rawQuery("SELECT * FROM DATOS1;",null);
        if (cursor.moveToFirst()){
            do {
                Datos datos = new Datos();
                datos.setNumero(cursor.getInt(0));
                datos.setFecTamitaje(cursor.getString(1));
                datos.setNombreCompleto(cursor.getString(2));
                datos.setTipoID(cursor.getString(3));
                datos.setNumeroId(cursor.getString(4));
                datos.setNombreEPS(cursor.getString(5));
                datos.setiPS(cursor.getString(6));
                datos.setTelefono(cursor.getString(7));
                datos.setDireccion(cursor.getString(8));
                datos.setFecNac(cursor.getString(9));
                datos.setEdad(cursor.getInt(10));
                datos.setEdadCategorizada(cursor.getString(11));
                datos.setGenero(cursor.getString(12));
                datos.setTalla(cursor.getInt(13));
                datos.setPeso(cursor.getInt(14));
                datos.setPerimetroAbdominal(cursor.getInt(15));
                datos.setRealizarActividadFisicaD(cursor.getString(16));
                datos.setFrecuenciaVerdurasFrutas(cursor.getString(17));
                datos.setMedicamentosHipertension(cursor.getString(18));
                datos.setGlucosaAlta(cursor.getString(19));
                datos.setDiabetesFamiliares(cursor.getString(20));
                datos.setImc(cursor.getInt(21));
                datos.setClasificacionIMC(cursor.getString(22));
                datos.setRiesgoDeDiabetes(cursor.getString(23));
                datos.setPresionAS(cursor.getString(24));
                datos.setPresionDiastolica(cursor.getString(25));
                datos.setPresionArterial(cursor.getString(26));
                datos.setGlucometria(cursor.getString(27));
                datos.setDiabetes(cursor.getString(28));
                datos.setFuma(cursor.getString(29));
                datos.setPorcentajeRiesgo(cursor.getString(30));
                datos.setRiesgoCardio(cursor.getString(31));
                datos.setPacientePresentaR(cursor.getString(32));
                datos.setDetalleRiesgoPaciente(cursor.getString(33));
                datos.setRealiza(cursor.getString(34));
                datos.setLatitud(cursor.getString(36));
                datos.setLongitud(cursor.getString(37));
                results.add(datos);

            }while (cursor.moveToNext());
        }
        closeDB();
        cursor.close();
        return  results;
    }


    public void modificarExportar(List<Datos> datosList){
        openWriteDB();
        for (int i=0; i<datosList.size();i++){
            ContentValues values = new ContentValues();
            values.put("ENVIADO","SI");
            db.update("DATOS1",values,"NUMERO="+datosList.get(i).getNumero(),null);
        }
        closeDB();

    }


    public List<Datos> listaDatosPorIdentificacion(String identificacion){
        List<Datos> results  = new ArrayList<>();
        openReadDB();
        Cursor cursor = db.rawQuery("SELECT * FROM DATOS WHERE NUMEROID='"+identificacion+"';",null);
        if (cursor.moveToFirst()){
            do {
                Datos datos = new Datos();
                datos.setNumero(cursor.getInt(0));
                datos.setFecTamitaje(cursor.getString(1));
                datos.setNombreCompleto(cursor.getString(2));
                datos.setTipoID(cursor.getString(3));
                datos.setNumeroId(cursor.getString(4));
                datos.setNombreEPS(cursor.getString(5));
                datos.setiPS(cursor.getString(6));
                datos.setTelefono(cursor.getString(7));
                datos.setDireccion(cursor.getString(8));
                datos.setFecNac(cursor.getString(9));
                datos.setEdad(cursor.getInt(10));
                datos.setEdadCategorizada(cursor.getString(11));
                datos.setGenero(cursor.getString(12));
                datos.setTalla(cursor.getInt(13));
                datos.setPeso(cursor.getInt(14));
                datos.setPerimetroAbdominal(cursor.getInt(15));
                datos.setRealizarActividadFisicaD(cursor.getString(16));
                datos.setFrecuenciaVerdurasFrutas(cursor.getString(17));
                datos.setMedicamentosHipertension(cursor.getString(18));
                datos.setGlucosaAlta(cursor.getString(19));
                datos.setDiabetesFamiliares(cursor.getString(20));
                datos.setImc(cursor.getInt(21));
                datos.setClasificacionIMC(cursor.getString(22));
                datos.setRiesgoDeDiabetes(cursor.getString(23));
                datos.setPresionAS(cursor.getString(24));
                datos.setPresionDiastolica(cursor.getString(25));
                datos.setPresionArterial(cursor.getString(26));
                datos.setGlucometria(cursor.getString(27));
                datos.setDiabetes(cursor.getString(28));
                datos.setFuma(cursor.getString(29));
                datos.setPorcentajeRiesgo(cursor.getString(30));
                datos.setRiesgoCardio(cursor.getString(31));
                datos.setPacientePresentaR(cursor.getString(32));
                datos.setDetalleRiesgoPaciente(cursor.getString(33));
                datos.setRealiza(cursor.getString(34));
                results.add(datos);

            }while (cursor.moveToNext());
        }
        closeDB();
        cursor.close();
        return  results;
    }



    public List<Datos> listaDatosPorIdentificacion1(String identificacion){
        List<Datos> results  = new ArrayList<>();
        openReadDB();
        Cursor cursor = db.rawQuery("SELECT * FROM DATOS1 WHERE NUMEROID='"+identificacion+"';",null);
        if (cursor.moveToFirst()){
            do {
                Datos datos = new Datos();
                datos.setNumero(cursor.getInt(0));
                datos.setFecTamitaje(cursor.getString(1));
                datos.setNombreCompleto(cursor.getString(2));
                datos.setTipoID(cursor.getString(3));
                datos.setNumeroId(cursor.getString(4));
                datos.setNombreEPS(cursor.getString(5));
                datos.setiPS(cursor.getString(6));
                datos.setTelefono(cursor.getString(7));
                datos.setDireccion(cursor.getString(8));
                datos.setFecNac(cursor.getString(9));
                datos.setEdad(cursor.getInt(10));
                datos.setEdadCategorizada(cursor.getString(11));
                datos.setGenero(cursor.getString(12));
                datos.setTalla(cursor.getInt(13));
                datos.setPeso(cursor.getInt(14));
                datos.setPerimetroAbdominal(cursor.getInt(15));
                datos.setRealizarActividadFisicaD(cursor.getString(16));
                datos.setFrecuenciaVerdurasFrutas(cursor.getString(17));
                datos.setMedicamentosHipertension(cursor.getString(18));
                datos.setGlucosaAlta(cursor.getString(19));
                datos.setDiabetesFamiliares(cursor.getString(20));
                datos.setImc(cursor.getInt(21));
                datos.setClasificacionIMC(cursor.getString(22));
                datos.setRiesgoDeDiabetes(cursor.getString(23));
                datos.setPresionAS(cursor.getString(24));
                datos.setPresionDiastolica(cursor.getString(25));
                datos.setPresionArterial(cursor.getString(26));
                datos.setDiabetes(cursor.getString(27));
                datos.setFuma(cursor.getString(28));
                datos.setPorcentajeRiesgo(cursor.getString(29));
                datos.setRiesgoCardio(cursor.getString(30));
                datos.setPacientePresentaR(cursor.getString(31));
                datos.setDetalleRiesgoPaciente(cursor.getString(32));
                datos.setRealiza(cursor.getString(33));

                results.add(datos);

            }while (cursor.moveToNext());
        }
        closeDB();
        cursor.close();
        return  results;
    }
    
    public int realizados(){
        openWriteDB();
        int contador=0;
        Cursor cursor1 = db.rawQuery("SELECT REALIZA FROM DATOS1;",null);
        if (cursor1.moveToFirst()){
            do {
                contador++;
            }while (cursor1.moveToNext());
        }
        cursor1.close();
        closeDB();


        return contador;
    }


    public int exportados(){
        openWriteDB();
        int contador=0;
        Cursor cursor1 = db.rawQuery("SELECT ENVIADO FROM DATOS1 WHERE ENVIADO='SI';",null);
        if (cursor1.moveToFirst()){
            do {
                contador++;
            }while (cursor1.moveToNext());
        }
        cursor1.close();
        closeDB();


        return contador;
    }

    public List<Datos> exportarPorRangoDeFecha(String date1, String date2){

        List<Datos> results  = new ArrayList<>();
        openReadDB();
        Cursor cursor = db.rawQuery("SELECT * FROM DATOS1 WHERE FECTAMITAJE BETWEEN '"+date1+"' AND '"+date2+"';",null);
        if (cursor.moveToFirst()){
            do {
                Datos datos = new Datos();
                datos.setNumero(cursor.getInt(0));
                datos.setFecTamitaje(cursor.getString(1));
                datos.setNombreCompleto(cursor.getString(2));
                datos.setTipoID(cursor.getString(3));
                datos.setNumeroId(cursor.getString(4));
                datos.setNombreEPS(cursor.getString(5));
                datos.setiPS(cursor.getString(6));
                datos.setTelefono(cursor.getString(7));
                datos.setDireccion(cursor.getString(8));
                datos.setFecNac(cursor.getString(9));
                datos.setEdad(cursor.getInt(10));
                datos.setEdadCategorizada(cursor.getString(11));
                datos.setGenero(cursor.getString(12));
                datos.setTalla(cursor.getInt(13));
                datos.setPeso(cursor.getInt(14));
                datos.setPerimetroAbdominal(cursor.getInt(15));
                datos.setRealizarActividadFisicaD(cursor.getString(16));
                datos.setFrecuenciaVerdurasFrutas(cursor.getString(17));
                datos.setMedicamentosHipertension(cursor.getString(18));
                datos.setGlucosaAlta(cursor.getString(19));
                datos.setDiabetesFamiliares(cursor.getString(20));
                datos.setImc(cursor.getInt(21));
                datos.setClasificacionIMC(cursor.getString(22));
                datos.setRiesgoDeDiabetes(cursor.getString(23));
                datos.setPresionAS(cursor.getString(24));
                datos.setPresionDiastolica(cursor.getString(25));
                datos.setPresionArterial(cursor.getString(26));
                datos.setGlucometria(cursor.getString(27));
                datos.setDiabetes(cursor.getString(28));
                datos.setFuma(cursor.getString(29));
                datos.setPorcentajeRiesgo(cursor.getString(30));
                datos.setRiesgoCardio(cursor.getString(31));
                datos.setPacientePresentaR(cursor.getString(32));
                datos.setDetalleRiesgoPaciente(cursor.getString(33));
                datos.setRealiza(cursor.getString(34));
                datos.setLatitud(cursor.getString(36));
                datos.setLongitud(cursor.getString(37));
                results.add(datos);

            }while (cursor.moveToNext());
        }
        closeDB();
        cursor.close();
        return  results;

    }

    public void closeDB(){
        db.close();
    }

}
