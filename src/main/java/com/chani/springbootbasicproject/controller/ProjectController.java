package com.chani.springbootbasicproject.controller;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @GetMapping
    public List<Map<String, String>> projects() {
        return List.of(
                Map.of(
                        "title", "캠퍼스 커뮤니티 플랫폼",
                        "stack", "Spring Boot, JPA, MySQL",
                        "summary", "학내 공지/중고거래/스터디 모집 기능 구현"
                ),
                Map.of(
                        "title", "개인 포트폴리오 사이트",
                        "stack", "Spring Boot, Thymeleaf, AWS",
                        "summary", "자기소개/프로젝트/연락처 페이지 제공"
                )
        );
    }
}
