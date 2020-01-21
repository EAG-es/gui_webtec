/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import innui.contextos.filas;
import innui.contextos.i_eles;
import static innui.html.Urls.completar_URL;
import innui.webtec.A_ejecutores;
import innui.webtec.Rutas;
import innui.webtec.String_webtec_controlador;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import static innui.webtec.Webtec_controlador.k_contexto_urls_fila_nombre;

/**
 * Clase para presentar el historial de urls públicas de la aplicación
 */
public class ver_historiales extends A_ejecutores {
    /**
     * Sufijo para pasar datos a la clase que procesa la plantilla para cada línea del historial 
     */
    public static String k_sufijo_lineas = "_lineas"; //NOI18N
    /**
     * Protocolo qeu usar para procesar la url de cada línea del historial
     */
    public static String k_protocolo_lineas = "https"; //NOI18N
    /**
     * Nombre clave en el mapa donde se guarda el texto con las líneas de los historiales.
     */
    public static String k_mapa_ver_historiales = "innui_webtec_gui_ver_historiales"; //NOI18N
    /**
     * Nombre clave utilizado en el mapa que se envía para procesar cada línea del historial.
     */
    public static String k_mapa_ver_historiales_lineas_href = "innui_webtec_gui_ver_historiales_lineas_href";
    
    /**
     * Genera una entrada en el mapa con el texto de los historiales, qeu se utilizará en su plantilla asociada.
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        filas urls_fila = null;
        String innui_webtec_ver_historiales = ""; //NOI18N
        String_webtec_controlador string_webtec_controlador;
        URL url_plantilla = null;
        String nombre_plantilla = ""; //NOI18N
        String ruta_ver_historiales = ""; //NOI18N
        Map<String, Object> objects_mapa_local = null;
        try {
            string_webtec_controlador = new String_webtec_controlador();            
            urls_fila = (filas) contexto.leer(k_contexto_urls_fila_nombre).dar();
            ret = (urls_fila != null);
            if (ret) {
                ruta_ver_historiales = this.getClass().getName();
                ruta_ver_historiales = Rutas.convertir_nombre_clase_a_ruta(ruta_ver_historiales, error);
                ret = (ruta_ver_historiales != null); 
            }
            if (ret) {
                nombre_plantilla = ruta_ver_historiales + k_sufijo_lineas;
                url_plantilla = completar_URL(nombre_plantilla, k_protocolo_lineas, error);
                ret = (url_plantilla != null);
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);
                for (i_eles ele: urls_fila) {
                    objects_mapa_local.put(k_mapa_ver_historiales_lineas_href, ele.dar());
                    ret = string_webtec_controlador.procesar_url_sin_historial(contexto, url_plantilla, objects_mapa_local, error);
                    if (ret == false) {
                        string_webtec_controlador.poner_error(error[0]);
                        innui_webtec_ver_historiales = string_webtec_controlador.contenido;
                        break;
                    }
                    innui_webtec_ver_historiales = innui_webtec_ver_historiales + string_webtec_controlador.contenido;
                }
            }
            if (ret) {
                objects_mapa.put(k_mapa_ver_historiales, innui_webtec_ver_historiales); 
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ERROR EN VER_HISTORIALES. {0}"), new Object[] {error[0]});
            ret = false;
        }
        return ret;
    }
    
}
