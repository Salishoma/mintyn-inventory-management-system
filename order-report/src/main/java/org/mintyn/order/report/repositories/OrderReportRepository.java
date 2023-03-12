package org.mintyn.order.report.repositories;

import org.mintyn.order.report.entities.OrderReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReportRepository extends JpaRepository<OrderReport, Long> {
}
