package com.example.doantn.service;

import com.example.doantn.dto.MonthlyRevenueDTO;
import com.example.doantn.dto.OrderItemDTO;
import com.example.doantn.dto.OrderRequest;
import com.example.doantn.dto.ProductRevenueDTO;
import com.example.doantn.entity.*;
import com.example.doantn.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;



    @Autowired
    private CartItemRepository cartItemRepository;

    public Order createOrder(OrderRequest orderRequestDTO) {
        // Xử lý logic để tạo đơn hàng và lưu vào cơ sở dữ liệu
        // Thường bạn sẽ có các bước như:
        // 1. Lấy thông tin user từ orderRequestDTO
        // 2. Lấy thông tin địa chỉ từ orderRequestDTO
        // 3. Lấy danh sách sản phẩm và số lượng từ orderRequestDTO
        // 4. Tạo đối tượng Order từ thông tin thu được
        // 5. Lưu đối tượng Order vào cơ sở dữ liệu
        // 6. Trả về đối tượng Order đã được lưu

        // Ở đây là một ví dụ giả định:
        User user = userRepository.findById(orderRequestDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Address address = addressRepository.findById(orderRequestDTO.getAddressId()).orElseThrow(() -> new IllegalArgumentException("Address not found"));
        // Sử dụng múi giờ Việt Nam để tạo đối tượng thời gian
        ZonedDateTime orderDate = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        Order order = new Order(user, orderDate.toLocalDateTime(), orderRequestDTO.getStatus(), orderRequestDTO.getTotalAmount());
        order.setAddress(address);
        orderRepository.save(order);
        List<OrderItemDTO> orderItemDTOs = orderRequestDTO.getOrderItems();
       Set<OrderItem> orderItems = new HashSet<>(); // Chuyển từ List sang Set
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            Product product = productRepository.findById(orderItemDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            OrderItem orderItem = new OrderItem(order, product, orderItemDTO.getQuantity());
            orderItems.add(orderItem);
            orderItemRepository.save(orderItem);
        }

        order.setOrderItems(orderItems);
        return order;
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllOrdersForUser(Long userId) {
        // Triển khai phương thức này để lấy danh sách các đơn đặt hàng của người dùng dựa trên ID của họ
        return orderRepository.findByUserId(userId);
    }
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public Order increaseOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        int newStatus = order.getStatus() + 1;
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
    public List<Order> getOrdersWithStatusZero() {
        return orderRepository.findByStatusZero();
    }
    public List<Order> getOrdersWithStatusNotZero() {
        return orderRepository.findByStatusNotZero();
    }

    public List<Order> getOrdersWithStatusBetweenOneAndThree() {
        return orderRepository.findByStatusBetweenOneAndThree();
    }

    public List<Order> getOrdersWithStatusOutsideOneToThree(int page, int size) {
        return orderRepository.findByStatusOutsideOneToThree( PageRequest.of(page, size));
    }

    public List<Order> getOrdersWithStatusZero(Long userId ) {
        return orderRepository.findByUserIdAndStatusZero(userId);
    }

    public List<Order> getOrdersWithStatusBetweenOneAndThree(Long userId ) {
        return orderRepository.findByUserIdAndStatusBetweenOneAndThree(userId);
    }

    public List<Order> getOrdersWithStatusOutsideOneToThree(Long userId , int page, int size) {
        return orderRepository.findByUserIdAndStatusOutsideOneToThree(userId , PageRequest.of(page, size));
    }

    public List<MonthlyRevenueDTO> getMonthlyRevenue() {
        return orderRepository.findMonthlyRevenue();
    }
    public List<ProductRevenueDTO> getTop10ProductRevenueByStatusOutsideOneToThree() {
        return orderRepository.findTop10ProductsByRevenue(PageRequest.of(0, 10));
    }
}