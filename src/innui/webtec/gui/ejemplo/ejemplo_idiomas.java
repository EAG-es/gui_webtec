/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui.ejemplo;

import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.reiniciar_mapa;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_parametro_accion;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author emilio
 */
public class ejemplo_idiomas extends A_ejecutores {
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        autoformularios autoformulario;
        String innui_webtec_gui_autoformularios = "";
        Map<String, Object> objects_mapa_local = null;
        Locale locale;
        String lenguaje;
        try {
            autoformulario = new autoformularios();
            autoformulario.configurar(contexto);
            objects_mapa_local = new LinkedHashMap();
            objects_mapa_local.putAll(objects_mapa);
            ret = reiniciar_mapa(objects_mapa_local, error);
            if (ret) {
                locale = Locale.getDefault();
                lenguaje = locale.getLanguage();
                objects_mapa_local.put("idioma", lenguaje);
                objects_mapa_local.put(k_parametro_accion, "https://innui/webtec/gui/ejemplo/ejemplo_procesar_idiomas");
                ret = autoformulario.ejecutar(objects_mapa_local, error);
                if (ret) { 
                    innui_webtec_gui_autoformularios = (String) objects_mapa_local.get("innui_webtec_gui_autoformularios"); //NOI18N
                    objects_mapa.put("innui_webtec_gui_menu_aplicaciones_extras", innui_webtec_gui_autoformularios); //NOI18N
                }
            }
            if (ret == false) {
                objects_mapa.put("innui_webtec_gui_menu_aplicaciones_extras", error[0]); //NOI18N
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error al ejecutar ejemplo_idiomas. " + error[0];
            ret = false;
        }
        return ret;
    }
    
}
