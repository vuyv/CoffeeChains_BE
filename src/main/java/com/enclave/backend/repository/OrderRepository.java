package com.enclave.backend.repository;

import com.enclave.backend.entity.Branch;
import com.enclave.backend.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.hibernate.loader.Loader.SELECT;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM Order o WHERE o.createdBy.branch.id= :branchId")
    List<Order> findByBranch(@Param("branch") short branchId);

    @Query("SELECT o FROM Order o WHERE o.createdBy.branch.id= :branchId AND o.id = :id")
    Order findOrderByIdInBranch(@Param("branch") short branchId, @Param("id") String id);

    //MANAGER
    @Query(value = "SELECT COUNT(*) FROM Orders, employee WHERE orders.created_by = employee.id AND DATE(orders.created_at) = DATE(:date) AND employee.branch_id = :branchId", nativeQuery = true)
    int getCountOfBranchOrderByDate(@Param("branch") short branchId, @Param("date") Date date);

    @Query(value = "SELECT Sum(total_price) FROM Orders, employee WHERE orders.created_by = employee.id AND (DATE(orders.created_at)) = DATE(:date) AND employee.branch_id = :branchId", nativeQuery = true)
    double getCountOfBranchTotalPriceByDate(@Param("branch") short branchId, @Param("date") Date date);

    @Query(value = "SELECT date(orders.created_at), sum(orders.total_price) FROM orders, employee where orders.created_by = employee.id and date(orders.created_at) >= date(:sevenDaysAgo) and employee.branch_id = :branchId GROUP BY date(orders.created_at)", nativeQuery = true)
    List<Object[]> getCountOfTotalPriceInBranchWeekly(@Param("branch") short branchId, @Param("sevenDaysAgo") Date sevenDaysAgo);


    //OWNER
    @Query(value = "SELECT COUNT(*) FROM Orders WHERE DATE(created_at) = DATE(:date)", nativeQuery = true)
    int getCountOfAllOrderByDate(@Param("date") Date date);

    @Query(value = "SELECT Sum(total_price) FROM Orders WHERE (DATE(created_at)) = DATE(:date) ", nativeQuery = true)
    double getCountOfAllTotalPriceByDate(@Param("date") Date date);

    @Query(value = "  SELECT branch.name,count(*) FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND (DATE(orders.created_at)) = DATE(:date) group by branch.name;\n", nativeQuery = true)
    List<Object[]> getCountOfOrderEachBranch(@Param("date") Date date);

    @Query(value = "  SELECT branch.name,sum(orders.total_price) FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND (DATE(orders.created_at)) = DATE(:date) group by branch.name;\n", nativeQuery = true)
    List<Object[]> getCountOfTotalPriceEachBranch(@Param("date") Date date);


}
