package marmas.arithmetic.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import marmas.arithmetic.model.MathOperationType;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "result_attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultAttemptEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "factor_a")
    private int factorA;

    @Column(name = "factor_b")
    private int factorB;

    @Column(name = "operation")
    @Convert(converter = MathOperationJpaConverter.class)
    private MathOperationType operation;

    @Column(name = "result")
    private int result;

    @Column(name = "is_correct")
    private boolean correct;
}
