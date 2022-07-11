package com.max.back.common.sercurity.intercept;

import cn.hutool.json.JSONUtil;
import com.max.back.common.http.ResponseResult;
import com.max.back.common.http.StatusCode;
import com.max.back.common.sercurity.anno.AuthIgrone;
import com.max.back.common.sercurity.utils.HttpJwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	private HttpJwtTokenUtil jwtHttpUtil;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info(request.getRequestURI() + "is start");
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Annotation authIgrone =  handlerMethod.getMethodAnnotation(AuthIgrone.class);
       //不需要认证
        if(authIgrone != null) {
        	return true;
        }
        
        boolean res = jwtHttpUtil.validate(request);
		//权限不足
		if(!res) {
			ResponseResult<Object> rrs = ResponseResult.getMessageResult(null,"E001", StatusCode.C405);
			response.getWriter().write(JSONUtil.toJsonStr(rrs));
			return false;
		} else {
			return true;
		}
	}
}
