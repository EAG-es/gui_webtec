/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import innui.webtec.A_ejecutores;
import java.util.Map;

/**
 * Clase de autoformulario, con plantilla asociada
 * Genera un input radio HTML
 */
public class radios extends A_ejecutores {
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String valor = (String) objects_mapa.get("innui_webtec_gui_autoformularios_valor");
        String valor_anterior = (String) objects_mapa.get("innui_webtec_gui_autoformularios_valor_anterior");
        String checked = (String) objects_mapa.get("innui_webtec_gui_autoformularios_checked");
        if (checked == null) {
            objects_mapa.put("innui_webtec_gui_autoformularios_checked", "");
        }
        if (valor != null && valor_anterior != null) {
            if (valor.equals(valor_anterior)) {
                objects_mapa.put("innui_webtec_gui_autoformularios_checked", "checked");
            } else {
                objects_mapa.put("innui_webtec_gui_autoformularios_checked", "");
                objects_mapa.put("innui_webtec_gui_autoformularios_valor", valor_anterior);
            }
        }
        return ret;
    }
    
}
