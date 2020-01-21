/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import static innui.html.Urls.completar_URL;
import static innui.html.Urls.extraer_path;
import static innui.html.Urls.extraer_protocolo;
import innui.webtec.String_webtec_controlador;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.leer_penultimo_con_distinta_ruta_historial;
import static innui.webtec.Webtec_controlador.leer_ultimo_historial;
import java.util.Map;
import java.net.URLEncoder;
import java.net.URL;
import java.util.LinkedHashMap;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;

/**
 * Clase con la que regenerar un autoformulario; como un texto, dejándolo en el mapa de datos.
 */
public class autoformularios_errores extends A_ejecutores {
    public static String k_mapa_autoformulario_errores = "innui_webtec_gui_autoformularios_errores"; //NOI18N
    /**
     * Genera un dato en el mapa con el texto del autoformulario el el que se ha encocontrado el error.
     * El nombre y el contenido del error no es alterado, y debe estar dentro del mapa de datos.
     * El texto del autoformulario se almacena en el mapa con la clave: k_mapa_autoformulario_errores
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String_webtec_controlador string_webtec_controlador;
        String url_recibida = ""; //NOI18N
        String url_formulario = ""; //NOI18N
        String path_formulario = ""; //NOI18N
        String nombre_accion_plantilla = ""; //NOI18N
        String parametros = ""; //NOI18N
        URL url_plantilla = null;
        String url_protocolo = ""; //NOI18N
        Map<String, Object> objects_mapa_local = null;
        try {
            string_webtec_controlador = new String_webtec_controlador();
            url_recibida = leer_ultimo_historial(contexto, error);
            ret = (url_recibida != null);                                     
            if (ret) {
                url_formulario = leer_penultimo_con_distinta_ruta_historial(contexto, error);
                ret = (url_formulario != null);                   
            }                        
            if (ret) {
                path_formulario = extraer_path(url_formulario, "", error); //NOI18N
                url_protocolo = extraer_protocolo(url_formulario, error);
                ret = (url_protocolo != null);
            }                                     
            if (ret) {
                nombre_accion_plantilla = URLEncoder.encode(url_recibida, "UTF-8"); //NOI18N
                parametros = k_mapa_autoformularios_accion + "=" + nombre_accion_plantilla; //NOI18N
                url_recibida = path_formulario + "?" + parametros; //NOI18N
            }
            if (ret) {
                url_plantilla = completar_URL(url_recibida, url_protocolo, error);
                ret = (url_plantilla != null);
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);
                ret = string_webtec_controlador.procesar_url_sin_historial(contexto, url_plantilla, objects_mapa_local, error);
            }
            if (ret == false) {
                string_webtec_controlador.poner_error(error[0]);
            }
            if (ret) {
                objects_mapa.put(k_mapa_autoformulario_errores, string_webtec_controlador.contenido); //NOI18N
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ERROR AL EJECUTAR EN AUTOFORMULARIOS_ERRORES. {0}"), new Object[] {error[0]});
            ret = false;
        }
        return ret;
    }
    
}
