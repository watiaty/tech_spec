package by.waitaty.webrise.repository;

import by.waitaty.webrise.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "subscriptions")
    Optional<User> findWithSubscriptionsById(Long id);

    @Modifying
    @Query(value = "DELETE FROM users_subscriptions WHERE user_id = :id AND subscription_id = :subscriptionId", nativeQuery = true)
    void deleteSubscriptionByIdAndSubscriptionId(@Param("id") Long id, @Param("subscriptionId") Long subscriptionId);

}
