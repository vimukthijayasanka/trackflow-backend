package lk.ijse.dep13.backendexpensemanager.repository;

import lk.ijse.dep13.backendexpensemanager.entity.IncomeExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncomeExpenseRepo extends JpaRepository<IncomeExpense, Long> {
Optional<IncomeExpense> findIncomeExpenseByUserName(String userName);

}
