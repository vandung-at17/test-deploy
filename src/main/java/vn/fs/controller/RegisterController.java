package vn.fs.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.fs.entities.RoleEntity;
import vn.fs.entities.UserEntity;
import vn.fs.model.dto.UserDto;
import vn.fs.repository.UserRepository;
import vn.fs.service.IUserService;
import vn.fs.service.ISendMailService;
import vn.fs.service.impl.ReCaptchaValidationService;

/**
 * @author DongTHD
 *
 */
@Controller
public class RegisterController {
	
	@Autowired
	private ReCaptchaValidationService validator;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private IUserService userService;

	@Autowired
	ISendMailService sendMailService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	HttpSession session;

	@GetMapping("/register")
	public ModelAndView registerForm(ModelMap model) {
		model.addAttribute("user", new UserEntity());
		return new ModelAndView("web/register", model);
	}

	@PostMapping("/register")
	public String register(ModelMap model, @Validated @ModelAttribute("user") UserDto dto, BindingResult result,
			@RequestParam("password") String password,@RequestParam(name = "g-recaptcha-response") String captcha) {
		if (validator.validateCaptcha(captcha)) {
			if (result.hasErrors()) {
				return "web/register";
			}
			if (!checkEmail(dto.getEmail())) {
				model.addAttribute("error", "Email này đã được sử dụng!");
				return "web/register";
			}
			session.removeAttribute("otp");
			int random_otp = (int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
			session.setAttribute("otp", random_otp);
			String body = "<div>\r\n" + "<h3>Mã xác thực OTP của bạn là: <span style=\"color:#119744; font-weight: bold;\">"
					+ random_otp + "</span></h3>\r\n" + "</div>";
			sendMailService.queue(dto.getEmail(), "Đăng kí tài khoản", body);

			model.addAttribute("user", dto);
			model.addAttribute("message", "Employee added!!");
			model.addAttribute("message", "Mã xác thực OTP đã được gửi tới Email : " + dto.getEmail() + " , hãy kiểm tra Email của bạn!");
			return "/web/confirmOtpRegister";

			
		} else {
			model.addAttribute("message", "Please Verify Captcha");
			return "web/register";
		}
		
	}

	@PostMapping("/confirmOtpRegister")
	public ModelAndView confirmRegister(ModelMap model, @ModelAttribute("user") UserDto dto,
			@RequestParam("password") String password, @RequestParam("otp") String otp) {
		if (otp.equals(String.valueOf(session.getAttribute("otp")))) {
			dto.setPassword(bCryptPasswordEncoder.encode(password));
			dto.setRegisterDate(new Date());
			dto.setStatus(true);
			dto.setAvatar("user.png");
			Collection<RoleEntity> collection = new HashSet<RoleEntity>();
			collection.add(new RoleEntity("ROLE_USER"));
			dto.setRoles(collection);
			userService.save(dto);

			session.removeAttribute("otp");
			model.addAttribute("message", "Đăng kí thành công");
			return new ModelAndView("web/login");
		}

		model.addAttribute("user", dto);
		model.addAttribute("error", "Mã xác thực OTP không chính xác, hãy thử lại!");
		return new ModelAndView("web/confirmOtpRegister", model);
	}

	// check email
	public boolean checkEmail(String email) {
		List<UserEntity> list = userRepository.findAll();
		for (UserEntity c : list) {
			if (c.getEmail().equalsIgnoreCase(email)) {
				return false;
			}
		}
		return true;
	}

}
