package org.mintyn.inventory.response.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mintyn.inventory.response.model.OrderResponse;
import org.mintyn.order.report.dtos.OrderReportResponse;
import org.mintyn.order.report.entities.OrderReport;
import org.mintyn.order.report.repositories.OrderReportRepository;
import org.mintyn.order.report.services.OrderReportService;
import org.mintyn.order.report.services.impl.OrderReportServiceImpl;
import org.mintyn.order.report.utils.ExcelFileGenerator;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderReportTest {

    @Mock
    private OrderReportRepository mockOrderReportRepository;

    @Mock
    private ExcelFileGenerator mockExcelFileGenerator;

    @Mock
    private OrderReportService orderReportService;

    OrderResponse orderResponse = new OrderResponse();

    OrderReport orderReport = new OrderReport();

    @BeforeEach
    void setUp() {
        orderReportService = new OrderReportServiceImpl(mockOrderReportRepository, mockExcelFileGenerator);

        orderResponse.setOrderId("111");
        orderResponse.setOrderCreatedDate(LocalDate.of(2020, 1, 1));
        orderResponse.setCustomerName("Oma");
        orderResponse.setCustomerPhoneNumber("111-222-333");
        orderResponse.setProductName("Mintyn product");
        orderResponse.setQuantity(4);
        orderResponse.setPrice(BigDecimal.valueOf(12));
        orderResponse.setTotalPrice(BigDecimal.valueOf(48));

        orderReport = new OrderReport();
        orderReport.setId(1L);
        orderReport.setOrderId("111");
        orderReport.setCustomerName("Oma");
        orderReport.setCustomerPhoneNumber("111-222-333");
        orderReport.setProductName("Mintyn product");
        orderReport.setOrderQuantity(4);
        orderReport.setProductPrice(BigDecimal.valueOf(12));
        orderReport.setTotalProductPrice(BigDecimal.valueOf(48));

    }


    @Test
    void testGetReportFromDateRange() {
        // Setup
        final OrderReportResponse expectedResult = new OrderReportResponse("Excel File Generated", HttpStatus.OK);

        // Configure OrderReportRepository.findAllByOrderCreatedDateIsBetween(...).
        final List<OrderReport> orderReports = List.of(orderReport);
        when(mockOrderReportRepository.findAllByOrderCreatedDateIsBetween(any(),any())).thenReturn(orderReports);

        // Run the test
        final OrderReportResponse result = orderReportService.generateOrderReport(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1));

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockExcelFileGenerator).generateTerminalRequestData(List.of(orderReport),
                "2020-01-01 to 2020-01-01");
    }
}
