package com.simpleblw.generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simpleblw.generator.service.PDFService;

/**
 * PDF controller.
 *
 * @author <a href="diwad1024@gmail.com">Dawid Pietryga</a>
 */
@Controller
public class PDFController {

  @Autowired
  private PDFService pdfService;

  @PostMapping("/pdf/{lang}/{name}/{title}")
  @ResponseBody
  public String generate(@RequestBody String html, @PathVariable("lang") String lang, @PathVariable("name") String name,
    @PathVariable("title") String title) {
    return pdfService.writePDF(html, lang, name, title);
  }
}
