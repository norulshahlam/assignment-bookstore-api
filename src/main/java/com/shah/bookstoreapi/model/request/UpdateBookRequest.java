package com.shah.bookstoreapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookRequest extends CreateBookRequest {


    private UUID isbn;

}
