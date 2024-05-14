package com.aluracursos.LibrosAPIDesafio.principal;

import com.aluracursos.LibrosAPIDesafio.model.Datos;
import com.aluracursos.LibrosAPIDesafio.model.DatosLibros;
import com.aluracursos.LibrosAPIDesafio.service.ConsumoAPI;
import com.aluracursos.LibrosAPIDesafio.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    //variables
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    Scanner teclado = new Scanner(System.in);
    
    public void muestraMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);

        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println("datos: "+datos);

        // top 10 libros más descargados
        System.out.println("\nTOP 10 LIBROS MÁS DESCARGADOS\n");
        datos.libros().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        // Busqueda de libros por nombre
        System.out.println("Ingrese el nombre del libro que desea leer: ");
        var busqueda = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE+"?search="+busqueda.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(busqueda.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado");
        }

        // Estadisticas de los libros
        DoubleSummaryStatistics est = datos.libros().stream()
                .filter(d -> d.numeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));

        System.out.println("Cantidad media de descargas: " +est.getAverage());
        System.out.println("Cantidad máxima de descargar: " +est.getMax());
        System.out.println("Cantidad mínima de descargas: " + est.getMin());
        System.out.println("Cantidad de libros: "+est.getCount());


        // libros por fechas
        System.out.println("""
                \nDebes ingresar las fechas de inicio y fin de busqueda:
                                    1900 a 2015
                          Buscará los libros de 1900 al 2015
                """);
        System.out.println("Ingrese la fecha inicial de busqueda: ");
        var fechaInicial = teclado.nextInt();
        System.out.println("Ingrese la fecha final de busqueda: ");
        var fechafinal = teclado.nextInt();
        json = consumoAPI.obtenerDatos(URL_BASE+
                "?author_year_start="+fechaInicial+
                "&author_year_end="+fechafinal);

        var librosPorFecha = conversor.obtenerDatos(json, Datos.class);
        librosPorFecha.libros().stream()
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

    }
}
