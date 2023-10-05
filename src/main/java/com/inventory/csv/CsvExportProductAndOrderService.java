package com.inventory.csv;

import com.inventory.dto.OrderDTO;
import com.inventory.dto.ProductDTO;
import com.inventory.service.OrderService;
import com.inventory.service.ProductService;
import com.opencsv.CSVWriter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class CsvExportProductAndOrderService {

    private ProductService productService;
    private OrderService orderService;
    private ModelMapper modelMapper;

    @Autowired
    public CsvExportProductAndOrderService(ProductService productService, OrderService orderService, ModelMapper modelMapper) {
        this.productService = productService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    public void exportProductsToCsv(String filePath) throws IOException {
        List<ProductDTO> productDtos = productService.viewAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());


        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Define the header row
            String[] header = {"Product ID", "Product Name", "Product Price", "In Stock", "Is Live", "Quantity", "Supplier"};
            writer.writeNext(header);

            for (ProductDTO productDto : productDtos) {
                String[] data = {
                        String.valueOf(productDto.getProduct_id()),
                        productDto.getProduct_name(),
                        String.valueOf(productDto.getProduct_prize()),
                        String.valueOf(productDto.isStock()),
                        String.valueOf(productDto.isLive()),
                        String.valueOf(productDto.getProduct_quantity()),
                        productDto.getSupplier() != null ? productDto.getSupplier().getSupplierName() : ""
                };
                writer.writeNext(data);
            }
        }
    }

    public void exportOrdersToCsv(String filePath) throws IOException {
        List<OrderDTO> orderDtos = orderService.getAllOrders()
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Define the header row
            String[] header = {"Order ID", "Order Status", "Payment Status", "Order Delivered", "Order Amount", "Billing Address", "Order Created At", "Username"};
            writer.writeNext(header);

            // Write data rows
            for (OrderDTO orderDto : orderDtos) {
                String[] data = {
                        String.valueOf(orderDto.getOrderId()),
                        orderDto.getOrderStatus(),
                        orderDto.getPaymentStatus(),
                        String.valueOf(orderDto.getOrderDelivered()),
                        String.valueOf(orderDto.getOrderAmt()),
                        orderDto.getBillingAddress(),
                        String.valueOf(orderDto.getOrderCreatedAt()),
                        orderDto.getUser() != null ? orderDto.getUser().getName() : ""
                };
                writer.writeNext(data);
            }
        }
    }
}
