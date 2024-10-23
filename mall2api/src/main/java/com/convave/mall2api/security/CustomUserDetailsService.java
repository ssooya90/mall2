package com.convave.mall2api.security;

import com.convave.mall2api.domain.Member;
import com.convave.mall2api.dto.MemberDTO;
import com.convave.mall2api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("---------------loadUserByUsername--------------");
		log.info("---------------username--------------");
		log.info(username);
		log.info("---------------// username--------------");

		Member member = memberRepository.getWithRoles(username);

		log.info(member.toString());

		MemberDTO memberDTO = new MemberDTO(
				member.getIdx(),
				member.getEmail()
				, member.getPassword()
				, member.getNickname()
				, member.isSocial()
				, member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).toList());


		log.info("LOG CHECK");
		log.info(memberDTO);

		return memberDTO;
	}
}
