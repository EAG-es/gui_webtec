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
 *
 * @author emilio
 */
public class Textos {
    
    public static Map<String, String> [] leer(String contenidos_texto, String [] error) {
        boolean ret = true;
        Gson gson = null;
        Map<String, String> [] contenidos_mapas_array = new LinkedHashMap [1];
        Map<String, String> contenidos_mapa = new LinkedHashMap();
        try {
            contenidos_texto = contenidos_texto.trim();
            if (contenidos_texto.startsWith("[")) { //NOI18N
                gson = new GsonBuilder().create();
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
    
}
