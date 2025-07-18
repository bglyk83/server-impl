package com.koreait.service_impl.service;

import com.koreait.service_impl.dto.LoginRequestDto;
import com.koreait.service_impl.dto.LoginResponseDto;
import com.koreait.service_impl.dto.SignupRequestDto;
import com.koreait.service_impl.entity.MemberEntity;
import com.koreait.service_impl.mapper.MemberMapper;
import com.koreait.service_impl.component.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public void signup(SignupRequestDto signupRequestDto) {
        if (memberMapper.findByUsername(signupRequestDto.getUsername()) != null) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }
        if (memberMapper.findByEmail(signupRequestDto.getEmail()) != null) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        MemberEntity member = new MemberEntity(
                signupRequestDto.getUsername(),
                encodedPassword,
                signupRequestDto.getEmail(),
                signupRequestDto.getNickname()
        );
        memberMapper.insertMember(member);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        MemberEntity member = memberMapper.findByUsername(loginRequestDto.getUsername());
        if (member == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        String token = jwtTokenProvider.createToken(member.getUsername());
        return new LoginResponseDto(token, member.getUsername(), member.getNickname());
    }

    @Transactional
    @Override
    public void updateMember(Long memberId, String email, String nickname) {
        MemberEntity member = memberMapper.findById(memberId);
        if (member == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        member.setEmail(email);
        member.setNickname(nickname);
        memberMapper.updateMember(member);
    }

    @Override
    public MemberEntity getMemberInfo(Long memberId) {
        MemberEntity member = memberMapper.findById(memberId);
        if (member == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        return member;
    }

    @Override
    public MemberEntity getMemberInfo(String username) {
        MemberEntity member = memberMapper.findByUsername(username);
        if (member == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        return member;
    }
} 