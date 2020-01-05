/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import static innui.archivos.Rutas.aumentar_ruta;
import innui.archivos.Utf8;
import innui.contextos.i_eles;
import innui.json.Textos;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.k_mapa_nombre_plantilla;
import static innui.webtec.chunk.Procesamientos.k_datos_extension;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import static innui.webtec.chunk.Procesamientos.k_ruta_plantillas;

/**
 * Clase que genera un menu de aplicaciones, y los fragmentos de plantilla necesarios para incorporarlo en otras plantillas
 * El menú consiste en un buscador de palabras de menú, un listado de acciones dentro del menú, y de los objetos que aparecen en el menú.
 * - Contenido del menú: Se describe en un archivo JSON (con el nombre menu_aplicaciones)
 * Este se localiza, por defecto, a partir de la información del nombre de la plantilla (k_mapa_nombre_plantilla) que incorpora la clase Procesamientos de: webtec_lib
 * la internacionalización se añade automaticamente. 
 * El contenido JSON es un array de objetos, uno por opción del menú, con los campos:
 * -- texto: Frase con: verbo_en_infinitivo_(acción) objetos_(ninguno_uno_o_mas)
 * -- prefijo: HTML de un enlace con la URL que realiza la acción. (Le sigue el texto preparado para la intenacionalizacion con {$in_} y {$_in})
 * -- sufijo: Parte final del HTML
 * - acción de menú: La primera palabra de cada opción del menú
 * - objetos del menú: Las segundas y siguientes palabras de cada opción de menú
 */
public class menu_aplicaciones extends A_ejecutores {
    /**
     * Clave del mapa y el contexto para guardar el array de contenidos del menú
     */
    public static String k_mapa_contenido_array = "innui_webtec_gui_menu_aplicaciones_contenido_array"; //NOI18N
    /**
     * Clave del mapa y el contexto para guardar el array de acciones del menú
     */
    public static String k_mapa_accion_array = "innui_webtec_gui_menu_aplicaciones_accion_array"; //NOI18N
    /**
     * Clave del mapa y el contexto para guardar el array de objetos del menú
     */
    public static String k_mapa_objeto_array = "innui_webtec_gui_menu_aplicaciones_objeto_array"; //NOI18N
    /**
     * Clave del mapa donde almacenar código HTML adicional para el menú
     */
    public static String k_mapa_extras = "innui_webtec_gui_menu_aplicaciones_extras"; 
    /**
     * Clave del campo texto, de cada opción del menú del archivo JSON
     */
    public static String k_nombre_campo_json_texto = "texto"; //NOI18N 
    /**
     * Clave del campo texto cada entrada del array JSON generado con los array de acciones y objetos
     */
    public static String k_nombre_campo_texto = "\"texto\": \""; //NOI18N 
    /**
     * Clave del campo prefijo cada entrada del array JSON generado con los array de acciones y objetos
     */
    public static String k_nombre_campo_prefijo = "\"prefijo\": "; //NOI18N 
    /**
     * Clave del campo infijo cada entrada del array JSON generado con los array de acciones y objetos
     */
    public static String k_nombre_campo_infijo = "\"infijo\": "; //NOI18N 
    /**
     * Clave del campo sufijo cada entrada del array JSON generado con los array de acciones y objetos
     */
    public static String k_nombre_campo_sufijo = "\"sufijo\": "; //NOI18N 
    /**
     * Dato del campo prefijo cada entrada del array JSON generado con los array de acciones
     */
    public static String k_prefijo_accion = "\"<a href='' class='innui_webtec_gui_menu_aplicaciones_a' onclick=\\\"innui_webtec_gui_menu_aplicaciones_encontrar_contenido('\","; //NOI18N
    /**
     * Dato del campo infijo cada entrada del array JSON generado con los array de acciones
     */
    public static String k_infijo_accion = "\"'); return false;\\\" >\","; //NOI18N
    /**
     * Dato del campo sufijo cada entrada del array JSON generado con los array de acciones
     */
    public static String k_sufijo_accion = "\"</a>&nbsp;&nbsp;&nbsp;&nbsp;\""; //NOI18N
    /**
     * Dato del campo prefijo cada entrada del array JSON generado con los array de objetos
     */
    public static String k_prefijo_objeto = "\"<a href='' class='innui_webtec_gui_menu_aplicaciones_a' onclick=\\\"innui_webtec_gui_menu_aplicaciones_encontrar_contenido('\","; //NOI18N
    /**
     * Dato del campo infijo cada entrada del array JSON generado con los array de objetos
     */
    public static String k_infijo_objeto = "\"'); return false;\\\" >\","; //NOI18N
    /**
     * Dato del campo sufijo cada entrada del array JSON generado con los array de objetos
     */
    public static String k_sufijo_objeto = "\"</a>&nbsp;&nbsp;&nbsp;&nbsp;\""; //NOI18N    

