package it.ur3.siw.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.ur3.siw.model.Utente;
import it.ur3.siw.model.enums.UserRole;
import it.ur3.siw.repository.UtenteRepository;

@Controller
public class HomeController {

	private UtenteRepository utenteRepository;
	private PasswordEncoder passwordEncoder;

	public HomeController(UtenteRepository utenteService, PasswordEncoder passwordEncoder) {
		super();
		this.utenteRepository = utenteService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "authentication/login";
	}

	@GetMapping("/register")
	public String register() {
		return "authentication/register";
	}

	@PostMapping("/register")
	public String processRegister(@ModelAttribute("username") String username,
			@ModelAttribute("password") String password, Model model) {
		if (utenteRepository.findByUsername(username).isPresent()) {
			model.addAttribute("error", "Username già in uso");
			return "authentication/register";
		}

		Utente nuovoUtente = new Utente();
		nuovoUtente.setUsername(username);
		nuovoUtente.setPassword(passwordEncoder.encode(password));
		nuovoUtente.setRole(UserRole.ROLE_USER);

		utenteRepository.save(nuovoUtente);

		return "redirect:/login?registered=true";
	}
}
