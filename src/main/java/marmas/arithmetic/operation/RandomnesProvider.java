package marmas.arithmetic.operation;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
class RandomnesProvider {
  public int getRandomNumberFor(int range) {
    if (range <= 1) {
      return 0;
    }
    return new Random().nextInt(range);
  }
}
