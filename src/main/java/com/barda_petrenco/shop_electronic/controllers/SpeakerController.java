package com.barda_petrenco.shop_electronic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpeakerController {

    @GetMapping("/speaker")
    public String showSpeaker1Page() {
        return "speaker";
    }

    @GetMapping("/speaker1")
    public String showSpeaker2Page() {
        return "speaker1";
    }

    @GetMapping("/speaker3")
    public String showSpeaker3Page() {
        return "speaker3";
    }
}