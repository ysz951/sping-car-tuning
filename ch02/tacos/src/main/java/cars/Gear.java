package cars;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Gear {
  
  private final String id;
  private final String name;
  private final Type type;
//  Audio, Interior, Engine, Suspension, Tires
  public static enum Type {
    Audio, Interior, Engine, Suspension, Tires
//    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }

}
