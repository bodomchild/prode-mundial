package com.facrod.prodemundial.service;

import com.facrod.prodemundial.dto.SignInDTO;
import com.facrod.prodemundial.dto.SignUpDTO;
import com.facrod.prodemundial.exceptions.AppException;

public interface ProdeUserService {

    SignUpDTO signUp(SignUpDTO user) throws AppException;

    String signIn(SignInDTO user) throws AppException;

}
