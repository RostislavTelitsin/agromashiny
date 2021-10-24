package ru.agromashiny.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ImgRepository extends JpaRepository<ImgFile, Integer> {
}
