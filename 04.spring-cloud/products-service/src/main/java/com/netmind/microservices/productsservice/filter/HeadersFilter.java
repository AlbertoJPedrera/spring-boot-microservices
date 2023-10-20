package com.netmind.microservices.productsservice.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Enumeration;

import org.springframework.stereotype.Component;

@Component
public class HeadersFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Enumeration headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = httpRequest.getHeader(key);
            if(key.indexOf("added")>=0) System.out.println("****HeadersFilter request headers->" + key + ":" + value);
        }

        Collection<String> headerresNames = httpResponse.getHeaderNames();
        for (String headerName:headerresNames) {
            String value = httpResponse.getHeader(headerName);
            if(headerName.indexOf("added")>=0) System.out.println("****HeadersFilter response headers->" + headerName + ":" + value);
        }

        filterchain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
    }
}
