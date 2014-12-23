package com.scnet.iyiming.repository;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import com.scnet.iyiming.entity.user.User;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

	@Override
	public Long getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
			return ((User) authentication.getPrincipal()).getId();
		}
		return (Long) RequestContextHolder.getRequestAttributes().getAttribute("USER_ID", 1);
	}
}
