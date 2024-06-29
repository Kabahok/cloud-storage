package ru.egoravdeev.cloud_storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.egoravdeev.cloud_storage.model.File;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Integer> {
    Optional<File> findByFilename(String filename);
}
