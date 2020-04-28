package marmas.arithmetic.repository;

import marmas.arithmetic.entity.ResultAttemptEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResultAttemptRepository extends CrudRepository<ResultAttemptEntity, Long> {
    List<ResultAttemptEntity> findAll();
}
