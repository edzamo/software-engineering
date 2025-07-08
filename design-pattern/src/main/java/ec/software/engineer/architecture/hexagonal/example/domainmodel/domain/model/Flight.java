package ec.software.engineer.architecture.hexagonal.example.domainmodel.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class Flight {
  @Getter
  private String id;
  @Setter
  @Getter
  private Route route;
  @Setter
  @Getter
  private LocalDateTime departureTime;
  @Getter
  private LocalDateTime arrivalTime;
  @Setter
  @Getter
  private int availableSeats;
  @Setter
  @Getter
  private BigDecimal price;
  private boolean isAvailable;
  private List<Booking> bookings;


  public Flight(String id) {
    this.id = id;
    this.isAvailable = true;
  }

  public Flight(String id, Route route, LocalDateTime departureTime,
      LocalDateTime arrivalTime, int availableSeats, BigDecimal price) {
    this(id);
    this.route = route;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.availableSeats = availableSeats;
    this.price = price;
    this.bookings = new ArrayList<>();
  }

  public void addBooking(Booking booking) {
    bookings.add(booking);
    availableSeats -= booking.getPassengers();
  }

  public boolean isAvailable(int passengers) {
    return availableSeats >= passengers;
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
