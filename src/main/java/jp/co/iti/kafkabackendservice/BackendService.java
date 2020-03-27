package jp.co.iti.kafkabackendservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Map;

// データ受信側サービス（リクエストを Kafka から pull して、そのレスポンスを再度 Kafka に push する）クラス
@Component
public class BackendService {
    //
    private static final Logger logger = LoggerFactory.getLogger(BackendService.class);

    // @KafkaListener の指定により、指定の topic からのメッセージの pull を実行
    // @SendTo の指定によって、当メソッドのレスポンスが、そのままレスポンス用の topic に push されることになる
    @KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
    @SendTo("${kafka.reply.topic}")
    public String handle(
            @Payload String requestValue,
            @Headers Map<String, String> headers) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Request request = mapper.readValue(requestValue, Request.class);

        // 受け取ったメッセージを元に加工した文字列を、レスポンスメッセージとして設定
        Reply reply =
                new Reply(request.getIndex(), request.getMessage() + ". done!");

        String replyValue = mapper.writeValueAsString(reply);
        logger.info("#### request messsage: " + requestValue +
                ", request header: " + headers.toString() +
                ", reply message: " + replyValue);

        return replyValue;
    }
}
