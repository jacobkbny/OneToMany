package com.tistory.jacobcloud.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tistory.jacobcloud.model.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
