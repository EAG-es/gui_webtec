/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import static ingui.javafx.webtec.FXML_webtecController.k_urls_fila_nombre;
import static innui.archivos.Rutas.aumentar_ruta;
import innui.archivos.Utf8;
import innui.contextos.a_eles;
import innui.contextos.filas;
import innui.html.Urls;
import static innui.html.Urls.completar_URL;
import innui.webtec.String_webtec_controlador;
import innui.webtec.A_ejecutores;
import java.util.Map;
import java.util.Map.Entry;
import innui.json.Textos;
import static innui.webtec.Ejecutores.k_mapa_ruta_base;
import innui.webtec.Rutas;
import java.net.URL;
import java.util.LinkedHashMap;

public class autoformularios extends A_ejecutores {
    public static String k_parametro_accion = "accion"; //NOI18N
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String clave;
        String valor;
        String_webtec_controlador string_webtec_controlador;
        String nombre_clase;
        String nombre_plantilla = ""; //NOI18N
        String nombre_accion_plantilla = ""; //NOI18N
        String protocolo_accion_plantilla = ""; //NOI18N
        Map<String, Object> objects_mapa_local = null;
        Map<String, String> [] json_mapa_array = null;
        String formulario = ""; //NOI18N
        String ruta_json = ""; //NOI18N
        String ruta_autoformulario = ""; //NOI18N
        URL url_plantilla;
        filas fila;
        a_eles a_ele;
        String cancelacion_url_texto = ""; //NOI18N
        String accion = ""; //NOI18N
        String contenido = ""; //NOI18N
        String mensaje_error = ""; //NOI18N
        try {
            contexto.subir();
            string_webtec_controlador = new String_webtec_controlador();
            a_ele = contexto.leer(k_urls_fila_nombre);
            fila = a_ele.dar();
            int tam = fila.size();
            tam = tam - 2;
            if (tam >= 0) {
                a_ele = fila.get(tam);
                if (a_ele != null) {
                    cancelacion_url_texto = a_ele.dar();
                }
            }
            nombre_clase = this.getClass().getName();
            nombre_clase = nombre_clase.replace(".", "_"); //NOI18N
            accion = (String) objects_mapa.get(k_parametro_accion);
            ret = (accion != null);
            if (ret == false) {
                error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("FALTA EL PARAMETRO: {0}. "), new Object[] {k_parametro_accion});
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
                ruta_json = (String) objects_mapa.get(k_mapa_ruta_base);
                ruta_json = aumentar_ruta(ruta_json, nombre_accion_plantilla, error);
                ret = (ruta_json != null);
            }
            if (ret) {
                contenido = Utf8.leer(ruta_json + ".json", error); //NOI18N
                ret = (contenido != null);                
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);                
                json_mapa_array = poner_json_en_mapa(contenido, objects_mapa_local, error);
                ret = (json_mapa_array != null);
            } 
            if (ret) {
                ret = string_webtec_controlador.configurar(contexto, false, error);
            }
            if (ret) {
                ruta_autoformulario = this.getClass().getPackage().getName();
                ruta_autoformulario = Rutas.convertir_nombre_clase_a_ruta(ruta_autoformulario, error);                
                for (Map <String, String> mapa: json_mapa_array) {
                    objects_mapa_local = new LinkedHashMap();
                    objects_mapa_local.putAll(objects_mapa);
                    for (Entry<String, String> entry: mapa.entrySet()) {
                        clave = entry.getKey();
                        valor = (String) entry.getValue();
                        if (clave.equals("plantilla")) { //NOI18N
                            nombre_plantilla = aumentar_ruta(ruta_autoformulario, valor, error);
                            ret = (nombre_plantilla != null); 
                            if (ret == false) {
                                break;
                            }
                        } else if (clave.equals("checked")) { //NOI18N
                            valor = "checked"; //NOI18N
                        } else if (clave.equals("selected")) { //NOI18N
                            valor = "selected"; //NOI18N
                        }
                        objects_mapa_local.put(nombre_clase + "_" + clave, valor); //NOI18N
                    }
                    if (ret == false) {
                        break;
                    }
                    url_plantilla = completar_URL(nombre_plantilla, protocolo_accion_plantilla, error);
                    ret = string_webtec_controlador.procesar_url(url_plantilla, objects_mapa_local, error);
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
                nombre_plantilla = aumentar_ruta(ruta_autoformulario, "ocultos", error); //NOI18N
                ret = (nombre_plantilla != null); 
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);
                url_plantilla = completar_URL(nombre_plantilla, protocolo_accion_plantilla, error);
                objects_mapa_local.put(nombre_clase + "_" + "nombre", "innui_webtec_gui_autoformularios_cancelacion"); //NOI18N
                if (objects_mapa.containsKey("innui_webtec_gui_autoformularios_cancelacion")) { //NOI18N
                    cancelacion_url_texto = (String) objects_mapa.get("innui_webtec_gui_autoformularios_cancelacion"); //NOI18N
                }
                objects_mapa_local.put(nombre_clase + "_" + "valor", cancelacion_url_texto); //NOI18N
                ret = string_webtec_controlador.procesar_url(url_plantilla, objects_mapa_local, error);
                if (ret == false) {
                    string_webtec_controlador.poner_error(error[0]);
                    formulario = string_webtec_controlador.contenido;
                } else {
                    formulario = formulario + string_webtec_controlador.contenido;
                }
            }
            if (ret) {
                objects_mapa.put(nombre_clase + "_" + "accion", accion); //NOI18N
                objects_mapa.put(nombre_clase + "_" + "cancelacion", cancelacion_url_texto); //NOI18N
                objects_mapa.put(nombre_clase, formulario); //NOI18N
                if (mensaje_error.isEmpty()) {
                    mensaje_error = (String) objects_mapa.get("innui_webtec_gui_autoformularios_error"); //NOI18N
                }
                if (mensaje_error == null) {
                    mensaje_error = ""; //NOI18N
                }
                objects_mapa.put(nombre_clase + "_" + "error", mensaje_error); //NOI18N
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
        } finally {
            contexto.bajar();
        }
        return ret;
    }

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
                    valor = (String) objects_mapa.get("innui_webtec_gui_autoformularios_cancelacion");
                    if (valor != null) {
                        hay_parametros = true;
                    }
                    if (hay_parametros == false) {
                        for (Map<String, String> mapa: retorno_mapas_array) {
                        nombre = mapa.get("nombre"); //NOI18N
                        valor = (String) objects_mapa.get(nombre);
                        if (valor != null) {
                            hay_parametros = true;
                            break;
                        }
                    }
                }
                for (Map<String, String> mapa: retorno_mapas_array) {
                    nombre = mapa.get("nombre"); //NOI18N
                    if (nombre == null) {
                        ret = false;
                        error[0] = java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("NO SE ENCUENTRA EL ATRIBUTO: NOMBRE. ");
                        break;
                    }
                    valor_anterior = mapa.get("valor");
                    valor = (String) objects_mapa.get(nombre);
                    if (valor != null) {
                        mapa.put("valor_anterior", valor_anterior); //NOI18N
                        mapa.put("valor", valor); //NOI18N
                    } else {
                        plantilla = mapa.get("plantilla"); 
                        if (plantilla.equals("radios")) {
                            mapa.put("valor_anterior", null); //NOI18N
                            mapa.put("valor", valor_anterior); //NOI18N
                        } else if (plantilla.equals("checkboxes")) { //NOI18N
                            if (hay_parametros) {
                                if (mapa.containsKey("checked")) { //NOI18N
                                    mapa.remove("checked"); //NOI18N
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
