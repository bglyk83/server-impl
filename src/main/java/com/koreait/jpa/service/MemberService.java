package com.koreait.jpa.service;

import com.koreait.jpa.dto.LoginRequestDto;
import com.koreait.jpa.dto.LoginResponseDto;
import com.koreait.jpa.dto.SignupRequestDto;
import com.koreait.jpa.entity.MemberEntity;
import com.koreait.jpa.mapper.MemberMapper;
import com.koreait.jpa.component.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        // 중복 사용자명 확인
        if (memberMapper.findByUsername(signupRequestDto.getUsername()) != null) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }

        // 중복 이메일 확인
        if (memberMapper.findByEmail(signupRequestDto.getEmail()) != null) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 생성
        MemberEntity member = new MemberEntity(
                signupRequestDto.getUsername(),
                encodedPassword,
                signupRequestDto.getEmail(),
                signupRequestDto.getNickname()
        );

        memberMapper.insertMember(member);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // 사용자 조회
        MemberEntity member = memberMapper.findByUsername(loginRequestDto.getUsername());
        if (member == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(member.getUsername());

        return new LoginResponseDto(token, member.getUsername(), member.getNickname());
    }

    @Transactional
    public void updateMember(Long memberId, String email, String nickname) {
        MemberEntity member = memberMapper.findById(memberId);
        if (member == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        member.setEmail(email);
        member.setNickname(nickname);
        memberMapper.updateMember(member);
    }

    public MemberEntity getMemberInfo(Long memberId) {
        MemberEntity member = memberMapper.findById(memberId);
        if (member == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        return member;
    }

    public MemberEntity getMemberInfo(String username) {
        MemberEntity member = memberMapper.findByUsername(username);
        if (member == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        return member;
    }
}
