/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui.ejemplo;

import innui.webtec.A_ejecutores;
import static innui.webtec.gui.autoformularios.k_mapa_autoformulario_error;
import innui.webtec.gui.autoformularios_errores;
import static innui.webtec.gui.autoformularios_errores.k_mapa_autoformulario_errores;
import java.util.Map;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class ejemplo_procesar_autoformularios extends A_ejecutores {
    public static String k_mapa_ejemplo_procesar_autoformularios_errores = "innui_webtec_gui_ejemplo_ejemplo_procesar_autoformularios_errores"; //NOI18N
    
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        autoformularios_errores autoformulario_error;
        String innui_webtec_gui_autoformularios_errores;
        try {
            objects_mapa.put(k_mapa_autoformulario_error,  //NOI18N
                    java.util.ResourceBundle.getBundle("in/innui/webtec/gui/ejemplo/in").getString("EJEMPLO DEL PASO DE UN MENSAJE DE ERROR. ")
            ); 
            autoformulario_error = new autoformularios_errores();
            autoformulario_error.configurar(contexto);
            ret = autoformulario_error.ejecutar(objects_mapa, error);
            innui_webtec_gui_autoformularios_errores = (String) objects_mapa.get(k_mapa_autoformulario_errores); 
            objects_mapa.put(k_mapa_ejemplo_procesar_autoformularios_errores, innui_webtec_gui_autoformularios_errores); //NOI18N
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/ejemplo/in").getString("ERROR AL EJECUTAR EN EJEMPLO_PROCESAR_AUTOFORMULARIOS. {0}"), new Object[] {error[0]});
            ret = false;
        }
        return ret;
    }

}
