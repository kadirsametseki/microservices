package com.kodlamaio.filterService.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.filterService.business.abstracts.FilterService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/filters")
@AllArgsConstructor
public class FiltersController {
	
	private FilterService filterService;
}
