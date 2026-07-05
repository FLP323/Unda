package it.ur3.siw.restcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.ur3.siw.model.Canzone;
import it.ur3.siw.repository.CanzoneRepository;

@RestController
@RequestMapping("/rest/canzoni")
public class CanzoneRestController {

    private final CanzoneRepository canzoneRepository;

    public CanzoneRestController(CanzoneRepository canzoneRepository) {
        this.canzoneRepository = canzoneRepository;
    }

    @GetMapping("/search")
    public List<Canzone> searchByTitle(@RequestParam String q) {
        return canzoneRepository.findByTitoloContainingIgnoreCase(q);
    }
}