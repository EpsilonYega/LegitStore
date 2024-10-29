package orderservice.api.order_service.api.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import orderservice.api.order_service.event.OrderPlacedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final JavaMailSender javaMailSender;
    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Получено сообщение от order-placed, содержание: {}", orderPlacedEvent);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("legitstore@email.com");
            messageHelper.setTo(orderPlacedEvent.getEmail());
            messageHelper.setSubject(String.format("Ваш заказ под номером %s успешно создан", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                    Здравствуйте,

                    Ваш заказ под номером %s был успешно создан.

                    С уважением,
                    ЛегитСтор
                    """, orderPlacedEvent.getOrderNumber()));
        };
        try {
//            javaMailSender.send(messagePreparator);
            log.info("Информация о заказе отправлено на почту");
        }
        catch (MailException e) {
            log.error("Произошла ошибка во время отправки email", e);
            throw new RuntimeException("Произошла ошибка во время отправки email от legitstore@email.com", e);
        }
    }
}
