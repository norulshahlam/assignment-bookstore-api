package com.shah.bookstoreapi.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shah.bookstoreapi.model.ResponseStatus;
import lombok.*;

/**
 * @author NORUL
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyResponse<T> {

    @NonNull
    ResponseStatus status;
    T data;
    String errorMessage;

    public static <T> MyResponse successResponse(T data) {
        return MyResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(data)
                .build();
    }

    public static MyResponse failureResponse(String errorMessage) {
        return MyResponse.builder()
                .status(ResponseStatus.FAILURE)
                .errorMessage(errorMessage)
                .build();
    }

    public static <T> MyResponse failureResponse(T data, String errorMessage) {
        return MyResponse.builder()
                .status(ResponseStatus.FAILURE)
                .data(data)
                .errorMessage(errorMessage)
                .build();
    }
}
