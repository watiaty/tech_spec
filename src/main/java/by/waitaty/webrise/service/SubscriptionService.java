package by.waitaty.webrise.service;

import by.waitaty.webrise.model.entity.Subscription;
import java.util.List;

public interface SubscriptionService {

    List<Subscription> getTopSubscriptions();

    Subscription getSubscription(String name);

}