    /**
     * Genera los valores de contenidoes, acciónes y objetos que son manejados por el código javascript de la plantilla asociada.
     * La plantilla asociada puede incluir los códigos javascript y HTML, por defecto, definidos en: menu_aplicaciones.c.html
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String ruta;
        String contenidos_texto = ""; //NOI18N
        String acciones_texto = ""; //NOI18N
        String objetos_texto = ""; //NOI18N
        Map<String, String> [] contenidos_mapas_array = null;
        i_eles ele_contenido_array;
        i_eles ele_accion_array;
        i_eles ele_objeto_array;
        try {
            ele_contenido_array = contexto.leer(k_mapa_contenido_array); //NOI18N
            ele_accion_array = contexto.leer(k_mapa_accion_array); //NOI18N
            ele_objeto_array = contexto.leer(k_mapa_objeto_array); //NOI18N
            if (ele_contenido_array.es_nulo()) {
                ruta = obtener_ruta_json(getClass().getSimpleName(), objects_mapa, error);
                ret = (ruta != null);
                if (ret) {
                    contenidos_texto = Utf8.leer(ruta, error);
                    contenidos_mapas_array = Textos.leer(contenidos_texto, error);
                    ret = (contenidos_mapas_array != null);
                }
                if (ret) {
                    int i = 0;
                    String contenido_texto = ""; //NOI18N
                    Set<String> acciones_set = new TreeSet();
                    Set<String> objetos_set = new TreeSet();
                    String [] palabras;
                    for (Map<String, String> contenido: contenidos_mapas_array) {
                        contenido_texto = contenido.get(k_nombre_campo_json_texto); //NOI18N
                        palabras = contenido_texto.split("\\s"); //NOI18N
                        i = 0;
                        for (String palabra : palabras) {
                            if (i == 0) {
                                acciones_set.add( 
                                        "{"
                                        + k_nombre_campo_texto + palabra + "\"," //NOI18N 
                                        + k_nombre_campo_prefijo + k_prefijo_accion
                                        + k_nombre_campo_infijo + k_infijo_accion
                                        + k_nombre_campo_sufijo + k_sufijo_accion
                                        + "}"); //NOI18N
                            } else {
                                objetos_set.add( 
                                        "{"
                                        + k_nombre_campo_texto + palabra + "\"," //NOI18N 
                                        + k_nombre_campo_prefijo + k_prefijo_objeto
                                        + k_nombre_campo_infijo + k_infijo_objeto
                                        + k_nombre_campo_sufijo + k_sufijo_objeto
                                        + "}"); //NOI18N
                            }
                            i = i + 1;
                        }
                    }
                    acciones_texto = "["; //NOI18N
                    for (String texto: acciones_set) {
                        if (acciones_texto.endsWith("}")) { //NOI18N
                            acciones_texto = acciones_texto + ", "; //NOI18N
                        }
                        acciones_texto = acciones_texto + texto;
                    }                
                    acciones_texto = acciones_texto + "]"; //NOI18N
                    objetos_texto = "["; //NOI18N
                    for (String texto: objetos_set) {
                        if (objetos_texto.endsWith("}")) { //NOI18N
                            objetos_texto = objetos_texto + ", "; //NOI18N
                        }
                        objetos_texto = objetos_texto + texto;
                    }                 
                    objetos_texto = objetos_texto + "]"; //NOI18N
                    objects_mapa.put(k_mapa_contenido_array, contenidos_texto); //NOI18N
                    objects_mapa.put(k_mapa_accion_array, acciones_texto); //NOI18N
                    objects_mapa.put(k_mapa_objeto_array, objetos_texto); //NOI18N
                    contexto.fondear_con_datos(k_mapa_contenido_array, contenidos_texto //NOI18N
                        , k_mapa_accion_array, acciones_texto //NOI18N
                        , k_mapa_objeto_array, objetos_texto); //NOI18N
                }            
            } else {
                objects_mapa.put(k_mapa_contenido_array, ele_contenido_array.dar()); //NOI18N
                objects_mapa.put(k_mapa_accion_array, ele_accion_array.dar()); //NOI18N
                objects_mapa.put(k_mapa_objeto_array, ele_objeto_array.dar()); //NOI18N
            }
        } catch (Exception e) {
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ERROR EN EJECUTAR. {0}"), new Object[] {error[0]});
            ret = false;
        }
        return ret;
    }
    /**
     * Obtiene la ruta y nombre del archivo JSON qeu describe el menú de aplicaciones.
     * @param nombre_archivo Nombre del archivo JSON
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return La ruta del archivo, null si hay error.
     */
    public static String obtener_ruta_json(String nombre_archivo, Map<String, Object> objects_mapa, String[] error) {
        String retorno = null;
        File file;
        URL url;
        try {
            retorno = (String) objects_mapa.get(k_mapa_nombre_plantilla);
            retorno = aumentar_ruta(k_ruta_plantillas, retorno, error);
            if (retorno != null) {
                file = new File(retorno);
                retorno = file.getParent();
                retorno = aumentar_ruta(retorno, nombre_archivo + k_datos_extension, error);
            }
            if (retorno != null) {
                url = menu_aplicaciones.class.getResource(retorno);
                file = new File(url.toURI());
                retorno = file.getAbsolutePath();
            }
        } catch (Exception e) {
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ERROR EN OBTENER_RUTA_JSON. {0}"), new Object[] {error[0]});
            retorno = null;
        }
        return retorno;
    }

}
