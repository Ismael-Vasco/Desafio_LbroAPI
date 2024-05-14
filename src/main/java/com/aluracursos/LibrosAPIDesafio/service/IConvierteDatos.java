package com.aluracursos.LibrosAPIDesafio.service;

public interface IConvierteDatos {

    // para retornar datos genericos, es decir, una cosa u otra
    <T> T obtenerDatos(String json, Class<T> clase);

}
