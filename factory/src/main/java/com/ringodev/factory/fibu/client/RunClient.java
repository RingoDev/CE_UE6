package com.ringodev.factory.fibu.client;

import com.ringodev.factory.fibu.shared.Order;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RunClient {

    public static void sendOrderToFibu(Order o) throws RemoteException, NotBoundException {
        final RMIClient client = new RMIClient();
        client.startClient();

        try {
            client.getServer().sendOrder(o);
        } catch (final Exception e) {
            System.out.println("Error > " + e.getMessage());
        }
    }
}
