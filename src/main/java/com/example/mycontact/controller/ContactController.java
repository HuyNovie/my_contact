// Xử lý các HTTP request từ client, gọi service tương ứng và trả về response.

package com.example.mycontact.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.mycontact.entities.Contact;
import com.example.mycontact.service.ContactService;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;

@Controller

public class ContactController {
	
	@Autowired
	private ContactService contactService;

	@RolesAllowed({"ADMIN","USER"})
	 @RequestMapping(value = {"/contact" }, method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("contact" , contactService.findAll());//Gọi contactService.findAll() để lấy tất cả liên hệ
		return "contactPage";
		
	}
	@RolesAllowed({"ADMIN","USER"})
	@GetMapping("/contact/search")
	public String search(@RequestParam("name") String name, Model model) {
		
		if(StringUtils.isEmpty(name)) {
			return "redirect:/contact";

		}
		model.addAttribute("contact" , contactService.search(name));
		return "contactPage";
	}

	@RolesAllowed({"ADMIN"})
	@GetMapping("contact/add")
	public String add(Model model) {
		model.addAttribute("contact", new Contact());
		return "form";
	}
	@RolesAllowed({"ADMIN"})
	//Gửi form POST để lưu liên hệ (mới hoặc chỉnh sửa)
	@PostMapping("/contact/save")
	public String save(@Valid Contact contact, BindingResult result, RedirectAttributes redirect) {
		
		if(result.hasErrors()) {
			return "form";
		}
		contactService.save(contact);
		redirect.addFlashAttribute("successMessage", "Saved contact successfuly!");
		return "redirect:/contact";
	}
	@RolesAllowed({"ADMIN"})
	@GetMapping("/contact/{id}/edit")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("contact", contactService.findById(id));
		return "form";
	}
	@RolesAllowed({"ADMIN"})
	//POST request để đảm bảo không xoá bằng link GET (an toàn hơn)
	@PostMapping("/contact/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		contactService.delete(id);
		redirect.addFlashAttribute("successMessage", "Delete contact successfuly!");
		return "redirect:/contact";
	}

}
