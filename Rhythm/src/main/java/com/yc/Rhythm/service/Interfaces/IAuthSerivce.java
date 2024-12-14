package com.yc.Rhythm.service.Interfaces;

import com.yc.Rhythm.dto.req.LoginRequest;
import com.yc.Rhythm.dto.req.SignupRequest;
import com.yc.Rhythm.dto.res.JwtResponse;

public interface IAuthSerivce {
     JwtResponse register(SignupRequest registerRequestDto);
     JwtResponse login(LoginRequest loginRequest);

}
