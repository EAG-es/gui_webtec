/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Clase que analiza un texto JSON qeu pueda ajustarse en un array de mapas de claves de texto y contenido de texto.
 */
public class Textos {
    public static String k_formato_fecha = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    /**
     * Lee un texto con contenido JSON y lo ajusta a: Map&lt;String, String>&gt; []
     * @param contenidos_texto Texto JSON
     * @param error Mensaje de error, si lo hay
     * @return Un array de mapas con los datos del texto JSON, o null si hay error
     */
    public static Map<String, String> [] leer(String contenidos_texto, String [] error) {
        boolean ret = true;
        Gson gson = null;
        Map<String, String> [] contenidos_mapas_array = new LinkedHashMap [1];
        Map<String, String> contenidos_mapa = new LinkedHashMap();
        try {
            contenidos_texto = contenidos_texto.trim();
            gson = new GsonBuilder().setDateFormat(k_formato_fecha).create();
            if (contenidos_texto.startsWith("[")) { //NOI18N
                contenidos_mapas_array = gson.fromJson(contenidos_texto, contenidos_mapas_array.getClass());
                ret = (contenidos_mapas_array != null);
            } else {
                contenidos_mapa = gson.fromJson(contenidos_texto, contenidos_mapa.getClass());
                ret = (contenidos_mapa != null);
                if (ret) {
                    contenidos_mapas_array[0] = contenidos_mapa;
                }
            }
        } catch (Exception e) {
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/json/in").getString("ERROR EN LEER EN TEXTOS. {0}"), new Object[] {error[0]});
            ret = false;
        }
        if (ret) {
            return contenidos_mapas_array;
        } else {
            return null;
        }
    }
    
    /**
     * Escribe un texto con contenido JSON a partir de: Map&lt;String, String>&gt;
     * @param contenidos_mapa Un array de mapas con los datos del texto JSON
     * @param error Mensaje de error, si lo hay
     * @return Texto JSON, o null si hay error
     */
    public static String escribir(Map<String, String> contenidos_mapa, String [] error) {
        String retorno = null;
        Gson gson = null;
        try {
            gson = new GsonBuilder().setDateFormat(k_formato_fecha).setPrettyPrinting().create();
            retorno = gson.toJson(contenidos_mapa);
        } catch (Exception e) {
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/json/in").getString("ERROR EN ESCRIBIR EN TEXTOS. {0}"), new Object[] {error[0]});
            retorno = null;
        }
        return retorno;
    }

}
