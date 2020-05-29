package com.macarios.gms.service;

import com.macarios.gms.model.Zip;
import com.macarios.gms.repository.ZipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Victor Macarios
 * @since May/2020
 * ZipService is the class responsible for interactions with the repository.
 */
@Service
public class ZipService {

	private ZipRepository zipRepository;

	public ZipService(@Autowired ZipRepository zipRepository) {
		this.zipRepository = zipRepository;
	}

	/**
	 * @return every zip entries.
	 */
	public List<Zip> findAll() {
		return zipRepository.findAll();
	}

	/**
	 * Save a zip object into DB.
	 *
	 * @param zip is the object to be saved.
	 * @return the saved object.
	 */
	public Zip save(Zip zip) {
		return zipRepository.save(zip);
	}

	/**
	 * Find a zip using the id.
	 *
	 * @param id - the desired id.
	 * @return an Optional of a zip object.
	 */
	public Optional<Zip> findById(Integer id) {
		return zipRepository.findById(id);
	}

	/**
	 * Delete a zip using the id.
	 *
	 * @param id - the desired id to be removed.
	 */
	public void deleteById(Integer id) {
		zipRepository.deleteById(id);
	}

	/**
	 * Update a zip.
	 *
	 * @param newZip - the updated zip.
	 * @return a zip body.
	 */
	public Zip update(Zip newZip) {
		return zipRepository.save(newZip);
	}
}
