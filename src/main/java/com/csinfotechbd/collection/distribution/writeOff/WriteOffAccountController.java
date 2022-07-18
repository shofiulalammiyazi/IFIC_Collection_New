package com.csinfotechbd.collection.distribution.writeOff;
/*

 */

import com.csinfotechbd.user.UserPrincipal;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/collection/distribution/wirteoff/")
public class WriteOffAccountController {

    @Autowired
    private WriteOffAccountService writeOffAccountService;

    @Autowired
    private WriteOffAccountRepository writeOffAccountRepository;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("writeOffList", writeOffAccountService.getAll());
        return "collection/distribution/writeOffAccount/writeOff";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        return "collection/distribution/writeOffAccount/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, @RequestParam("file") MultipartFile multipartFile) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (multipartFile.isEmpty()) {
            model.addAttribute("validationError", "Attach a excel file");
            return "collection/distribution/writeOffAccount/create";
        }

        if (!multipartFile.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !multipartFile.getContentType().equals("application/vnd.ms-excel")) {
            model.addAttribute("validationError", "Only excel file format is supported");
            return "collection/distribution/writeOffAccount/create";
        }

        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            List<WriteOffAccountInfo> writeOffAccountInfos = new ArrayList<>();

            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = xssfSheet.getRow(i);
                WriteOffAccountInfo writeOffAccountInfo = new WriteOffAccountInfo();

                if (row.getCell(0) != null && row.getCell(0).toString().length() > 1 && row.getCell(3) != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

                    /*fileDateNiAct = simpleDateFormat.parse(row.getCell(5).toString());
                    fileDateArthorin = simpleDateFormat.parse(row.getCell(7).toString());
                    fileDatePenalCode = simpleDateFormat.parse(row.getCell(9).toString());
                    nextAskingDate = simpleDateFormat.parse(row.getCell(11).toString());
                    auctionNoticeDate = simpleDateFormat.parse(row.getCell(17).toString());
                    nPublicDate = simpleDateFormat.parse(row.getCell(18).toString());
                    auctionDate = simpleDateFormat.parse(row.getCell(19).toString());*/

                    if (row.getCell(3).toString().equals("Loan")) {

                        if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                            writeOffAccountInfo.setLoanAccountNo(row.getCell(0).toString());
                        } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            Long custId = Double.valueOf(row.getCell(0).toString()).longValue();
                            writeOffAccountInfo.setLoanAccountNo(custId.toString());
                        }

                        writeOffAccountInfo.setLoanAccountName(row.getCell(1) != null ? row.getCell(1).toString() : null);
                        writeOffAccountInfo.setCustomerId(row.getCell(1) != null ? row.getCell(1).toString() : null);
                        writeOffAccountInfo.setSchemeCode(row.getCell(2) != null ? row.getCell(2).toString() : null);
                        writeOffAccountInfo.setPrductUnit(row.getCell(3) != null ? row.getCell(3).toString() : null);
                        /*writeOffAccountInfo.setCaseAmountNiAct(row.getCell(6).toString());
                        writeOffAccountInfo.setCaseAmountArthorin(row.getCell(8).toString());
                        writeOffAccountInfo.setCaseAmountPenalCode(row.getCell(10).toString());
                        writeOffAccountInfo.setCaseStatus(row.getCell(12).toString());
                        writeOffAccountInfo.setCaseDealingPerson(row.getCell(13).toString());
                        writeOffAccountInfo.setLawyerName(row.getCell(14).toString());
                        writeOffAccountInfo.setCourtName(row.getCell(15).toString());
                        writeOffAccountInfo.setPresentOutstanding(row.getCell(16).toString());
                        writeOffAccountInfo.setPrductUnit(row.getCell(4).toString());


                        writeOffAccountInfo.setFileDateNiAct(fileDateNiAct);
                        writeOffAccountInfo.setFileDateArthorin(fileDateArthorin);
                        writeOffAccountInfo.setFileDatePenalCode(fileDateNiAct);
                        writeOffAccountInfo.setFileDateNiAct(fileDatePenalCode);
                        writeOffAccountInfo.setNextAskingDate(nextAskingDate);
                        writeOffAccountInfo.setAuctionNoticeDate(auctionNoticeDate);
                        writeOffAccountInfo.setnPublicDate(nPublicDate);
                        writeOffAccountInfo.setAuctionDate(auctionDate);*/

                        writeOffAccountInfo.setWriteOffAccount("1");
                        writeOffAccountInfo.setCreatedBy(user.getUsername());
                        writeOffAccountInfo.setCreatedDate(new Date());
                        writeOffAccountInfos.add(writeOffAccountInfo);
                    } else if (row.getCell(3).toString().equals("Card")) {

                        if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                            writeOffAccountInfo.setContractId(row.getCell(0).toString());
                        } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            Long custId = Double.valueOf(row.getCell(0).toString()).longValue();
                            writeOffAccountInfo.setContractId(custId.toString());
                        }

                        writeOffAccountInfo.setCardName(row.getCell(1) != null ? row.getCell(1).toString() : null);
                        writeOffAccountInfo.setCustomerId(row.getCell(1) != null ? row.getCell(1).toString() : null);
                        writeOffAccountInfo.setSchemeCode(row.getCell(2) != null ? row.getCell(2).toString() : null);
                        writeOffAccountInfo.setPrductUnit(row.getCell(3) != null ? row.getCell(3).toString() : null);
                        /*writeOffAccountInfo.setCaseAmountNiAct(row.getCell(6).toString());
                        writeOffAccountInfo.setCaseAmountArthorin(row.getCell(8).toString());
                        writeOffAccountInfo.setCaseAmountPenalCode(row.getCell(10).toString());
                        writeOffAccountInfo.setCaseStatus(row.getCell(12).toString());
                        writeOffAccountInfo.setCaseDealingPerson(row.getCell(13).toString());
                        writeOffAccountInfo.setLawyerName(row.getCell(14).toString());
                        writeOffAccountInfo.setCourtName(row.getCell(15).toString());
                        writeOffAccountInfo.setPresentOutstanding(row.getCell(16).toString());
                        writeOffAccountInfo.setPrductUnit(row.getCell(4).toString());*/
                        writeOffAccountInfo.setWriteOffAccount("1");
                        writeOffAccountInfo.setCreatedBy(user.getUsername());
                        writeOffAccountInfo.setCreatedDate(new Date());
                        /*writeOffAccountInfo.setFileDateNiAct(fileDateNiAct);
                        writeOffAccountInfo.setFileDateArthorin(fileDateArthorin);
                        writeOffAccountInfo.setFileDatePenalCode(fileDateNiAct);
                        writeOffAccountInfo.setFileDateNiAct(fileDatePenalCode);
                        writeOffAccountInfo.setNextAskingDate(nextAskingDate);
                        writeOffAccountInfo.setAuctionNoticeDate(auctionNoticeDate);
                        writeOffAccountInfo.setnPublicDate(nPublicDate);
                        writeOffAccountInfo.setAuctionDate(auctionDate);*/

                        writeOffAccountInfos.add(writeOffAccountInfo);
                    }
                }

            }
            writeOffAccountRepository.saveAll(writeOffAccountInfos);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/collection/distribution/wirteoff/list";
    }
}
