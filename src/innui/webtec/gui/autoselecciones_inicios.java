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
 * Genera la cabecera de un select que automáticamente sigue la url indicada en el valor de la opción que se selecciona.
 */
public class autoselecciones_inicios extends A_ejecutores {
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        return ret;
    }

}
