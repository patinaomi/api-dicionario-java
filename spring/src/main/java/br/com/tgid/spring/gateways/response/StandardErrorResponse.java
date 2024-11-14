package br.com.tgid.spring.gateways.response;

public record StandardErrorResponse(Integer status, String msg, Long timeStamp) {

}
