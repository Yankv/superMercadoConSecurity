package com.yank.superMercado.exception;

/**
 * Excepción lanzada cuando un producto no tiene stock suficiente para completar
 * una operación.
 * Esta excepción se utiliza para indicar que la cantidad solicitada excede el
 * stock disponible.
 */
public class InsufficientStockException extends RuntimeException {
    /**
     * Constructor que recibe un mensaje descriptivo del error.
     * 
     * @param message Mensaje que describe el motivo del error de stock
     *                insuficiente.
     */
    public InsufficientStockException(String message) {
        super(message);
    }
}
