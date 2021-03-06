package marmas.arithmetic.service;

import org.springframework.stereotype.Service;

@Service
public class AdditionResultAttemptService {
    public boolean verifyAdditionAttempt(int factorA, int factorB, int attempt) {
        return factorA + factorB == attempt;
    }
}
