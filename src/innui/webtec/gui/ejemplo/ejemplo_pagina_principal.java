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
 * Clase de ejemplo de la página principal de una aplicación, solo con un menú y la traducción a Inglés
 */
public class ejemplo_pagina_principal extends A_ejecutores {
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
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
    /**
     * Método estático con el código necesario para cargar en el mapa los datos del menú de aplicaciones, y del selector de idioma
     * @param contexto Contexto de la aplicación
     * @param objects_mapa Mapa con los datos que utilizar, más los datos resultantes.
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */
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
