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

                AccountInformationDto AccountInformationDto= new AccountInformationDto();

                AccountInformationDto.setLoanACNo(resultSet.getString("NEEAN"));
                AccountInformationDto.setLastPaymentDate(resultSet.getString("OMDTE01"));
                AccountInformationDto.setLastPaymentAmount(resultSet.getString("OMARC01"));
                AccountInformationDto.setSettlementLinkAccountBalance(resultSet.getString("SCBAL"));
                AccountInformationDto.setLinkMotherAccountNo(resultSet.getString("NEEAN01"));
                AccountInformationDto.setRoutingNo(resultSet.getString("BSBBRN"));
                AccountInformationDto.setMobile(resultSet.getString("AAZMPHN"));
                AccountInformationDto.setBranchName(resultSet.getString("CABRN"));
                AccountInformationDto.setBranchCode(resultSet.getString("V5ABD"));
                AccountInformationDto.setOverdue(resultSet.getString("OVERDUE"));
                AccountInformationDto.setEmiAmount(resultSet.getString("OMNWR"));
                AccountInformationDto.setEmiDate(resultSet.getString("OMDTE"));
                AccountInformationDto.setLastPaymentAmount(resultSet.getString("OMNWR01"));
                AccountInformationDto.setProductType(resultSet.getString("V5DLP"));
                //accountInformationDto.setSanctionAmount(resultSet.getString("OTDLA2"));
                AccountInformationDto.setActualTenor(resultSet.getString("OTMDT"));
                AccountInformationDto.setDisbursementDate(resultSet.getString("OTSDTE"));
                AccountInformationDto.setTotalOutstanding(resultSet.getString("V5BAL"));
                AccountInformationDto.setBorrowersName(resultSet.getString("GFCUN"));
                AccountInformationDto.setProfession(resultSet.getString("BHDSC"));
                AccountInformationDto.setEmail(resultSet.getString("AAZEAD1"));
                AccountInformationDto.setNid(resultSet.getString("AAZIDN"));
                AccountInformationDto.setProfessionSegment(resultSet.getString("BGPROF"));
                AccountInformationDto.setExpiryDate(resultSet.getString("OTMDT"));
                AccountInformationDto.setDisbursementAmount(resultSet.getString("OTDLA2"));



                AccountInformationDto.setCustomerName(resultSet.getString("GFCUN"));
                //accountInformationDto.setCustomerId(resultSet.getString("GFCPNC"));
                // accountInformationDto.setCustomerType(resultSet.getString(""));
                // accountInformationDto.setSpouse(resultSet.getString(""));
                AccountInformationDto.setDob(resultSet.getString("AAZBDTE"));
                AccountInformationDto.setGender(resultSet.getString("BGSEX"));
                AccountInformationDto.setFatherName(resultSet.getString("BGFNAM"));
                AccountInformationDto.setMotherName(resultSet.getString("BGMNAM"));
                AccountInformationDto.setNi(resultSet.getString("AAZIDN"));
                AccountInformationDto.setTin(resultSet.getString("BGTIN"));


                AccountInformationDtoList.add(AccountInformationDto);

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
