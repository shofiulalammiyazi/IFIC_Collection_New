package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository;

import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountInformationDao {


    @Autowired
    @Qualifier("clientDbServerIfic")
    private DataSource clientDbServerIfic;

    public  List<AccountInformationDto> getData(){

        String query = "select  * from CRLIB.CRPF";

        List<AccountInformationDto> AccountInformationDtoList = new ArrayList<>();
        Statement statement=null;
        Connection connection = null;

        try{

            connection = clientDbServerIfic.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){

                AccountInformationDto accountInformationDto= new AccountInformationDto();

                accountInformationDto.setLoanACNo(resultSet.getString("NEEAN"));
                accountInformationDto.setLastPaymentDate(resultSet.getString("OMDTE01"));
                accountInformationDto.setLastPaymentAmount(resultSet.getString("OMARC01"));
                accountInformationDto.setSettlementLinkAccountBalance(resultSet.getString("SCBAL"));
                accountInformationDto.setLinkMotherAccountNo(resultSet.getString("NEEAN01"));
                accountInformationDto.setRoutingNo(resultSet.getString("BSBBRN"));
                accountInformationDto.setMobile(resultSet.getString("AAZMPHN"));
                accountInformationDto.setBranchName(resultSet.getString("CABRN"));
                accountInformationDto.setBranchCode(resultSet.getString("V5ABD"));
                accountInformationDto.setOverdue(resultSet.getString("OVERDUE"));
                accountInformationDto.setEmiAmount(resultSet.getString("OMNWR"));
                accountInformationDto.setEmiDate(resultSet.getString("OMDTE"));
                //accountInformationDto.setLastPaymentAmount(resultSet.getString("OMNWR01"));
                accountInformationDto.setProductType(resultSet.getString("V5DLP"));
                //accountInformationDto.setSanctionAmount(resultSet.getString("OTDLA2"));
                accountInformationDto.setActualTenor(resultSet.getString("OTMDT"));
                accountInformationDto.setDisbursementDate(resultSet.getString("OTSDTE"));
                accountInformationDto.setTotalOutstanding(resultSet.getString("V5BAL"));
                accountInformationDto.setBorrowersName(resultSet.getString("GFCUN"));
                accountInformationDto.setProfession(resultSet.getString("BHDSC"));
                accountInformationDto.setEmail(resultSet.getString("AAZEAD1"));
                accountInformationDto.setNid(resultSet.getString("AAZIDN"));
                accountInformationDto.setProfessionSegment(resultSet.getString("BGPROF"));
                accountInformationDto.setExpiryDate(resultSet.getString("OTMDT"));
                accountInformationDto.setDisbursementAmount(resultSet.getString("OTDLA2"));



                accountInformationDto.setCustomerName(resultSet.getString("GFCUN"));
                //accountInformationDto.setCustomerId(resultSet.getString("GFCPNC"));
                // accountInformationDto.setCustomerType(resultSet.getString(""));
                // accountInformationDto.setSpouse(resultSet.getString(""));
                accountInformationDto.setDob(resultSet.getString("AAZBDTE"));
                accountInformationDto.setGender(resultSet.getString("BGSEX"));
                accountInformationDto.setFatherName(resultSet.getString("BGFNAM"));
                accountInformationDto.setMotherName(resultSet.getString("BGMNAM"));
                accountInformationDto.setNi(resultSet.getString("AAZIDN"));
                accountInformationDto.setTin(resultSet.getString("BGTIN"));


                AccountInformationDtoList.add(accountInformationDto);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {

                if (statement !=null){
                    statement.close();
                }

                if(connection !=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return AccountInformationDtoList;
    }




}
