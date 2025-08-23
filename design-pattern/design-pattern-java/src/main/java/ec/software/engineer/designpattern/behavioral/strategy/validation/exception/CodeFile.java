package ec.software.engineer.designpattern.behavioral.strategy.validation.exception;


import lombok.experimental.UtilityClass;

@UtilityClass
public final class CodeFile {


  public static final String CODE_VALIDATION_TABS_OK = "ok.tsv.tab.000";
  public static final String CODE_VALIDATION_TABS_ERROR = "error.tsv.tab.001";
  public static final String VALIDATION_TABS_ERROR_DETAIL = "Error: Delimitador de columnas incorrecto: El delimitador de columnas deberá ser 'tabulador'";
  public static final String CODE_VALIDATION_HEADER_OK = "ok.tsv.header.001";
  public static final String CODE_VALIDATION_HEADER_ERROR = "error.tsv.header.002";
  public static final String VALIDATION_HEADER_ERROR_DETAIL = "Error: El archivo no contiene las columnas requeridas";
  public static final String CODE_VALIDATION_ROWS_EXIT_OK = "ok.tsv.rowsExist.001";
  public static final String CODE_VALIDATION_ROWS_EXIT_ERROR = "error.tsv.RowsExist.002";
  public static final String VALIDATION_ROWS_EXIT_ERROR_DETAIL = "Error: El archivo no contiene las columnas requeridas";
  public static final int ERROR_CODE_VALIDATION_FILE_EXCEPTION = 9001;
}
