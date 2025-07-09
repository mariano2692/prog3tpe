package main;
import java.util.ArrayList;
import java.util.List;

public class Backtracking {
    private int cantidadEstadosGenerados;
    private int piezasAcumuladas; // Suma acumulada para evitar calcularla en cada iteración
    private int piezasSolucion; // Suma final de piezas en mejorSolucion
    private List<Maquina> mejorSolucion;

    public Backtracking() {
        this.cantidadEstadosGenerados = 0;
        this.piezasAcumuladas = 0;
        this.piezasSolucion = 0;
        this.mejorSolucion = new ArrayList<>();
    }

    /* Estrategia Backtracking:
    * - Árbol de exploración:
    * Se construyen todas las combinaciones posibles de máquinas que sumen la cantidad exacta de piezas.
    * Cada nodo representa una elección parcial.
    *
    * - Estados:
    * Un estado final ocurre al recorrer todas las máquinas. Es solución si la suma de piezas alcanza justo el objetivo.
    *
    * - Podas:
    * Se descarta una rama si al agregar una máquina se supera el total necesario, o si la solución actual 
    * ya no puede mejorar a la mejor encontrada en cuanto a cantidad de máquinas.
    *
    * - Objetivo:
    * Conservar la combinación que requiera la menor cantidad de máquinas encendidas.
    *
    * - Complejidad:
    * En el peor caso es O(n^k), con n máquinas y k la profundidad del árbol.
    */

    public List<Maquina> backtracking(List<Maquina> M, int piezasTotales) {
        List<Maquina> solucionActual = new ArrayList<>();

       // CORRECCIÓN
       // Se añadió el parámetro índice al método recursivo para que el bucle arranque desde esa posición,
       // evitando volver a considerar las mismas máquinas y así prevenir estados y soluciones duplicadas.
       // Esto también mejora la eficiencia del backtracking.
        backtracking(M, piezasTotales, solucionActual, 0);

        System.out.println("Solución obtenida.");
        System.out.println("  → Secuencia de máquinas: " + this.mejorSolucion);
        System.out.println("  → Piezas producidas: " + this.piezasSolucion);
        System.out.println("  → Puestas en funcionamiento: " + this.mejorSolucion.size());
        System.out.println("  → Cantidad de estados generados: " + this.cantidadEstadosGenerados);

        return this.mejorSolucion;
    }

    private void backtracking(List<Maquina> M, int piezasTotales, List<Maquina> solucionActual, int indice) {
        cantidadEstadosGenerados++;

        // Caso base: Producir las piezas totales
        if (piezasAcumuladas == piezasTotales) {
            if (mejorSolucion.isEmpty() || solucionActual.size() < mejorSolucion.size()) {
                mejorSolucion = new ArrayList<>(solucionActual);
                piezasSolucion = piezasAcumuladas;
            }
            return;
        }

        // Complejidad O(n), siendo n la cantidad de máquinas a recorrer a partir de la posición índice
        for (int i = indice; i < M.size(); i++) {
            Maquina m = M.get(i);

            // Poda considerando la cantidad de piezas
            if (piezasAcumuladas + m.getPiezas() <= piezasTotales) {
                // Intento de asignación
                solucionActual.add(m);
                piezasAcumuladas += m.getPiezas();

                // CORRECCIÓN
                // Poda considerando tamaño de solucion actual vs mejor encontrada hasta el momento
                if (mejorSolucion.isEmpty() || solucionActual.size() < mejorSolucion.size()) {
                    // Recursión
                    backtracking(M, piezasTotales, solucionActual, i);
                }

                // Backtrack
                solucionActual.remove(solucionActual.size() - 1);
                piezasAcumuladas -= m.getPiezas();
            }
        }
    }
}