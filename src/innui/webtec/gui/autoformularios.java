/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import static innui.archivos.Rutas.aumentar_ruta;
import innui.archivos.Utf8;
import innui.html.Urls;
import static innui.html.Urls.completar_URL;
import innui.webtec.String_webtec_controlador;
import innui.webtec.A_ejecutores;
import java.util.Map;
import java.util.Map.Entry;
import innui.json.Textos;
import innui.webtec.Rutas;
import static innui.webtec.Webtec_controlador.k_mapa_ruta_base;
import java.net.URL;
import java.util.LinkedHashMap;
import static innui.webtec.Webtec_controlador.leer_penultimo_historial;
import static innui.webtec.chunk.Procesamientos.k_datos_extension;

/**
 * Clase derivada de A_ejecutores qeu construye un formualario a partir de un archivo JSON y los datos contenidos en un mapa.
 * - El archivo JSON debe tener el mismo nombre que el archivo indicado en la url del parámetro: k_mapa_autoformulario_accion, indicado en el mapa.
 - El formato del archivo JSON es un array de objetos que contienen los atributos a usar por las plantillas qeu procesan cada elemento de formulario.
 Los campos obligatorios son: 
 -- "plantilla", cuya clase será procesada, y la plantilla del mismo nombre, también.
 -- "nombre", para identificar el dato que se recive cuando se rellena y envía el formulario.
 Otros campos:
 -- titulo: Texto que usar para ayudar a entender el dato qeu se desea obtener.
 -- valor: Valor por defecto.
 Las plantillas: circulos tienen todos el mismo nombre y distintos valores, y pueden tener un campo: checked
 Las plantillas: checkboxes pueden tener un campo: checked
 Los select usan 3 plantillas: selecciones_inicios, selecciones_opciones y selecciones_finales. Todos con el mismo nombre, distinto valor, y el campo: selected, opcional.
 La plantillas para ustoselecciones son una variación de la de los select: autoselecciones_inicios, selecciones_opciones y selecciones_finales.
 Es posible diseñar nuevas clases y plantillas que configuren nuevos elementos de los formularios.
 El prefijo: ${$in_} y el sufijo: {$_in}, permiten incorporarl un sistema de internacionalización que depende de la clase: innui.webtec.chunk.Procesamientos
 [
     {
         "plantilla":"fechas",
         "nombre":"fecha",
         "valor":"",
         "titulo":"{$in_}Fecha{$_in}"
     },
     {
         "plantilla":"lineas",
         "nombre":"texto",
         "valor":"",
         "titulo":"{$in_}Introduzca, un texto:{$_in}"
     }
 ]
 */
