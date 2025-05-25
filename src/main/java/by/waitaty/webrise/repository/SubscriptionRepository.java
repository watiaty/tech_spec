package by.waitaty.webrise.repository;

import by.waitaty.webrise.model.entity.Subscription;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(
        value = """
               SELECT s.*
               FROM subscriptions s
               JOIN users_subscriptions us ON s.id = us.subscription_id
               GROUP BY s.id
               ORDER BY COUNT(us.user_id) DESC
               LIMIT 3
               """,
        nativeQuery = true
    )
    List<Subscription> findTopSubscriptions();

    Optional<Subscription> findByName(String name);

}
