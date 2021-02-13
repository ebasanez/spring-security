package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Possession;
import com.example.demo.model.User;
import com.example.demo.repository.PossessionRepository;
import com.example.demo.service.MyAclPermissionService;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-13
 */

@RestController
@RequestMapping("/possessions")
@RequiredArgsConstructor
public class PossessionController{

	private final PossessionRepository possessionRepository;

	private final MyAclPermissionService permissionService;

	@PostAuthorize("hasPermission(returnObject, 'READ')")
	@GetMapping("/{id}")
	@ResponseBody
	public Possession get(@PathVariable Integer id){
		return possessionRepository.findById(id).orElse(null);
	}

	@PostMapping
	public ModelAndView create(@Valid CreatePossessionInputDto dto, Authentication authentication){
		final Possession possession = possessionRepository.save(toEntity(dto));
		permissionService.addPermissionForUser(possession, BasePermission.ADMINISTRATION, authentication.getName());
		return new ModelAndView("redirect:/user?message?Possession created with id " + possession.getId());
	}


	@Data
	public static class CreatePossessionInputDto{
		private Integer owner;
		private String name;
	}

	@Getter
	@Builder
	public static class PossessionOutputDto{
		private final Integer id;
		private final String name;
		private final Integer owner;

	}

	private Possession toEntity(CreatePossessionInputDto inputDto){
		User owner = new User();
		owner.setId(inputDto.owner);
		Possession possession = new Possession();
		possession.setOwner(owner);
		possession.setName(inputDto.name);
		return possession;
	}

}
