package com.uttesh.uploader.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.uttesh.uploader.dao.DocumentDAO;
import com.uttesh.uploader.model.Document;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploaderController {
	
	@Autowired
	private DocumentDAO documentDao;

    public DocumentDAO getDocumentDao() {
        return documentDao;
    }

    public void setDocumentDao(DocumentDAO documentDao) {
        this.documentDao = documentDao;
    }
        
        
	
	@RequestMapping("/index")
	public String index(Map<String, Object> map) {
		try {
			map.put("document", new Document());
			map.put("documentList", documentDao.list());
		}catch(Exception e) {
			e.printStackTrace();
		}

		return "documents";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			@ModelAttribute("document") Document document,
			@RequestParam("file") MultipartFile file) {
		
		
		System.out.println("Name:" + document.getName());
		System.out.println("Desc:" + document.getDescription());
		System.out.println("File:" + file.getName());
		System.out.println("ContentType:" + file.getContentType());
		
		try {
			document.setFilename(file.getOriginalFilename());
			document.setContentType(file.getContentType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			documentDao.save(document,file.getInputStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/index.html";
	}

	@RequestMapping("/download/{documentId}")
	public String download(@PathVariable("documentId")
			Integer documentId, HttpServletResponse response) {
		
		Document doc = documentDao.get(documentId);
		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" +doc.getFilename()+ "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getContentType());
                        
                       byte[] buffer = doc.getContent();
                        InputStream is = new ByteArrayInputStream(buffer);
			IOUtils.copy(is, out);
			out.flush();
			out.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
		return null;
	}

	@RequestMapping("/remove/{documentId}")
	public String remove(@PathVariable("documentId")
			Integer documentId) {
		
		documentDao.remove(documentId);
		
		return "redirect:/index.html";
	}
	
}
