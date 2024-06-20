package com.barda_petrenco.shop_electronic.controllers;

import com.barda_petrenco.shop_electronic.dto.BucketDTO;
import com.barda_petrenco.shop_electronic.service.BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class BucketController {
    private final BucketService bucketService;
    private final Logger logger = LoggerFactory.getLogger(BucketController.class);

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            logger.debug("Fetching bucket for user: {}", username);
            BucketDTO bucketDTO = bucketService.getBucketByUser(username);
            if (bucketDTO != null) {
                model.addAttribute("bucket", bucketDTO);
            } else {
                model.addAttribute("bucket", new BucketDTO());
                logger.debug("Bucket is empty for user: {}", username);
            }
        } else {
            model.addAttribute("bucket", new BucketDTO());
            logger.debug("No authenticated user found.");
        }
        return "bucket";
    }

    @PostMapping("/bucket/remove/{productId}")
    public String removeProductFromBucket(@PathVariable Long productId, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal != null) {
            String username = principal.getName();
            bucketService.removeFromUserBucket(productId, username);
            logger.debug("Removed product {} from user {}'s bucket", productId, username);
            redirectAttributes.addFlashAttribute("message", "Product removed successfully.");
        } else {
            logger.warn("Unauthenticated user tried to remove product {}", productId);
            redirectAttributes.addFlashAttribute("error", "You must be logged in to remove products from the bucket.");
        }
        return "redirect:/bucket";
    }
}
