package com.example.cosmetic_store.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomXmlMessageConverter extends MappingJackson2XmlHttpMessageConverter {

    private static final String XSLT_PROCESSING_INSTRUCTION = 
        "<?xml-stylesheet type=\"text/xsl\" href=\"/xsl/products.xsl\"?>";

    public CustomXmlMessageConverter() {
        super(new XmlMapper());
        setSupportedMediaTypes(List.of(
            MediaType.APPLICATION_XML,
            new MediaType("application", "xml", StandardCharsets.UTF_8)
        ));
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        
        String xml = super.getXmlMapper().writeValueAsString(object);
        
        // XSLT processing instruction
        if (xml.startsWith("<?xml")) {
            int endOfFirstLine = xml.indexOf("?>") + 2;
            String processedXml = xml.substring(0, endOfFirstLine) + 
                                "\n" + XSLT_PROCESSING_INSTRUCTION + 
                                xml.substring(endOfFirstLine);
            outputMessage.getBody().write(processedXml.getBytes(StandardCharsets.UTF_8));
        } else {
            String processedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                XSLT_PROCESSING_INSTRUCTION + "\n" + xml;
            outputMessage.getBody().write(processedXml.getBytes(StandardCharsets.UTF_8));
        }
    }
}