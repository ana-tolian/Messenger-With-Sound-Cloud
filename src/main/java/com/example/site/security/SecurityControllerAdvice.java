package com.example.site.security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ServerWebExchange;

//@ControllerAdvice
public class SecurityControllerAdvice {
//    @ModelAttribute
//    Mono<CsrfToken> csrfToken(ServerWebExchange exchange) {
//        Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
//        return csrfToken.doOnSuccess(token -> exchange.getAttributes()
//                .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
//    }
}
