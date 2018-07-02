package com.github.sjacek.swagger_demo.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;

import static com.github.sjacek.swagger_demo.config.SwaggerConfig.V01;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@ApiModel(description = "LMD: NowOutput")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-06-28T17:46:29.455+02:00")
class NowOutput {
    @JsonProperty("dt")
    private OffsetDateTime dt;

    @ApiModelProperty(required = true, value = "")
    public OffsetDateTime getDt() {
        return dt;
    }

    public void setDt(OffsetDateTime dt) {
        this.dt = dt;
    }
}

@RestController
@RequestMapping(value = "/" + V01, produces = TEXT_PLAIN_VALUE)
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String ZONE_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    @GetMapping("/hello")
    String hello() {
        return "Hello, world!";
    }

    @GetMapping(value = "/now", produces = APPLICATION_JSON_VALUE)
    NowOutput now() {
        NowOutput ret = new NowOutput();
        ret.setDt(OffsetDateTime.now());
//        String now = OffsetDateTime.now().format(ISO_DATE_TIME);
        logger.debug(ret.getDt().toString());
        return ret;
    }

    @GetMapping("/parse_date/{dt}")
    String parseDate(@PathVariable("dt") OffsetDateTime dt) {
        String s = dt.format(ofPattern(ZONE_DATE_TIME));
        logger.debug(s);
        return s;
    }

    @GetMapping("/send_text/{text}")
    String sendText(@PathVariable("text") String text) {
        logger.debug(text);
        return text + "<br>OK";
    }

    @Setter
    private static class SendDateOutput {
        String ret;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "/send_date")
    SendDateOutput sendDate(@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DATE_TIME) OffsetDateTime date) {
        logger.debug(date.toString());
        SendDateOutput ret = new SendDateOutput();
        ret.setRet(date + "<br>OK");
        return ret;
    }
}
