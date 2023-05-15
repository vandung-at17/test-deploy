package vn.fs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.model.dto.TransactionCompleteDto;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController(value ="VNPay")
@RequestMapping("/api")
public class VNPayPaymentController {
	@GetMapping("/vnpay_result")
	public ResponseEntity<?> completeTransactionVNPayPayment (HttpServletRequest req, HttpServletResponse resp,
			@RequestParam(name = "vnp_OrderInfo", required = false) String vnp_OrderInfo,
			@RequestParam(name = "vnp_Amount", required = false) Integer vnp_Amount,
			@RequestParam(name = "vnp_BankCode", required = false) String vnp_BankCode,
			@RequestParam(name = "vnp_BankTranNo", required = false) String vnp_BankTranNo,
			@RequestParam(name = "vnp_CardType", required = false) String vnp_CardType,
			@RequestParam(name = "vnp_PayDate", required = false) String vnp_PayDate,
			@RequestParam(name = "vnp_ResponseCode", required = false) String vnp_ResponseCode,
			@RequestParam(name = "vnp_TransactionNo", required = false) String vnp_TransactionNo,
			@RequestParam(name = "vnp_TxnRef",required= false) String vnp_TxnRef
			){
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
		}else {
			//xử lý sự kiện sau khi thanh toán không thành công
			transactionCompleteDto.setStatus(false);
			transactionCompleteDto.setAmount(vnp_Amount);
			transactionCompleteDto.setBankName(vnp_BankCode);
			transactionCompleteDto.setMessage("Failed");
			transactionCompleteDto.setData("");
		}
		return ResponseEntity.status(HttpStatus.OK).body(transactionCompleteDto);
	}
}
