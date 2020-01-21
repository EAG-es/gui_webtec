/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import innui.archivos.Utf8;
import innui.json.Textos;
import static innui.webtec.gui.menu_aplicaciones.obtener_ruta_json;
import java.util.Map;

/**
 *
 * @author emilio
 */
public class configuraciones {
    public static String k_nombre_archivo_configuraciones = "configuraciones";
    
    public static boolean leer(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String ruta;        
        String contenidos_texto = ""; //NOI18N
        Map<String, String> [] contenidos_mapas_array = null;
        ruta = obtener_ruta_json(k_nombre_archivo_configuraciones, objects_mapa, error);
        ret = (ruta != null);
        if (ret) {
            contenidos_texto = Utf8.leer(ruta, error);
            contenidos_mapas_array = Textos.leer(contenidos_texto, error);
            ret = (contenidos_mapas_array != null);
        }
        if (ret) {
            for (Map<String, String> contenido: contenidos_mapas_array) {
                objects_mapa.putAll(contenido);
            }
        }
        return ret;
    }
    
    public static boolean leer(Map<String, Object> objects_mapa, Map<String, String> configuraciones_mapa, String[] error) {
        boolean ret = true;
        String ruta;        
        String contenidos_texto = ""; //NOI18N
        Map<String, String> [] contenidos_mapas_array = null;
        ruta = obtener_ruta_json(k_nombre_archivo_configuraciones, objects_mapa, error);
        ret = (ruta != null);
        if (ret) {
            contenidos_texto = Utf8.leer(ruta, error);
            contenidos_mapas_array = Textos.leer(contenidos_texto, error);
            ret = (contenidos_mapas_array != null);
        }
        if (ret) {
            for (Map<String, String> contenido: contenidos_mapas_array) {
                configuraciones_mapa.putAll(contenido);
            }
        }
        return ret;
    }

    public static boolean escribir(Map<String, String> string_mapa, Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String ruta;        
        String contenidos_texto = ""; //NOI18N    
        ruta = obtener_ruta_json(k_nombre_archivo_configuraciones, objects_mapa, error);
        ret = (ruta != null);
        if (ret) {
            contenidos_texto = Textos.escribir(string_mapa, error);          
            ret = (contenidos_texto != null);
        }
        if (ret) {
            ret = Utf8.escribir(ruta, contenidos_texto, error);
        }
        return ret;
    }
    
}
