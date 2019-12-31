/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import innui.contextos.a_eles;
import innui.contextos.filas;
import static innui.html.Urls.completar_URL;
import innui.webtec.A_ejecutores;
import innui.webtec.Rutas;
import innui.webtec.String_webtec_controlador;
import static innui.webtec.Webtec_controlador.k_urls_fila_nombre;
import static innui.webtec.gui.ver_historiales_lineas.k_mapa_nombre_href;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author emilio
 */
public class ver_historiales extends A_ejecutores {
    public static String k_sufijo_lineas = "_lineas"; //NOI18N
    public static String k_protocolo_lineas = "https"; //NOI18N
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        filas urls_fila = null;
        String innui_webtec_ver_historiales = ""; //NOI18N
        String_webtec_controlador string_webtec_controlador;
        string_webtec_controlador = new String_webtec_controlador();
        URL url_plantilla = null;
        String nombre_plantilla = ""; //NOI18N
        String ruta_ver_historiales = ""; //NOI18N
        Map<String, Object> objects_mapa_local = null;
        try {
            urls_fila = (filas) contexto.leer(k_urls_fila_nombre).dar();
            contexto.subir();
            ret = (urls_fila != null);
            if (ret) {
                ruta_ver_historiales = this.getClass().getName();
                ruta_ver_historiales = Rutas.convertir_nombre_clase_a_ruta(ruta_ver_historiales, error);
                ret = (ruta_ver_historiales != null); 
            }
            if (ret) {
                nombre_plantilla = ruta_ver_historiales + k_sufijo_lineas;
                ret = string_webtec_controlador.configurar(contexto, false, error);
            }
            if (ret) {
                url_plantilla = completar_URL(nombre_plantilla, k_protocolo_lineas, error);
                ret = (url_plantilla != null);
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);
                for (a_eles ele: urls_fila) {
                    objects_mapa_local.put(k_mapa_nombre_href, ele.dar());
                    ret = string_webtec_controlador.procesar_url(url_plantilla, objects_mapa_local, error);
                    if (ret == false) {
                        string_webtec_controlador.poner_error(error[0]);
                        innui_webtec_ver_historiales = string_webtec_controlador.contenido;
                        break;
                    }
                    innui_webtec_ver_historiales = innui_webtec_ver_historiales + string_webtec_controlador.contenido;
                }
            }
            if (ret) {
                objects_mapa.put("innui_webtec_gui_ver_historiales", innui_webtec_ver_historiales); //NOI18N
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ERROR EN VER_HISTORIALES. {0}"), new Object[] {error[0]});
            ret = false;
        } finally {
            contexto.bajar();
        }
        return ret;
    }
    
}
