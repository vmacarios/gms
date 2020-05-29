package com.macarios.gms.controller;

import com.macarios.gms.model.Zip;
import com.macarios.gms.service.ZipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author Victor Macarios
 * @since May/2020
 * ZipController is the class that receives data from the user and interact with the service.
 */
@RestController
public class ZipController {

	private static final Logger logger = LoggerFactory.getLogger(ZipController.class);
	private ZipService zipService;

	@Autowired
	public ZipController(ZipService zipService) {
		this.zipService = zipService;
	}


	/**
	 * The GET action retrieves all ZIP from DB.
	 *
	 * @return all zips on DB.
	 */
	@GetMapping("/zips")
	@ResponseBody
	private List<Zip> getAllZips() {
		return zipService.findAll();
	}
//	The below method shows another ways to do the same as above
//	ResponseEntity<List<Zip>> getAllZips() {
//		Both lines has the same behaviour. Choose just one.
//		return new ResponseEntity<>(zipService.findAll(), HttpStatus.OK);
//		return ResponseEntity.ok().body(zipService.findAll());
//	}

	/**
	 * The GET action uses an id to find the desired zip.
	 *
	 * @param id - The ID identifies the zip.
	 * @return a ResponseEntity with the requested body, or not found.
	 */
	@GetMapping("/zips/{id}")
	private ResponseEntity<Zip> getOneZip(@PathVariable("id") Integer id) {
		Optional<Zip> zip = zipService.findById(id);
		return zip.map(z -> new ResponseEntity<>(z, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * The POST action create a zip entry in DB.
	 *
	 * @param zip - The object to be stored.
	 * @return the HttpStatus as 201 (CREATED).
	 */
	@PostMapping("/zips")
	private ResponseEntity<Zip> create(@RequestBody Zip zip) {
		return new ResponseEntity<>(zipService.save(zip), HttpStatus.CREATED);
	}

	/**
	 * The DELETE action remove a zip entry from DB.
	 *
	 * @param id - The object to be deleted.
	 * @return the HttpStatus as 204(NO_CONTENT).
	 */
	@DeleteMapping("/zips/{id}")
	private ResponseEntity<Zip> delete(@PathVariable("id") Integer id) {
		try {
			zipService.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

	/**
	 * The PUT action updates a zip entry.
	 *
	 * @param id     - the object to be updated.
	 * @param newZip - the new object.
	 * @return a ResponseEntity with the body, or not found.
	 */
	@PutMapping("/zips/{id}")
	private ResponseEntity<Zip> update(@PathVariable("id") Integer id, @RequestBody Zip newZip) {
		Optional<Zip> updatedZip = zipService.findById(id)
				.map(zip -> {
					zip.setZip(newZip.getZip());
					zip.setAddress(newZip.getAddress());
					zip.setComp(newZip.getComp());
					zip.setNeighborhood(newZip.getNeighborhood());
					zip.setCity(newZip.getCity());
					zip.setState(newZip.getState());
					zip.setUpdatedAt(Instant.now());
					return zip;
				});
		return updatedZip.map(zip -> new ResponseEntity<>(zipService.update(zip), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
