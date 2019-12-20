/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui.ejemplo;

import innui.webtec.A_ejecutores;
import innui.webtec.gui.menu_aplicaciones;
import java.util.Map;

/**
 *
 * @author emilio
 */
public class ejemplo_pagina_principal extends A_ejecutores {
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        menu_aplicaciones menu_aplicacion;
        try {
            menu_aplicacion = new menu_aplicaciones();
            menu_aplicacion.configurar(contexto);
            ret = menu_aplicacion.ejecutar(objects_mapa, error);
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = "";
            }
            error[0] = "Error al ejecutar en ejemplo_procesar_autoformularios. " + error[0];
            ret = false;
        }
        return ret;
    }

    
}
