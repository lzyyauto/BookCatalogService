package org.lzyyauto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zingliu
 * @date 2025/5/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVO<T> {
    private int status;
    private String message;
    private T data;


    public ResponseVO<T> ok(T data) {
        return new ResponseVO<>(200, "success", data);
    }

    public ResponseVO<String> error(String message) {
        return new ResponseVO<>(500, message, null);
    }
}


