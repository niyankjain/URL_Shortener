package com.io.infracloud.urlshortener.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashUtils {

  private final static Logger LOGGER = LoggerFactory.getLogger(HashUtils.class);

  public static String sha256(String input) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(hash);
    } catch (Exception e) {
      LOGGER.error("Exception occurred while going to generate Long URL Hash for input: {}, cause: {}", input,
          ExceptionUtils.getStackTrace(e));
      throw new RuntimeException(String.format("Not able to generate Short URL cause: %s", e.getMessage()));
    }
  }
}
