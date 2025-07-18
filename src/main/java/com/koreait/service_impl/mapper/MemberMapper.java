package com.koreait.service_impl.mapper;

import com.koreait.service_impl.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    
    // 회원가입
    int insertMember(MemberEntity member);
    
    // 사용자명으로 회원 조회
    MemberEntity findByUsername(@Param("username") String username);
    
    // 이메일로 회원 조회
    MemberEntity findByEmail(@Param("email") String email);
    
    // 회원 정보 수정
    int updateMember(MemberEntity member);
    
    // 회원 정보 조회
    MemberEntity findById(@Param("id") Long id);
} 