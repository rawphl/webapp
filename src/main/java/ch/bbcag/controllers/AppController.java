package ch.bbcag.controllers;

import ch.bbcag.models.ApplicationUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class AppController extends BaseController {
    public ModelAndView index() {
        ApplicationUser user = getCurrentUser();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
