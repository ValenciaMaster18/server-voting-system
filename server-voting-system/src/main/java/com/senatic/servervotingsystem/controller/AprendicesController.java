package com.senatic.servervotingsystem.controller;

import java.util.List;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.senatic.servervotingsystem.controller.exceptionHandler.exception.EntityAlreadyExistException;
import com.senatic.servervotingsystem.controller.exceptionHandler.exception.EntityNotFoundException;
import com.senatic.servervotingsystem.controller.exceptionHandler.exception.FileNotValidException;
import com.senatic.servervotingsystem.model.dto.AprendizDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.mapper.AprendizMapper;
import com.senatic.servervotingsystem.service.AprendicesService;
import com.senatic.servervotingsystem.service.FileHandlerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/aprendices")
@RequiredArgsConstructor
@Validated
public class AprendicesController {

    private final AprendicesService aprendicesService;
    private final AprendizMapper aprendizMapper;
    private final FileHandlerService fileHandlerService;

    @GetMapping
    public ResponseEntity<Page<AprendizDTO>> handleGetAprendices(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "9") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Aprendiz> aprendices = aprendicesService.getAprendicesPaginate(paging);
        if (aprendices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Page<AprendizDTO> aprendicesDTO = new PageImpl<AprendizDTO>(aprendices.map(aprendizMapper::pojoToDto).toList());
        return ResponseEntity.status(HttpStatus.OK).body(aprendicesDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AprendizDTO>> handleSearchByExample(
            @RequestBody AprendizDTO exampleDTO,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "9") Integer size) {
        Example<Aprendiz> example = Example.of(aprendizMapper.dtoToPojo(exampleDTO));
        Pageable paging = PageRequest.of(page, size);
        Page<Aprendiz> aprendices = aprendicesService.getAprendicesPaginateByExample(paging, example);
        if (aprendices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Page<AprendizDTO> aprendicesDTO = new PageImpl<AprendizDTO>(aprendices.map(aprendizMapper::pojoToDto).toList());
        return ResponseEntity.status(HttpStatus.OK).body(aprendicesDTO);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> handleCreateAprendiz(@RequestBody AprendizDTO aprendizDTO)
            throws EntityAlreadyExistException {
        if (aprendicesService.alreadyExist(aprendizDTO)) {
            throw new EntityAlreadyExistException("APRENDIZ already exist");
        }
        Aprendiz aprendiz = aprendizMapper.dtoToPojo(aprendizDTO);
        aprendicesService.addAprendiz(aprendiz);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/csv")
    public ResponseEntity<HttpStatus> handleSaveAprendicesByCSV(@RequestBody MultipartFile csvFile)
            throws FileNotValidException {
        if (!csvFile.isEmpty()) {
            if (fileHandlerService.isFormatValid(csvFile.getOriginalFilename(), ".csv")) {
                List<AprendizDTO> aprendicesDTO = fileHandlerService.readCsvToAprendizDTO(csvFile);
                List<Aprendiz> aprendices = aprendicesDTO.stream().map(aprendizMapper::dtoToPojo).toList();
                aprendicesService.addAprendices(aprendices);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            throw new FileNotValidException("File must be in valid format");
        }
        throw new FileNotValidException("File must not be empty. Can not be readed");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> handleDeleteAprendizById(@PathVariable String id)
            throws EntityNotFoundException {
        if (!aprendicesService.alreadyExist(id)) {
            throw new EntityNotFoundException("APRENDIZ not found with id: " + id);
        }
        aprendicesService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping
    public ResponseEntity<HttpStatus> handleEditAprendiz(@RequestBody AprendizDTO aprendizDTO)
            throws EntityNotFoundException {
        if (!aprendicesService.alreadyExist(aprendizDTO)) {
            throw new EntityNotFoundException("APRENDIZ not found with id: " + aprendizDTO.getNumeroDocumento());
        }
        aprendicesService.updateAprendiz(aprendizDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