public class autoformularios extends A_ejecutores {
    /**
     * Campo JSON
     */
    public static String k_campo_plantilla = "plantilla"; //NOI18N    
    /**
     * Campo JSON
     */
    public static String k_campo_valor = "valor"; //NOI18N    
    /**
     * Campo JSON
     */
    public static String k_campo_valor_anterior = "valor_anterior"; //NOI18N    
    /**
     * Campo JSON
     */
    public static String k_campo_nombre = "nombre"; //NOI18N    
    /**
     * Campo JSON
     */
    public static String k_campo_checked = "checked"; //NOI18N    
    /**
     * Campo JSON
     */
    public static String k_campo_selected = "selected"; //NOI18N    
    /**
     * Valor posible del campo JSON: plantilla
     */
    public static String k_dato_plantilla_ocultos = "ocultos"; //NOI18N    
    /**
     * Valor posible del campo JSON: plantilla
     */
    public static String k_dato_plantilla_radios = "radios"; //NOI18N    
    /**
     * Valor posible del campo JSON: plantilla
     */
    public static String k_dato_plantilla_checkboxes = "checkboxes"; //NOI18N    
    /**
     * Sufijo que se utiliza para pasar datos en el mapa, en referencia con la url de la acción enlazada con el formulario
     */
    public static String k_sufijo_accion = "accion"; //NOI18N
    /**
     * Sufijo que se utiliza para pasar datos en el mapa, en referencia con la url de la cancelación enlazada con el formulario
     */
    public static String k_sufijo_cancelacion = "cancelacion"; //NOI18N
    /**
     * Sufijo que se utiliza para pasar datos en el mapa, con un mensaje de error
     */
    public static String k_sufijo_error = "error"; //NOI18N
    /**
     * Nombre del parámetro del mapa que indica el archivo que procesará el formulario, y cuyo nombre coincide con el del archivo JSON qeu utilizar para construir el formulario.
     */
    public static String k_mapa_autoformulario_accion = "innui_webtec_gui_autoformularios_accion"; //NOI18N
    /**
     * Nombre de la clave que por defecto que utilizar para poner el texto de un mensaje de error en el formulario
     */
    public static String k_mapa_autoformulario_error = "innui_webtec_gui_autoformularios_error"; //NOI18N   
    /**
     * Nombre de la clave para poner la url de cancelación en el formulario
     */
    public static String k_mapa_autoformulario_cancelacion = "innui_webtec_gui_autoformularios_cancelacion"; //NOI18N   
    /**
     * Nombre de la clave para poner el valor de un elemento del formulario
     */
    public static String k_mapa_autoformulario_valor = "innui_webtec_gui_autoformularios_valor";
    /**
     * Nombre de la clave para poner el valor anterior de un elemento del formulario
     */
    public static String k_mapa_autoformulario_valor_anterior = "innui_webtec_gui_autoformularios_valor_anterior";
    /**
     * Nombre de la clave para poner el valor: selected, en un elemento del formulario
     */
    public static String k_mapa_autoformulario_selected = "innui_webtec_gui_autoformularios_selected";
    /**
     * Nombre de la clave para poner el texto del formulario construido
     */
    public static String k_mapa_autoformulario = "innui_webtec_gui_autoformularios"; //NOI18N   
    /**
     * Código para la construcción de un formulario automáticamenta a partir de un archivo JSON
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String clave;
        String valor;
        String_webtec_controlador string_webtec_controlador;
        String nombre_clase = ""; //NOI18N
        String nombre_plantilla = ""; //NOI18N
        String nombre_accion_plantilla = ""; //NOI18N
        String protocolo_accion_plantilla = ""; //NOI18N
        Map<String, Object> objects_mapa_local = null;
        Map<String, String> [] json_mapa_array = null;
        String formulario = ""; //NOI18N
        String ruta_json = ""; //NOI18N
        String ruta_autoformulario = ""; //NOI18N
        URL url_plantilla;
        String cancelacion_url_texto = ""; //NOI18N
        String accion = ""; //NOI18N
        String contenido = ""; //NOI18N
        String mensaje_error = ""; //NOI18N
        try {
            string_webtec_controlador = new String_webtec_controlador();
            cancelacion_url_texto = leer_penultimo_historial(contexto, error);
            if (cancelacion_url_texto == null) {
                cancelacion_url_texto = "";
            }
            if (ret) {
                nombre_clase = this.getClass().getName();
                nombre_clase = nombre_clase.replace(".", "_"); //NOI18N
                accion = (String) objects_mapa.get(k_mapa_autoformulario_accion);
                ret = (accion != null);
            }
            if (ret == false) {
                error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("FALTA EL PARAMETRO: {0}. "), new Object[] {k_mapa_autoformulario_accion});
            }
            if (ret) {
                protocolo_accion_plantilla = Urls.extraer_protocolo(accion, error);
                ret = (protocolo_accion_plantilla != null);
            }
            if (ret) {
                nombre_accion_plantilla = Urls.extraer_path(accion, "", error); //NOI18N
                ret = (nombre_accion_plantilla != null);
            }
            if (ret) {
                ruta_json = obtener_ruta_json(nombre_accion_plantilla, objects_mapa, error);
            }
            if (ret) {
                contenido = Utf8.leer(ruta_json, error); //NOI18N
                ret = (contenido != null);                
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);                
                json_mapa_array = poner_json_en_mapa(contenido, objects_mapa_local, error);
                ret = (json_mapa_array != null);
            } 
            if (ret) {
                ruta_autoformulario = obtener_carpeta_clases(nombre_accion_plantilla, objects_mapa, error);           
                ret = (ruta_autoformulario != null);
            } 
            if (ret) {
                for (Map <String, String> mapa: json_mapa_array) {
                    objects_mapa_local = new LinkedHashMap();
                    objects_mapa_local.putAll(objects_mapa);
                    for (Entry<String, String> entry: mapa.entrySet()) {
                        clave = entry.getKey();
                        valor = (String) entry.getValue();
                        if (clave.equals(k_campo_plantilla)) { 
                            nombre_plantilla = aumentar_ruta(ruta_autoformulario, valor, error);
                            ret = (nombre_plantilla != null); 
                            if (ret == false) {
                                break;
                            }
                        } else if (clave.equals(k_campo_checked)) { 
                            valor = k_campo_checked; 
                        } else if (clave.equals(k_campo_selected)) { 
                            valor = k_campo_selected; 
                        }
                        objects_mapa_local.put(nombre_clase + "_" + clave, valor); //NOI18N
                    }
                    if (ret == false) {
                        break;
                    }
                    url_plantilla = completar_URL(nombre_plantilla, protocolo_accion_plantilla, error);
                    ret = string_webtec_controlador.procesar_url_sin_historial(contexto, url_plantilla, objects_mapa_local, error);
                    if (ret == false) {
                        string_webtec_controlador.poner_error(error[0]);
                        formulario = string_webtec_controlador.contenido;
                        break;
                    } else {
                        formulario = formulario + string_webtec_controlador.contenido;
                    }
                }
            }
            if (ret) {
                nombre_plantilla = aumentar_ruta(ruta_autoformulario, k_dato_plantilla_ocultos, error); //NOI18N
                ret = (nombre_plantilla != null); 
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);
                url_plantilla = completar_URL(nombre_plantilla, protocolo_accion_plantilla, error);
                objects_mapa_local.put(nombre_clase + "_" + k_campo_nombre, k_mapa_autoformulario_cancelacion); //NOI18N
                if (objects_mapa.containsKey(k_mapa_autoformulario_cancelacion)) { //NOI18N
                    cancelacion_url_texto = (String) objects_mapa.get(k_mapa_autoformulario_cancelacion); //NOI18N
                }
                objects_mapa_local.put(nombre_clase + "_" + k_campo_valor, cancelacion_url_texto); //NOI18N
                ret = string_webtec_controlador.procesar_url_sin_historial(contexto, url_plantilla, objects_mapa_local, error);
                if (ret == false) {
                    string_webtec_controlador.poner_error(error[0]);
                    formulario = string_webtec_controlador.contenido;
                } else {
                    formulario = formulario + string_webtec_controlador.contenido;
                }
            }
            if (ret) {
                objects_mapa.put(nombre_clase + "_" + k_sufijo_accion, accion); //NOI18N
                objects_mapa.put(nombre_clase + "_" + k_sufijo_cancelacion, cancelacion_url_texto); //NOI18N
                objects_mapa.put(nombre_clase, formulario); //NOI18N
                if (mensaje_error.isEmpty()) {
                    mensaje_error = (String) objects_mapa.get(k_mapa_autoformulario_error); //NOI18N
                }
                if (mensaje_error == null) {
                    mensaje_error = ""; //NOI18N
                }
                objects_mapa.put(nombre_clase + "_" + k_sufijo_error, mensaje_error); //NOI18N
            } else {
                objects_mapa.put(nombre_clase, error[0]);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ERROR EN FORMULARIO.EJECUTAR. {0}"), new Object[] {error[0]});
            ret = false;
        }
        return ret;
    }
    /**
     * Obtiene la ruta con el nombre del archivo JSON que define el autoformulario.
     * @param nombre_accion_sin_extension Ruta de url descrita en la acción del formulario
     * @param objects_mapa Datos adicionales
     * @param error Mensaje de error, si lo hay.
     * @return La ruta absoluta del archivo json, null si hay algún error
     */
    public String obtener_ruta_json(String nombre_accion_sin_extension, Map<String, Object> objects_mapa, String [] error) {
        String retorno = null;
        retorno = (String) objects_mapa.get(k_mapa_ruta_base);
        retorno = aumentar_ruta(retorno, nombre_accion_sin_extension, error);
        if (retorno != null) {
            retorno = retorno + k_datos_extension;
        }
        return retorno;
    }
    /**
     * Obtiene la ruta con el nombre de la carpeta de clases de los componentes del autoformulario.
     * @param nombre_accion_sin_extension Ruta de url descrita en la acción del formulario
     * @param objects_mapa Datos adicionales
     * @param error mMnsaje de error, si lo hay.
     * @return La ruta con el nombre delas carpetas de clases de los componentes del autoformulario, null si hay algún error
     */    
    public String obtener_carpeta_clases(String nombre_accion_sin_extension, Map<String, Object> objects_mapa, String [] error) {
        String retorno = null;
        retorno = this.getClass().getPackage().getName();
        retorno = Rutas.convertir_nombre_clase_a_ruta(retorno, error);
        return retorno;
    }  
    /**
     * Procesa un texto JSON de generación de autoformulario y carga los datos en un mapa.
     * @param json_texto Texto JSON
     * @param objects_mapa Mapa donde obtener datos que incorporar al mapa resultante.
     * @param error mensaje de error, si lo hay.
     * @return Un mapa con los datos del archivo JSON más los valores pasados en: objects_mapa, relacionados por: "nombre".
     * Si sustituye datos "valor", crea un " valor_anterior"
     * Con las plantillas: "radios" siempre crea un "valor_anterior"
     * Con las plantillas: "checkboxes" elimina el parametro "checked" 
     * si hay parámetros leidos de una query de autoformulario, 
     * o existe algún parámetro con el mismo nombre que algún elemento del JSON
     */
    public Map<String, String> [] poner_json_en_mapa(String json_texto, Map<String, Object> objects_mapa, String [] error) {
        boolean ret = true;
        String nombre;
        String valor;
        String valor_anterior;
        String plantilla;
        Map<String, String> [] retorno_mapas_array = new Map [1];
        boolean hay_parametros = false;
        try {
            if (ret) {
                retorno_mapas_array = Textos.leer(json_texto, error);
                ret = (retorno_mapas_array != null);
            }            
            if (ret) {
                valor = (String) objects_mapa.get(k_mapa_autoformulario_cancelacion);
                if (valor != null) {
                    hay_parametros = true;
                }
                if (hay_parametros == false) {
                    for (Map<String, String> mapa: retorno_mapas_array) {
                        nombre = mapa.get(k_campo_nombre); //NOI18N
                        valor = (String) objects_mapa.get(nombre);
                        if (valor != null) {
                            hay_parametros = true;
                            break;
                        }
                    }
                }
                for (Map<String, String> mapa: retorno_mapas_array) {
                    nombre = mapa.get(k_campo_nombre); //NOI18N
                    if (nombre == null) {
                        ret = false;
                        error[0] = java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("NO SE ENCUENTRA EL ATRIBUTO: NOMBRE. ");
                        break;
                    }
                    valor_anterior = mapa.get(k_campo_valor);
                    valor = (String) objects_mapa.get(nombre);
                    if (valor != null) {
                        mapa.put(k_campo_valor_anterior, valor_anterior); //NOI18N
                        mapa.put(k_campo_valor, valor); //NOI18N
                    } else {
                        plantilla = mapa.get(k_campo_plantilla); 
                        if (plantilla.equals(k_dato_plantilla_radios)) {
                            mapa.put(k_campo_valor_anterior, null); //NOI18N
                            mapa.put(k_campo_valor, valor_anterior); //NOI18N
                        } else if (plantilla.equals(k_dato_plantilla_checkboxes)) { //NOI18N
                            if (hay_parametros) {
                                if (mapa.containsKey(k_campo_checked)) { //NOI18N
                                    mapa.remove(k_campo_checked); //NOI18N
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ERROR AL GENERAR_PARAMETROS_URL EN AUTOFORMULARIOS_ERRORES. {0}"), new Object[] {error[0]});
            ret = false;
            retorno_mapas_array = null;
        }
        return retorno_mapas_array;
    }

}
