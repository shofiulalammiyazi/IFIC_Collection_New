package com.csinfotechbd.collection.samd.dataEntry.cardDistribution;

import com.csinfotechbd.beans.Validation;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.common.CommonService;
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
public class SamCardDistributionService implements CommonService<SamCardDistribution> {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SamCardDistributionRepository samCardDistributionRepository;

    /*To Insert Data From Excel*/
    public Map<String, Object> storeData(MultipartFile file){
        Map<String, Object> response = new HashMap<>();
        // Check Validity
        List<String> errorMessages = this.validate(file);

        // check has errors or not
        if (errorMessages.size() == 0){
            List<SamCardDistribution> allSamCardDistributions = new ArrayList<>();
            try{
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

                /*Get First Row for Column Names*/
                Iterator<Row> rows = xssfSheet.iterator();
                Row row = rows.next();

                /*Get All Column Names*/
                Iterator<Cell> cells = row.cellIterator();
                List<String> cellNames = new ArrayList<>();
                while (cells.hasNext()){
                    cellNames.add(Objects.toString(cells.next(), "").toUpperCase());
                }

                while (rows.hasNext()){
                    row = rows.next();
                    SamCardDistribution samCardDistribution = new SamCardDistribution();

                    Cell cellContractID = row.getCell(cellNames.indexOf("CONTRACT ID"));
                    samCardDistribution.setContractId(Objects.toString(cellContractID, ""));

                    Cell cellCustomerID = row.getCell(cellNames.indexOf("CUSTOMER ID"));
                    samCardDistribution.setCustomerId(Objects.toString(cellCustomerID, ""));

                    Cell cellDealerID = row.getCell(cellNames.indexOf("DEALER ID"));
                    samCardDistribution.setDealerId(Objects.toString(cellDealerID, ""));

                    Cell cellDealerName = row.getCell(cellNames.indexOf("DEALER NAME"));
                    samCardDistribution.setDealerName(Objects.toString(cellDealerName, ""));

                    /*Remember data in memory to save all data*/
                    allSamCardDistributions.add(samCardDistribution);
                }

                // Save ALL Data
                samCardDistributionRepository.saveAll(allSamCardDistributions);
            } catch (IOException e){
                e.printStackTrace();
            }

            response.put("outcome", "success");
        }
        else{
            response.put("outcome", "failure");
            response.put("errors", errorMessages);
        }

        return response;
    }

    public Map<String, Object> updateManualDealerAllocationData(SamCardDistributionDTO distributionDTO){
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(distributionDTO);

        if (errorMessages.size() == 0){
            List<Long> selectedIds = Arrays.asList(distributionDTO.getSelectedIds());
            List<SamCardDistribution> distributions = samCardDistributionRepository.findAllByIds(selectedIds);
            EmployeeInfoEntity dealer = employeeService.getById(distributionDTO.getDealerId());

            for (SamCardDistribution distribution: distributions){

                distribution.setDealerId(dealer.getUser().getUsername());
                distribution.setDealerName(dealer.getUser().getFirstName());
            }

            samCardDistributionRepository.saveAll(distributions);
            response.put("outcome", "success");
        }
        else {
            response.put("errors", errorMessages);
            response.put("outcome", "failure");
        }

        return response;
    }

    @Override
    public Map<String, Object> storeData(SamCardDistribution data) {
        return null;
    }

    @Override
    public Map<String, Object> updateData(SamCardDistribution data) {
        return null;
    }

    @Override
    public Map<String, Object> deleteData(SamCardDistribution data) {
        return null;
    }

    @Override
    public SamCardDistribution findDataById(Long id) {
        return null;
    }

    @Override
    public List<SamCardDistribution> findAllData() {
        List<SamCardDistribution> allDistributions = samCardDistributionRepository.findAll();
        return allDistributions;
    }

    @Override
    public List<String> validate(SamCardDistribution data) {
        return null;
    }

    /* Validate Manual Card Distribution/ File Upload*/
    public List<String> validate(MultipartFile file) {
        ArrayList<String> errors = new ArrayList<>();
        List<String> validCells = Arrays.asList(
                new String[]{"CUSTOMER ID", "CONTRACT ID", "DEALER ID", "DEALER NAME"});

        if (Validation.isStringEmpty(file.getOriginalFilename())){
            errors.add("Attachment File required");
        }
        else {
            try{
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

                Iterator<Row> rows = xssfSheet.iterator();
                Row row = rows.next();

                Iterator<Cell> cells = row.cellIterator();
                List<String> addedCellNames = new ArrayList<String>();

                while (cells.hasNext()){
                    addedCellNames.add(Objects.toString(cells.next(), "").toUpperCase());
                }

                //Check Columns Validity
                for (String cellName:validCells){
                    if (!addedCellNames.contains(cellName)){
                        errors.add(cellName + " is required");
                    }
                }

            } catch (IOException e){
                e.printStackTrace();
            }
        }

        return errors;
    }

    public List<String> validate(SamCardDistributionDTO distributionDTO) {
        ArrayList<String> errors = new ArrayList<>();

        if (distributionDTO.getDealerId() == null){
            errors.add("Dealer empty is not allowed.");
        }

        return errors;
    }
}
