package org.example.web;

import lombok.RequiredArgsConstructor;
import org.example.config.auth.dto.SessionUser;
import org.example.service.PostsService;
import org.example.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    /*
    * 홈 화면을 반환 - 전체 글 조회 화면을 반환
    * */
    @GetMapping("/")
    public String index(Model model){  // 리턴 값이 String이면 뷰를 찾아줌 (뷰리졸버)
        /* Posts를 모델에 담기 */
        model.addAttribute("posts", postsService.findAllDesc());    // 조회 결과를 모델에 담아서 뷰로 전달달

        /* User을 모델에 담기 */
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user!=null){
            model.addAttribute("userName", user.getName());
        }
       return "index";
    }

    /*
    * Post 등록 화면을 반환
    * */
    @GetMapping("/posts/save")
    public String PostsSave(){
        return "posts-save";
    }

    /*
    * Post 수정 화면을 반환
    * */
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
