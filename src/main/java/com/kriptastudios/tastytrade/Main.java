package com.kriptastudios.tastytrade;

import com.kriptastudios.tastytrade.model.Account;

public class Main {

    // La firma correcta, con (String[] args)
    public static void main(String[] args) {
        System.out.println("Probando el SDK de Tastytrade...");

        // Reemplaza con tu login real
        TastytradeClient client = new TastytradeClient("eyJhbGciOiJFZERTQSIsInR5cCI6InJ0K2p3dCIsImtpZCI6ImZ0NnFfY3hsdmp3VkJSS0FxMkNXWFoyazFudUFyRkgzNDA0QlBaLXg3UDgiLCJqa3UiOiJodHRwczovL2ludGVyaW9yLWFwaS5jaDIudGFzdHl3b3Jrcy5jb20vb2F1dGgvandrcyJ9.eyJpc3MiOiJodHRwczovL2FwaS50YXN0eXRyYWRlLmNvbSIsInN1YiI6IlU0OWI1ZDA0Zi1iM2RhLTQ4YTQtODdiNy02MTA5MDliMzNmMjEiLCJpYXQiOjE3NjE5OTgxMTYsImF1ZCI6ImVlNmFhODU0LWU1OTYtNDU5Mi1hOTRiLTA5YTJmMzQ2MzljNCIsImdyYW50X2lkIjoiRzJhMDMyZTU0LWQzOTUtNGE3Yy1hZDc5LTM1NmU2OTkwOTIyMyIsInNjb3BlIjoicmVhZCBvcGVuaWQifQ.JKqdh1G065kWbT9CGTmTtjgZkG5QiWxUQbTqHnB9mN2uYO0F6Kmo-HrFmWtJ_6cZyavQbsrSFOEPz3h50m4iAQ", "8ee1e1be954a3ad7ac5732f452cb6db4de2b8e89", true);

        try {
            // 1. Autenticarse
            client.login();

            // 2. Probar un endpoint
            Account myAccount = client.getAccount("tu-numero-de-cuenta");

            System.out.println("¡Éxito! Se ha obtenido la cuenta.");
            System.out.println("Número de cuenta: " + myAccount.getAccountNumber());
            System.out.println("Poder de compra: " + myAccount.getBuyingPower());

        } catch (Exception e) {
            System.err.println("Ha ocurrido un error al probar el cliente:");
            e.printStackTrace();
        }
    }
}