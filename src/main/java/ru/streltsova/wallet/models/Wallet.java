package ru.streltsova.wallet.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="wallet")
public class Wallet {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="wallet_uuid")
    @NotNull
    private String uuid;
    @Column(name="amount")
    @PositiveOrZero
    private BigDecimal amount;

    public Wallet(Integer id, String uuid, BigDecimal amount) {
        this.id = id;
        this.uuid = uuid;
        this.amount = amount;
    }

    public Wallet(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
