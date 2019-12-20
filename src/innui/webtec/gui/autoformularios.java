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
    public static String k_parametro_accion = "accion";
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String clave;
        String valor;
        String_webtec_controlador string_webtec_controlador;
        String nombre_clase;
        String nombre_plantilla = "";
        String nombre_accion_plantilla = "";
        String protocolo_accion_plantilla = "";
        Map<String, String> [] gui_autoformularios_mapas_array = null;
        Map<String, Object> objects_mapa_local = null;
        String formulario = "";
        String ruta_json = "";
        String ruta_autoformulario = "";
        URL url_plantilla;
        filas fila;
        a_eles a_ele;
        String cancelacion_url = "";
        String accion = "";
        String contenido = "";
        String mensaje_error = "";
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
                    cancelacion_url = a_ele.dar();
                }
            }
//            nombre_clase = (String) objects_mapa.get(Ejecutores.k_mapa_nombre_clase);
            nombre_clase = this.getClass().getName();
            nombre_clase = nombre_clase.replace(".", "_");
            gui_autoformularios_mapas_array 
                    = (Map<String, String> []) objects_mapa.get("innui_webtec_gui_autoformularios_mapas_array");
            accion = (String) objects_mapa.get(k_parametro_accion);
            ret = (accion != null);
            if (ret == false) {
                error[0] = "Falta el parametro: " + k_parametro_accion + ". ";
            }
            if (ret) {
                protocolo_accion_plantilla = Urls.extraer_protocolo(accion, error);
                ret = (protocolo_accion_plantilla != null);
            }
            if (gui_autoformularios_mapas_array == null) {
                if (ret) {
                    nombre_accion_plantilla = Urls.extraer_path(accion, "", error);
                    ret = (nombre_accion_plantilla != null);
                }
                if (ret) {
                    ruta_json = (String) objects_mapa.get(k_mapa_ruta_base);
                    ruta_json = aumentar_ruta(ruta_json, nombre_accion_plantilla, error);
                    ret = (ruta_json != null);
                }
                if (ret) {
                    contenido = Utf8.leer(ruta_json + ".json", error);
                    ret = (contenido != null);                
                }
                if (ret) {
                    gui_autoformularios_mapas_array = Textos.leer(contenido, error);
                    ret = (gui_autoformularios_mapas_array != null);
                }
            }
            if (ret) {
                ret = string_webtec_controlador.configurar(contexto, false, error);
            }
            if (ret) {
                ruta_autoformulario = this.getClass().getPackage().getName();
                ruta_autoformulario = Rutas.convertir_nombre_clase_a_ruta(ruta_autoformulario, error);
                for (Map <String, String> mapa: gui_autoformularios_mapas_array) {
                    objects_mapa_local = new LinkedHashMap();
                    objects_mapa_local.putAll(objects_mapa);
                    for (Entry<String, String> entry: mapa.entrySet()) {
                        clave = entry.getKey();
                        valor = (String) entry.getValue();
                        if (clave.equals("plantilla")) {
                            nombre_plantilla = aumentar_ruta(ruta_autoformulario, valor, error);
                            ret = (nombre_plantilla != null); 
                            if (ret == false) {
                                break;
                            }
                        } else if (clave.equals("checked")) {
                            valor = "checked";
                        }
                        objects_mapa_local.put(nombre_clase + "_" + clave, valor);
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
                nombre_plantilla = aumentar_ruta(ruta_autoformulario, "ocultos", error);
                ret = (nombre_plantilla != null); 
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);
                url_plantilla = completar_URL(nombre_plantilla, protocolo_accion_plantilla, error);
                objects_mapa_local.put(nombre_clase + "_" + "nombre", "innui_webtec_gui_autoformularios_cancelacion");
                if (objects_mapa.containsKey("innui_webtec_gui_autoformularios_cancelacion")) {
                    cancelacion_url = (String) objects_mapa.get("innui_webtec_gui_autoformularios_cancelacion");
                }
                objects_mapa_local.put(nombre_clase + "_" + "valor", cancelacion_url);
                ret = string_webtec_controlador.procesar_url(url_plantilla, objects_mapa_local, error);
                if (ret == false) {
                    string_webtec_controlador.poner_error(error[0]);
                    formulario = string_webtec_controlador.contenido;
                } else {
                    formulario = formulario + string_webtec_controlador.contenido;
                }
            }
            if (ret) {
                objects_mapa.put("innui_webtec_gui_autoformularios_accion", accion);
                objects_mapa.put("innui_webtec_gui_autoformularios_cancelacion", cancelacion_url);
                objects_mapa.put("innui_webtec_gui_autoformularios", formulario);
                if (mensaje_error.isEmpty()) {
                    mensaje_error = (String) objects_mapa.get("error");
                }
                if (mensaje_error == null) {
                    mensaje_error = "";
                }
                objects_mapa.put("error", mensaje_error);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = "";
            }
            error[0] = "Error en formulario.ejecutar. " + error[0];
            ret = false;
        } finally {
            contexto.bajar();
        }
        return ret;
    }

}
