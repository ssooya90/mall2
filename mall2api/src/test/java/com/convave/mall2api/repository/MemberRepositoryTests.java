package com.convave.mall2api.repository;

import com.convave.mall2api.domain.Member;
import com.convave.mall2api.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testInsertMember() {

		for (int i = 0; i < 10; i++) {

			Member member = Member.builder()
					.email("test" + i + "@test.com")
					.password(passwordEncoder.encode("1111"))
					.nickname("test" + i)
					.build();

			member.addRole(MemberRole.USER);

			if (i >= 5) {
				member.addRole(MemberRole.MANAGER);
			}

			if (i >= 8) {
				member.addRole(MemberRole.ADMIN);
			}

			memberRepository.save(member);

		}

	}

	@Test
	public void testRead () {

		String email = "test0@test.com";

		Member member = memberRepository.getWithRoles(email);

//		Optional<Member> result = memberRepository.findById(idx);
//		Member member = result.orElseThrow();

		log.info(member.toString());




	}


}
