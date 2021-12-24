package com.gh.fileserver.service;

import com.gh.fileserver.domain.File;
import com.gh.fileserver.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * File 服务.
 *
 * @since 1.0.0 2017年7月30日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	public FileRepository fileRepository;

	@Override
	public File saveFile(File file) {
		return fileRepository.save(file);
	}

	@Override
	public void removeFile(String id) {
		fileRepository.deleteById(id);
	}

	@Override
	public Optional<File> getFileById(String id) {
		return fileRepository.findById(id);
	}

	@Override
	public List<File> listFilesByPage(int pageIndex, int pageSize) {
		Page<File> page = null;
		List<File> list = null;
		
		Sort sort = Sort.by(Sort.Direction.DESC,"uploadDate");
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		
		page = fileRepository.findAll(pageable);
		list = page.getContent();
		return list;
	}
}
