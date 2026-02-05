package com.io.infracloud.urlshortener.dto;

import jakarta.annotation.Nonnull;

@Nonnull
public record LongURLRecord(@Nonnull String longURL) {
}
