package org.mintyn.order.report.repositories;

import org.mintyn.order.report.entities.OrderReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderReportRepository extends JpaRepository<OrderReport, Long> {
    List<OrderReport> findAllByOrderCreatedDateIsBetween(final LocalDate from, final LocalDate to);
}
