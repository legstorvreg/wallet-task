package ru.streltsova.wallet.dto;

import ru.streltsova.wallet.enums.OperationsEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public class WalletDTO {
    @NotNull
    private UUID walletId;
    @NotNull
    private OperationsEnum operationType;
    @Positive
    private BigDecimal amount;

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public OperationsEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationsEnum operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
