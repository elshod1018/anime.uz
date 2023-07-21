package uz.anime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.anime.domains.UserSMS;
import uz.anime.enums.SMSCodeType;

public interface UserSMSRepository extends JpaRepository<UserSMS, Integer> {
    @Query("select u from UserSMS u where u.userId = ?1 and u.type = ?2 and u.toTime > CURRENT_TIMESTAMP and u.expired = false")
    UserSMS findByUserId(Integer userId, SMSCodeType type);
}
