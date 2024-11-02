package com.example.seguridad_calidad.services;

import org.springframework.stereotype.Service;

import com.example.seguridad_calidad.Model.RecetaDetail;

import java.util.List;
import java.util.ArrayList;

@Service
public class RecetaDetailService {
    private final List<RecetaDetail> recetaDetail = new ArrayList<>();

    public RecetaDetailService() {

        recetaDetail.add(
                new RecetaDetail("Pizza Margherita", "/img/pizza-margarita.jpg",
                        "220 g. de masa de pizza italiana" + //
                                "    90 g. de queso mozzarella" + //
                                "    Hojas de albahaca fresca" + //
                                "    100 g. de salsa de tomate natural triturado" + //
                                "    Sal (al gusto)" + //
                                "    Una pizca de pimienta negra recién molida" + //
                                "    Aceite de oliva virgen extra",
                        "Si hemos optado por preparar una masa de pizza casera, en el blog podéis encontrar la receta de como la hacemos en casa. Se trata de una masa muy sabrosa y tierna que os animo a que preparéis.\r\n"
                                + //
                                "Una vez que tenemos la masa de pizza preparada, lo primero que hacemos es encender el horno. A tope, lo máximo que de vuestro horno. En mi caso a 250º C durante unos 10 minutos y que esté caliente en el momento de introducir la pizza.",
                        "30 min", "Fácil"));

        recetaDetail.add(
                new RecetaDetail("Tacos al Pastor", "/img/taco2.jpg",
                        "8 Tortillas para Tacos & Fajitas Pancho Villa" + //
                                " 1 kg Carne de cerdo deshuesada" + //
                                " 8 Rodajas de piña" + //
                                " 4 Naranjas" + //
                                " Vinagre de manzana" + //
                                " Paprika" + //
                                " Merkén" + //
                                " Orégano" + //
                                " 1 Sazonador para Carne y Pollo" + //
                                " 1 Diente de ajo" + //
                                " Salsa Ají Chipotle Pancho Villa",
                        "Trozar carne de cerdo en láminas de 1cm. de espesor y disponer en una bandeja para marinar."
                                + //
                                "2" + //
                                "" + //
                                "Disponer en una licuadora, 4 rodajas de piña, el jugo de las 4 naranjas, 4 cucharadas de vinagre de manzana, paprika, merkén, orégano, 1 diente de ajo y 2 cucharadas de Sazonador para Carne y Pollo Pancho Villa."
                                + //
                                "3" + //
                                "" + //
                                "Licuar mezcla hasta crear pasta uniforme." + //
                                "4" + //
                                "" + //
                                "Marinar carne de cerdo al menos 2 horas en el refrigerador con pasta licuada." + //
                                "5" + //
                                "" + //
                                "Preparar los trozos de cerdo marinado y piñas a la plancha hasta que estén bien dorados y listos para servir."
                                + //
                                "6" + //
                                "" + //
                                "Disponer trozos de carne y piña sobre tortillas para tacos Pancho Villa, doblar y servir."
                                + //
                                "7" + //
                                "" + //
                                "Sazonar con Salsa de Ají Chipotle Pancho Villa a gusto, salsa verde y/o jugo de limón fresco."
                                + //
                                "",
                        "45 min", "Media"));
        recetaDetail.add(
                new RecetaDetail("Sushi de Salmón", "/img/sushiMain.webp",
                        "Arroz, salmón fresco, alga nori, wasabi, salsa de soja",
                        "En primer lugar, cubre con film la esterilla para hacer makis. Esto nos permite mantener limpia la esterilla, que luego es un engorro lavar. Parte la hoja de alga nori en dos (basta doblarla varias veces y partirla con la mano). Colócala en un extremo de la esterilla y extiende el arroz, dejando un par de centímetros libres en el extremo. Corta una tira de salmón, de la longitud del alga y en torno a dos centímetros de ancho, y colócala en la mitad. Puedes añadir también aguacate o cualquier otra vedura, pero nosotros no lo hemos hecho. Enrolla la esterilla, comenzando por la parte del arroz, presionando muy suavemente hasta formar un rulo. Luego, en una tabla, corta el maki en porciones individuales. Para estas cantidades deben salir ocho unidades. Sirve con salsa de soja, wasabi y el jengibre marinado. ",
                        "60 min",
                        "Difícil"));

    }

    public RecetaDetail getRecetasDetail(String nombre) {
        System.out.println("Buscando receta con nombre: " + nombre);
        for (RecetaDetail receta : recetaDetail) {
            if (receta.getNombre().equals(nombre)) {
                return receta;
            }
        }
        return null;
    }

}
