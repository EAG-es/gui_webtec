/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import innui.webtec.A_ejecutores;
import java.util.Map;

/**
 *
 * @author emilio
 */
public class radios extends A_ejecutores {
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String valor = (String) objects_mapa.get("innui_webtec_gui_autoformularios_valor");
        String valor_anterior = (String) objects_mapa.get("innui_webtec_gui_autoformularios_valor_anterior");
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
