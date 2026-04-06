package com.jonrodriguez.qrcode.aws.s3.docker.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jonrodriguez.qrcode.aws.s3.docker.dto.qrcode.QrCodeGenerateResponse;
import com.jonrodriguez.qrcode.aws.s3.docker.ports.StoragePort;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class QrCodeGenerateService {
    private final StoragePort storage;

    public QrCodeGenerateService(StoragePort storage) {
        this.storage = storage;
    }
    public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] PngQrCodeData = pngOutputStream.toByteArray();

        // Gerar um nome de arquivo único para o QR code
        String fileName = "qrcode_" + System.currentTimeMillis() + ".png";

        // Fazer upload do QR code para o S3 e obter a URL
        String url = storage.upload(PngQrCodeData, UUID.randomUUID().toString(), "image/png");

        return new QrCodeGenerateResponse(url);
    }
}