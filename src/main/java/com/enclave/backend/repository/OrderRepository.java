package com.enclave.backend.repository;

import com.enclave.backend.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

    //Chart: weekly revenue
//    @Query(value = "SELECT date(orders.created_at), sum(orders.total_price) FROM orders, employee where orders.created_by = employee.id and date(orders.created_at) >= date(:sevenDaysAgo) and employee.branch_id = :branchId GROUP BY date(orders.created_at) ORDER BY date(orders.created_at) ASC", nativeQuery = true)
//    List<Object[]> getCountOfTotalPriceInBranchWeekly(@Param("branch") short branchId, @Param("sevenDaysAgo") Date sevenDaysAgo);

    @Query(value = "SELECT date(orders.created_at), sum(orders.total_price) FROM orders, employee where orders.created_by = employee.id and orders.created_at BETWEEN :startDate AND :endDate and employee.branch_id = :branchId GROUP BY date(orders.created_at) ORDER BY date(orders.created_at) ASC", nativeQuery = true)
    List<Object[]> getCountOfTotalPriceInBranchWeekly(@Param("branch") short branchId, @Param("startDate") String startDate, @Param("endDate") String endDate );


    //OWNER
    @Query(value = "SELECT COUNT(*) FROM Orders WHERE DATE(created_at) = DATE(:date)", nativeQuery = true)
    int getCountOfAllOrderByDate(@Param("date") Date date);

    @Query(value = "SELECT Sum(total_price) FROM Orders WHERE (DATE(created_at)) = DATE(:date) ", nativeQuery = true)
    double getCountOfAllTotalPriceByDate(@Param("date") Date date);

    @Query(value = "SELECT branch.name,count(*) FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND (DATE(orders.created_at)) = DATE(:date) group by branch.name;\n", nativeQuery = true)
    List<Object[]> getCountOfOrderEachBranch(@Param("date") Date date);

    @Query(value = "SELECT branch.name,sum(orders.total_price) FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND (DATE(orders.created_at)) = DATE(:date) group by branch.name;\n", nativeQuery = true)
    List<Object[]> getCountOfTotalPriceEachBranch(@Param("date") Date date);

    @Query(value = "SELECT sum(orders.total_price) FROM orders where month(orders.created_at) = month(now()) -1", nativeQuery = true)
    double getLastMonthRevenue();

    @Query(value = "SELECT sum(orders.total_price) FROM orders where month(orders.created_at) = month(now())", nativeQuery = true)
    double getCurrentMonthRevenue();

    @Query(value = "SELECT sum(orders.total_price) FROM orders where week(orders.created_at)=week(now())-1", nativeQuery = true)
    double getLastWeekRevenue();

    @Query(value = "SELECT sum(orders.total_price) FROM orders where week(orders.created_at)=week(now())", nativeQuery = true)
    double getCurrentWeekRevenue();

    @Query(value = "SELECT branch.name,sum(orders.total_price) AS TOTAL FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND  week(orders.created_at)=week(now()) group by branch.name ORDER BY TOTAL DESC LIMIT 6", nativeQuery = true)
    List<Object[]> topWeeklySeller();

    @Query(value = "SELECT product.name, sum(order_detail.quantity) AS QUANTITY FROM order_detail JOIN orders ON orders.id = order_detail.order_id JOIN product ON order_detail.product_id = product.id JOIN employee on orders.created_by = employee.id JOIN branch on employee.branch_id = branch.id where employee.branch_id = :branchId AND week(orders.created_at)=week(now()) group by product.name order by QUANTITY desc LIMIT 10", nativeQuery = true)
    List<Object[]> getBestSellingProducts(@Param("branchId") short branchId);

    //Report
    @Query(value = "SELECT branch.name, branch.address, count(*) AS orderQuantity, sum(orders.total_price) as revenue FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND (DATE(orders.created_at)) = DATE(now()) group by branch.name", nativeQuery = true)
    List<Object[]> getDailyRevenueAllBranch();

    @Query(value = "SELECT count(*) AS orderQuantity, sum(orders.total_price) as revenue FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND (DATE(orders.created_at)) = DATE(now()) group by branch.name", nativeQuery = true)
    List<Object[]> getDailyRevenueEachBranch();
}
