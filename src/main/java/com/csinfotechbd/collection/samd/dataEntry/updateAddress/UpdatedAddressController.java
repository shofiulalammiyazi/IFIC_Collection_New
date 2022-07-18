package com.csinfotechbd.collection.samd.dataEntry.updateAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/samd/dataEntry/update-address")
public class UpdatedAddressController {


    @Autowired
    private UpdatedAddressService updatedAddressService;

    @GetMapping("/find")
    @ResponseBody
    private UpdateAddress getUpdatedAddress(@RequestParam String customerId){
        UpdateAddress updateAddress = updatedAddressService.findUpdateAddressByCustomerId(customerId);
        return updateAddress;
    }

    @GetMapping("/find-by")
    @ResponseBody
    private UpdateAddress findUpdatedAddress(@RequestParam Long id){
        UpdateAddress updateAddress = updatedAddressService.findUpdateAddressById(id);
        return updateAddress;
    }


    @PostMapping("/create")
    @ResponseBody
    private UpdateAddress createUpdatedAddress(UpdateAddress updateAddress){
        UpdateAddress updateAddress1 = updatedAddressService.save(updateAddress);
        return updateAddress1;
    }
}
