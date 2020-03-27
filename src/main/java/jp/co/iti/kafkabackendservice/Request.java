package jp.co.iti.kafkabackendservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * リクエストデータ用クラス
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    //
    private Long index;
    //
    private String message;
}
