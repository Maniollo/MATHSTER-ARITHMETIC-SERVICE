package marmas.arithmetic.service

import spock.lang.Specification

import java.util.stream.IntStream


class FactorGeneratorSpec extends Specification {
    def "should return a number within range <0,N>"() {
        when: 'generate a big set of numbers'
        FactorGenerator factorGenerator = new FactorGenerator()
        def array = IntStream.range(0, 1000)
                .map({ t -> factorGenerator.generate(20) })
                .toArray()
        then:
        array.every {it in 0..20}
    }
}
