package uz.anime.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.anime.domains.Lists;

import java.util.List;
import java.util.Optional;

public interface ListsRepository extends JpaRepository<Lists, Integer> {
    @Query("select l from Lists l where l.typeId = ?1 and l.deleted = false")
    List<Lists> findByTypeId(Integer typeId);

    @NotNull
    @Query("select l from Lists l where l.id = ?1 and l.deleted = false")
    Optional<Lists> findById(@NotNull Integer integer);
}