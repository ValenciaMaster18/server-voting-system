package com.senatic.servervotingsystem.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.senatic.servervotingsystem.model.dto.AprendizDTO;
import com.senatic.servervotingsystem.model.mapper.GenericMapper;

public interface FileHandlerService extends GenericMapper<String, MultipartFile> {
    List<AprendizDTO> readCsvToAprendizDTO(MultipartFile csv);
    boolean isFormatValid(String filename, String endsWith);
}
