/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui.ejemplo;

import innui.contextos.a_eles;
import innui.contextos.filas;
import innui.webtec.A_ejecutores;
import innui.webtec.String_webtec_controlador;
import static innui.webtec.Webtec_controlador.k_urls_fila_nombre;
import static innui.webtec.Webtec_controlador.reiniciar_mapa;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author emilio
 */
public class ejemplo_procesar_idiomas extends A_ejecutores {
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String_webtec_controlador string_webtec_controlador;
        Map<String, Object> objects_mapa_local = null;
        String idioma = "";
        URL url_plantilla;
        filas fila;
        a_eles a_ele;        
        Locale nuevo_locale;
        String previa_url_texto = ""; //NOI18N
        try {
            contexto.subir();
            a_ele = contexto.leer(k_urls_fila_nombre);
            fila = a_ele.dar();
            int tam = fila.size();
            tam = tam - 2;
            if (tam >= 0) {
                a_ele = fila.get(tam);
                if (a_ele != null) {
                    previa_url_texto = a_ele.dar();
                }
            }   
            fila.removeLast(); // Interfiere con la repetición de la penúltima url llamada (a veces consulta con la antepenúltima)
            string_webtec_controlador = new String_webtec_controlador();
            ret = string_webtec_controlador.configurar(contexto, false, error);
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);                               
                ret = reiniciar_mapa(objects_mapa_local, error);
            }
            if (ret) {
                idioma = (String) objects_mapa.get("idioma");
                nuevo_locale = new Locale(idioma);
                Locale.setDefault(nuevo_locale);
                url_plantilla = new URL(previa_url_texto);
                ret = string_webtec_controlador.procesar_url(url_plantilla, objects_mapa_local, error);
                objects_mapa.put("innui_webtec_gui_ejemplo_ejemplo_procesar_idiomas", string_webtec_controlador.contenido); //NOI18N
            } else {
                objects_mapa.put("innui_webtec_gui_ejemplo_ejemplo_procesar_idiomas", error[0]); //NOI18N
            }               
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/ejemplo/in").getString("ERROR AL EJECUTAR EN EJEMPLO_PROCESAR_AUTOFORMULARIOS. {0}"), new Object[] {error[0]});
            ret = false;
        } finally {
            contexto.bajar();
        }
        return ret;
    }

}
