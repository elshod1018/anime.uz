package uz.anime.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.anime.domains.Anime;

import java.util.Optional;

public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    @NotNull
    @Query("select a from Anime a where a.id = ?1 and a.deleted = false")
    Optional<Anime> findById(@NotNull Integer integer);

    @NotNull
    @Query("select a from Anime a where a.deleted = false")
    Page<Anime> findAll(@NotNull Pageable pageable);
}