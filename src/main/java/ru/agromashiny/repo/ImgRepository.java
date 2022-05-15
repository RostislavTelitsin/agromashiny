package ru.agromashiny.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.agromashiny.models.ImgFile;


public interface ImgRepository extends JpaRepository<ImgFile, Integer> {
}
