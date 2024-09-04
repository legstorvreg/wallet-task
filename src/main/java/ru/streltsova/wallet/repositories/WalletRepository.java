package ru.streltsova.wallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.streltsova.wallet.models.Wallet;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Optional<Wallet> findByUuid(String uuid);
}
