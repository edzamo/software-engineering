package ec.software.engineer.designpattern.behavioral.strategy.validation.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Generated
@JsonPropertyOrder({"SERVICIO", "CTA_EMPRESA", "CONTRAPARTIDA", "MONEDA", "MONTO", "OPERACION",
    "COD_BCO", "TIPO_CTA", "NUM_CTA", "TIPO_ID", "ID", "NOMBRE", "BASE_RENTA", "COD_RENTA",
    "MONTO_RENTA_RETENIDO", "BASE_IVA", "COD_IVA", "MONTO_IVA_RETENIDO", "DESCRIPCION", "EMAIL"})
public class PaymentFile {

  @JsonProperty("SERVICIO")
  private String servicio;
  @JsonProperty("CTA_EMPRESA")
  private String ctaEmpresa;
  @JsonProperty("CONTRAPARTIDA")
  private String contrapartida;
  @JsonProperty("MONEDA")
  private String moneda;
  @JsonProperty("MONTO")
  private String monto;
  @JsonProperty("OPERACION")
  private String operacion;
  @JsonProperty("COD_BCO")
  private String codBco;
  @JsonProperty("TIPO_CTA")
  private String tipoCta;
  @JsonProperty("NUM_CTA")
  private String numCta;
  @JsonProperty("TIPO_ID")
  private String tipoId;
  @JsonProperty("ID")
  private String id;
  @JsonProperty("NOMBRE")
  private String nombre;
  @JsonProperty("BASE_RENTA")
  private String baseRenta;
  @JsonProperty("COD_RENTA")
  private String codRenta;
  @JsonProperty("MONTO_RENTA_RETENIDO")
  private String montoRentaRetenido;
  @JsonProperty("BASE_IVA")
  private String baseIva;
  @JsonProperty("COD_IVA")
  private String codIva;
  @JsonProperty("MONTO_IVA_RETENIDO")
  private String montoIvaRetenido;
  @JsonProperty("DESCRIPCION")
  private String descripcion;
  @JsonProperty("EMAIL")
  private String email;


}
