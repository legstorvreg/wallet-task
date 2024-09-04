package ru.streltsova.wallet.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.streltsova.wallet.dto.WalletDTO;
import ru.streltsova.wallet.enums.OperationsEnum;
import ru.streltsova.wallet.models.Wallet;
import ru.streltsova.wallet.repositories.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @Test
    public void testDoOperation_walletNotFound(){
        WalletDTO dto = new WalletDTO();
        dto.setWalletId(UUID.randomUUID());

        when(walletRepository.findByUuid(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> walletService.doOperation(dto));
    }

    @Test
    public void testDoOperation_deposit(){
        WalletDTO dto = new WalletDTO();
        dto.setWalletId(UUID.randomUUID());
        dto.setOperationType(OperationsEnum.DEPOSIT);
        dto.setAmount(BigDecimal.TEN);

        Wallet wallet = new Wallet();
        wallet.setAmount(BigDecimal.ONE);

        when(walletRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(wallet));

        Wallet result = walletService.doOperation(dto);

        assertEquals(BigDecimal.valueOf(11), result.getAmount());
    }

    @Test
    public void testDoOperation_withdraw(){
        WalletDTO dto=new WalletDTO();

        dto.setWalletId(UUID.randomUUID());
        dto.setOperationType(OperationsEnum.WITHDRAW);
        dto.setAmount(BigDecimal.ONE);

        Wallet wallet =new Wallet();
        wallet.setAmount(BigDecimal.TEN);

        when(walletRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(wallet));

        Wallet result = walletService.doOperation(dto);

        assertEquals(BigDecimal.valueOf(9), result.getAmount());
    }

    @Test
    public void testDoOperation_notEnoughMoney(){
        WalletDTO dto=new WalletDTO();

        dto.setWalletId(UUID.randomUUID());
        dto.setOperationType(OperationsEnum.WITHDRAW);
        dto.setAmount(BigDecimal.TEN);

        Wallet wallet =new Wallet();
        wallet.setAmount(BigDecimal.ONE);

        when(walletRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(wallet));

        assertThrows(IllegalStateException.class, () -> walletService.doOperation(dto));
    }

    @Test
    public void testDoOperation_withdraw2(){
        WalletDTO dto=new WalletDTO();

        dto.setWalletId(UUID.randomUUID());
        dto.setOperationType(OperationsEnum.WITHDRAW);
        dto.setAmount(BigDecimal.ONE);

        Wallet wallet =new Wallet();
        wallet.setAmount(BigDecimal.ONE);

        when(walletRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(wallet));

        Wallet result = walletService.doOperation(dto);

        assertEquals(BigDecimal.ZERO, result.getAmount());
    }

    @Test
    public void testGetBalance_NotFound(){
        String uuid= "123";

        when(walletRepository.findByUuid(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,() ->walletService.getBalance(uuid));
    }

    @Test
    public void testGetBalance(){
        String uuid="123";

        Wallet wallet=new Wallet();
        wallet.setAmount(BigDecimal.ONE);

        when(walletRepository.findByUuid(anyString())).thenReturn(Optional.of(wallet));

        assertEquals(BigDecimal.ONE,walletService.getBalance(uuid));

    }
}
