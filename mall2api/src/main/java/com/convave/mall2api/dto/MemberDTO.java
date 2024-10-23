package com.convave.mall2api.dto;

import com.convave.mall2api.domain.MemberRole;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDTO extends User {

	private Long idx;

	private String email;

	private String password;

	private String nickname;
	
	private boolean social;

	private List<String> memberRoleList = new ArrayList<>();

	public MemberDTO(Long idx, String email, String password, String nickname, boolean social, List<String> memberRoleList){
		super(
				email,
				password,
				memberRoleList.stream().map(str -> new SimpleGrantedAuthority("ROLE_"+str)).collect(Collectors.toList())
		);

		this.idx = idx;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.social = social;
		this.memberRoleList = memberRoleList;
	}

	public Map<String, Object> getClaims() {

		Map<String, Object> dataMap = new HashMap<>();

		dataMap.put("idx", idx);
		dataMap.put("email", email);
		dataMap.put("nickname", nickname);
		dataMap.put("password", password);
		dataMap.put("social", social);
		dataMap.put("memberRoleList", memberRoleList);

		return dataMap;

	}

}
