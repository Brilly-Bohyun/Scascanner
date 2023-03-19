package com.scascanner.studycafe.web.pay.response.etc;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Failure {

    private String code;
    private String message;
}
