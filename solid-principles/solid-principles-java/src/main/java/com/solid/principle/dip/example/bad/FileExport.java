package com.solid.principle.dip.example.bad;

/**
 * Interfaz para operaciones de exportación de archivos
 */
public interface FileExport {

    void exportCSV();

    void exportPDF();

    void exportDocx();
}
