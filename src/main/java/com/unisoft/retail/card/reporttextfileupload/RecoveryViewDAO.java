package com.unisoft.retail.card.reporttextfileupload;

import org.springframework.stereotype.Repository;

@Repository
public class RecoveryViewDAO {

//    @Autowired
//    @Qualifier("clientDbServer3DataSource")
//    private  DataSource clientDbServer3DataSourch;
//
//    public RecoveryViewDTO getRecoveryView(String contractNo, String date){
//
//
//
//        String query ="SELECT * FROM BAOBAB.RECOVERY_VIEW WHERE ACCOUNT='"+contractNo+"' and to_char(lastdeptime,'DD-MON-YY') ='"+date+"' ORDER BY lastdeptime DESC FETCH FIRST ROW ONLY";
//
//        RecoveryViewDTO recoveryViewDTO = new RecoveryViewDTO();
//
//        Statement statement=null;
//        Connection connection = null;
//
//        try {
//            connection = clientDbServer3DataSourch.getConnection();
//            statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()){
//                recoveryViewDTO.setAvailableBalanceCashBdt(resultSet.getString("AVAILBALANCE"));
//                recoveryViewDTO.setLastPaymentAmountBdt(resultSet.getString("LASTDEPAMOUNT"));
//                recoveryViewDTO.setLastPaymentDateBdt(resultSet.getString("LASTDEPTIME"));
//            }
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }finally {
//            try {
//
//                if (statement !=null){
//                    statement.close();
//                }
//
//                if(connection !=null){
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        return recoveryViewDTO;
//    }
}
