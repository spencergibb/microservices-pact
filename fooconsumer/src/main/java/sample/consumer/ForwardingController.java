package sample.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Spencer Gibb
 */
@Controller
public class ForwardingController {

	@RequestMapping("/all")
	public String all() {
		return "forward:/collaborators/all";
	}
}
