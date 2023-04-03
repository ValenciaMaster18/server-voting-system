package com.senatic.servervotingsystem.service.Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.senatic.servervotingsystem.model.dto.AprendizDTO;
import com.senatic.servervotingsystem.service.FileHandlerService;

import jakarta.xml.bind.DatatypeConverter;

@Service
public class FileHandlerServiceImpl implements FileHandlerService {

    @Override
    public boolean isFormatValid(String filename, String endsWith) {
        if (!filename.isEmpty()) {
            if (filename.endsWith(endsWith)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<AprendizDTO> readCsvToAprendizDTO(MultipartFile csv) {
        BufferedReader br;
        List<AprendizDTO> aprendicesDTO = new ArrayList<>();
        try {
            String line;
            InputStream is = csv.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                // Manipulate the line
                String[] fields = line.split(";");

                AprendizDTO dto = AprendizDTO.builder()
                        .ficha(fields[0])
                        .programa(fields[1])
                        .tipoDocumento(fields[2])
                        .numeroDocumento(fields[3])
                        .nombre(fields[4])
                        .apellido(fields[5])
                        .celular(fields[6])
                        .correoElectronico(fields[7])
                        .estado(fields[8])

                        .build();
                aprendicesDTO.add(dto);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return aprendicesDTO;
    }

    @Override
    public String dtoToPojo(MultipartFile dto) {
        String base64 = "";
        try {
            base64 = Base64.getEncoder().encodeToString(dto.getBytes());
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    @Override
    public MultipartFile pojoToDto(String pojo) {
        byte[] data = DatatypeConverter.parseBase64Binary(pojo);
        MultipartFile file = new MockMultipartFile("file.jpg", data);
        return file;
    }

}
