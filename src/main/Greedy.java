package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Greedy {
    private int cantidadCandidatosConsiderados;
    private int piezasAcumuladas; // Suma acumulada para evitar calcularla en cada iteración
    private List<Maquina> S; // Solución

    public Greedy() {
        this.cantidadCandidatosConsiderados = 0;
        this.piezasAcumuladas = 0;
        this.S = new ArrayList<>();
    }

    /* Estrategia Greedy:
    * - Candidatos:
    * El conjunto inicial incluye todas las máquinas disponibles.
    *
    * - Selección:
    * Se ordenan por cantidad de piezas en orden descendente (Longest Processing Time First) 
    * para reducir la cantidad de máquinas encendidas. Se elige la máquina más productiva 
    * que no exceda el objetivo, repitiendo hasta alcanzar la meta o quedarnos sin opciones.
    * El costo se mide por la cantidad de candidatos evaluados.
    *
    * - Solución:
    * No siempre encuentra la mejor solución ni garantiza alcanzarla, al priorizar decisiones locales.
    *
    * - Complejidad:
    * O(n log n) debido al ordenamiento. Sin sort sería O(n), pero perderíamos la estrategia 
    * para minimizar el número de máquinas activas, lo que podría empeorar el resultado.
    */

    public List<Maquina> greedy(List<Maquina> M, int piezasTotales) {
        // Ordenar las máquinas por piezas desc
        // Complejidad sort O(n log n)
        Collections.sort(M, new ComparadorPiezasDesc()); // Longest Processing Time First

        int i = 0;

        // Complejidad O(n) en el peor de los casos
        while (i < M.size() && !solucion(S, piezasTotales)) {
            Maquina m = M.get(i);
            this.cantidadCandidatosConsiderados++;

            if (factible(S, m, piezasTotales)) {
                S.add(m);
                piezasAcumuladas += m.getPiezas();
            } else {
                i++; // Probar con la siguiente máquina
            }
        }

        if (solucion(S, piezasTotales)) {
            System.out.println("Solución obtenida.");
            System.out.println("  → Secuencia de máquinas: " + this.S);
            System.out.println("  → Piezas producidas: " + this.piezasAcumuladas);
            System.out.println("  → Puestas en funcionamiento: " + this.S.size());
            System.out.println("  → Cantidad de candidatos considerados: " + this.cantidadCandidatosConsiderados);
            return this.S;
        }

        return null;
    }

    private boolean solucion(List<Maquina> S, int piezasTotales) {
        return piezasAcumuladas == piezasTotales;
    }

    // Determina si válido agregar el candidato seleccionado a la solución.
    private boolean factible(List<Maquina> S, Maquina x, int piezasTotales) {
        return (piezasAcumuladas + x.getPiezas() <= piezasTotales);
    }
}