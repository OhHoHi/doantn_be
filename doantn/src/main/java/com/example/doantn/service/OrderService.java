package com.example.doantn.service;

import com.example.doantn.dto.*;
import com.example.doantn.entity.*;
import com.example.doantn.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


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
    private NotificationRepository notificationRepository;




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
            // Giảm số lượng sản phẩm và cập nhật trạng thái nếu cần
            product.reduceQuantity(orderItemDTO.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem(order, product, orderItemDTO.getQuantity());
            orderItems.add(orderItem);
            orderItemRepository.save(orderItem);
        }

        order.setOrderItems(orderItems);
        return order;
    }
    public String addProductQuantity(Long productId, int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (!optionalProduct.isPresent()) {
            return "Product not found";
        }

        Product product = optionalProduct.get();
        product.setTotalQuantity(product.getTotalQuantity() + quantity);
        product.addQuantity();
        productRepository.save(product);

        return "Product quantity updated successfully";
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
        Order updatedOrder = orderRepository.save(order);

        // Tạo thông báo mới
        createAndSaveNotification(order, newStatus);

        return updatedOrder;
    }
    public Order decreaseOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        int newStatus = order.getStatus() - 1;
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
//public Order decreaseOrderStatus(Long orderId) {
//    Order order = orderRepository.findById(orderId)
//            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
//
//    // Giảm trạng thái của đơn hàng đi một đơn vị
//    int newStatus = order.getStatus() - 1;
//    order.setStatus(newStatus);
//
//    // Lấy ra tất cả các mục đơn hàng của đơn hàng
//    Set<OrderItem> orderItems = order.getOrderItems();
//
//    // In ra thông tin của orderItems để kiểm tra
//    System.out.println("OrderItems: " + orderItems);
//
//    for (OrderItem orderItem : orderItems) {
//        Product product = orderItem.getProduct();
//        int quantity = orderItem.getQuantity();
//        // Cộng trả lại số lượng sản phẩm vào kho
//        int newQuantity = product.getTotalQuantity() + quantity;
//        product.setTotalQuantity(newQuantity);
//        // Lưu thay đổi số lượng sản phẩm vào cơ sở dữ liệu
//        productRepository.save(product);
//    }
//
//    // Lưu thay đổi trạng thái và số lượng sản phẩm vào cơ sở dữ liệu và trả về đơn hàng đã được cập nhật
//    return orderRepository.save(order);
//}
    private void createAndSaveNotification(Order order, int newStatus) {
        String message;
        switch (newStatus) {
            case 1:
                message = "Đơn hàng "+ order.getId() +" của bạn đang được chuẩn bị";
                break;
            case 2:
                message = "Đơn hàng "+ order.getId() +" của bạn đã được chuyển đi";
                break;
            case 3:
                message = "Đơn hàng "+ order.getId() +" đã được giao thành công";
                break;
            default:
                if (newStatus > 3) {
                    message = "Đơn hàng "+ order.getId() +" của bạn đã được giao thành công";
                } else {
                    message = "Your order status has been updated.";
                }
                break;
        }

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setSentDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        notification.setUser(order.getUser());
        notificationRepository.save(notification);

        // Giả lập gửi thông báo (có thể gửi qua email, SMS, v.v.)
        // sendNotification(notification);
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

    public List<Order> getOrdersByStatusLessThanZeroAndUserId(Long userId) {
        return orderRepository.findByStatusLessThanZeroAndUserId(userId);
    }

    public List<Order> getOrdersByStatusLessThanZero() {
        return orderRepository.findByStatusLessThanZero();
    }

    public List<MonthlyRevenueDTO> getMonthlyRevenue() {
        return orderRepository.findMonthlyRevenue();
    }
    public List<ProductRevenueDTO> getTop10ProductRevenueByStatusOutsideOneToThree() {
        return orderRepository.findTop10ProductsByRevenue(PageRequest.of(0, 10));
    }
}