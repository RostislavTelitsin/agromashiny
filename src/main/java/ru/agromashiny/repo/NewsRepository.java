package ru.agromashiny.repo;

import org.springframework.data.repository.CrudRepository;
import ru.agromashiny.models.News;


public interface NewsRepository extends CrudRepository<News, Integer> {
}
