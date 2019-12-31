/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui.ejemplo;

import innui.contextos.contextos;
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
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/ejemplo/in").getString("ERROR AL EJECUTAR EN EJEMPLO_PAGINA_PRINCIPAL. {0}"), new Object[] {error[0]});
            ret = false;
        }
        return ret;
    }
    
    public static boolean poner_cabecera_en_mapa(contextos contexto, Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        menu_aplicaciones menu_aplicacion;
        ejemplo_idiomas ejemplo_idioma;
        menu_aplicacion = new menu_aplicaciones();
        menu_aplicacion.configurar(contexto);
        ret = menu_aplicacion.ejecutar(objects_mapa, error);
        if (ret) {
            ejemplo_idioma = new ejemplo_idiomas();
            ejemplo_idioma.configurar(contexto);
            ret = ejemplo_idioma.ejecutar(objects_mapa, error);
        }
        return ret;
    }
    
}
