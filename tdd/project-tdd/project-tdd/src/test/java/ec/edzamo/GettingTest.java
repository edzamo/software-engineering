package ec.edzamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;

class GettingTest {


  @Test
   void shouldReturnAnInstanceGreetings(){
    Getting getting = new Getting();
    assertNotNull(getting);

  }

  @Test
  void  shouldReturnHelloWhenCallInstanceGreeting(){
    Getting getting = new Getting();
    String cad= getting.sayHello();
    assertEquals("hello word", cad);

  }

}
