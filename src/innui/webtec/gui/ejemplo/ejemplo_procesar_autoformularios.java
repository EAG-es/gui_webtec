/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui.ejemplo;

import innui.webtec.A_ejecutores;
import innui.webtec.gui.autoformularios_errores;
import java.util.Map;

/**
 *
 * @author emilio
 */
public class ejemplo_procesar_autoformularios extends A_ejecutores {
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        autoformularios_errores autoformulario_error;
        String innui_webtec_gui_autoformularios_errores;
        try {
            // Si al procesar el formulario fuera necesario volver a pedirlo.
            // Este puede generarse automaticamente, si existe el archivo json
            // con el mismo nombre de la clase y los datos que usar para crear el formulario.
            objects_mapa.put("innui_webtec_gui_autoformularios_error",  //NOI18N
                    java.util.ResourceBundle.getBundle("in/innui/webtec/gui/ejemplo/in").getString("EJEMPLO DEL PASO DE UN MENSAJE DE ERROR. ")
            ); 
            autoformulario_error = new autoformularios_errores();
            autoformulario_error.configurar(contexto);
            ret = autoformulario_error.ejecutar(objects_mapa, error);
            innui_webtec_gui_autoformularios_errores = (String) objects_mapa.get("innui_webtec_gui_autoformularios_errores"); //NOI18N
            objects_mapa.put("innui_webtec_gui_ejemplo_ejemplo_procesar_autoformularios_errores", innui_webtec_gui_autoformularios_errores); //NOI18N
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
