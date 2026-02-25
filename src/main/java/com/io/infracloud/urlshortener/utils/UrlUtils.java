package com.io.infracloud.urlshortener.utils;

import java.net.URI;
import java.util.List;

public class UrlUtils {

  public static List<String> BLACKLISTED_DOMAINS = null;

  public static String extractDomain(String longUrl) {

    URI uri = URI.create(longUrl);
    String host = uri.getHost(); // www.youtube.com

    if (host == null) {
      throw new IllegalArgumentException("Invalid URL");
    }

    // remove www.
    return host.startsWith("www.") ? host.substring(4) : host;
  }

}
