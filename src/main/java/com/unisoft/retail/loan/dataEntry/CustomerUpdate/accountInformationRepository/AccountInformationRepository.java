package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository;

import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountInformationRepository extends JpaRepository<AccountInformationEntity, Long> {

    @Query(value = "select * From account_information_entity Where replace(loanacno,' ','') = ? fetch first row only ", nativeQuery = true)
    AccountInformationEntity findAccountInformationEntityByLoanACNo(String loanACNo);

    AccountInformationEntity getByLoanACNo(String accountNumber);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE REPLACE(LOANACNO,' ','') = ?1", nativeQuery = true)
    public AccountInformationEntity getByLoanAccountNo(String accountNo);
}
