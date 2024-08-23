package cloud4.team4.travelog.domain.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/main")
@Controller
public class BoardController {

    @GetMapping
    public String getMainPage(Model model) {
        return "main";
    }
}
