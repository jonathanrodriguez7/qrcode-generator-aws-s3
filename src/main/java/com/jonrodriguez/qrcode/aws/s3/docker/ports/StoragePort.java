package com.jonrodriguez.qrcode.aws.s3.docker.ports;

public interface StoragePort {
    String upload(byte[] fileData, String fileName, String contentType);

}
