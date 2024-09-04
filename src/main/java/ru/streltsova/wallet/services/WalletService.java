package ru.streltsova.wallet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.streltsova.wallet.dto.WalletDTO;
import ru.streltsova.wallet.enums.OperationsEnum;
import ru.streltsova.wallet.models.Wallet;
import ru.streltsova.wallet.repositories.WalletRepository;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WalletService {
    private final Set<String> cache = ConcurrentHashMap.newKeySet();

    @Autowired
    private WalletRepository walletRepository;

    public void setWalletRepository(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Wallet doOperation(WalletDTO dto) {
        String uuid = dto.getWalletId().toString();
        try {
            synchronized (this) {
                while (cache.contains(uuid)) {
                    wait();
                }
                cache.add(uuid);
            }
            Wallet wallet = walletRepository.findByUuid(uuid)
                    .orElseThrow(() -> new IllegalArgumentException("This wallet is not found!"));
            if (dto.getOperationType() == OperationsEnum.DEPOSIT) {
                wallet.setAmount(wallet.getAmount().add(dto.getAmount()));
            } else {
                if (wallet.getAmount().compareTo(dto.getAmount()) < 0) {
                    throw new IllegalStateException("Not enough money!");
                }
                wallet.setAmount(wallet.getAmount().subtract(dto.getAmount()));
            }
            return wallet;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            synchronized (this) {
                cache.remove(uuid);
                notifyAll();
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalance(String uuid) {
        return walletRepository.findByUuid(uuid)
                .map(Wallet::getAmount)
                .orElseThrow(() -> new IllegalArgumentException("This wallet is not found!"));
    }
}
