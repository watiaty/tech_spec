package by.waitaty.webrise.service.impl;

import by.waitaty.webrise.common.ErrorMessages;
import by.waitaty.webrise.model.entity.Subscription;
import by.waitaty.webrise.repository.SubscriptionRepository;
import by.waitaty.webrise.service.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Subscription> getTopSubscriptions() {
        log.info("Получение топа подписок");

        return subscriptionRepository.findTopSubscriptions();
    }

    @Override
    @Transactional(readOnly = true)
    public Subscription getSubscription(String name) {
        return subscriptionRepository.findByName(name)
            .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.SUBSCRIPTION_NOT_FOUND));
    }

}
