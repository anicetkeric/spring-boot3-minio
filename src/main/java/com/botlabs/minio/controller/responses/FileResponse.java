package com.botlabs.minio.controller.responses;

public record FileResponse(String fileName, String fileType, long size) {}
