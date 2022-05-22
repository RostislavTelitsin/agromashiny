package ru.agromashiny.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.agromashiny.models.VisitCounter;

public interface CounterRepository extends CrudRepository<VisitCounter, Integer> {

}
