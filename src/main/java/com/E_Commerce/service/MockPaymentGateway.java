package com.E_Commerce.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.E_Commerce.dto.response.PaymentResponse;

@Service
public class MockPaymentGateway {

        public PaymentResponse pay(
                        Double amount) {

                PaymentResponse response = new PaymentResponse();

                response.setTransactionId(

                                UUID.randomUUID()

                                                .toString()

                );

                boolean success =

                                Math.random()

                                                > 0.3;

                response.setStatus(

                                success

                                                ?

                                                "SUCCESS"

                                                :

                                                "FAILED"

                );

                return response;

        }

}