package com.nelioalves.cursomc.api;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@UtilityClass
public class ResourceUriHelper {

    void addUriResponseHeader(Object resourceId) {
        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resourceId)
                .toUri();

        final var response = getHttpServletResponse();

        response.setHeader(HttpHeaders.LOCATION, uri.toString());
    }

    private HttpServletResponse getHttpServletResponse() {
        final var servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Objects.requireNonNull(servletRequestAttributes);

        final var response = servletRequestAttributes.getResponse();
        Objects.requireNonNull(response);

        return response;
    }

}
