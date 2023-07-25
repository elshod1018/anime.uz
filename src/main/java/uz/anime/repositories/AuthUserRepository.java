package uz.anime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.anime.domains.AuthUser;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    @Query("select (count(a.id) > 0) from AuthUser a where a.username = ?1 and a.deleted = false ")
    boolean existsByUsername(String username);

    @Query("select a from AuthUser a where a.email = ?1")
    Optional<AuthUser> findByEmail(String email);

    @Query("select a from AuthUser a where a.username = ?1")
    Optional<AuthUser> findByUsername(String username);
}
