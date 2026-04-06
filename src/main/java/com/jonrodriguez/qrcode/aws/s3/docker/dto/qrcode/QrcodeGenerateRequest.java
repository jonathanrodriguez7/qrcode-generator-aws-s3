package com.jonrodriguez.qrcode.aws.s3.docker.dto.qrcode;

public record QrcodeGenerateRequest(String text) {

}
///Aqui no caso poderia ser uma string URL depende do payload também porém vai ser mais simples apenas para json
