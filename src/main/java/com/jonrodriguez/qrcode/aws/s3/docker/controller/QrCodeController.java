package com.jonrodriguez.qrcode.aws.s3.docker.controller;


import com.jonrodriguez.qrcode.aws.s3.docker.dto.qrcode.QrCodeGenerateResponse;
import com.jonrodriguez.qrcode.aws.s3.docker.dto.qrcode.QrcodeGenerateRequest;
import com.jonrodriguez.qrcode.aws.s3.docker.services.QrCodeGenerateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/qrcode")
public class QrCodeController {



    private  final QrCodeGenerateService qrCodeGenerateService;

    public QrCodeController(QrCodeGenerateService qrCodeGenerateService){
        this.qrCodeGenerateService = qrCodeGenerateService;
    }
    /** toda requisicao que chegar no metodo post http vai puxar aqui*/
    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrcodeGenerateRequest request){
        try {
             QrCodeGenerateResponse response = this.qrCodeGenerateService.generateAndUploadQrCode(request.text());
             return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new QrCodeGenerateResponse("Error generating QR code: " + e.getMessage()));
        }
    }
}
