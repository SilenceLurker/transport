package xyz.silencelurker.file.transport.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

/**
 * 该过滤器用于设置响应字符集编码
 * 
 * @author Silence_Lurker
 */
@Component
@WebFilter("/*")
public class CharacterFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

}
