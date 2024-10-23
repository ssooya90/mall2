package com.convave.mall2api.repository;

import com.convave.mall2api.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@EntityGraph(attributePaths = {"memberRoleList"})
	@Query("select m from Member m where m.email = :email")
	Member getWithRoles(@Param("email") String email);

}
