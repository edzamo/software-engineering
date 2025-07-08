package ec.software.engineer.architecture.hexagonal.example.transactionscript.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;


@Data
public class Flight {

  @Getter
  private String id;
  private String origin;
  private String destination;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private int availableSeats;
  private BigDecimal price;
  private boolean isAvailable;

  public Flight(String id) {
    this.id = id;
    this.isAvailable = true;
  }

  public Flight(String id, String origin, String destination,
      LocalDateTime departureTime, LocalDateTime arrivalTime, int availableSeats,
      BigDecimal price) {
    this(id);
    this.origin = origin;
    this.destination = destination;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.availableSeats = availableSeats;
    this.price = price;
  }


  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    this.isAvailable = available;
  }

  public String toString() {
    return id;
  }

}

