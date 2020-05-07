package com.macarios.gms.service;

import com.macarios.gms.model.Zip;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZipService {
	public ZipService() {
	}

	public List<Zip> findAll() {
		return new ArrayList<>();
	}
}
