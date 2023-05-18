package vn.fs.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.commom.CommomDataService;
import vn.fs.converter.UserConverter;
import vn.fs.entities.CartItemEntity;
import vn.fs.entities.OrderDetailEntity;
import vn.fs.entities.OrderEntity;
import vn.fs.entities.UserEntity;
import vn.fs.model.dto.TransactionCompleteDto;
import vn.fs.model.dto.UserDto;
import vn.fs.repository.OrderDetailRepository;
import vn.fs.repository.OrderRepository;
import vn.fs.service.IShoppingCartService;
import vn.fs.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController(value ="VNPay")
@RequestMapping("/api")
public class VNPayPaymentController {
	@Autowired
	HttpSession session;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private CommomDataService commomDataService;
	
	@ModelAttribute(value = "user")
	public UserDto user(Model model, Principal principal, UserDto userDto, HttpServletRequest request) {
		if (principal != null) {
			model.addAttribute("userDto", new UserDto());
			session = request.getSession();
			String email = session.getAttribute("email").toString();
			String login = session.getAttribute("login").toString();
			if (login.equals("DATABASE")) {
				userDto = userService.findByEmail(principal.getName());
				if (userDto != null) {
					model.addAttribute("userDto", userDto);
				}
			}else {
				if (email != null) {
					userDto = userService.findByEmail(email);
					model.addAttribute("userDto", userDto);
				}
			}
		}
		return userDto;
	}
	
	@GetMapping("/vnpay_result")
	public String completeTransactionVNPayPayment (HttpServletRequest req, HttpServletResponse resp,UserDto userDto,
			@RequestParam(name = "vnp_OrderInfo", required = false) String vnp_OrderInfo,
			@RequestParam(name = "vnp_Amount", required = false) Integer vnp_Amount,
			@RequestParam(name = "vnp_BankCode", required = false) String vnp_BankCode,
			@RequestParam(name = "vnp_BankTranNo", required = false) String vnp_BankTranNo,
			@RequestParam(name = "vnp_CardType", required = false) String vnp_CardType,
			@RequestParam(name = "vnp_PayDate", required = false) String vnp_PayDate,
			@RequestParam(name = "vnp_ResponseCode", required = false) String vnp_ResponseCode,
			@RequestParam(name = "vnp_TransactionNo", required = false) String vnp_TransactionNo,
			@RequestParam(name = "vnp_TxnRef",required= false) String vnp_TxnRef
			) throws MessagingException{
		// Lấy thời gian khi thanh toán thành công
		String year = vnp_PayDate.substring(0, 4);
		String month = vnp_PayDate.substring(4, 6);
		String date = vnp_PayDate.substring(6, 8);
		String hour = vnp_PayDate.substring(8, 10);
		String minutes = vnp_PayDate.substring(10, 12);
		String second = vnp_PayDate.substring(12, 14);
		String timePay = date + "-" + month + "-" + year + " " + hour + ":" + minutes + ":" + second;
		TransactionCompleteDto transactionCompleteDto = new TransactionCompleteDto();
		if (vnp_ResponseCode.equals("00")) {
			//xử lý sự kiện sau khi thanh toán thành công
			transactionCompleteDto.setStatus(true);
			transactionCompleteDto.setAmount(vnp_Amount);
			transactionCompleteDto.setBankName(vnp_BankCode);
			transactionCompleteDto.setMessage("Successfully");
			transactionCompleteDto.setData("");
			Collection<CartItemEntity> cartItems = shoppingCartService.getCartItems();
			double giatri = 0;
			for (CartItemEntity cartItem : cartItems) {
				double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
				giatri += price - (price * cartItem.getProduct().getDiscount() / 100);
			}
			if ((giatri*100) != Double.valueOf(vnp_Amount)) {
				return null;
			}
			session = req.getSession();
			Date dateoder = new Date();
			OrderEntity orderFinal = new OrderEntity();
			orderFinal.setOrderDate(dateoder);
			orderFinal.setStatus(2);
			orderFinal.getOrderId();
			orderFinal.setUser(userConverter.toEntity(userDto));
			orderFinal.setAmount(Double.valueOf(vnp_Amount));
			orderRepository.save(orderFinal);
			for (CartItemEntity cartItem : cartItems) {
				OrderDetailEntity orderDetail = new OrderDetailEntity();
				orderDetail.setQuantity(cartItem.getQuantity());
				orderDetail.setOrder(orderFinal);
				orderDetail.setProduct(cartItem.getProduct());
				double unitPrice = cartItem.getProduct().getPrice();
				orderDetail.setPrice(unitPrice);
				orderDetailRepository.save(orderDetail);
			}
			commomDataService.sendSimpleEmail(userDto.getEmail(), "Greeny-Shop Xác Nhận Đơn hàng", "aaaa", cartItems,
					giatri, orderFinal);

			shoppingCartService.clear();
			session.removeAttribute("cartItems");
			return "web/checkout_success";
		}else {
			//xử lý sự kiện sau khi thanh toán không thành công
			transactionCompleteDto.setStatus(false);
			transactionCompleteDto.setAmount(vnp_Amount);
			transactionCompleteDto.setBankName(vnp_BankCode);
			transactionCompleteDto.setMessage("Failed");
			transactionCompleteDto.setData("");
			return null;
		}
		//return ResponseEntity.status(HttpStatus.OK).body(transactionCompleteDto);
	}
}
