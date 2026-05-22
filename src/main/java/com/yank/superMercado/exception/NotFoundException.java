package com.yank.superMercado.exception;

/**
 * Excepción lanzada cuando no se encuentra algún recurso buscado.
 */
public class NotFoundException extends RuntimeException {
    /**
     * Constructor que recibe un mensaje descriptivo del error.
     * 
     * @param message Mensaje que describe el motivo del error de no encontrado.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
