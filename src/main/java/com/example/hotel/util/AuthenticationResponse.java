package com.example.hotel.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(

    @JsonProperty("access_token") String accessToken,
    @JsonProperty("refresh_token") String refreshToken

) {}
