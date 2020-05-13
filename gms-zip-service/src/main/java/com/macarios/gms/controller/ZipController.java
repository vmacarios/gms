package com.macarios.gms.controller;

import com.macarios.gms.model.Zip;
import com.macarios.gms.service.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ZipController {
	private ZipService zipService;

	@Autowired
	public ZipController(ZipService zipService) {
		this.zipService = zipService;
	}


	/**
	 * GET method
	 *
	 * @return all zips on DB.
	 */
	@GetMapping("/zips")
	@ResponseBody
	List<Zip> getAllZips() {
		return zipService.findAll();
	}
//	ResponseEntity<List<Zip>> getAllZips() {
//		Both lines has the same behaviour. Choose one.
//		return new ResponseEntity<>(zipService.findAll(), HttpStatus.OK);
//		return ResponseEntity.ok().body(zipService.findAll());
//	}

	@GetMapping("/zip/{id}")
	ResponseEntity<Zip> getOneZip(@PathVariable("id") Integer id) {
		Zip zip = zipService.findById(id);
		return zip == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(zip, HttpStatus.OK);
	}

	/**
	 * The POST method create a zip entry in DB using
	 *
	 * @param zip received and
	 * @return the HttpStatus as 201 (CREATED).
	 */
	@PostMapping("/zips")
	ResponseEntity<Zip> create(@RequestBody Zip zip) {
		return new ResponseEntity<>(zipService.save(zip), HttpStatus.CREATED);
	}
}
