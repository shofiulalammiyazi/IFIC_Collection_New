package com.unisoft.collection.samd.setup.temporaryjobdelegation;

import com.unisoft.user.UserPrincipal;

import java.util.List;

public interface TemporaryJobDelegationService {
    TemporaryJobDelegation saveTemporaryJobDelegation(TemporaryJobDelegation temporaryJobDelegation, UserPrincipal userPrincipal);

    List<TemporaryJobDelegation> findAllTemporaryJobDelegation();

    TemporaryJobDelegation findById(Long id);
}
