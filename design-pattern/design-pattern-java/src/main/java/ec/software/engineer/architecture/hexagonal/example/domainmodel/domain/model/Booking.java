package ec.software.engineer.architecture.hexagonal.example.domainmodel.domain.model;

import lombok.Data;

@Data
public class Booking {
  private String id;
  private User user;
  private Flight flight;
  private int passengers;

  public Booking(String id, User user, Flight flight, int passengers) {
    this.id = id;
    this.user = user;
    this.flight = flight;
    this.passengers = passengers;
  }

}
