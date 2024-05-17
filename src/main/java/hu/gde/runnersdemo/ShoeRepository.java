package hu.gde.runnersdemo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShoeRepository extends JpaRepository<ShoeEntity, Long> {
    List<ShoeEntity> findByRunner(RunnerEntity runner);
}