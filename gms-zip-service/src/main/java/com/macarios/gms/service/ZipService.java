package com.macarios.gms.service;

import com.macarios.gms.model.Zip;
import com.macarios.gms.repository.ZipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZipService {
	private ZipRepository zipRepository;

	public ZipService() {
	}

	public ZipService(ZipRepository zipRepository) {
		this.zipRepository = zipRepository;
	}

	public List<Zip> findAll() {
		return zipRepository.findAll();
	}
}
