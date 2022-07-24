package com.unisoft.collection.samd.dataEntry.agencyAllocationViaExcel;


import com.unisoft.beans.Validation;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.samd.setup.agencySamd.AgencySamdEntity;
import com.unisoft.collection.samd.setup.agencySamd.AgencySamdService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class AgencyAllocationServiceImpl implements AgencyAllocationService{

    @Autowired
    private AgencyAllocationRepository agencyAllocationRepository;
    @Autowired
    private AgencySamdService agencySamdService;
    @Autowired
    private LoanAccountBasicService loanAccountBasicService;


    @Override
    public Map<String, Object> saveData(MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(file);
        if (errorMessages.size() == 0){
            List<AgencyAllocation> agencyAllocationList = new ArrayList<>();
            List<String> accountNoList = new ArrayList<>();
            List<AgencyAllocation> previousData = new ArrayList<>();

            try {
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

                Iterator<Row> rows = xssfSheet.iterator();
                Row row = rows.next();

                Iterator<Cell> cells = row.cellIterator();
                List<String> cellNames = new ArrayList<>();

                while (cells.hasNext()){
                    cellNames.add(Objects.toString(cells.next(), ""));
                }

                while (rows.hasNext()){
                    row = rows.next();

                    AgencyAllocation agencyAllocation = new AgencyAllocation();

                    Cell cellAccountNo = row.getCell(cellNames.indexOf("Account Number"));
                    String accountNo = Objects.toString(cellAccountNo, "");
                    accountNoList.add(accountNo);

                    Cell cellAccountName = row.getCell(cellNames.indexOf("Account Name"));
                    String accountName = Objects.toString(cellAccountName, "");

                    Cell cellCIF = row.getCell(cellNames.indexOf("CIF"));
                    String cif = Objects.toString(cellCIF, "");

                    Cell cellLLN = row.getCell(cellNames.indexOf("LLN"));
                    String lln = Objects.toString(cellLLN, "");

                    Cell cellDealerId = row.getCell(cellNames.indexOf("Dealer ID"));
                    String dealerId = Objects.toString(cellDealerId, "");

                    Cell cellAgencyName = row.getCell(cellNames.indexOf("Agency Name"));
                    String agencyName = Objects.toString(cellAgencyName, "");


                    agencyAllocation.setAccountNo(accountNo);
                    agencyAllocation.setAccountName(accountName);
                    agencyAllocation.setCif(cif);
                    agencyAllocation.setLln(lln);
                    agencyAllocation.setDealerId(dealerId);
                    agencyAllocation.setAgencyName(agencyName);
                    agencyAllocation.setLatest(true);

                    AgencySamdEntity agencySamdEntity = agencySamdService.findAgencySamdEntityByName(agencyName);
                    agencyAllocation.setAgencySamdEntity(agencySamdEntity);

//                    LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(accountNo);
//                    agencyAllocation.setLoanAccountBasicInfo(loanAccountBasicInfo);
                    agencyAllocationList.add(agencyAllocation);
                }


                List<AgencyAllocation> previAgencyAllocatoins = agencyAllocationRepository.findAgencyAllocationList(accountNoList, true);
                if (previAgencyAllocatoins.size() != 0){
                    for (AgencyAllocation agencyAllocation: agencyAllocationList){
                        agencyAllocation.setLatest(false);
                        previousData.add(agencyAllocation);
                    }
                    agencyAllocationRepository.saveAll(previousData);
                }

                agencyAllocationRepository.saveAll(agencyAllocationList);

            } catch (IOException e) {
                e.printStackTrace();
            }
             response.put("outcome", "success");
        }else {
            response.put("outcome", "failure");
            response.put("errors", errorMessages);
        }
        return response;
    }

    @Override
    public List<AgencyAllocation> findAllLatest() {
        return agencyAllocationRepository.findAgencyAllocationsByLatest();
    }

    private List<String> validate(MultipartFile file) {
        ArrayList<String> errors = new ArrayList<>();
        List<String> validCells = Arrays.asList(
                new String[]{"Account Number", "Account Name","CIF", "LLN", "Dealer ID", "Agency Name"}
        );

        if (Validation.isStringEmpty(file.getOriginalFilename())){
            errors.add("Attachment File required");
        }else {
            try {
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xShet = xssfWorkbook.getSheetAt(0);
                Iterator<Row> rowIterator = xShet.iterator();
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> addedCellname = new ArrayList<>();

                while (cellIterator.hasNext()){
                    addedCellname.add(Objects.toString(cellIterator.next(), ""));
                }

                for (String cellname : validCells){
                    if (!addedCellname.contains(cellname)){
                        errors.add(cellname + "is required");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return errors;
    }
}
