package by.waitaty.webrise.controller;

import by.waitaty.webrise.mapper.SubscriptionMapper;
import by.waitaty.webrise.model.entity.Subscription;
import by.waitaty.webrise.service.SubscriptionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    @GetMapping("/top")
    public List<Subscription> getTopSubscriptions() {
        return subscriptionService.getTopSubscriptions();
    }

}
