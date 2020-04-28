CREATE TABLE result_attempts
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    operation  VARCHAR(20) NOT NULL,
    factor_a   INT         NOT NULL,
    factor_b   INT         NOT NULL,
    result     INT         NOT NULL,
    is_correct BOOLEAN     NOT NULL,
    PRIMARY KEY (id)
);