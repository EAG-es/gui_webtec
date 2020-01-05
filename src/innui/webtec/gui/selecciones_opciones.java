/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import innui.webtec.A_ejecutores;
import static innui.webtec.gui.autoformularios.k_mapa_autoformulario_selected;
import static innui.webtec.gui.autoformularios.k_mapa_autoformulario_valor;
import static innui.webtec.gui.autoformularios.k_mapa_autoformulario_valor_anterior;
import java.util.Map;

/**
 * Clase de autoformulario, con plantilla asociada
 * Genera las etiquetas option de un select
 */
public class selecciones_opciones extends A_ejecutores {

    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String valor = (String) objects_mapa.get(k_mapa_autoformulario_valor);
        String valor_anterior = (String) objects_mapa.get(k_mapa_autoformulario_valor_anterior);
        String selected = (String) objects_mapa.get(k_mapa_autoformulario_selected);
        if (selected == null) {
            objects_mapa.put(k_mapa_autoformulario_selected, "");
        }        
        if (valor != null && valor_anterior != null) {
            if (valor.equals(valor_anterior)) {
                objects_mapa.put(k_mapa_autoformulario_selected, "selected");
            } else {
                objects_mapa.put(k_mapa_autoformulario_selected, "");
                objects_mapa.put(k_mapa_autoformulario_valor, valor_anterior);
            }
        }
        return ret;
    }
    
}
