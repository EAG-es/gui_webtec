/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import innui.contextos.a_eles;
import innui.contextos.filas;
import static innui.html.Urls.completar_URL;
import static innui.html.Urls.extraer_path;
import static innui.html.Urls.extraer_protocolo;
import innui.webtec.String_webtec_controlador;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.k_urls_fila_nombre;
import java.util.Map;
import java.net.URLEncoder;
import java.net.URL;
import java.util.LinkedHashMap;

/**
 *
 * @author emilio
 */
public class autoformularios_errores extends A_ejecutores {
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String_webtec_controlador string_webtec_controlador;
        filas fila;
        a_eles a_ele;
        String url_recibida = ""; //NOI18N
        String url_formulario = ""; //NOI18N
        String path_formulario = ""; //NOI18N
        String nombre_accion_plantilla = ""; //NOI18N
        String nombre_accion_protocolo = ""; //NOI18N
        URL url_accion_plantilla = null;
        String parametros = ""; //NOI18N
        URL url_plantilla = null;
        String url_protocolo = ""; //NOI18N
        Map<String, Object> objects_mapa_local = null;
        try {
            contexto.subir();
            string_webtec_controlador = new String_webtec_controlador();
            a_ele = contexto.leer(k_urls_fila_nombre);
            fila = a_ele.dar();             
            int tam = fila.size();
            tam = tam - 1;
            if (tam >= 0) {
                a_ele = fila.get(tam);
                if (a_ele != null) {
                    url_recibida = a_ele.dar();
                    nombre_accion_plantilla = extraer_path(url_recibida, "", error); //NOI18N
                    ret = (nombre_accion_plantilla != null);
                    if (ret) {
                        nombre_accion_protocolo = extraer_protocolo(url_recibida, error);
                        ret = (nombre_accion_protocolo != null);
                    }                                     
                }
            } else {
                ret = false;
                error[0] = java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("NO SE HA PODIDO OBTENER LA URL RECIBIDA. ");
            }
            if (ret) {
                while (true) {
                    tam = tam - 1;
                    if (tam < 0) {
                        ret = false;
                        error[0] = java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("NO SE HA PODIDO OBTENER LA URL DEL FORMULARIO. ");
                        break;
                    }
                    a_ele = fila.get(tam);
                    if (a_ele != null) {
                        url_formulario = a_ele.dar();
                        path_formulario = extraer_path(url_formulario, "", error); //NOI18N
                        ret = (path_formulario != null);
                        if (ret == false) {
                            break;
                        }
                        if (path_formulario.startsWith(nombre_accion_plantilla) == false) { // url distinta de la del formulario
                            break;
                        }
                    }                
                }
            }
            if (ret) {
                ret = string_webtec_controlador.configurar(contexto, false, error);
            }                            
            if (ret) {
                url_protocolo = extraer_protocolo(url_formulario, error);
                ret = (url_protocolo != null);
            }                                     
            if (ret) {
                url_accion_plantilla = completar_URL(nombre_accion_plantilla, nombre_accion_protocolo, error);
                ret = (url_accion_plantilla != null);
            }
            if (ret) {
                nombre_accion_plantilla = url_accion_plantilla.toExternalForm();
                nombre_accion_plantilla = URLEncoder.encode(nombre_accion_plantilla, "UTF-8"); //NOI18N
                parametros = url_accion_plantilla.getQuery();
                if (parametros == null) {
                    parametros = "";
                } else {
                    parametros = "&" + parametros;
                }
                parametros = "accion=" + nombre_accion_plantilla + parametros; //NOI18N
                url_recibida = path_formulario + "?" + parametros; //NOI18N
            }
            if (ret) {
                url_plantilla = completar_URL(url_recibida, url_protocolo, error);
                ret = (url_plantilla != null);
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);
                ret = string_webtec_controlador.procesar_url(url_plantilla, objects_mapa_local, error);
            }
            if (ret == false) {
                string_webtec_controlador.poner_error(error[0]);
            }
            if (ret) {
                objects_mapa.put("innui_webtec_gui_autoformularios_errores", string_webtec_controlador.contenido); //NOI18N
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ERROR AL EJECUTAR EN AUTOFORMULARIOS_ERRORES. {0}"), new Object[] {error[0]});
            ret = false;
        } finally {
            contexto.bajar();
        }
        return ret;
    }
    
}
