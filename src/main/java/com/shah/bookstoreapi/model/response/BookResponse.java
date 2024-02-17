package com.shah.bookstoreapi.model.response;

import com.shah.bookstoreapi.model.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * @author NORUL
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse<T> {

    @NonNull
    ResponseStatus status;
    T data;
    String errorMessage;

    public static <T> BookResponse successResponse(T data) {
        return BookResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(data)
                .build();
    }
    public static BookResponse failureResponse(String errorMessage) {
        return BookResponse.builder()
                .status(ResponseStatus.FAILURE)
                .errorMessage(errorMessage)
                .build();
    }
    public static <T> BookResponse failureResponse(T data, String errorMessage) {
        return BookResponse.builder()
                .status(ResponseStatus.FAILURE)
                .data(data)
                .errorMessage(errorMessage)
                .build();
    }
}
