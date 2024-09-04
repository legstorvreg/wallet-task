package ru.streltsova.wallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.streltsova.wallet.dto.WalletDTO;
import ru.streltsova.wallet.services.WalletService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value="/wallet")
    public ResponseEntity<HttpStatus> doOperation(@RequestBody @Valid WalletDTO walletDTO){
        walletService.doOperation(walletDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/wallets/{walletId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID walletId){
       BigDecimal balance= walletService.getBalance(walletId.toString());
        return new ResponseEntity<>(balance,HttpStatus.OK);

    }
}
