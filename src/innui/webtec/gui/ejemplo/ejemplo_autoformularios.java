/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui.ejemplo;

import innui.webtec.A_ejecutores;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.ejemplo.ejemplo_pagina_principal.poner_cabecera_en_mapa;
import java.util.Map;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;

/**
 * Calse de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class ejemplo_autoformularios extends A_ejecutores {
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        autoformularios autoformulario;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                if (objects_mapa.containsKey(k_mapa_autoformularios_error) == false) { //NOI18N
                    objects_mapa.put(k_mapa_autoformularios_error, ""); //NOI18N
                }
                autoformulario = new autoformularios();
                autoformulario.configurar(contexto);
                ret = autoformulario.ejecutar(objects_mapa, error);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/ejemplo/in").getString("ERROR AL EJECUTAR EN EJEMPLO_AUTOFORMULARIOS. {0}"), new Object[] {error[0]});
            ret = false;
        }
        return ret;
    }

    
}
