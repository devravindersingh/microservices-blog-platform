package com.ravinder.api.gateway.filter;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TracingFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(TracingFilter.class);

    @Autowired
    private Tracer tracer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Span currentSpan = tracer.currentSpan();
        if(currentSpan != null){
            String traceId = currentSpan.context().traceId();
            String spanId = currentSpan.context().spanId();
            String path = exchange.getRequest().getPath().toString();
            String method = exchange.getRequest().getMethod().toString();
            logger.info("Incoming request: {} {} with traceId: {}, spanId: {}", method, path, traceId, spanId);
        } else {
            logger.info("No tracing context for request: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getPath());
        }

        return chain.filter(exchange);
    }
}
