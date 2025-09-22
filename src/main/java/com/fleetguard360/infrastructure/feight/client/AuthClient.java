package com.fleetguard360.infrastructure.feight.client;

import com.fleetguard360.infrastructure.feight.dto.AuthReqFeightDTO;
import com.fleetguard360.infrastructure.feight.dto.AuthResFeightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth", url = "${auth.service.url}")
public interface AuthClient {

    @PostMapping("/login")
    AuthResFeightDTO login(@RequestBody AuthReqFeightDTO request);
}
