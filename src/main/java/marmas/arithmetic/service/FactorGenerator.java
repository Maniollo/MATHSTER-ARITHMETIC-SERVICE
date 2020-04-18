package marmas.arithmetic.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FactorGenerator {
    public int generate(int range) {
        return new Random().nextInt(range + 1);
    }

}
