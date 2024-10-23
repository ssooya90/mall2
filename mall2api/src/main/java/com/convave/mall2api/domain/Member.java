package com.convave.mall2api.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "memberRoleList")
@Table(name = "tbl_member")
@Entity
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;

	@Column(unique=true)
	private String email;

	private String password;

	private String nickname;

	private boolean social;

	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private List<MemberRole> memberRoleList = new ArrayList<>();


	public void addRole(MemberRole role) {memberRoleList.add(role);}

	public void clearRole () { memberRoleList.clear();}

	public void changeNickname(String nickname) {
		this.nickname = nickname;
	}

	public void changeSocial(boolean social) {
		this.social = social;
	}

	public void changePassword(String password) {
		this.password = password;
	}







}
