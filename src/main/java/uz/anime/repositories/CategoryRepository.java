package uz.anime.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.anime.domains.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @NotNull
    Page<Category> findAll(@NotNull Pageable pageable);
    @NotNull
    @Override
    @Query("select c from Category c where c.id = ?1 and c.deleted = false")
    Optional<Category> findById(@NotNull Integer integer);
}