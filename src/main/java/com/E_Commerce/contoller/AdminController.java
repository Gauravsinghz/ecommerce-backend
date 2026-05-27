package com.E_Commerce.contoller;

import org.springframework.web.bind.annotation.*;

import com.E_Commerce.dto.response.DashboardResponse;

import com.E_Commerce.service.AdminService;

    @RestController
    @RequestMapping("/api/admin")
    public class AdminController {
        private AdminService service;
        public AdminController(AdminService service) {
            this.service =service;
        }
        @GetMapping("/dashboard")
        public DashboardResponse dashboard() {
            return service.dashboard();
        }

}