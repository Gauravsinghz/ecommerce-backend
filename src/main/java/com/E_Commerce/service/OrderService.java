package com.E_Commerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_Commerce.dto.request.ApplyCouponRequest;
import com.E_Commerce.dto.request.CreateOrderRequest;
import com.E_Commerce.dto.request.OrderStatusRequest;
import com.E_Commerce.dto.request.PaymentRequest;
import com.E_Commerce.dto.response.OrderDetailsResponse;
import com.E_Commerce.dto.response.OrderResponse;
import com.E_Commerce.dto.response.PaymentResponse;
import com.E_Commerce.entity.Address;
import com.E_Commerce.entity.Cart;
import com.E_Commerce.entity.Coupon;
import com.E_Commerce.entity.Order;
import com.E_Commerce.entity.OrderItem;
import com.E_Commerce.entity.OrderStatus;
import com.E_Commerce.entity.Product;
import com.E_Commerce.entity.User;
import com.E_Commerce.exception.InvalidCouponException;
import com.E_Commerce.exception.MinimumAmountNotMetException;
import com.E_Commerce.exception.OrderAlreadyDeliveredException;
import com.E_Commerce.exception.OrderNotFoundException;
import com.E_Commerce.exception.OutOfStockException;
import com.E_Commerce.exception.PaymentFailedException;
import com.E_Commerce.exception.UserNotFoundException;
import com.E_Commerce.repository.CartRepository;
import com.E_Commerce.repository.CouponRepository;
import com.E_Commerce.repository.OrderItemRepository;
import com.E_Commerce.repository.OrderRepository;
import com.E_Commerce.repository.ProductRepository;
import com.E_Commerce.repository.UserRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final CouponRepository couponRepository;

    @Autowired
    private MockPaymentGateway gateway;

    public OrderService(OrderRepository repository, UserRepository userRepository,
            CartRepository cartRepository, ProductRepository productRepository,
            OrderItemRepository orderItemRepository, CouponRepository couponRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.couponRepository = couponRepository;
    }

    public OrderResponse create(CreateOrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Cart> cartItems = cartRepository.findByUser_Id(user.getId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Address address = cartItems.get(0).getSelectedAddress();
        if (address == null) {
            throw new RuntimeException("Select address before checkout");
        }

        double total = 0;
        for (Cart cart : cartItems) {
            Product product = cart.getProduct();
            int remaining = product.getQuantity() - cart.getQuantity();
            if (remaining < 0) {
                throw new OutOfStockException("Out of stock: " + product.getName());
            }
            product.setQuantity(remaining);
            productRepository.save(product);
            total += product.getPrice() * cart.getQuantity();
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(total);
        order.setAddress(address);
        Order saved = repository.save(order);

        for (Cart cart : cartItems) {
            OrderItem item = new OrderItem();
            item.setOrder(saved);
            item.setProduct(cart.getProduct());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getProduct().getPrice());
            orderItemRepository.save(item);
        }

        cartRepository.deleteAll(cartItems);

        return toResponse(saved);
    }

    public OrderDetailsResponse get(Long orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        List<OrderItem> items = orderItemRepository.findByOrder_Id(orderId);
        OrderDetailsResponse response = new OrderDetailsResponse();
        response.setOrderId(order.getId());
        response.setUser(order.getUser().getFullName());
        response.setTotal(order.getTotalPrice());
        List<OrderDetailsResponse.Item> result = new ArrayList<>();
        for (OrderItem item : items) {
            OrderDetailsResponse.Item i = new OrderDetailsResponse.Item();
            i.setProduct(item.getProduct().getName());
            i.setQuantity(item.getQuantity());
            i.setPrice(item.getPrice());
            result.add(i);
        }
        response.setItems(result);
        return response;
    }

    public OrderResponse updateStatus(Long orderId, OrderStatusRequest request) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        OrderStatus newStatus = OrderStatus.valueOf(request.getStatus().toUpperCase());
        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new OrderAlreadyDeliveredException("Delivered order cannot be updated");
        }
        if (newStatus == OrderStatus.CANCELLED) {
            List<OrderItem> items = orderItemRepository.findByOrder_Id(orderId);
            for (OrderItem item : items) {
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        }
        order.setStatus(newStatus);
        return toResponse(repository.save(order));
    }

    public OrderResponse applyCoupon(ApplyCouponRequest request) {
        Order order = repository.findById(request.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new OrderAlreadyDeliveredException("Coupon cannot be applied to a " + order.getStatus() + " order");
        }
        Coupon coupon = couponRepository.findByCode(request.getCoupon())
                .orElseThrow(() -> new InvalidCouponException("Coupon invalid"));
        if (order.getTotalPrice() < coupon.getMinimumAmount()) {
            throw new MinimumAmountNotMetException("Minimum amount not met");
        }
        double discounted = order.getTotalPrice() - coupon.getDiscount();
        double tax = discounted * 0.18;
        double shipping = discounted > 100000 ? 0 : 200;
        double finalAmount = discounted + tax + shipping;
        order.setTotalPrice(discounted);
        order.setTax(tax);
        order.setShipping(shipping);
        order.setFinalAmount(finalAmount);
        return toResponse(repository.save(order));
    }

    public OrderResponse pay(PaymentRequest request) {
        Order order = repository.findById(request.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        PaymentResponse payment = gateway.pay(order.getFinalAmount());
        if (!"SUCCESS".equals(payment.getStatus())) {
            throw new PaymentFailedException("Payment failed");
        }
        order.setStatus(OrderStatus.PAID);
        return toResponse(repository.save(order));
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse r = new OrderResponse();
        r.setId(order.getId());
        r.setUserName(order.getUser().getFullName());
        r.setAddress(order.getAddress() != null ? order.getAddress().getLine1() : null);
        r.setTotalPrice(order.getTotalPrice());
        r.setStatus(order.getStatus());
        r.setTax(order.getTax());
        r.setShipping(order.getShipping());
        r.setFinalAmount(order.getFinalAmount());
        return r;
    }
}
