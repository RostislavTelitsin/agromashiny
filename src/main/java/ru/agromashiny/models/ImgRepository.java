package ru.agromashiny.models;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ImgRepository extends JpaRepository<ImgFile, Integer> {
}
