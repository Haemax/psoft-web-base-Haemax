package com.ufcg.psoft.commerce.service.util;

import org.springframework.stereotype.Service;

@Service
public class CredenciaisServiceImpl implements CredenciaisService{

    @Override
    public boolean validaCodigoAcesso(String src1, String src2) {
        return src1.equals(src2);
    }
}
