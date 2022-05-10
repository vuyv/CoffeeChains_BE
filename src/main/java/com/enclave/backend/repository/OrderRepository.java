package com.enclave.backend.repository;

import com.enclave.backend.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.cache.annotation.Cacheable;
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

    ///////////////////////////////////////////////////////////////////////
    @Query("SELECT o FROM Order o WHERE o.createdBy.branch.id= :branchId AND o.createdAt = :date")
    List<Order> getDailyOrdersInBranch(@Param("branch") short branchId, @Param("date") Date date);

    @Query("SELECT o FROM Order o WHERE o.createdBy.branch.id= :branchId AND o.createdAt between :startDate and :endDate")
    List<Order> getOrdersInBranchByTime(@Param("branch") short branchId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //MANAGER
    @Query(value = "SELECT COUNT(*) FROM Orders, employee WHERE orders.created_by = employee.id AND DATE(orders.created_at) = DATE(:date) AND employee.branch_id = :branchId", nativeQuery = true)
    int getCountOfBranchOrderByDate(@Param("branch") short branchId, @Param("date") Date date);

    @Query(value = "SELECT Sum(total_price) FROM Orders, employee where orders.status=\"CREATED\" and orders.created_by = employee.id AND (DATE(orders.created_at)) = DATE(:date) AND employee.branch_id = :branchId", nativeQuery = true)
    double getCountOfBranchTotalPriceByDate(@Param("branch") short branchId, @Param("date") Date date);

    //Chart: weekly revenue
    @Query(value = "SELECT sum(orders.total_price) FROM orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id where orders.status=\"CREATED\" and week(orders.created_at)=week(now()) AND branch.id = :branchId", nativeQuery = true)
    double getWeeklyRevenueEachBranch(@Param("branchId") short branchId);

    @Query(value = "SELECT sum(orders.total_price) FROM orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id where orders.status=\"CREATED\" and month(orders.created_at)=month(now()) AND branch.id = :branchId", nativeQuery = true)
    double getCurrentMonthRevenueEachBranch(@Param("branchId") short branchId);

    @Query(value = "SELECT sum(orders.total_price) FROM orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id where orders.status=\"CREATED\" and month(orders.created_at)=month(now())-1 AND branch.id = :branchId", nativeQuery = true)
    double getLastMonthRevenueEachBranch(@Param("branchId") short branchId);

    @Query(value = "SELECT date(orders.created_at), sum(orders.total_price) FROM orders, employee where orders.status=\"CREATED\" and orders.created_by = employee.id and orders.created_at BETWEEN :startDate AND :endDate and employee.branch_id = :branchId GROUP BY date(orders.created_at) ORDER BY date(orders.created_at) ASC", nativeQuery = true)
    List<Object[]> getCountOfTotalPriceInBranchWeekly(@Param("branch") short branchId, @Param("startDate") String startDate, @Param("endDate") String endDate );

    // Chart order quantity (sold, canceled) in branch
    @Query(value = "select t1.m1, t1.sold, t2.cancel from (SELECT month(orders.created_at) m1 , count(*) as sold FROM orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id where branch.id = :branchId and orders.status= \"CREATED\" group by month(orders.created_at)) t1 join (SELECT month(orders.created_at) m2, count(*) as cancel FROM orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id where branch.id = :branchId and orders.status= \"CANCELED\" group by month(orders.created_at) )  t2 on t1.m1 = t2.m2 order by m1 asc;", nativeQuery = true)
    List<Object[]> getMonthlyOrderQuantityBranchBothStatus(@Param("branchId") short branchId);

    //OWNER
    @Query(value = "SELECT COUNT(*) FROM Orders where orders.status=\"CREATED\" and DATE(created_at) = DATE(:date)", nativeQuery = true)
    int getCountOfAllOrderByDate(@Param("date") Date date);

    @Query(value = "SELECT Sum(total_price) FROM Orders where orders.status=\"CREATED\" and (DATE(created_at)) = DATE(:date) ", nativeQuery = true)
    double getCountOfAllTotalPriceByDate(@Param("date") Date date);

    @Query(value = "SELECT branch.name,count(*) FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND (DATE(orders.created_at)) = DATE(:date) group by branch.name;\n", nativeQuery = true)
    List<Object[]> getCountOfOrderEachBranch(@Param("date") Date date);

    @Query(value = "SELECT branch.name,sum(orders.total_price) FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id where orders.status=\"CREATED\" AND (DATE(orders.created_at)) = DATE(:date) group by branch.name;\n", nativeQuery = true)
    List<Object[]> getCountOfTotalPriceEachBranch(@Param("date") Date date);

    @Query(value = "SELECT sum(orders.total_price) FROM orders where orders.status=\"CREATED\" and month(orders.created_at) = month(now()) -1", nativeQuery = true)
    double getLastMonthRevenue();

    @Query(value = "SELECT sum(orders.total_price) FROM orders where orders.status=\"CREATED\" and month(orders.created_at) = month(now())", nativeQuery = true)
    double getCurrentMonthRevenue();

    @Query(value = "SELECT sum(orders.total_price) FROM orders where orders.status=\"CREATED\" and week(orders.created_at)=week(now())-1", nativeQuery = true)
    double getLastWeekRevenue();

    @Query(value = "SELECT sum(orders.total_price) FROM orders where orders.status=\"CREATED\" and week(orders.created_at)=week(now())", nativeQuery = true)
    double getCurrentWeekRevenue();

    @Query(value = "SELECT branch.name,sum(orders.total_price) AS TOTAL FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id where orders.status=\"CREATED\" AND  week(orders.created_at)=week(now()) group by branch.name ORDER BY TOTAL DESC LIMIT 6", nativeQuery = true)
    List<Object[]> topWeeklySeller();

    @Query(value = "SELECT product.name, sum(order_detail.quantity) AS QUANTITY FROM order_detail JOIN orders ON orders.id = order_detail.order_id JOIN product ON order_detail.product_id = product.id JOIN employee on orders.created_by = employee.id JOIN branch on employee.branch_id = branch.id where orders.status=\"CREATED\" and employee.branch_id = :branchId AND week(orders.created_at)=week(now()) group by product.name order by QUANTITY desc LIMIT 6", nativeQuery = true)
    List<Object[]> getBestSellingProducts(@Param("branchId") short branchId);

//    @Cacheable("topProducts")
    @Query(value = "SELECT product.name, sum(order_detail.quantity) AS QUANTITY FROM order_detail JOIN orders ON orders.id = order_detail.order_id JOIN product ON order_detail.product_id = product.id JOIN employee on orders.created_by = employee.id  where orders.status=\"CREATED\" and employee.branch_id = :branchId AND orders.created_at BETWEEN :startDate AND :endDate  group by product.name order by quantity desc LIMIT 6", nativeQuery = true)
    List<Object[]> getTopProducts(@Param("branchId") short branchId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    //Report
    @Query(value = "SELECT branch.name, branch.address, count(*) AS orderQuantity, sum(orders.total_price) as revenue FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND (DATE(orders.created_at)) = DATE(now()) group by branch.name", nativeQuery = true)
    List<Object[]> getDailyRevenueAllBranch();

    @Query(value = "SELECT count(*) AS orderQuantity, sum(orders.total_price) as revenue FROM Orders JOIN employee ON orders.created_by = employee.id JOIN branch ON employee.branch_id = branch.id AND (DATE(orders.created_at)) = DATE(now()) group by branch.name", nativeQuery = true)
    List<Object[]> getDailyRevenueEachBranch();
}
