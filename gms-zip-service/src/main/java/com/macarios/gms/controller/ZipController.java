package com.macarios.gms.controller;

import com.macarios.gms.model.Zip;
import com.macarios.gms.service.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ZipController {
	private ZipService zipService;

	public ZipController() {
	}

	@Autowired
	public ZipController(ZipService zipService) {
		this.zipService = zipService;
	}

	@GetMapping("/zips")
	@ResponseBody
	List<Zip> getAllZips(){
		return zipService.findAll();
	}
//	ResponseEntity<List<Zip>> getAllZips() {
//		Both lines has the same behaviour. Choose one.
//		return new ResponseEntity<>(zipService.findAll(), HttpStatus.OK);
//		return ResponseEntity.ok().body(zipService.findAll());
//	}
}
