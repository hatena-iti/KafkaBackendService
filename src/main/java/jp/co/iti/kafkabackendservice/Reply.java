package jp.co.iti.kafkabackendservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * レスポンスデータ用クラス
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    //
    private Long index;
    //
    private String message;
}
