package com.simple.StES.intercepter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.simple.StES.vo.memVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        memVo member = (memVo) session.getAttribute("memVo");

        String requestURI = request.getRequestURI();
        
        // 보호된 리소스의 경로를 예시로 작성
        boolean isProtected = requestURI.startsWith("/itemBasket/insert.do");

        // 보호된 리소스에 대해 로그인이 필요하고, 로그인 페이지와 로그인 처리 페이지가 아닌 경우만 리디렉션 처리
        if (member == null && isProtected && !requestURI.startsWith("/mem/login") && !requestURI.startsWith("/login")) {
            response.sendRedirect("/mem/login");
            return false; // 요청 처리 중단
        }

        return true; // 요청 계속 진행
    }
	
}
