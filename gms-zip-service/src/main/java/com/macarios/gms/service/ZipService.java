package com.macarios.gms.service;

import com.macarios.gms.model.Zip;
import com.macarios.gms.repository.ZipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for the interactions with the repository
 */
@Service
public class ZipService {

	private ZipRepository zipRepository;

	public ZipService(@Autowired ZipRepository zipRepository) {
		this.zipRepository = zipRepository;
	}

	/**
	 *
	 * @return every zip entries
	 */
	public List<Zip> findAll() {
		return zipRepository.findAll();
	}

	/**
	 *
	 * @param zip is the object to be saved
	 * @return the saved object
	 */
	public Zip save(Zip zip) {
		return zipRepository.save(zip);
	}

	public Optional<Zip> findById(Integer id) {
		return zipRepository.findById(id);
	}
}
