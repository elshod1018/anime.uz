package uz.anime.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.anime.domains.Document;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    @Query("select d from Document d where d.deleted = false and d.generatedName ilike ?1")
    Optional<Document> findByGeneratedName(String generatedName);

    @NotNull
    @Query("select d from Document d where d.deleted = false and d.id = ?1")
    Optional<Document> findById(@NotNull Integer id);
}